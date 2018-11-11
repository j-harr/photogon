package image;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

public class Camera {
    FrameGrabber frameGrabber;
    public Camera(){
        frameGrabber = new VideoInputFrameGrabber(0);
    }

    public void getFrame(){
        IplImage image;
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        try {
            frameGrabber.start();
            image = converter.convert(frameGrabber.grab());
            cvSaveImage("savedMe.jpg", image);
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }
}
