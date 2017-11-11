import static org.junit.Assert.*;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.xtext.example.mydsl.videoGen.ImageDescription;
import org.xtext.example.mydsl.videoGen.MandatoryMedia;
import org.xtext.example.mydsl.videoGen.Media;
import org.xtext.example.mydsl.videoGen.MediaDescription;
import org.xtext.example.mydsl.videoGen.VideoDescription;
import org.xtext.example.mydsl.videoGen.VideoGeneratorModel;
import org.xtext.example.mydsl.videoGen.VideoText;

public class VideoGenTestJava1 {

    String path = "/home/ario/dev/java/runtime-EclipseXtext/videogen/test2.videogen";

    @Test
    public void testInJava1() {

        VideoGeneratorModel videoGen = new VideoGenHelper().loadVideoGenerator(URI.createURI(path));
        assertNotNull(videoGen);

        System.out.println(videoGen.getInformation().getAuthorName());
        for (Media m : videoGen.getMedias()) {
            generate(m);
        }
    }

    public void generate(Media m) {
        if (m instanceof MandatoryMedia)
            generate((MandatoryMedia)m);
    }

    public void generate(MandatoryMedia m) {
        MediaDescription md = m.getDescription();
        if (md instanceof VideoDescription)
            generate((VideoDescription)m.getDescription());
    }

    private void generate(VideoDescription description) {
        System.out.println("ffmpeg -i " + description.getLocation() + " \\");
        System.out.println(generate(description.getText()) + "\\");
        System.out.println(" out.mkv");
    }

	private String generate(VideoText text) {
		String color = text.getColor();
		if (color == null)
			color = "white";

		int size = text.getSize();
		if (size == 0)
			size = 12;

		String position = text.getPosition();
		String ypos = "(h/PHI)+th";
		if (position.equals("TOP")) {
			ypos = "0";
		} else if (position.equals("BOTTOM")) {
			ypos = "h-(th*2)";
		} else if (position.equals("CENTER")) {
			ypos = "h/2 - th/2";
		}

		String content = text.getContent();
		//drawbox=y=ih/PHI:color=black@0.4:width=iw:height=48:t=max,
		String template = "-vf \"format=yuv444p, drawtext=fontfile=OpenSans-Regular.ttf:text='" + content + "':fontcolor=" + color + ":fontsize=" + size + ":x=(w-tw)/2:y=" + ypos + ", format=yuv420p\"";
		return template;
	}



}
