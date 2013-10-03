import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;


public class JobMatcherTest {

    @Test
    public void isSatisfactory() {
        assertTrue(isSatisfactory(""));
    }
    
    private boolean isSatisfactory(String s) {
        ArrayList<String> matches = new ArrayList<String>(Arrays.asList(s.split(" ")));
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
            }
        }
        
        return false;
    }
    
    private ArrayList<ArrayList<Character>> makeCompanies() {
        ArrayList<ArrayList<Character>> data = new ArrayList<ArrayList<Character>>();
        
        data.add(new ArrayList<Character>(Arrays.asList('A', '2', '5', '1', '3', '4')));
        data.add(new ArrayList<Character>(Arrays.asList('B', '1', '2', '3', '4', '5')));
        data.add(new ArrayList<Character>(Arrays.asList('C', '5', '3', '2', '1', '4')));
        data.add(new ArrayList<Character>(Arrays.asList('D', '1', '3', '2', '4', '5')));
        data.add(new ArrayList<Character>(Arrays.asList('E', '2', '3', '5', '4', '1')));
        
        return data;
    }
    
    private ArrayList<ArrayList<Character>> makeProgrammers() {
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
