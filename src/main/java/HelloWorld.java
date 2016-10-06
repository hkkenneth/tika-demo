import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;


public class HelloWorld {

    public static void main(String[] args) throws IOException, SAXException, TikaException {
        for (String s: args) {
            System.out.println("Parsing " + s);
            try (InputStream stream = HelloWorld.class.getResourceAsStream(s);
                 TikaInputStream tikaStream = TikaInputStream.get(stream)) {

                TikaConfig config = new TikaConfig(HelloWorld.class.getResource("tika-config.xml"));
                Parser autoParser = new AutoDetectParser(config);
                BodyContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();
                ParseContext context = new ParseContext();
                context.set(TikaConfig.class, config);

                //parsing the file
                autoParser.parse(tikaStream, handler, metadata, context);
                System.out.println("File content : " + handler.toString());
                printMeta(metadata);

                MediaType mt = MediaType.image("tiff");
                Parser tiffParser = ((AutoDetectParser) autoParser)
                                    .getParsers(context).get(mt);
                if (tiffParser == null) {
                    System.out.println("Parser for tiff is NULL!");
                } else {
                    System.out.println("Parser for tiff: " +
                                       tiffParser.getClass().getName());
                }


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
