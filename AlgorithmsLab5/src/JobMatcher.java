import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class JobMatcher {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<ArrayList<Character>> companies = null;
        ArrayList<ArrayList<Character>> programmers = null;
        companies = parseInput("/home/mart2967/git/AlgorithmsLab5/AlgorithmsLab5/src/testData.txt", true);
        programmers = parseInput("/home/mart2967/git/AlgorithmsLab5/AlgorithmsLab5/src/testData.txt", false);
        System.out.println(companies);
        System.out.println(programmers);



    }

    public static ArrayList<ArrayList<Character>> parseInput(String filename, boolean company) throws FileNotFoundException {
        ArrayList<ArrayList<Character>> output = new ArrayList<ArrayList<Character>>();
        Scanner s = new Scanner(new File(filename));
        int row = 0;
        while(s.hasNextLine()) {
            String line = s.nextLine();
            String[] elements = line.split(" ");
            ArrayList<Character> column = new ArrayList<Character>();
            if (company) {
                for(int col = 0; col < elements.length/2; col++) {
                    column.add(elements[col].charAt(0));
                }

            } else {
                for(int col = elements.length/2; col < elements.length; col++) {
                    column.add(elements[col].charAt(0));
                }
            }
            output.add(column);
            row++;
        }
        s.close();
        return output;
    }

}
