package com.example.dentall.ui.gallery;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.example.dentall.R;
import org.opencv.core.Point;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

        public class GalleryFragment extends Fragment {
            private String currentPhotoPath;

            private static final int REQUEST_CAMERA = 1;
            private static final int REQUEST_GALLERY = 2;
            private static final int MAX_POINTS = 4;
            private List<Double> angles = new ArrayList<>();
            private int currentMode=0;

            private ImageView imageView;
            private Button btnChoose;

            private File file;
            private Uri uri;
            private Intent cameraIntent;
            private Intent galleryIntent;

            private Bitmap selectedBitmap;
            private Bitmap drawingBitmap;
            private Canvas canvas;
            private Paint paint;
            private List<Point> selectedPoints = new ArrayList<>();
            private int pointsAdded = 0;

            @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

                imageView = rootView.findViewById(R.id.imageView);

                imageView.setOnTouchListener(new View.OnTouchListener() {
                    private GestureDetector gestureDetector = new GestureDetector(requireContext(), new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapConfirmed(MotionEvent e) {
                            handleTap(e);
                            return true;
                        }

                        @Override
                        public boolean onDown(MotionEvent e) {
                            Log.d("GalleryFragment", "onDown called");
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            Log.d("GalleryFragment", "onLongPress called");
                        }
                    });

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.d("GalleryFragment", "onTouch called");
                        return gestureDetector.onTouchEvent(event);
                    }
                });

                paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(10f);
                paint.setStyle(Paint.Style.STROKE);

                ImageButton btnCamera = rootView.findViewById(R.id.btnCamera);
                ImageButton btnGallery = rootView.findViewById(R.id.btnGallery);

                btnCamera.setOnClickListener(view -> {
                    Log.d("GalleryFragment", "Before taking photo from camera");
                    takePhotoFromCamera();
                    Log.d("GalleryFragment", "After taking photo from camera");
                });

                btnGallery.setOnClickListener(view -> choosePhotoFromGallery());

                return rootView;

            }
            private void takePhotoFromCamera() {
                cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Handle the error
                        ex.printStackTrace();
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(requireContext(),
                                "com.example.dentall.fileprovider", photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, REQUEST_CAMERA);
                    }
                }
            }
            private File createImageFile() throws IOException {
                // Create an image file name
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                File image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );

                // Save a file: path for use with ACTION_VIEW intents
                currentPhotoPath = image.getAbsolutePath();
                return image;
            }
            private void choosePhotoFromGallery() {
                galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_GALLERY);
            }
            private Bitmap loadImage(Uri selectedImage) {
                Bitmap bitmap = null;
                try {
                    if (selectedImage != null) {
                        bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImage);
                    } else {
                        // Gérer le cas où selectedImage est nul
                        Log.e("GalleryFragment", "Selected image URI is null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                if (resultCode == Activity.RESULT_OK) {
                    if (requestCode == REQUEST_CAMERA) {
                        selectedBitmap = loadImage(uri);
                        showInfoDialog(getModeMessage(currentMode));

                    } else if (requestCode == REQUEST_GALLERY) {
                        selectedBitmap = loadImage(data.getData());
                        showInfoDialog(getModeMessage(currentMode));

                    }

                    // Appliquer le traitement d'image si nécessaire
                    selectedBitmap = ImageProcessingUtils.applyGaussianBlur(selectedBitmap, 5);
                    selectedBitmap = ImageProcessingUtils.applyCannyEdgeDetectionWithSpace(selectedBitmap, 50, 150,300);
                    selectedBitmap = ImageProcessingUtils.applyDilation(selectedBitmap);

                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                    // Ajouter du padding supplémentaire en haut (ajustez la valeur selon vos besoins)
                    int paddingTop = 30; // ajustez la valeur en conséquence
                    imageView.setPadding(0, paddingTop, 0, 0);

                    imageView.setImageBitmap(selectedBitmap);
                }
            }
            private void handleTap(MotionEvent event) {
                // Check if the maximum number of points has not been reached
                if (pointsAdded < MAX_POINTS) {
                    float x = event.getX();
                    float y = event.getY();

                    // Convert screen coordinates to bitmap coordinates
                    float[] pts = {x, y};
                    Matrix inverse = new Matrix();
                    imageView.getImageMatrix().invert(inverse);
                    inverse.mapPoints(pts);

                    x = pts[0];
                    y = pts[1];

                    float circleRadius = 3f; // Taille fixe du rayon du cercle (ajustez selon vos besoins)
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.GREEN);

                    if (drawingBitmap == null) {
                        drawingBitmap = Bitmap.createBitmap(selectedBitmap.getWidth(), selectedBitmap.getHeight(), Bitmap.Config.RGB_565);
                        canvas = new Canvas(drawingBitmap);
                        canvas.drawBitmap(selectedBitmap, 0, 0, null);
                    }

                    // Draw all existing points
                    for (Point point : selectedPoints) {
                        canvas.drawCircle((float) point.x, (float) point.y, circleRadius, paint);
                    }

                    // Draw the new point
                    canvas.drawCircle(x, y, circleRadius, paint);

                    selectedPoints.add(new Point(x, y));
                    pointsAdded++;

                    // Update the image with each added point
                    imageView.setImageBitmap(drawingBitmap);

                    // If the maximum number of points is reached, perform the processing
                    if (pointsAdded == MAX_POINTS) {
                        performProcessing();
                    }
                }
            }
            private void performProcessing() {
                // Check if the number of added points is equal to the maximum number of points
                if (pointsAdded == MAX_POINTS) {
                    // Create a single drawingBitmap for both tangents and axes
                    drawingBitmap = Bitmap.createBitmap(selectedBitmap.getWidth(), selectedBitmap.getHeight(), Bitmap.Config.RGB_565);
                    canvas = new Canvas(drawingBitmap);
                    canvas.drawBitmap(selectedBitmap, 0, 0, null);

                    if (selectedPoints.size() == 4) {
                        // Draw tangents for the first and second pair of points
                        drawTangent(selectedPoints.get(0), selectedPoints.get(1));
                        drawTangent(selectedPoints.get(2), selectedPoints.get(3));

                        // Draw vertical axes based on the first and third points

                        // Calculate the intersection point
                        Point intersectionPoint = calculateIntersection(selectedPoints.get(0), selectedPoints.get(1),
                                selectedPoints.get(2), selectedPoints.get(3));

                        // Extend the tangents to the intersection point
                        extendTangents(selectedPoints.get(0), selectedPoints.get(1), intersectionPoint);
                        extendTangents(selectedPoints.get(2), selectedPoints.get(3), intersectionPoint);
                    }

                    // Reset the points to allow for a new selection
                    resetPoints();
                }
            }
            private void drawTangent(Point startPoint, Point endPoint) {
                Log.d("GalleryFragment", "drawTangent(Point, Point) called");

                // Calculate the slope between the selected points
                double slope = (endPoint.y - startPoint.y) / (endPoint.x - startPoint.x);

                // Draw the tangent using the calculated slope
                drawTangent(startPoint, slope);
            }
            private void drawTangent(Point startPoint, double slope) {
                // Log to indicate that the method is called
                Log.d("GalleryFragment", "drawTangent method called");

                // Ensure that drawingBitmap and canvas are already initialized
                if (drawingBitmap == null) {
                    drawingBitmap = Bitmap.createBitmap(selectedBitmap.getWidth(), selectedBitmap.getHeight(), Bitmap.Config.RGB_565);
                    canvas = new Canvas(drawingBitmap);
                    canvas.drawBitmap(selectedBitmap, 0, 0, null);
                }

                float x1 = (float) startPoint.x;
                float y1 = (float) startPoint.y;

                float x2;
                float y2;
                if (currentMode==0 || currentMode==1 ){
                    // Check if the slope is infinite (division by zero)
                    if (Double.isInfinite(slope)) {
                        x2 = x1;
                        y2 = y1 + 300; // Adjust the length of the tangent as needed
                    } else {
                        // Calculate x2 based on the direction of the tangent
                        x2 = slope > 0 ? x1 + 300 : x1 - 300;

                        // Calculate y2 based on the calculated x2
                        y2 = (float) (y1 + slope * (x2 - x1));
                    }

                    // Calculate the angle between the tangent and the vertical
                    double angleRad = Math.atan2(x2 - x1, y2 - y1);
                    double angleDeg = Math.toDegrees(angleRad);
                    // Log for debugging
                    Log.d("GalleryFragment", "Angle between tangent and vertical: " + angleDeg + " degrees");

                    // Calculate the intersection point
                    Point intersectionPoint = calculateIntersection(startPoint, new Point(x2, y2), selectedPoints.get(2), selectedPoints.get(3));
                    if (intersectionPoint != null) {
                        // Draw the intersection point
                        paint.setColor(Color.GREEN);
                        paint.setStyle(Paint.Style.FILL);
                        canvas.drawCircle((float) intersectionPoint.x, (float) intersectionPoint.y, 3f, paint);
                    }

                    // Update the image with each added tangent
                    paint.setColor(Color.BLUE); // Color of the tangent (e.g., blue)
                    paint.setStrokeWidth(5f);
                    canvas.drawLine(x1, y1, x2, y2, paint);

                    // Update the image with each added tangent
                    imageView.setImageBitmap(drawingBitmap);

                    // Store the angle for the current tangent
                    angles.add(angleDeg);

                    // Check if both tangents are drawn
                    Log.d("GalleryFragment", "Number of elements in angles: " + angles.size());
                    if (angles.size() == 2) {
                        evaluateSymmetry();
                        currentMode++;

                        showAngleDialog(angles.get(0), angles.get(1));
                        showInfoDialog(getModeMessage(currentMode));

                        angles.clear(); // Assurez-vous de vider la liste après l'affichage du dialogue
                    }
                }
                else {
                    // Check if the slope is zero (horizontal line)
                    if (slope == 0) {
                        x2 = x1 + 300; // Adjust the length of the tangent as needed
                        y2 = y1;
                    } else {
                        // Calculate y2 based on the direction of the tangent
                        y2 = slope > 0 ? y1 + 300 : y1 - 300;

                        // Calculate x2 based on the calculated y2
                        x2 = (float) (x1 + (y2 - y1) / slope);
                    }

                    // Calculate the angle between the tangent and the horizontal
                    double angleRad = Math.atan2(y2 - y1, x2 - x1);
                    double angleDeg = Math.toDegrees(angleRad);

                    // Log for debugging
                    Log.d("GalleryFragment", "Angle between tangent and horizontal: " + angleDeg + " degrees");

                    // Calculate the intersection point
                    Point intersectionPoint = calculateIntersection(startPoint, new Point(x2, y2), selectedPoints.get(2), selectedPoints.get(3));
                    if (intersectionPoint != null) {
                        // Draw the intersection point
                        paint.setColor(Color.GREEN);
                        paint.setStyle(Paint.Style.FILL);
                        canvas.drawCircle((float) intersectionPoint.x, (float) intersectionPoint.y, 3f, paint);
                    }

                    // Update the image with each added tangent
                    paint.setColor(Color.BLUE); // Color of the tangent (e.g., blue)
                    paint.setStrokeWidth(5f);
                    canvas.drawLine(x1, y1, x2, y2, paint);

                    // Update the image with each added tangent
                    imageView.setImageBitmap(drawingBitmap);

                    // Store the angle for the current tangent
                    angles.add(angleDeg);

                    // Check if both tangents are drawn
                    Log.d("GalleryFragment", "Number of elements in angles: " + angles.size());
                    if (angles.size() == 2) {
                        evaluateSymmetry();
                        currentMode++;

                        showAngleDialog(angles.get(0), angles.get(1));
                        showInfoDialog(getModeMessage(currentMode));

                        angles.clear(); // Assurez-vous de vider la liste après l'affichage du dialogue
                    }

                }
                if (currentMode ==3) {
                    imageView.setOnTouchListener(null);
                    showChooseAnotherImageAlert();
                    currentMode = 0;
                }


            }
            private void showChooseAnotherImageAlert() {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Choisir une autre image")
                        .setMessage("Veuillez choisir une autre image pour continuer.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Action à effectuer lorsque l'utilisateur clique sur OK
                                // Vous pouvez ajouter du code ici pour permettre à l'utilisateur de choisir une autre image
                                dialog.dismiss();
                            }
                        });

                // Afficher la boîte de dialogue
                builder.create().show();
            }
            private void showAngleDialog(double angle1, double angle2) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Angles between tangents and vertical");
                builder.setMessage("Angle 1: " + angle1 + " degrees\nAngle 2: " + angle2 + " degrees");
                builder.setPositiveButton("OK", null);
                builder.show();
            }
            private void extendTangents(Point startPoint, Point endPoint, Point intersectionPoint) {
                float x1 = (float) startPoint.x;
                float y1 = (float) startPoint.y;

                float x2;
                float y2;

                // Calculate the slope between the selected points
                double slope = (endPoint.y - startPoint.y) / (endPoint.x - startPoint.x);

                // Check if the slope is infinite (division by zero)
                if (Double.isInfinite(slope)) {
                    x2 = x1;
                    y2 = y1 + 300; // Adjust the length of the tangent as needed
                } else {
                    // Calculate x2 based on the direction of the tangent
                    x2 = slope > 0 ? x1 + 3000 : x1 - 3000;

                    // Calculate y2 based on the calculated x2
                    y2 = (float) (y1 + slope * (x2 - x1));
                }


                // Draw line from the intersection point to the end of the tangent
                paint.setColor(Color.RED); // Color for the extended line
                canvas.drawLine((float) intersectionPoint.x, (float) intersectionPoint.y, x2, y2, paint);

                // Update the image with each added tangent and extension
                imageView.setImageBitmap(drawingBitmap);
            }
            private void evaluateSymmetry() {
                Log.d("GalleryFragment", "evaluateSymmetry() called");

                // Check if both angles are available
                Log.d("GalleryFragment", "Angles list: " + angles.toString());

                if (angles.size() == 2) {
                    Log.d("GalleryFragment", "Drawing tangents - Angle 1: " + angles.get(0) + " degrees");
                    Log.d("GalleryFragment", "Drawing tangents - Angle 2: " + angles.get(1) + " degrees");

                    double angle1 = angles.get(0);
                    double angle2 = angles.get(1);

                    // Définir un seuil pour l'évaluation de la symétrie (ajuster si nécessaire)
                    double symmetryThreshold = 5.0; // Ajuster le seuil selon les besoins

                    // Calculer la différence absolue entre les angles
                    double angleDifference = Math.abs(angle1 - angle2);

                    // Journaliser les angles pour le débogage
                    Log.d("GalleryFragment", "Angle 1: " + angle1 + " degrés");
                    Log.d("GalleryFragment", "Angle 2: " + angle2 + " degrés");

                    // Vérifier si la différence dépasse le seuil
                    if (angleDifference > symmetryThreshold) {
                        // Indiquer une possible asymétrie
                        Log.d("GalleryFragment", "Possible asymétrie - Différence d'angle : " + angleDifference + " degrés");
                        showSymmetryDialog("Asymétrie détectée", "La différence d'angle dépasse le seuil, indiquant une possible asymétrie.");
                    } else {
                        // Indiquer la symétrie
                        Log.d("GalleryFragment", "Symétrie - Les angles sont symétriques");
                        showSymmetryDialog("Symétrie détectée", "Les angles sont symétriques.");
                    }
                }
            }
            private void showSymmetryDialog(String title, String message) {
                Log.d("GalleryFragment", "showSymmetryDialog() called");

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setPositiveButton("OK", null);
                builder.show();
            }
            private void resetPoints() {
                selectedPoints.clear();
                drawingBitmap = null;
                pointsAdded = 0;
            }
            private Point calculateIntersection(Point startPoint1, Point endPoint1, Point startPoint2, Point endPoint2) {
                double x1 = startPoint1.x;
                double y1 = startPoint1.y;
                double x2 = endPoint1.x;
                double y2 = endPoint1.y;

                double x3 = startPoint2.x;
                double y3 = startPoint2.y;
                double x4 = endPoint2.x;
                double y4 = endPoint2.y;

                double a1 = y2 - y1;
                double b1 = x1 - x2;
                double c1 = a1 * x1 + b1 * y1;

                double a2 = y4 - y3;
                double b2 = x3 - x4;
                double c2 = a2 * x3 + b2 * y3;

                double determinant = a1 * b2 - a2 * b1;

                if (determinant == 0) {
                    // Les lignes sont parallèles, pas d'intersection
                    return null;
                } else {
                    double x = (b2 * c1 - b1 * c2) / determinant;
                    double y = (a1 * c2 - a2 * c1) / determinant;
                    return new Point(x, y);
                }
            }
            private void showInfoDialog(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Info")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Action à effectuer lorsque l'utilisateur clique sur OK
                                dialog.dismiss();
                            }
                        });

                // Afficher la boîte de dialogue
                builder.create().show();
            }
            private String getModeMessage(int mode) {
                switch (mode) {
                    case 0:
                        return "Sélectionnez 4 points pour les angles de dépouille";
                    case 1:
                        return "Sélectionnez autres 4 points pour les angles externes (par rapport à la verticale)";
                    case 2:
                        return "Sélectionnez deux derniers points pour les angles internes (par rapport à l'horizontale)";
                    default:
                        return "";
                }

            }
        }
