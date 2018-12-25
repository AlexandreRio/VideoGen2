package videogentools;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.URI;
import org.xtext.example.mydsl.videoGen.Media;
import org.xtext.example.mydsl.videoGen.VideoGeneratorModel;


public class Main {

    static String path = "/home/ario/dev/java/runtime-EclipseXtext/videogen/test2.videogen";

    public static void main(String... args) {
    	System.out.println("Duration " + FFMPEG.ffmpegComputeDuration("res/kaamelott.mkv"));
    	VideoGeneratorModel videoGen = new VideoGenHelper().loadVideoGenerator(URI.createURI(path));
        assertNotNull(videoGen);

        System.out.println(videoGen.getInformation().getAuthorName());
        for (Media m : videoGen.getMedias()) {
            GenerateHelper.generate(m);
        }
    }
}