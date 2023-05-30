import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
public class Decoder {
    Scanner sc = new Scanner(System.in);
    int compressed_code;
    ArrayList<Character> chr;
    //ArrayList<Double> probability;
    ArrayList<Double> lower;
    ArrayList<Double> upper;
    String s;

    public Decoder(int n, String s) {
        compressed_code = n;
        this.s = s;
        chr=new ArrayList<Character>();
        //probability = new ArrayList<Double>();
        lower = new ArrayList<Double>();
        upper = new ArrayList<Double>();
    }

    public void add_char_value_upper_and_lower() {
        try {
            File myObj = new File("C:\\Users\\DELL\\OneDrive\\Documents\\GitHub\\ArithmeticBinaryCoding\\file.txt" );
            Scanner myReader = new Scanner(myObj);
            for (int i = 0; i < compressed_code; i++) {
                char c = myReader.next().charAt(0);
                //double prob = myReader.nextDouble();
                double low = myReader.nextDouble();
                double high = myReader.nextDouble();
                chr.add(c);
                //probability.add(prob);
                lower.add(low);
                upper.add(high);

            }


            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public double smallest_range(){
        add_char_value_upper_and_lower();
        double range = 1;
        for(int i = 0; i < compressed_code; i++){
            if(upper.get(i) - lower.get(i) < range)
                range = upper.get(i) - lower.get(i);
        }
        return range;

    }
    public int range_k(){
        double x = smallest_range();
        double temp = 1;
        int k = 1;
        while (temp > x) {

            temp = 1 / (Math.pow(2, k));
            k++;
        }
        return k-1;
    }
    public String decode(){
        int cnt = 0;
        String text = "";
        String temp = "";
        int k = range_k();
        double low=0;
        double high=0;
        int start=0;
        boolean ok=true;
        while (ok) {
            if (start==k)
                ok=false;
            for (int i = start; i < k + start; i++) {
                temp = temp + s.charAt(i);
            }
            double ans = 0;
            for (int i = 0; i < temp.length(); i++) {
                if (temp.charAt(i) == '1') {
                    ans = ans + Math.pow(2, temp.length() - i-1);
                }
            }

            if (cnt != 0) {
                ans = (ans / (Math.pow(2, k)));
                ans=((ans-low)/(high-low));
                for (int i = 0; i <compressed_code; i++) {
                    if (ans > lower.get(i) && ans < upper.get(i)) {
                        double low2=low;
                        double high2=high;
                        high = upper.get(i);
                        low = lower.get(i);
                        text=text + chr.get(i);
                        low=low2+(high2-low2)*low;
                        high=low2+(high2-low2)*high;
                        break;
                    }
                }
            } else {
                ans = (ans / (Math.pow(2, k)));
                for (int i = 0; i < compressed_code; i++) {
                    if (ans > lower.get(i) && ans < upper.get(i)) {
                        high = upper.get(i);
                        low = lower.get(i);
                        text = text + chr.get(i);
                        low=0+(1)*low;
                        high=0+(1)*high;
                        cnt++;
                        break;
                    }
                }

            }
            while (true) {
                if (low > 0.5) {
                    low = (low - 0.5) * 2;
                    high = (high - 0.5) * 2;
                    start++;
                } else if (high < 0.5) {

                    high = high * 2;
                    low = low * 2;
                    start++;
                } else {
                    temp="";
                    break;
                }
            }
        }

        return text;
    }

}

