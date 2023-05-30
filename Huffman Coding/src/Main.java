import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.println("Enter a message: ");
        Scanner input = new Scanner(System.in);
        String message = input.nextLine();
        Data data = new Data(message);
        Encoding encoder = new Encoding(data);
        encoder.encode();
        Decoding decoder = new Decoding(data);
        decoder.decode();


//        String res = "1111111111111111111111111111111111101010101010101010101010101010101010101010100010010010010010010010010010010010010010010010000000000000000000000000000000000000000000000000000000001010101010101010101010101010101010101010101010101010101010100010001";
//        if(data.encoded.equals(res)){
//            System.out.println("YES");
//        }

        //aaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbcccccccccccccccddddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeff
    }
}