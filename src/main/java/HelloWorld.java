import java.io.File;
import java.net.URL;
import org.apache.tika.Tika;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class HelloWorld {

    public static void main(String[] args) throws IOException, SAXException, TikaException, URISyntaxException {
        Tika tika = new Tika();
        URL fileUrl = HelloWorld.class.getResource(args[0]);
        File file = new File(fileUrl.toURI());
        String filetype = tika.detect(file);
        System.out.println(filetype);
    }

}
