package com.cvlh.util;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.deeplearning4j.zoo.PretrainedType;
import org.deeplearning4j.zoo.ZooModel;
import org.deeplearning4j.zoo.model.ResNet50;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cvlh.util.Constant.OPENCV_EYES_PRETRAINED_MODEL;
import static com.cvlh.util.Constant.OPENCV_FACE_PRETRAINED_MODEL;

/**
 *
 */
public class ImageUtil {

    private static final int DEFAULT_WIDTH = 64;
    private static final int DEFAULT_HEIGHT = 128;

    /**
     * detect all appeared faces given an image file path
     *
     * @param imagePath
     * @return
     */
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static ArrayList<Mat> detectFaces(String imagePath) {
        ArrayList<Mat> matList = new ArrayList<>();
        CascadeClassifier faceDetector = new CascadeClassifier(
                OPENCV_FACE_PRETRAINED_MODEL);
        Mat image = Imgcodecs.imread(imagePath);

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));

        for (Rect rect : faceDetections.toArray()) {
            Mat dstImage = image.submat(rect);
            Imgproc.resize(dstImage, dstImage, new Size(64, 64));
            ArrayList<double[]> arrayList = detectEyes(dstImage);
            double angle = 0.0d;
            if (arrayList.size() == 2) {
                angle = calculateAngle(arrayList.get(0), arrayList.get(1));
            }
            System.out.println("angle is " + angle);
            Mat rotatedFace = rotateFace(dstImage, angle); // face after alignment
//            Imgcodecs.imwrite("D:\\TestFace\\output\\4.png", rotatedFace);

            matList.add(rotatedFace);
        }

        return matList;
    }

    /**
     * @param faceImagePath
     * @return
     */
    public static HashMap<Integer, Rect[]> detectFaceCoordinate(String faceImagePath) {
        CascadeClassifier faceDetector = new CascadeClassifier(
                OPENCV_FACE_PRETRAINED_MODEL);
        Mat image = Imgcodecs.imread(faceImagePath);
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));
        HashMap<Integer, Rect[]> hashMap = new HashMap<>();
        hashMap.put(faceDetections.toArray().length, faceDetections.toArray());

        return hashMap;
    }

    /**
     * detect eyes' location
     *
     * @param faceMat
     */
    public static ArrayList<double[]> detectEyes(Mat faceMat) {
        MatOfRect eyeDetections = new MatOfRect();
        CascadeClassifier eyeDetector = new CascadeClassifier(
                OPENCV_EYES_PRETRAINED_MODEL);
        eyeDetector.detectMultiScale(faceMat, eyeDetections, 1.1, 2, 0,
                new Size(1, 1), new Size());

        ArrayList<double[]> arrayList = new ArrayList<>();
        for (Rect rect : eyeDetections.toArray()) {
            double[] eyesLocation = new double[2];
            eyesLocation[0] = rect.x;
            eyesLocation[1] = rect.y;
            arrayList.add(eyesLocation);
        }

        return arrayList;
    }

    /**
     * extract HOG features of face image mat
     *
     * @param mat face mat
     * @return hog features
     */
    @Deprecated
    public static float[] hogFeature(Mat mat) {
//        mat = rotateFace(mat, calculateAngle(detectEyes(mat).get(0), detectEyes(mat).get(1)));
        Imgproc.resize(mat, mat, new Size(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        HOGDescriptor hogDescriptor = new HOGDescriptor();
        MatOfFloat matOfFloat = new MatOfFloat();
        MatOfPoint locations = new MatOfPoint();
        hogDescriptor.compute(mat, matOfFloat, new Size(0, 0), new Size(0, 0),
                locations);
        int size = (int) (matOfFloat.total() * matOfFloat.channels());
        float[] temp = new float[size];
        matOfFloat.get(0, 0, temp);

        return temp;
    }


    /**
     * sigmoid-like normalization method
     * (optional)
     *
     * @param x
     * @return
     */
    @Deprecated
    public static double rectifiedSigmoid(double x) {
        double value = (1 / (1 + Math.pow(Math.E, -50 * (x - 0.78))));

        return value * 100;
    }

    /**
     * polynomial normalization
     *
     * @param x
     * @return
     */
    @Deprecated
    public static double polynomialNormalize(double x) {
        double threshold = 0.8;
        double value = x > threshold ? x : Math.pow(x, 2);

        return value * 100;
    }

    /**
     * calculate face lean angle
     *
     * @param point1
     * @param point2
     * @return
     */
    public static double calculateAngle(double[] point1, double[] point2) {
        double angle;
        if (point2[0] > point1[0] && point2[1] > point1[1]) {
            angle = Math
                    .atan((point2[1] - point1[1]) / (point2[0] - point1[0]));
        } else if (point2[0] > point1[0] && point2[1] < point1[1]) {
            angle = Math
                    .atan((point1[1] - point2[1]) / (point2[0] - point1[0]));
        } else if (point2[0] < point1[0] && point2[1] > point1[1]) {
            angle = Math
                    .atan((point2[1] - point1[1]) / (point1[0] - point2[0]));
        } else if (point2[0] < point1[0] && point2[1] < point1[1]) {
            angle = Math
                    .atan((point1[1] - point2[1]) / (point1[0] - point2[0]));
        } else {
            angle = 0.0d;
        }

        return -angle;
    }

    /**
     * rotate face
     *
     * @param faceMat
     * @return
     */
    public static Mat rotateFace(Mat faceMat, double angle) {
        Mat rotationMatrix = Imgproc.getRotationMatrix2D(new Point(
                DEFAULT_WIDTH, DEFAULT_HEIGHT), angle, 1);
        Size size = new Size(faceMat.cols(), faceMat.cols());
        Imgproc.warpAffine(faceMat, faceMat, rotationMatrix, size);

        return faceMat;
    }

    /**
     * compare the cosine similarity of two face images
     *
     * @param faceFile1
     * @param faceFile2
     * @return
     * @version V1.1
     */
    public static HashMap<String, Object> faceCompare(String faceFile1, String faceFile2) throws IOException {
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<Mat> faceMatList1 = ImageUtil.detectFaces(faceFile1);
        ArrayList<Mat> faceMatList2 = ImageUtil.detectFaces(faceFile2);

        if (faceMatList1.size() < 1 && faceMatList2.size() < 1) {
            result.put("desc", "No faces are detected!");
            result.put("similarity", 0);

            return result;
        } else {
            INDArray indArray1 = ImageUtil.faceNetFeature(faceMatList1.get(0));
            INDArray indArray2 = ImageUtil.faceNetFeature(faceMatList2.get(0));
            double cosineSim = Transforms.cosineSim(indArray1, indArray2);
            System.out.println("similarity is " + cosineSim);

            if (cosineSim >= 0.85d) {
                result.put("desc", "high");
                result.put("similarity", cosineSim);
            } else if (cosineSim >= 0.50d && cosineSim < 0.85d) {
                result.put("desc", "medium");
                result.put("similarity", cosineSim);
            } else if (cosineSim < 0.5d) {
                result.put("desc", "low");
                result.put("similarity", cosineSim);
            }

            return result;
        }
    }

    /**
     * extract deep convolution networks' top layers feature map and reshape it as facial feature vector
     *
     * @param faceRegionMat
     * @throws IOException
     */
    public static INDArray faceNetFeature(Mat faceRegionMat) throws IOException {
        if (faceRegionMat.size().height != 224 && faceRegionMat.size().width != 224) {
            Imgproc.resize(faceRegionMat, faceRegionMat, new Size(224, 224));
        }
        if (!new File(Constant.PRETRAINED_MODEL_VGGFACE).exists()) {
            ZooModel zooModel = new ResNet50();
            zooModel.initPretrained(PretrainedType.VGGFACE);
        }
        File locationToSave = new File(Constant.PRETRAINED_MODEL_VGGFACE);
        ComputationGraph vgg16 = ModelSerializer.restoreComputationGraph(locationToSave);
        NativeImageLoader loader = new NativeImageLoader(224, 224, 3, true);
//        INDArray image = loader.asMatrix(faceMat);   // why this not work????
        Imgcodecs.imwrite("1.jpg", faceRegionMat);
        INDArray image = loader.asMatrix(new File("1.jpg"));
        DataNormalization scaler = new VGG16ImagePreProcessor();
        scaler.transform(image);
        Map<String, INDArray> map = vgg16.feedForward(image, false);
        new File("1.jpg").delete();

        return map.get("fc6");
    }

    public static void main(String[] args) {
        try {
            /*INDArray indArray1 = faceNetFeature(Imgcodecs.imread("D:\\TestFace\\output\\1.png"));
            INDArray indArray2 = faceNetFeature(Imgcodecs.imread("D:\\TestFace\\output\\2.png"));
            double cosSim = Transforms.cosineSim(indArray1, indArray2);
            System.out.println("similarity is " + cosSim);*/
            faceCompare("C:\\Users\\29140\\Desktop\\Face_Report\\Beauty-Faces\\2016301110085.jpg",
                    "C:\\Users\\29140\\Desktop\\Face_Report\\Beauty-Faces\\2016305120047.jpg").forEach((s, o) -> {
                System.out.println(s + " : " + o);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}