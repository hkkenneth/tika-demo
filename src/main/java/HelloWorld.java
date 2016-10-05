import java.io.InputStream;
import org.apache.tika.Tika;

import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class HelloWorld {

    public static void main(String[] args) throws IOException, SAXException, TikaException {
        Tika tika = new Tika();
        try (InputStream stream = HelloWorld.class.getResourceAsStream(args[0])) {
            System.out.println(tika.parseToString(stream));
        }
    }

}
