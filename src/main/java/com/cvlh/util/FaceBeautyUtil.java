package com.cvlh.util;

import com.cvlh.entity.FaceAttractiveness;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.datavec.api.io.filters.BalancedPathFilter;
import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.io.labels.PathLabelGenerator;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.split.InputSplit;
import org.datavec.image.loader.BaseImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.datavec.image.transform.FlipImageTransform;
import org.datavec.image.transform.ImageTransform;
import org.datavec.image.transform.PipelineImageTransform;
import org.datavec.image.transform.RandomCropTransform;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.LearningRatePolicy;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * this util is designed for face beauty prediction based on SCTU-FBP benchmark
 * http://www.hcii-lab.net/data/SCUT-FBP/CN/download.html
 */
public class FaceBeautyUtil {

    private static final String ATTRACTIVENESS_LABEL_EXCEL_PATH = "E:\\DataSet\\Face\\SCUT-FBP\\Rating_Collection\\AttractivenessLabel.xlsx";
    private static final Logger log = LoggerFactory.getLogger(FaceBeautyUtil.class);
    private static final int IMAGE_WIDTH = 128;
    private static final int IMAGE_HEIGHT = 128;
    private static final long seed = 12345;
    private static final Random randNumGen = new Random(seed);
    private static final String IMAGE_FACE_DIR = "E:\\DataSet\\Face\\SCUT-FBP\\Faces";


    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * load label Excel file
     *
     * @param attractivenessLabelExcelPath
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static ArrayList<FaceAttractiveness> loadAttractivenessLabel(String attractivenessLabelExcelPath) throws IOException, InvalidFormatException {
        ArrayList<FaceAttractiveness> faceAttractivenessArrayList = new ArrayList<>();
        Workbook wb = WorkbookFactory.create(new File(attractivenessLabelExcelPath));
        Sheet sheet = wb.getSheetAt(0);
        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            FaceAttractiveness faceAttractiveness = new FaceAttractiveness((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getNumericCellValue());
            faceAttractivenessArrayList.add(faceAttractiveness);
        }

        return faceAttractivenessArrayList;
    }

    /**
     * run opencv face detector and save them to the same size
     *
     * @param faceImageDir
     */
    private static void detectAndCropFace(String faceImageDir, String destDir) {
        CascadeClassifier faceDetector = new CascadeClassifier(ImageUtil.OPENCV_FACE_PRETRAINED_MODEL);

        File tempFile = new File(destDir);
        if (!tempFile.exists() || !tempFile.isDirectory())
            tempFile.mkdirs();

        for (File file : new File(faceImageDir).listFiles()) {
            Mat image = Imgcodecs.imread(file.getAbsolutePath());
            MatOfRect faceDetections = new MatOfRect();
            faceDetector.detectMultiScale(image, faceDetections);

            System.out.println(String.format("Detected %s faces",
                    faceDetections.toArray().length));
            if (faceDetections.toArray().length > 0) {
                Mat faceMat = image.submat(faceDetections.toArray()[0]);
                Imgproc.resize(faceMat, faceMat, new Size((double) FaceBeautyUtil.IMAGE_WIDTH, (double) FaceBeautyUtil.IMAGE_HEIGHT));
                Imgcodecs.imwrite(destDir + File.separator + file.getName().split("/")[file.getName().split("/").length - 1], faceMat);
            }
        }
        log.info("All face images have been written!!");
    }

    public static void preProcessData() throws IOException, InterruptedException {
        // Instantiating RecordReader. Specify height and width of images.
        log.info("==========preparing for training data and test data=================");
        String[] allowedExtensions = BaseImageLoader.ALLOWED_FORMATS;
        FileSplit filesInDir = new FileSplit(new File(FaceBeautyUtil.IMAGE_FACE_DIR), allowedExtensions, randNumGen);
        ParentPathLabelGenerator labelMaker = new ParentPathLabelGenerator();
        BalancedPathFilter pathFilter = new BalancedPathFilter(randNumGen, allowedExtensions, labelMaker);
        InputSplit[] filesInDirSplit = filesInDir.sample(pathFilter, 80, 20);
        InputSplit trainData = filesInDirSplit[0];
        InputSplit testData = filesInDirSplit[1];

        ImageRecordReader recordReader = new ImageRecordReader(FaceBeautyUtil.IMAGE_HEIGHT, FaceBeautyUtil.IMAGE_HEIGHT, 3, labelMaker);
        ImageTransform transform = new FlipImageTransform(1);
        recordReader.initialize(trainData, transform);
        recordReader.initialize(testData, transform);
        int outputNum = recordReader.numLabels();
        DataSetIterator dataIter = new RecordReaderDataSetIterator(recordReader, 64, 1, outputNum);

        // normalize data to 0~1 instead of 0~255
        DataNormalization scaler = new ImagePreProcessingScaler(0, 1);
        scaler.fit(dataIter);
        dataIter.setPreProcessor(scaler);

        log.info("==========data pre-processing is done===============================");

        while (dataIter.hasNext()) {
            DataSet ds = dataIter.next();
            System.out.println(ds);
            try {
                Thread.sleep(1000);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void train(double lr, int nEpochs, INDArray trainData, INDArray testData) throws IOException, InterruptedException {
        // learning rate schedule in the form of <Iteration #, Learning Rate>
        Map<Integer, Double> lrSchedule = new HashMap<>();
        lrSchedule.put(0, 0.01);
        lrSchedule.put(1000, 0.005);
        lrSchedule.put(3000, 0.001);

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(123)
                .iterations(nEpochs) // Training iterations as above
                .regularization(true).l2(0.0005)
                /*
                    Uncomment the following for learning decay and bias
                 */
                .learningRate(.01)//.biasLearningRate(0.02)
                /*
                    Alternatively, you can use a learning rate schedule.
                    NOTE: this LR schedule defined here overrides the rate set in .learningRate(). Also,
                    if you're using the Transfer Learning API, this same override will carry over to
                    your new model configuration.
                */
                .learningRateDecayPolicy(LearningRatePolicy.Schedule)
                .learningRateSchedule(lrSchedule)
                /*
                    Below is an example of using inverse policy rate decay for learning rate
                */
                .weightInit(WeightInit.XAVIER)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(Updater.NESTEROVS) //To configure: .updater(new Nesterovs(0.9))
                .regularization(true).l2(1e-4) //L2 regularization
                .list()
                .layer(0, new ConvolutionLayer.Builder(5, 5)
                        //nIn and nOut specify depth. nIn here is the nChannels and nOut is the number of filters to be applied
                        .nIn(3)
                        .stride(1, 1)
                        .nOut(20)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(2, new ConvolutionLayer.Builder(5, 5)
                        //Note that nIn need not be specified in later layers
                        .stride(1, 1)
                        .nOut(50)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(4, new DenseLayer.Builder().activation(Activation.RELU)
                        .nOut(500).build())
                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(50)
                        .activation(Activation.SOFTMAX)
                        .build())
                .setInputType(InputType.convolutionalFlat(28, 28, 1)) //See note below
                .backprop(true).pretrain(false).build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();


        log.info("Train model....");
        model.setListeners(new ScoreIterationListener(1));
        for (int i = 0; i < nEpochs; i++) {
            model.fit(trainData);
            log.info("*** Completed epoch {} ***", i);

            log.info("Evaluate model....");
//            Evaluation eval = model.evaluate(mnistTest);
//            log.info(eval.stats());
//            mnistTest.reset();
        }
        log.info("****************Example finished********************");


    }

    public static void main(String[] args) throws IOException, InterruptedException {
        preProcessData();
    }
}
