package com.cvlh.util;

import org.apache.commons.io.FileUtils;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.trainedmodels.Utils.ImageNetLabels;
import org.deeplearning4j.util.ModelSerializer;
import org.deeplearning4j.zoo.PretrainedType;
import org.deeplearning4j.zoo.ZooModel;
import org.deeplearning4j.zoo.model.ResNet50;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * a toolbox for machine learning
 */
public class MLUtil {

    private static final String PRETRAINED_MODEL_RESNET50 = "C:/Users/29140/.deeplearning4j/resnet50_dl4j_inference.zip";

    public static Map classifyImage(String imagePath) throws IOException {
        if (!new File(PRETRAINED_MODEL_RESNET50).exists()) {
            ZooModel zooModel = new ResNet50();
            ComputationGraph resNet50 = (ComputationGraph) zooModel.initPretrained(PretrainedType.IMAGENET);
        }

        File locationToSave = new File(MLUtil.PRETRAINED_MODEL_RESNET50);
        ComputationGraph vgg16 = ModelSerializer.restoreComputationGraph(locationToSave);
        NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
        INDArray image = loader.asMatrix(new File(imagePath));
        DataNormalization scaler = new VGG16ImagePreProcessor();
        scaler.transform(image);
        INDArray[] output = vgg16.output(false, image);
        HashMap<String, Float> precision = MLUtil.predict(output[0]);
        HashMap<String, Object> result = new HashMap<>();

        result.put("precision", precision);

        result.put("recall", precision);

        return result;
    }

    public static HashMap<String, Float> predict(INDArray predictions) {
        HashMap<String, Float> predictionResult = new HashMap();
        int[] top5 = new int[5];
        float[] top5Prob = new float[5];
        ArrayList<String> labels = ImageNetLabels.getLabels();
        int i = 0;
        for (int batch = 0; batch < predictions.size(0); ++batch) {
            for (INDArray currentBatch = predictions.getRow(batch).dup(); i < 5; ++i) {
                top5[i] = Nd4j.argMax(currentBatch, new int[]{1}).getInt(new int[]{0, 0});
                top5Prob[i] = currentBatch.getFloat(batch, top5[i]);
                currentBatch.putScalar(0, top5[i], 0.0D);
                predictionResult.put(labels.get(top5[i]), top5Prob[i]);
            }
        }

        return predictionResult;
    }

}
