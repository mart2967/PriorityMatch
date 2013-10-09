import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/*
 * LAB 5
 * Max Marti
 * Katie Reddemann
 * 
 * Behold the algorithm
 * Become the algorithm
 * 
 * 1. The algorithm, contained in the method makePairs(), will return a satisfactory pairing given input of two tables of data.
 *    The input data is generated in parseInput() and is represented by an ArrayList of ArrayLists containing Characters.
 * 2. To test the algorithm, provide a path to a text file formatted like the included file, either as a command line argument or by changing the string in the program.
 *    The included JUnit test file provides a comprehensive test of the algorithm from input sizes n=1 to n=9 on random and example data.
 *    The test file has a method that will check if a pairing is satisfactory.
 * 3. The algorithm is correct because it always stops and outputs a satisfactory pairing. 
 *    It accomplishes this by having several iterations in which programmers are either assigned to companies or switched between them. A company can only make an offer to each programmer once.
 *    Therefore the maximum number of iterations is equal to n, and the program will stop at this time.
 * 4. The maximum run-time of this program can be said to be O(n^2). In the worst case where all preferences are the same, only one programmer is assigned to a company in the first iteration.
 *    Knowing that the total possible number of pairings is n^2, we loop at most n times until each pair is made. In the worst case, only one programmer is assigned.
 *    However, that removes a company from the pool being looped through. If the initial loop costs n offers, then by the second, each programmer has n-1 options left.
 *    Since only one of the programmers can receive an offer from each company, the number of offers that can happen after the first iteration is at most (n-1)+(n-1)(n-2).
 *    Because at least there is one offer per iteration, the total number of iterations cannot exceed 1+(n-1)+(n-1)(n-2) = n^2-2n+2
 *    Therefore the efficiency of this algorithm is O(n^2-2n+2) == O(n^2).
 * 
 */

public class JobMatcher {

    public static void main(String[] args) throws FileNotFoundException {
        // the path can be a command line argument
        String path = (args[0] != null)? args[0]:"/home/mart2967/git/AlgorithmsLab5/AlgorithmsLab5/src/testData.txt";
        // parse the data from the two halves of the test file
        ArrayList<ArrayList<Character>> companies = parseInput(path, true);
        ArrayList<ArrayList<Character>> programmers = parseInput(path, false);
        // generate satisfactory pairing
        String pairings = makePairs(companies, programmers);
        System.out.println(pairings);
    }

    public static String makePairs(ArrayList<ArrayList<Character>> companies, ArrayList<ArrayList<Character>> programmers) {
        HashMap<Character, Character> programmersToCompanies = new HashMap<Character, Character>();
        HashMap<Character, ArrayList<Character>> offerHistory = new HashMap<>();
        for (ArrayList<Character> col : companies) {
            offerHistory.put(col.get(0), new ArrayList<Character>());
        }
        for (ArrayList<Character> col : programmers) {
            programmersToCompanies.put(col.get(0), null);
        }
        // while there is a programmer without a company
        while(programmersToCompanies.containsValue(null)) {
            // iterate through companies
            for (Character company = 'A'; company-65 < companies.size(); company++){
                // skip already paired companies
                if(programmersToCompanies.containsValue(company)) continue;
                // identify top-ranked programmer that has not already been offered a job
                Character targetProgrammer = getNextProgrammer(company, offerHistory, companies);
                // if the desired programmer is free, take them
                if (programmersToCompanies.get(targetProgrammer) == null) {
                    programmersToCompanies.put(targetProgrammer, company);
                } else {
                    // there is a company that has offered the programmer a job. Find them.
                    char otherCompany = programmersToCompanies.get(targetProgrammer);
                    // check if the programmer would prefer to work for the current company
                    if (programmers.get(targetProgrammer-49).indexOf(company) < programmers.get(targetProgrammer-49).indexOf(otherCompany)) {
                        programmersToCompanies.put(targetProgrammer, company);
                    }
                }
                // add the programmer to the list of programmers offered a job
                offerHistory.get(company).add(targetProgrammer);
            }
        }
        // convert the map to a parseable string and return
        return convertToString(programmersToCompanies);

    }

    // returns the first programmer not already offered a job by the company
    private static Character getNextProgrammer(Character company, HashMap<Character, ArrayList<Character>> offerHistory, ArrayList<ArrayList<Character>> companies) {
        for (int i = 1; i < companies.get(company-65).size(); i++) {
            if (!offerHistory.get(company).contains(companies.get(company-65).get(i))) {
                return companies.get(company-65).get(i);
            }
        }
        return null;
    }

    private static String convertToString(Map in) {
        String output = "";
        Iterator it = in.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            output += (pairs.getValue() + "" + pairs.getKey() + " ");
            it.remove();
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
