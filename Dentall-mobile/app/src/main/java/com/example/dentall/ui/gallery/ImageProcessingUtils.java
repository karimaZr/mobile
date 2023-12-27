// ImageProcessingUtils.java

package com.example.dentall.ui.gallery;

import org.opencv.android.Utils;
import org.opencv.core.Mat;


import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import org.opencv.imgproc.Imgproc;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImageProcessingUtils {

    static {
        System.loadLibrary("opencv_java4");
    }

    public static Bitmap convertToGrayScale(Bitmap originalBitmap) {
        Mat originalMat = new Mat();
        Utils.bitmapToMat(originalBitmap, originalMat);

        Mat grayMat = new Mat();
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_RGB2GRAY);

        Bitmap grayBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(grayMat, grayBitmap);

        originalMat.release();
        grayMat.release();

        return grayBitmap;
    }

    public static Bitmap applyGaussianBlur(Bitmap originalBitmap, int kernelSize) {
        Mat originalMat = new Mat();
        Utils.bitmapToMat(originalBitmap, originalMat);

        Mat blurredMat = new Mat();
        Imgproc.GaussianBlur(originalMat, blurredMat, new Size(kernelSize, kernelSize), 0);

        Bitmap blurredBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(blurredMat, blurredBitmap);

        originalMat.release();
        blurredMat.release();

        return blurredBitmap;
    }

    public static Bitmap applyCannyEdgeDetectionWithSpace(Bitmap originalBitmap, double threshold1, double threshold2, int marginTop) {
        Mat originalMat = new Mat();
        Utils.bitmapToMat(originalBitmap, originalMat);

        // Create a new Mat with additional space at the top
        Mat matWithSpace = new Mat(originalMat.rows() + marginTop, originalMat.cols(), originalMat.type(), new Scalar(0, 0, 0));  // Set to black

        // Copy the originalMat to the lower part of matWithSpace
        Mat lowerPart = matWithSpace.submat(new Rect(0, marginTop, originalMat.cols(), originalMat.rows()));
        originalMat.copyTo(lowerPart);

        Mat edges = new Mat();
        Imgproc.cvtColor(matWithSpace, edges, Imgproc.COLOR_RGB2GRAY);
        Imgproc.GaussianBlur(edges, edges, new Size(5, 5), 1.5, 1.5);
        Imgproc.Canny(edges, edges, threshold1, threshold2);

        // Rest of your code for image processing...

        Bitmap edgesBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight() + marginTop, Bitmap.Config.RGB_565);
        Utils.matToBitmap(edges, edgesBitmap);

        originalMat.release();
        matWithSpace.release();
        edges.release();

        return edgesBitmap;
    }

    public static Bitmap applyDilation(Bitmap originalBitmap) {
        Mat originalMat = new Mat();
        Utils.bitmapToMat(originalBitmap, originalMat);

        Mat dilatedMat = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.dilate(originalMat, dilatedMat, kernel);

        Bitmap dilatedBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(dilatedMat, dilatedBitmap);

        originalMat.release();
        dilatedMat.release();

        return dilatedBitmap;
    }

    public static Mat detectLines(Mat originalMat) {
        Mat lines = new Mat();
        Imgproc.HoughLinesP(originalMat, lines, 1, Math.PI / 180, 50, 50, 10);
        return lines;
    }

    public static List<Double> calculateAngles(Mat lines) {
        List<Double> angles = new ArrayList<>();

        for (int i = 0; i < lines.rows(); i++) {
            double[] vec = lines.get(i, 0);
            double x1 = vec[0], y1 = vec[1], x2 = vec[2], y2 = vec[3];
            double angle = Math.atan2(y2 - y1, x2 - x1) * (180 / Math.PI);
            angles.add(angle);
        }

        return angles;
    }
    public static List<Point> findEdgePoints(Bitmap originalBitmap, double threshold1, double threshold2, int marginTop) {
        Mat originalMat = new Mat();
        Utils.bitmapToMat(originalBitmap, originalMat);

        // Appliquer Canny pour détecter les bords
        Mat edges = new Mat();
        Imgproc.cvtColor(originalMat, edges, Imgproc.COLOR_RGB2GRAY);
        Imgproc.GaussianBlur(edges, edges, new Size(5, 5), 1.5, 1.5);
        Imgproc.Canny(edges, edges, threshold1, threshold2);

        // Trouver les contours dans l'image binaire
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(edges, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Convertir les contours en points
        List<Point> edgePoints = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            edgePoints.addAll((Collection<? extends Point>) contour2f.toList());
        }

        // Dessiner les contours sur l'image originale
        Imgproc.drawContours(originalMat, contours, -1, new Scalar(0, 255, 0), 2);

        // Convertir l'image Mat modifiée en Bitmap
        Bitmap resultBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(originalMat, resultBitmap);

        // Libérer les ressources
        originalMat.release();
        edges.release();

        return edgePoints;
    }
}
