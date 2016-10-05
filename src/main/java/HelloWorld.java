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
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.ocr.TesseractOCRParser;


public class HelloWorld {

    public static void main(String[] args) throws IOException, SAXException, TikaException {
        for (String s: args) {
            try (InputStream stream = HelloWorld.class.getResourceAsStream(s);
                 TikaInputStream tikaStream = TikaInputStream.get(stream)) {

                // Try to run this for testTiff.tiff
                Parser parser = new TesseractOCRParser();
                BodyContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();
                ParseContext context = new ParseContext();

                // Enable Tesseract
                TesseractOCRConfig ocrconfig = new TesseractOCRConfig(
                    HelloWorld.class.getResourceAsStream(
                        "TesseractOCRConfig.properties"));
                System.out.printf("TesseractOCRConfig:\n%s\n%d %d\n%s\n%s\n%s\n%d\n",
                                  ocrconfig.getLanguage(),
                                  ocrconfig.getMaxFileSizeToOcr(),
                                  ocrconfig.getMinFileSizeToOcr(),
                                  ocrconfig.getPageSegMode(),
                                  ocrconfig.getTessdataPath(),
                                  ocrconfig.getTesseractPath(),
                                  ocrconfig.getTimeout());

                context.set(TesseractOCRConfig.class, ocrconfig);
                // Meta name: [X-Parsed-By], Meta value: [org.apache.tika.parser.EmptyParser]

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
