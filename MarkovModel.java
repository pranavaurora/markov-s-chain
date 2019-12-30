import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class MarkovModel {
	
	int k;
	int remainder;
	String text;
	int textLength; 
	Map<String, TreeMap<String, Integer>> main = new TreeMap<String, TreeMap<String, Integer>>();

    // creates a Markov model of order k for the specified text
    public MarkovModel(String text, int k) {
    	this.k = k;
    	this.remainder = text.length() % k;
    	if (this.remainder == 0) {
    		char c = text.charAt(0);
    		String s = String.valueOf(c); 
    		this.text = text + s;
    	}
    	else {
    		this.text = text + text.subSequence(0, this.remainder + 1);
    	}
    	this.textLength = this.text.length();
    	this.text = this.text.replaceAll("\\s+","");


    	
    	for (int i = 0; i < this.text.length() - k; i++) {
    		String newString = this.text.substring(i, i+k);
    		String s;
    		char nextchar = this.text.charAt(i+k);
    		s = String.valueOf(nextchar); 

    		
    		if (!main.containsKey(newString)) {
    			TreeMap<String, Integer> subset = new TreeMap <String, Integer>();
    			subset.put(s, 1);
    			main.put(newString, subset);
    		}
    		else {
    			TreeMap<String, Integer> subset= main.get(newString);
    			if (!subset.containsKey(s)) {
    				subset.put(s, 1);
    			}
    			else {
    				subset.put(s, subset.get(s)+1);
    			}
    		}
    	}
    	
    }

    // returns the order k of this Markov model
    public int order() {
    	return k;
    }

    // returns a string representation of the Markov model (as described below)
    public String toString() {
    	String total = "";
    	for (String lol : main.keySet()) {
    		String one = lol + " ";
    		TreeMap<String, Integer> subset = main.get(lol);
    		for (String chars : subset.keySet()) {
    			String two = chars + " " + subset.get(chars);
    			one = one + two + " ";
    		}
    		total = total + one + System.lineSeparator();
    	}
    	return total;
    }

    // returns the number of times the specified kgram appears in the text
    public int freq(String kgram) {
    	int total = 0;
    	if (main.containsKey(kgram)) {
    		TreeMap <String, Integer> subset = main.get(kgram);
    		for (String main2 : subset.keySet()) {
        			total = total + subset.get(main2);
    		}
        	return total;
    	}
    	return total;
    }

    // returns the number of times the character c follows the specified
    // kgram in the text
    public int freq(String kgram, char c) {
        TreeMap<String, Integer> lol = main.get(kgram);
		String cnew = String.valueOf(c);  
        return lol.get(cnew);
    }
    

    // returns a random character that follows the specified kgram in the text,
    // chosen with weight proportional to the number of times that character
    // follows the specified kgram in the text
    public char random(String kgram) {
    	if (kgram != null && main.containsKey(kgram)) {
        	int total = this.freq(kgram);
            Random rand = new Random(); 
            int low = 1;
            int high = total;
            int rand_int1 = rand.nextInt(high-low+1) + low;
            int cumm = 0; 
            String s = null;
            char c = 0;
    	   	TreeMap<String, Integer> map1 = main.get(kgram);        
            for (String chars : map1.keySet()) {
            	cumm = cumm + map1.get(chars);
            	if (cumm >= rand_int1) {
            		s = chars;
            		break;
            	}
            }        

            if (s!= null) {
                 c = s.charAt(0);
            }
            return c;
    	}
    	return 0;
    }
    // tests this class by directly calling all instance methods
    public static void main(String[] args) {
        String text1 = "banana";
        String text2 = "gagggagaggcgagaaa";
        String text3 = "abc";
        MarkovModel model1 = new MarkovModel(text1, 2);
        MarkovModel model2 = new MarkovModel(text2, 2);
        MarkovModel model3 = new MarkovModel(text3, 3);
        System.out.println(model1.random("ag"));
        String output = model1.toString();
        String output2 = model2.toString();
        String output3 = model3.toString();
        System.out.println(output);
        System.out.println(output2);
        System.out.println(output3);

    }
}