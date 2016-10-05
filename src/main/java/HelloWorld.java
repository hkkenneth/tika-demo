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
import org.apache.tika.config.TikaConfig;

public class HelloWorld {

    public static void main(String[] args) throws IOException, SAXException, TikaException {
        for (String s: args) {
            try (InputStream stream = HelloWorld.class.getResourceAsStream(s);
                 TikaInputStream tikaStream = TikaInputStream.get(stream)) {

                // Mess with media type mapping
                TikaConfig config = new TikaConfig(HelloWorld.class.getResource("tika-config.xml"));
                Parser parser = new AutoDetectParser(config);
                BodyContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();
                ParseContext context = new ParseContext();

                //parsing the file
                parser.parse(tikaStream, handler, metadata, context);
                System.out.println("File content : " + handler.toString());
                printMeta(metadata);

                System.out.println("============================");
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
