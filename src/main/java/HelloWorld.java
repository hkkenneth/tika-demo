import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.detect.CompositeDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.parser.DefaultParser;

public class HelloWorld {

    public static void main(String[] args) throws IOException, SAXException, TikaException {
        for (String s: args) {
            try (InputStream stream = HelloWorld.class.getResourceAsStream(s);
                 TikaInputStream tikaStream = TikaInputStream.get(stream)) {
                Parser parser = new AutoDetectParser();
                BodyContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();
                ParseContext context = new ParseContext();

                //parsing the file
                parser.parse(tikaStream, handler, metadata, context);
                System.out.println("File content : " + handler.toString());
                printMeta(metadata);

                // Print which detector are used
                recursePrintDetector(((AutoDetectParser) parser).getDetector(), null);
                System.out.println("============================");
            }
        }
    }

    private static void recursePrintDetector(Detector self, Detector parent) {
        if (parent == null) {
            System.out.println("Root Detector : " + self.getClass().getName());
        } else {
          System.out.println("Parent Detector : " +
                             parent.getClass().getName() +
                             " Self : " +
                             self.getClass().getName());
        }

        if (self instanceof CompositeDetector) {
            for (Detector child: ((CompositeDetector) self).getDetectors()) {
                recursePrintDetector(child, self);
            }
        }
    }

    private static void printMeta(Metadata metadata) {
        for (String name: metadata.names()) {
            System.out.printf("Meta name: [%s], Meta value: [%s]\n", name,
                               metadata.get(name));
        }
    }

}
