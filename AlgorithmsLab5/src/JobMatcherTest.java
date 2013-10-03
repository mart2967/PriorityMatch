import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;


public class JobMatcherTest {
    JobMatcher target = new JobMatcher();
    @Test
    public void TestIsSatisfactory() {
        assertFalse(isSatisfactory("A1 B3 C2 D4 E5"));
        assertFalse(isSatisfactory("A2 B1 C5 D3 E4"));
    }

    @Test
    public void testRandomPairing() {
        for (int i = 0; i < 1000; i++) {
            String rp = randomPairing();
            if (isSatisfactory(rp)) {
                System.out.println(rp);
            }
        }
    }
    
    public String randomPairing() {
        Random rand = new Random();
        ArrayList<ArrayList<Character>> c = makeExampleCompanies();
        ArrayList<ArrayList<Character>> p = makeExampleProgrammers();
        String output = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                
                if (c.size() == 0 || p.size() == 0) break;
                
                if (j != 0 || i != 0) {
                    output += " ";
                }
                output += c.remove(rand.nextInt(c.size())).get(0);
                output += p.remove(rand.nextInt(p.size())).get(0);
            }
            
            
        }
        return output;
    }
    
    private boolean isSatisfactory(String s) {
        boolean output = true;
        ArrayList<ArrayList<Character>> c = makeExampleCompanies();
        ArrayList<ArrayList<Character>> p = makeExampleProgrammers();
        ArrayList<String> matches = new ArrayList<String>(Arrays.asList(s.split(" ")));
        //System.out.println("########## " + s + " ##########");
        for (int i = 0; i < matches.size(); i++) {
            char[] pair1 = new char[2];
            pair1[0] = matches.get(i).charAt(0);
            pair1[1] = matches.get(i).charAt(1);
            ArrayList<String> otherMatches = cloneList(matches);
            otherMatches.remove(i);

            for (int j = 0; j < otherMatches.size(); j++) {
                char[] pair2 = new char[2];
                pair2[0] = otherMatches.get(j).charAt(0);
                pair2[1] = otherMatches.get(j).charAt(1);

                
                // subtract 49 to convert 1-indexed chars to 0 indexed integers, 65 to convert capital letters to 0-indexed integers
                if ( (p.get(pair1[1]-49).indexOf(pair1[0]) < p.get(pair1[1]-49).indexOf(pair2[0])) ) { //P1 ranks C2 higher than C1 
                    if( (c.get(pair2[0]-65).indexOf(pair2[1]) < c.get(pair2[0]-65).indexOf(pair1[1])) ) { //C2 ranks P1 higher than P2
                        //System.out.print("UNSATISFACTORY: ");
                        output = false;
                    }
                }
                //System.out.println("[" + pair1[0] + "" + pair1[1] + "] [" + pair2[0] + "" + pair2[1] + "]");
            }
        }
        return output;
    }

    //private int innerArrayIndex(char startOfInnerArray) {
//
    //}

    private ArrayList<ArrayList<Character>> makeExampleCompanies() {
        ArrayList<ArrayList<Character>> data = new ArrayList<ArrayList<Character>>();

        data.add(new ArrayList<Character>(Arrays.asList('A', '2', '5', '1', '3', '4')));
        data.add(new ArrayList<Character>(Arrays.asList('B', '1', '2', '3', '4', '5')));
        data.add(new ArrayList<Character>(Arrays.asList('C', '5', '3', '2', '1', '4')));
        data.add(new ArrayList<Character>(Arrays.asList('D', '1', '3', '2', '4', '5')));
        data.add(new ArrayList<Character>(Arrays.asList('E', '2', '3', '5', '4', '1')));

        return data;
    }

    private ArrayList<ArrayList<Character>> makeExampleProgrammers() {
        ArrayList<ArrayList<Character>> data = new ArrayList<ArrayList<Character>>();

        data.add(new ArrayList<Character>(Arrays.asList('1', 'E', 'A', 'D', 'B', 'C')));
        data.add(new ArrayList<Character>(Arrays.asList('2', 'D', 'E', 'B', 'A', 'C')));
        data.add(new ArrayList<Character>(Arrays.asList('3', 'D', 'B', 'C', 'E', 'A')));
        data.add(new ArrayList<Character>(Arrays.asList('4', 'C', 'B', 'D', 'A', 'E')));
        data.add(new ArrayList<Character>(Arrays.asList('5', 'A', 'D', 'B', 'C', 'E')));

        return data;
    }

    public static ArrayList<String> cloneList(ArrayList<String> input) {
        ArrayList<String> output = new ArrayList<String>();
        for (String i: input) {
            output.add(i);
        }
        return output;
    }

}
