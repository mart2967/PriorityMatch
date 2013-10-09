import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

public class JobMatcherTest {
    
    @Test
    public void TestKnownFalse() {
        assertFalse(isSatisfactory("A1 B3 C2 D4 E5", makeExampleCompanies(), makeExampleProgrammers()));
        assertFalse(isSatisfactory("A2 B1 C5 D3 E4", makeExampleCompanies(), makeExampleProgrammers()));
    }
    
    @Test
    public void testSampleData(){
        ArrayList<ArrayList<Character>> c = makeExampleCompanies();
        ArrayList<ArrayList<Character>> p = makeExampleProgrammers();
        String result = JobMatcher.makePairs(c, p);
        assertTrue(isSatisfactory(result, c, p));
    }
    
    @Test
    public void testRandomData() {
        for (int i = 0; i < 100; i++) {
            for (int j = 1; j < 10; j++) {
                ArrayList<ArrayList<Character>> c = randomCompanies(j);
                ArrayList<ArrayList<Character>> p = randomProgrammers(j);
                String result = JobMatcher.makePairs(c, p);
                assertTrue(isSatisfactory(result, c, p));
            }
        }
    }
    
    @Test
    public void testParseData() {
        ArrayList<ArrayList<Character>> companies = null;
        ArrayList<ArrayList<Character>> programmers = null;
        try {
            companies = JobMatcher.parseInput("/home/mart2967/git/AlgorithmsLab5/AlgorithmsLab5/src/testData.txt", true);
            programmers = JobMatcher.parseInput("/home/mart2967/git/AlgorithmsLab5/AlgorithmsLab5/src/testData.txt", false);
        } catch (FileNotFoundException e) {
            System.out.println();
        }
        
        assertEquals(makeExampleCompanies(), companies);
        assertEquals(makeExampleProgrammers(), programmers);
    }
    
    public ArrayList<ArrayList<Character>> randomCompanies(int size) {
        ArrayList<ArrayList<Character>> output = new ArrayList<ArrayList<Character>>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            ArrayList<Character> column = new ArrayList<Character>();
            ArrayList<Character> rankings = new ArrayList<Character>();
            for (int x = 0; x < size; x++) {
                rankings.add((char) (x+49));
            }
            for (int j = 0; j < size + 1; j++) {
                if (j == 0) {
                    column.add((char) (i+65));
                } else {
                    Character r = rankings.get(rand.nextInt(rankings.size()));
                    column.add(r);
                    rankings.remove(r);
                }
            }
            output.add(column);
        }
        return output;
    }
    
    public ArrayList<ArrayList<Character>> randomProgrammers(int size) {
        ArrayList<ArrayList<Character>> output = new ArrayList<ArrayList<Character>>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            ArrayList<Character> column = new ArrayList<Character>();
            ArrayList<Character> rankings = new ArrayList<Character>();
            for (int x = 0; x < size; x++) {
                rankings.add((char) (x+65));
            }
            for (int j = 0; j < size + 1; j++) {
                if (j == 0) {
                    column.add((char) (i+49));
                } else {
                    Character r = rankings.get(rand.nextInt(rankings.size()));
                    column.add(r);
                    rankings.remove(r);
                }
            }
            output.add(column);
        }
        return output;
    }
    
    private String randomPairing() {
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
    
    private boolean isSatisfactory(String s, ArrayList<ArrayList<Character>> companies, ArrayList<ArrayList<Character>> programmers) {
        boolean output = true;
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

                
                // subtract 49 to convert 1-indexed chars to 0 indexed integers, 65 to convert capital letters to 0-indexed integers
                if ( (programmers.get(pair1[1]-49).indexOf(pair1[0]) > programmers.get(pair1[1]-49).indexOf(pair2[0])) ) { //P1 ranks C2 higher than C1 
                    if( (companies.get(pair2[0]-65).indexOf(pair2[1]) > companies.get(pair2[0]-65).indexOf(pair1[1])) ) { //C2 ranks P1 higher than P2
                        //System.out.print("UNSATISFACTORY: ");
                        output = false;
                    }
                }
                //System.out.println("[" + pair1[0] + "" + pair1[1] + "] [" + pair2[0] + "" + pair2[1] + "]");
            }
        }
        return output;
    }

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

    private static ArrayList<String> cloneList(ArrayList<String> input) {
        ArrayList<String> output = new ArrayList<String>();
        for (String i: input) {
            output.add(i);
        }
        return output;
    }

}
