public class TextGenerator {
    private int k, T;
    private String input;
    private String output;
    private String [] track = new String [2];
    
    
    TextGenerator(int args, int args2){
    	this.k = args;
        this.T = args2;
        // INPUT STRING TO CREATE A NEW TEXTGENERATOR HERE//
        this.input = "gagggagaggcgagaaa";
        this.output = this.input.substring(0,this.k);
        this.track[0] = this.input.substring(0,1);
        this.track[1] = this.input.substring(1,2);
        MarkovModel x = new MarkovModel(this.input,this.k);
        
        
        while (this.T > 2) {
        	String one = this.track[0];
        	String two = this.track[1];
        	char c = x.random(one + two);
        	String s = String.valueOf(c);
        	this.output	+= s;
        	this.track[0] = this.track[1];
        	this.track[1] = s;
        	this.T = this.T - 1;
        }
    }
    
    public String getString() {
    	return this.output;
    }

    public static void main(String[] args){
        TextGenerator object = new TextGenerator(2,12);
        System.out.println(object.getString());
    }
}