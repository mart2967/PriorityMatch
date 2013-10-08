import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class JobMatcher {

    public static void main(String[] args) throws FileNotFoundException {
        String path = "/home/mart2967/git/AlgorithmsLab5/AlgorithmsLab5/src/testData.txt";
        ArrayList<ArrayList<Character>> companies = null;
        ArrayList<ArrayList<Character>> programmers = null;
        companies = parseInput(path, true);
        programmers = parseInput(path, false);
        System.out.println(companies);
        System.out.println(programmers);

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
        
        while(programmersToCompanies.containsValue(null)) {
            for (Character company = 'A'; company-65 < companies.size(); company++){
                System.out.println("current company: " + company);
                if(programmersToCompanies.containsValue(company)) {
                    System.out.println(company + " already has a programmer");
                    continue;
                }
                Character targetProgrammer = getNextProgrammer(company, offerHistory, companies);
                System.out.println("next programmer: " + targetProgrammer);
                if (targetProgrammer != null) {

                    if (programmersToCompanies.get(targetProgrammer) == null) {
                        programmersToCompanies.put(targetProgrammer, company);
                        System.out.println(company + " is assigned free programmer " + targetProgrammer);
                        
                    } else {
                        char otherCompany = programmersToCompanies.get(targetProgrammer);
                        System.out.println(targetProgrammer + " is already assigned to company " + otherCompany);
                        if (programmers.get(targetProgrammer-49).indexOf(company) < programmers.get(targetProgrammer-49).indexOf(otherCompany)) {
                            programmersToCompanies.put(targetProgrammer, company);
                            System.out.println("programmer " + targetProgrammer + " prefers company " + company + " to " + otherCompany);
                        }else{
                            System.out.println("programmer " + targetProgrammer + " does not prefer company " + company + " to " + otherCompany + " and declines");
                        }
                    }
                    offerHistory.get(company).add(targetProgrammer);

                }

            }
            System.out.println("Pairs after round: " + programmersToCompanies);
            System.out.println("Offer history: " + offerHistory);
        }
        return convertToString(programmersToCompanies);

    }

    public static ArrayList<Character> getFreeCompanies(HashMap<Character, Character> CtoP) {
        ArrayList<Character> output = new ArrayList<Character>();
        Iterator it = CtoP.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (pair.getValue() == null){
                output.add((Character) pair.getValue());
            }
        }
        return output;
    }
    
    //should return the first programmer not already offered a job by company
    private static Character getNextProgrammer(Character company, HashMap<Character, ArrayList<Character>> offerHistory, ArrayList<ArrayList<Character>> companies) {
        for (int i = 1; i < companies.get(company-65).size(); i++) {
            if (!offerHistory.get(company).contains(companies.get(company-65).get(i))) {
                return companies.get(company-65).get(i);
            }
        }
        return null;
    }

    

//    public static String makePairs(ArrayList<ArrayList<Character>> companies, ArrayList<ArrayList<Character>> programmers) {
//        //char[][] pairings = new char[companies.size()][2];
//        HashMap<Character, Character> pairs = new HashMap<Character, Character>();
//        ArrayList<Character> freeCompanies = new ArrayList<Character>();
//        for (ArrayList<Character> col : programmers) {
//            pairs.put(col.get(0), null);
//        }
//        for (ArrayList<Character> col : companies) {
//            freeCompanies.add(col.get(0));
//        }
//        //int c = 0;
//        int i = 0;
//
//        for (int c = 0; c < companies.size(); c++) {
//            char prog = companies.get(c).get(1+i); // highest ranked programmer of company c
//            //if (pairs.get(prog) != null)
//            //System.out.println(prog);
//            if (pairs.get(prog) == null) {
//                pairs.put(prog, companies.get(c).get(0));
//                //freeCompanies.remove(c);
//            } else {
//                char otherCompany = pairs.get(prog);
//                //System.out.println("company " + (char)(c) + ", programmer " + prog);
//                //System.out.println("IF programmers.get(" + (prog-49) + ").indexOf(" + (char)(c+65) + ") > pairs.get(" + prog + ") - 65");
//                if (programmers.get(prog-49).indexOf(c) < programmers.get(prog-49).indexOf(otherCompany)) {
//                    //freeCompanies.add(pairs.get(prog));
//                    pairs.put(prog, companies.get(c).get(0));
//                    //freeCompanies.remove(c);
//                }
//            }
//            //            i++;
//            //            if (c < companies.size()-1) {
//            //                c++;
//            //            } else {
//            //                c = 0;
//            //                i++;
//            //            }
//            //System.out.println("i=" + i+ ", c=" + c);
//        }
//        return convertToString(pairs);
//    }



//    private static boolean unpairedExist(HashMap<Character, Character> pairs) {
//
//
//        Iterator it = pairs.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
//            if (pair.getValue() == null) return true;
//        }
//        return false;
//    }

    private static String convertToString(Map in) {
        String output = "";
        Iterator it = in.entrySet().iterator();
        //JobMatcherTest jit = new JobMatcherTest();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            output += (pairs.getValue() + "" + pairs.getKey() + " ");
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
