import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Encoder {
    Data data;

    Encoder(Data data){
        this.data = data;
    }
    public boolean needsScaling(double lower, double upper){  //needsScaling(0.5, 0.625)
        if(lower <= 0.5 && upper >= 0.5){
            return false;
        }
        else if ((lower < 0.5 && upper < 0.5) || lower > 0.5 && upper > 0.5){
            return true;
        }
        return false;
    }
    public void encode(){
        File file = new File("encode.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(fw);
        double probability = 0.0, lowRange = 0.0 , highRange = 0.0;
        Probabilities p;
        for(int i = 0; i < data.message.length(); i++){
            if(data.frequencies.containsKey(data.message.charAt(i))){
                int value = data.frequencies.get(data.message.charAt(i));
                data.frequencies.put(data.message.charAt(i), value + 1);
            }
            else{
                data.frequencies.put(data.message.charAt(i), 1);
            }
        }
        List<Probabilities> l = new ArrayList<Probabilities>();
        for (Map.Entry<Character, Integer> entry: data.frequencies.entrySet()){ // entry=2   A
            probability = (double)entry.getValue()/ (double) data.message.length(); // 2/4=0.5
            lowRange = highRange; //0.0
            highRange = probability + lowRange; //0.25+0.75=0.5
            p = new Probabilities(entry.getKey(), probability, lowRange, highRange); // C, 0.25, 0.75, 1
            l.add(p);   //Bnkhznhom fi list 3shan n3rf nhothom fi map btshel el probabilities
        }

        for (Probabilities prob : l){
            data.probabilities.put(prob.character, new ArrayList<Double>(Arrays.asList(prob.probability, prob.lowRange, prob.highRange)));
        }
        //System.out.println(data.probabilities);
        double lower = 0, upper = 0;
        for(int i = 0; i < data.message.length();i++){    // i=3   char=C    ACBA
            if(i == 0){
                for (Map.Entry<Character, List<Double>> entry : data.probabilities.entrySet()){
                    if(entry.getKey() == data.message.charAt(i)){
                        List<Double> list = entry.getValue();
                        lower = list.get(1); // 0.0
                        upper = list.get(2); // 0.5
                    }
                }
            }
            else{
                for (Map.Entry<Character, List<Double>> entry : data.probabilities.entrySet()){
                    if(entry.getKey() == data.message.charAt(i)){
                        List<Double> list = entry.getValue();
                        upper = lower + (upper - lower)* list.get(2); // 0.5 + (0.75 - 0.5) * 0.5 = 0.625
                        lower = lower + (upper - lower)* list.get(1); // 0.5 + (0.75 - 0.5) * 0.0 = 0.5
                    }
                }
            }
            while(needsScaling(lower, upper)){  //needsScaling(0.5, 0.625)
                if(lower < 0.5 && upper < 0.5){   //E1
                    data.encoded = data.encoded.concat("0");
                    lower = lower*2; // 0.4375*2 = 0.875
                    upper = upper*2; // 0.46875 * 2 = 0.9375
                }
                else if(lower > 0.5 && upper > 0.5){ //E2
                    data.encoded = data.encoded.concat("1");
                    lower = (lower - 0.5)*2; // (0.75 - 0.5) * 2 = 0.5
                    upper = (upper - 0.5)*2; // (0.875 - 0.5) * 2 = 0.75
                }
            }

        }
        System.out.println("Code = " + data.encoded);
        for(int i =0; i < data.encoded.length(); i++)
        {
            pw.print(" "+data.encoded.charAt(i));
        }
        pw.print("\n");

        for (Map.Entry<Character, List<Double>> entry : data.probabilities.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        for (Map.Entry<Character, List<Double>> entry : data.probabilities.entrySet()) {
            pw.println(entry.getKey() + " " + entry.getValue());
        }
        int compressed_size = data.encoded.length() ;
        int original_size = data.message.length() * 8;
        data.compressedSize = "compressed size = " + compressed_size + " bits";
        data.originalSize = "original size = " + original_size + " bits";
        pw.print(data.compressedSize+ "\n");
        pw.print(data.compressedSize + "\n");
        System.out.println(data.compressedSize);
        System.out.println(data.originalSize);

        pw.close();

    }
}
