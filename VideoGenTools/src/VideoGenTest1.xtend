import org.junit.Test
import org.eclipse.emf.common.util.URI

import static org.junit.Assert.*
import org.xtext.example.mydsl.videoGen.Media
import org.xtext.example.mydsl.videoGen.MandatoryMedia

class VideoGenTest1 {
	
	@Test
	def void testLoadModel() {
		
		val videoGen = new VideoGenHelper().loadVideoGenerator(URI.createURI("/home/ario/dev/java/runtime-EclipseXtext/videogen/test2.videogen"))
		assertNotNull(videoGen)
		println(videoGen.information.authorName)
		for (Media m : videoGen.medias)
			if (m instanceof MandatoryMedia) {
				println(m.description.location)
			}
		// and then visit the model
		// eg access video sequences: videoGen.videoseqs
		
	}
}