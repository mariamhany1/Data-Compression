import java.util.HashMap;
import java.util.List;

public class Data {
    String message, encoded, decoded, compressedSize, originalSize;
    HashMap<Character, Integer> frequencies;
    HashMap<Character, List<Double>> probabilities;
    Data(String message){
        this.message = message;
        frequencies = new HashMap<>();
        probabilities = new HashMap<>();
        encoded = "";
        decoded = "";
        compressedSize="";
        originalSize="";

    }
}
