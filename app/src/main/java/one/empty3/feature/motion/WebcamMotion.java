package one.empty3.feature.motion;



import one.empty3.feature.motion.Motion;

public class WebcamMotion extends Motion {

    @Override
    public BufferedImage process() {
        return processFrame().getImage();
    }
}
