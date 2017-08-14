package com.cvlh.util;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.trainedmodels.TrainedModels;
import org.deeplearning4j.util.ModelSerializer;
import org.deeplearning4j.zoo.PretrainedType;
import org.deeplearning4j.zoo.ZooModel;
import org.deeplearning4j.zoo.model.VGG16;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * a toolbox for machine learning
 */
public class MLUtil {

    public static Map classifyImage(String imagePath) {
//        do something
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> precision = new HashMap<>();
        precision.put("human", 0.841);
        precision.put("cat", 0.021);
        precision.put("car", 0.261);
        precision.put("dog", 0.151);
        precision.put("bike", 0.001);
        result.put("precision", precision);

        HashMap<String, Object> recall = new HashMap<>();
        recall.put("human", 0.096);
        recall.put("cat", 0.021);
        recall.put("car", 0.261);
        recall.put("dog", 0.151);
        recall.put("bike", 0.601);
        result.put("recall", recall);

        return result;
    }

    public static void main(String[] args) throws IOException {
        /*ZooModel zooModel = new VGG16();
        ComputationGraph vgg16 = (ComputationGraph) zooModel.initPretrained(PretrainedType.VGGFACE);
        NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
        INDArray image = loader.asMatrix(new File("D:\\TestFace\\1.jpg"));
        DataNormalization scaler = new VGG16ImagePreProcessor();
        scaler.transform(image);

        INDArray[] output = vgg16.output(false, image);
        System.out.println(TrainedModels.VGG16.decodePredictions(output[0]));*/

        File locationToSave = new File("C:/Users/29140/.deeplearning4j/vgg16_dl4j_vggface_inference.v1.zip");
        ComputationGraph vgg16 = ModelSerializer.restoreComputationGraph(locationToSave);
        NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
        INDArray image = loader.asMatrix(new File("D:\\TestFace\\1.jpg"));
        DataNormalization scaler = new VGG16ImagePreProcessor();
        scaler.transform(image);
        INDArray[] output = vgg16.output(false,image);

        // convert 1000 length numeric index of probabilities per label
        // to sorted return top 5 convert to string using helper function VGG16.decodePredictions
        // "predictions" is string of our results
        String predictions = TrainedModels.VGG16.decodePredictions(output[0]);

        System.out.println(predictions);


    }
}
