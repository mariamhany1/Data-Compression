import java.util.HashMap;
import java.util.Map;

public class Huffman {
    private Node root;
    private final String text;
    private Map<Character, Integer> frequencies;
    private Map<Character, String> codes;

    public Huffman(String text)
    {
        this.text = text;

    }
    public void calculateFrequencies(){
        frequencies = new HashMap<>();
        for(char character : text.toCharArray())
        {
            frequencies.put(character, frequencies.get(character) + 1);

        }
    }
}
