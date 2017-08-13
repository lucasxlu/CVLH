package test.bioinfo.algorithm;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;

import java.util.ArrayList;

public class OpenCVTest {

    private static final int DEFAULT_WIDTH = 64;
    private static final int DEFAULT_HEIGHT = 128;
    private static final String EYES_PRETRAINED_MODEL = "D:\\Users\\IdeaProjects\\TempProjects\\CVLH-BE\\src\\main\\resources\\haarcascade_eye.xml";
    private static final String FACE_PRETRAINED_MODEL = "D:\\Users\\IdeaProjects\\TempProjects\\CVLH-BE\\src\\main\\resources\\haarcascade_frontalface_default.xml";

    /**
     * 截取人脸进行比对。
     *
     * @param imagePath 原图片进行处理。
     * @return
     */
    public ArrayList<Mat> detectFaces(String imagePath) {
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
            Mat rotatedFace = rotateFace(dstImage, angle); // 校正后的人脸
            Imgcodecs.imwrite("D:\\TestFace\\output\\4.png", rotatedFace);

            matList.add(rotatedFace);
        }

        return matList;
    }

    /**
     * 检测人眼位置
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
     * 人脸特征向量提取的
     *
     * @param mat 人脸图片
     * @return 人脸特征向量
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
     * 人脸特征向量比对方法。
     *
     * @param featureVector1 第一张图片的特征向量
     * @param featureVector2 第二张图片的特征向量
     * @return 人脸相似度。
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
     * 类Sigmoid标准化处理
     * (可选)
     *
     * @param x
     * @return
     */
    public static double rectifiedSigmoid(double x) {
        double value = (1 / (1 + Math.pow(Math.E, -50 * (x - 0.78))));

        return value * 100;
    }

    /**
     * 二次幂标准化处理
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
     * 计算人脸倾斜值
     *
     * @param point1
     * @param point2
     * @return
     */
    public static double calculateAngle(double[] point1, double[] point2) {
        double angle = 0.0D;
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
     * 校正人脸
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

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        ArrayList<Mat> arrayList = new OpenCVTest().detectFaces("D:\\TestFace\\4.jpg");

        Mat image1 = Imgcodecs.imread("D:\\TestFace\\output\\1.png");
        Mat image2 = Imgcodecs.imread("D:\\TestFace\\output\\2.png");
        Imgproc.cvtColor(image1, image1, Imgproc.COLOR_BGR2GRAY, 0);
        Imgproc.cvtColor(image2, image2, Imgproc.COLOR_BGR2GRAY, 0);

        System.out.println(hogFeature(image1).length);
        System.out.println("similarity is "
                + cosineSimilarity(hogFeature(image1), hogFeature(image2)));
    }

}
