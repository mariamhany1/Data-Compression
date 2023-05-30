import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Encoding {
    Data data;
    Encoding(Data data){
        this.data = data;
    }
    void generate(Node root, String code){
        if(root.getLeftNode() == null && root.getRightNode() == null){
            if(code.length() == 0){
                code = code.concat("0");
            }
            data.codes.put(root.getString(), code);
            data.rcodes.put(code, root.getString());
            return;
        }
        generate(root.getLeftNode(), code.concat("1"));
        generate(root.getRightNode(), code.concat("0"));
    }
    public static int log2(double x) {
        int a = 1, cnt = 0;
        while (x > a + 1e-9){
            a *= 2;
            cnt++;
        }
        return cnt;
    }
    public void encode(){
        File file = new File("file.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(fw);
        for(int i = 0; i < data.message.length(); i++){
            if(data.frequencies.containsKey(data.message.charAt(i))){
                int value = data.frequencies.get(data.message.charAt(i));
                data.frequencies.put(data.message.charAt(i), value + 1);
            }
            else{
                data.frequencies.put(data.message.charAt(i), 1);
            }
        }
        Queue<Node> queue = new PriorityQueue<>();
        for(Map.Entry<Character, Integer> entry : data.frequencies.entrySet()){
            queue.add(new Node(String.valueOf(entry.getKey()), entry.getValue(), null, null));
        }

        while (queue.size() > 1){
            Node node1 = queue.poll();
            Node node2 = queue.poll();
            String result = node1.getString();
            result = result.concat(node2.getString());
            queue.add(new Node(result, node1.getFrequency() + node2.getFrequency(), node1, node2));
        }
        generate(queue.poll(), "");
        for(int i = 0; i < data.message.length();i++){
//            System.out.println(data.codes.get(String.valueOf(data.message.charAt(i))));
            String value = data.codes.get(String.valueOf(data.message.charAt(i)));
            data.encoded = data.encoded.concat(value);
        }

        int sum = 0;
        for(Map.Entry<Character, Integer> entry : data.frequencies.entrySet())
        {
            sum = sum + entry.getValue();
        }

        int originalSize = data.message.length() * ((int)log2(data.frequencies.size()) + 1);
        int compressedSize = data.encoded.length();
        double entropy = 0;
        for(Map.Entry<Character, Integer> entry : data.frequencies.entrySet()){
            double val = (double) entry.getValue() / data.message.length();
            entropy += val * Math.log((double) data.message.length() / entry.getValue()) / Math.log(2);
        }


        data.originalSize = "original size = " + originalSize + " bits";
        pw.print("original size = " + originalSize + " bits"+ "\n");
        System.out.println("original size = " + originalSize + " bits");
        //////////////////////////////////////////////////////////////////////
        data.compressedSize = "compressed size = " + compressedSize + " bits";
        pw.print("compressed size = " + compressedSize + " bits"+ "\n");
        System.out.println("compressed size = " + compressedSize + " bits");
        /////////////////////////////////////////////////////////////////////
        data.entropy = "Entropy = " + entropy + " bits/symbol";
        pw.print("Entropy = " + entropy + " bits/symbol"+ "\n");
        System.out.println("Entropy = " + entropy + " bits/symbol");
        ////////////////////////////////////////////////////////////////////
        data.H = "H = " + entropy * data.message.length() + " bits";
        pw.print("H = " + entropy * data.message.length() + " bits"+ "\n");
        System.out.println("H = " + entropy * data.message.length() + " bits");
        //////////////////////////////////////////////////////////////////
        pw.print("Code = " + data.encoded);
        System.out.println("Code = " + data.encoded);
        pw.close();

    }
}
