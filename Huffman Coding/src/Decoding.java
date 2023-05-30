public class Decoding {
    Data data;
    Decoding(Data data){
        this.data = data;
    }
    public void decode() {
        String code = "";
        for(int i = 0; i < data.encoded.length(); i++){
            String value = String.valueOf(data.encoded.charAt(i));
            code = code.concat(value);
            if(data.rcodes.containsKey(code)){
                data.decoded = data.decoded.concat(data.rcodes.get(code));
                code = "";
            }
        }
        System.out.println("Decoded = " + data.decoded);
    }
}
