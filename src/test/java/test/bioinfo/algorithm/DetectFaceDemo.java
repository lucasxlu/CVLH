package test.bioinfo.algorithm;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/*
 * Detects faces in an image, draws boxes around them, and writes the results
 * to "faceDetection.png".
 */
public class DetectFaceDemo {
    private static final String FACE_PRETRAINED_MODEL = "D:\\Users\\IdeaProjects\\TempProjects\\CVLH-BE\\src\\main\\resources\\haarcascade_frontalface_default.xml";
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;


    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void run() {

        CascadeClassifier faceDetector = new CascadeClassifier(FACE_PRETRAINED_MODEL);
        Mat image = Imgcodecs.imread("D:/TestFace/3.jpg");

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));

        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            /*Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
                    + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));*/
            String filename = "D:/TestFace/output/3.png";
            System.out.println(String.format("Writing %s", filename));
            Mat faceMat = image.submat(rect);
            Imgproc.resize(faceMat, faceMat, new Size(DEFAULT_WIDTH, DEFAULT_HEIGHT));
            Imgcodecs.imwrite(filename, faceMat);
        }

    }

    public static void main(String[] args) {
        new DetectFaceDemo().run();
    }
}
