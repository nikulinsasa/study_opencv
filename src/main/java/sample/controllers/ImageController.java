package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

public class ImageController {

    @FXML
    public ImageView image;

    @FXML
    public Screen screen;

    public void initialize() {
        loadImage();
    }

    private void loadImage() {
        Mat kernel = new Mat();
        int kernel_size;

        Mat sourceMat = Imgcodecs.imread("images/IMG_20171231_234805.jpg");

        Mat mat = new Mat();
        Imgproc.resize(sourceMat,mat,new Size(300,300));

        kernel_size = 3 + 2*( 10%5 );
        Mat ones = Mat.ones( kernel_size, kernel_size, CvType.CV_32F );
        Core.multiply(ones, new Scalar(1/(double)(kernel_size*kernel_size)), kernel);

        Imgproc.filter2D(mat,mat,-1,kernel,new Point( -1, -1),0,Core.BORDER_DEFAULT);

        MatOfByte mb = new MatOfByte();
        Imgcodecs.imencode(".jpg",mat,mb);

        image.setImage(new Image(new ByteArrayInputStream(mb.toArray())));
    }


}
