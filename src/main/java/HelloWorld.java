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
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;

public class HelloWorld {

    public static void main(String[] args) throws IOException, SAXException, TikaException {
        for (String s: args) {
            System.out.println("Parsing " + s);
            //InputStream stream = HelloWorld.class.getResourceAsStream(s);
            try (InputStream stream = new FileInputStream(new File(s));
                 TikaInputStream tikaStream = TikaInputStream.get(stream)) {

                TikaConfig config = new TikaConfig(HelloWorld.class.getResource("tika-config.xml"));
                PDFParser pdfParser = new PDFParser();
                PDFParserConfig parserConfig = new PDFParserConfig();
                parserConfig.setExtractAcroFormContent(true);
                parserConfig.setIfXFAExtractOnlyXFA(false);
                //parserConfig.setOcrStrategy(PDFParserConfig.OCR_STRATEGY.OCR_AND_TEXT_EXTRACTION);
                parserConfig.setOcrStrategy(PDFParserConfig.OCR_STRATEGY.NO_OCR);
                //parserConfig.setOcrStrategy(PDFParserConfig.OCR_STRATEGY.OCR_ONLY);
                pdfParser.setPDFParserConfig(parserConfig);
                BodyContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();
                ParseContext context = new ParseContext();
                //context.set(TikaConfig.class, config);
                context.set(PDFParserConfig.class, parserConfig);

                ToXMLContentHandler xmlHandler = new ToXMLContentHandler();
                //parsing the file
                pdfParser.parse(tikaStream, xmlHandler, metadata, context);
                System.out.println("File content : " + xmlHandler.toString());

                printMeta(metadata);
            }
        }
    }

    private static void printMeta(Metadata metadata) {
        for (String name: metadata.names()) {
            System.err.printf("Meta name: [%s], Meta value: [%s]\n", name,
                              metadata.get(name));
        }
    }

}
