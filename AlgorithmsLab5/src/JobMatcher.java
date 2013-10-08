import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class JobMatcher {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<ArrayList<Character>> companies = null;
        ArrayList<ArrayList<Character>> programmers = null;
        companies = parseInput("/home/mart2967/git/AlgorithmsLab5/AlgorithmsLab5/src/testData.txt", true);
        programmers = parseInput("/home/mart2967/git/AlgorithmsLab5/AlgorithmsLab5/src/testData.txt", false);
        System.out.println(companies);
        System.out.println(programmers);

        String pairings = makePairs(companies, programmers);
        System.out.println(pairings);
    }

    public static String makePairs(ArrayList<ArrayList<Character>> companies, ArrayList<ArrayList<Character>> programmers) {
        char[][] pairings = new char[companies.size()][2];
        HashMap<Character, Character> pairs = new HashMap<Character, Character>();
        for (ArrayList<Character> col : programmers) {
            pairs.put(col.get(0), null);
        }
        for ( int c = 0; c < companies.size(); c++) {
            char prog = companies.get(c).get(1); // highest ranked programmer of company c
            System.out.println(prog);
            if (pairs.get(prog) == null) {
                pairs.put(prog, companies.get(c).get(0));
            } else {
                if (programmers.get(prog-1).indexOf(c+49) > pairs.get(prog)) {
                    pairs.put(prog, companies.get(c).get(0));
                }
            }
        }
        System.out.println(pairs);
        return convertToString(pairs);
    }

   

    private static String convertToString(Map in) {
        String output = "";
        Iterator it = in.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            output += (pairs.getKey() + "" + pairs.getValue() + " ");
            it.remove(); // avoids a ConcurrentModificationException
        }
        return output;
    }

    public static ArrayList<ArrayList<Character>> parseInput(String filename, boolean company) throws FileNotFoundException {
        ArrayList<ArrayList<Character>> output = new ArrayList<ArrayList<Character>>();
        Scanner s = new Scanner(new File(filename));
        int row = 0;
        while(s.hasNextLine()) {
            String line = s.nextLine();
            String[] elements = line.split(" ");
            //ArrayList<Character> column = new ArrayList<Character>();
            
            if (company) {
                for(int col = 0; col < elements.length/2; col++) {
                    if (output.size() <= col) {
                        output.add(new ArrayList<Character>());
                    }
                    output.get(col).add(elements[col].charAt(0));
                }

            } else {
                for(int col = elements.length/2; col < elements.length; col++) {
                    if (output.size() <= col - elements.length/2) {
                        output.add(new ArrayList<Character>());
                    }
                    output.get(col - elements.length/2).add(elements[col].charAt(0));
                }
            }
            
            row++;
        }
        s.close();
        return output;
    }

}
