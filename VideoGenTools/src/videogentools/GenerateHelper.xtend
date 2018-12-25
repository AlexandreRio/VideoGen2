package videogentools

import java.util.ArrayList
import java.util.List
import org.xtext.example.mydsl.videoGen.BlackWhiteFilter
import org.xtext.example.mydsl.videoGen.Filter
import org.xtext.example.mydsl.videoGen.MandatoryMedia
import org.xtext.example.mydsl.videoGen.Media
import org.xtext.example.mydsl.videoGen.MediaDescription
import org.xtext.example.mydsl.videoGen.VideoDescription
import org.xtext.example.mydsl.videoGen.VideoText

class GenerateHelper {

    def static generate(Media m) {
        if (m instanceof MandatoryMedia)
            generate(m as MandatoryMedia)
    }

    def static generate(MandatoryMedia m) {
        val MediaDescription md = m.getDescription();
        if (md instanceof VideoDescription)
            generate(md);
    }

    def static generate(VideoDescription description) {
    	var command = "ffmpeg -i " + description.getLocation() + " \\";
        val List<String> filters = new ArrayList;
        filters.add(generate(description.getText()));
        filters.add(generate(description.getFilter()));
        command += "-vf \"" + String.join(",", filters) + "\" \\";
        command += " out.mkv";
    }

    def static generate(Filter filter) {
        var ret = "# Unsupported filter " + filter + " \n";
        if (filter instanceof BlackWhiteFilter) {
            ret = "hue=s=0";
        }
        return ret;
    }

    def static generate(VideoText text) {
        var color = text.getColor();
        if (color === null)
            color = "white";

        var size = text.getSize();
        if (size == 0)
            size = 12;

        var position = text.getPosition();
        var ypos = "(h/PHI)+th";
        if (position.equals("TOP")) {
            ypos = "0";
        } else if (position.equals("BOTTOM")) {
            ypos = "h-(th*2)";
        } else if (position.equals("CENTER")) {
            ypos = "h/2 - th/2";
        }

        var content = text.getContent();
        //drawbox=y=ih/PHI:color=black@0.4:width=iw:height=48:t=max,
        val template = "format=yuv444p, drawtext=fontfile=OpenSans-Regular.ttf:text='" + content + "':fontcolor=" + color + ":fontsize=" + size + ":x=(w-tw)/2:y=" + ypos + ", format=yuv420p";
        return template;
    }
}