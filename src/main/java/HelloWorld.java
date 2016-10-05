import org.apache.tika.Tika;

public class HelloWorld {

    public static void main(String[] args) {
        Tika tika = new Tika();

        System.out.println(tika.getParser().getClass().getName());
    }

}
