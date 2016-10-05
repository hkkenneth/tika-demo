import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MediaTypeRegistry;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

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

                // Print which media type registry used
                AutoDetectParser autoParser = ((AutoDetectParser) parser);
                MediaTypeRegistry mtr = autoParser.getMediaTypeRegistry();
                MediaTypeRegistry defaultMTR = MediaTypeRegistry.getDefaultRegistry();
                boolean isDefaultMTRUsed = mtr.equals(defaultMTR);
                if (isDefaultMTRUsed) {
                    System.out.println("Default MTR used");
                } else {
                    System.out.println("Default MTR not used");
                    System.out.println("MTR used:");
                    printMtr(mtr);
                }
                System.out.println("Default MTR:");
                printMtr(defaultMTR);
                System.out.println("============================");
            }
        }
    }

    private static void printMtr(MediaTypeRegistry mtr) {
        for (MediaType mt: mtr.getTypes()) {
            System.out.printf("Media type name: [%s]\n", mt.toString());
        }
    }

    private static void printMeta(Metadata metadata) {
        for (String name: metadata.names()) {
            System.out.printf("Meta name: [%s], Meta value: [%s]\n", name,
                              metadata.get(name));
        }
    }

}
