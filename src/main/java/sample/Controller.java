package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    public Button catchVideo;

    @FXML
    public ImageView videoField;

    private ScheduledExecutorService timer;

    private boolean isCameraActive = false;

    private VideoCapture capture = new VideoCapture();

    private static int cameraId = 0;

    public void startCamera(ActionEvent actionEvent) {
        if (!isCameraActive) {
            capture.open(cameraId);
            if (capture.isOpened()) {
                FrameGrabber fg = new FrameGrabber(capture, videoField);
                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(fg, 0, 33, TimeUnit.MILLISECONDS);
                isCameraActive = true;
                catchVideo.setText("Выключить камеру");
            } else {
                // log the error
                System.err.println("Impossible to open the camera connection...");
            }
        } else {
            timer.shutdown();
            isCameraActive = false;
            catchVideo.setText("Включить камеру");
        }
    }
}
