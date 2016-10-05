import org.apache.tika.language.LanguageIdentifier;

public class HelloWorld {

    public static void main(String args[]) {

        LanguageIdentifier identifier = new LanguageIdentifier("this is english");
        String language = identifier.getLanguage();
        System.out.println("Language of the given content is : " + language);

        // French
        identifier = new LanguageIdentifier("J’ achète du pain tous les jours.");
        language = identifier.getLanguage();
        System.out.println("Language of the given content is : " + language);

        // Spanish, but tika got it wrong (detected as 'gl': Galician language)
        identifier = new LanguageIdentifier("Por favor, ¿me podría dar un poquito de perejil?");
        language = identifier.getLanguage();
        System.out.println("Language of the given content is : " + language);
    }
}
