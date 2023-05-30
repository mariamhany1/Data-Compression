import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Enter a message: ");
        Scanner input = new Scanner(System.in);
        String message = input.nextLine();
        Data data = new Data(message);
        Encoder encoder = new Encoder(data);
        encoder.encode();
//        PrintWriter output_file = new PrintWriter("C:\\Users\\DELL\\OneDrive\\Documents\\GitHub\\ArithmeticBinaryCoding\\f.txt");
//        Decoder decoder = new Decoder(3, "01001100");
//        output_file.println("your decoded code is : " + decoder.decode());
//        output_file.close();
//        System.out.println("your decoded code is : " + decoder.decode());
    }
}