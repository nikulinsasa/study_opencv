package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;

public class FrameGrabber implements Runnable {

    private VideoCapture capture;

    private ImageView img;

    public FrameGrabber(VideoCapture capture, ImageView img) {
        this.capture = capture;
        this.img = img;
    }

    public void run() {
        Mat frame = grabFrame();
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png",frame,buffer);

        img.setImage(new Image(new ByteArrayInputStream(buffer.toArray())));
    }

    private Mat grabFrame() {
        Mat frame = new Mat();
        if(capture.isOpened()){
            capture.read(frame);

            if(!frame.empty()){
                Imgproc.cvtColor(frame,frame,Imgproc.COLOR_RGB2BGR);
            }

        }
        return frame;
    }

}
