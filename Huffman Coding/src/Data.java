import java.util.HashMap;

public class Data {
    String message, encoded, decoded, compressedSize, originalSize, entropy, overHead, H;
    HashMap<Character, Integer> frequencies;
    HashMap<String, String> codes, rcodes;
    Data(String message){
        this.message = message;
        frequencies = new HashMap<>();
        codes = new HashMap<>();
        rcodes = new HashMap<>();
        encoded = "";
        decoded = "";
        compressedSize="";
        originalSize="";
        entropy="";
        H="";
    }

}
