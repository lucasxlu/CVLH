package com.cvlh.util;

import it.unimi.dsi.fastutil.Hash;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class ImageUtil {

    private static final int DEFAULT_WIDTH = 64;
    private static final int DEFAULT_HEIGHT = 128;
    private static final String EYES_PRETRAINED_MODEL = "D:\\Users\\IdeaProjects\\TempProjects\\CVLH-BE\\src\\main\\resources\\haarcascade_eye.xml";
    private static final String FACE_PRETRAINED_MODEL = "D:\\Users\\IdeaProjects\\TempProjects\\CVLH-BE\\src\\main\\resources\\haarcascade_frontalface_default.xml";

    /**
     * detect all appeared faces given an image filepath
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
                FACE_PRETRAINED_MODEL);
        Mat image = Imgcodecs.imread(imagePath);

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));

        for (Rect rect : faceDetections.toArray()) {
            Mat dstImage = image.submat(rect);
            // Imgproc.resize(dstImage, dstImage, new Size(DEFAULT_WIDTH,
            // DEFAULT_HEIGHT));
            ArrayList<double[]> arrayList = detectEyes(dstImage);
            double angle = 0.0d;
            if (arrayList.size() == 2) {
                angle = calculateAngle(arrayList.get(0), arrayList.get(1));
            }
            System.out.println("angle is " + angle);
            Mat rotatedFace = rotateFace(dstImage, angle); // face after alignment
            Imgcodecs.imwrite("D:\\TestFace\\output\\4.png", rotatedFace);

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
                FACE_PRETRAINED_MODEL);
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
                EYES_PRETRAINED_MODEL);
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
     * calculate the cosine similarity of two feature vectors
     *
     * @param featureVector1
     * @param featureVector2
     * @return
     */
    public static double cosineSimilarity(float[] featureVector1,
                                          float[] featureVector2) {
        double numerator = 0.0d;
        for (int i = 0; i < featureVector2.length; i++) {
            numerator += featureVector1[i] * featureVector2[i];
        }

        double temp1 = 0.0d, temp2 = 0.0d;
        for (int i = 0; i < featureVector1.length; i++) {
            temp1 += Math.pow(featureVector1[i], 2);
        }
        for (int i = 0; i < featureVector2.length; i++) {
            temp2 += Math.pow(featureVector2[i], 2);
        }
        double denominator = Math.sqrt(temp1) * Math.sqrt(temp2);

        return polynomialNormalize(numerator / denominator);
    }


    /**
     * sigmoid-like normalization method
     * (optional)
     *
     * @param x
     * @return
     */
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
     */
    public static HashMap<Double, String> faceCompare(String faceFile1, String faceFile2) {
        Mat image1 = Imgcodecs.imread(faceFile1);
        Mat image2 = Imgcodecs.imread(faceFile2);
        Imgproc.cvtColor(image1, image1, Imgproc.COLOR_BGR2GRAY, 0);
        Imgproc.cvtColor(image2, image2, Imgproc.COLOR_BGR2GRAY, 0);

        HashMap<Double, String> result = new HashMap<>();
        double cosineSim = cosineSimilarity(hogFeature(image1), hogFeature(image2));

        if (cosineSim >= 0.85d)
            result.put(cosineSim, "high");
        else if (cosineSim >= 0.75d && cosineSim < 0.85d)
            result.put(cosineSim, "medium");
        else if (cosineSim < 0.75)
            result.put(cosineSim, "low");

        return result;
    }

}