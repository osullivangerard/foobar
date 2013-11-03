package giltchallengejava;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author osullivangerard
 */
public class GiltChallengeJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // verify that the input filename has been passed as a command line argument
        if (args.length < 1) {
            System.out.println("Please specify input filename as an argument.");
        } else {
            // Array to hold the boolean clauses representing each customer's paint preferences
            ArrayList<Clause> clausesList = new ArrayList<>();
            // Number of paints to prepare, as specified in the first line of the input file
            int numberOfPaints = -1;

            try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
                String currentLine;
                Boolean isFirstLine = true;
                // We use this invalid input flag to indicate when we should stop processing the input file due to invalid contents
                Boolean invalidInput = false;

                // Read the input file one line at a time
                while (((currentLine = br.readLine()) != null) && !invalidInput) {

                    if (isFirstLine) {
                        // the first line indicates the number of paints
                        isFirstLine = false;
                        try {
                            numberOfPaints = Integer.parseInt(currentLine);
                        } catch (NumberFormatException e) {
                            invalidInput = true;
                            System.out.println("Error parsing number from input file: " + e.getLocalizedMessage());
                        }
                    } else {
                        // subsequent lines each represent a customer's preferences
                        // Verify that the input line is divisible by 2 as a first check for valid input, e.g. sample expected format is 1G2M3G
                        if (currentLine.length() % 2 != 0) {
                            invalidInput = true;
                            System.out.println("Invalid input line: " + currentLine);
                        } else {
                            // split the input line into segments 2 characters in length
                            String[] currCustomer = currentLine.split("(?<=\\G.{2})"); // regex to split a string into segments 2 characters in length

                            // we will use a Set to store the paint preferences for this customer as a way of removing duplicates if they are present on the one input line
                            HashSet customerSet = new HashSet();

                            try {

                                // loop through the segments for this customer
                                int i = 0;
                                while ((i < currCustomer.length) && !invalidInput) {

                                    // check that the first character of this segment is an integer that is between 1 and the number of paints
                                    int currPaint = Integer.parseInt(currCustomer[i].substring(0, 1));
                                    if ((currPaint < 1) || (currPaint > numberOfPaints)) {
                                        invalidInput = true;
                                        System.out.println("Error parsing input line (invalid paint number): " + currCustomer[i]);
                                    }

                                    // check that the second character of this segment is either 'M' or 'G'
                                    String currPaintType = currCustomer[i].substring(1, 2);
                                    if ((!currPaintType.equals("M")) && (!currPaintType.equals("G"))) {
                                        invalidInput = true;
                                        System.out.println("Error parsing input line (invalid paint type): " + currCustomer[i]);
                                    }

                                    // if there were no validation errors on the segment, we will store the segment in our set for this customer
                                    if (!invalidInput) {
                                        // we will store a positive literal (paint type G) using the paint number, e.g. 2G is stored as 2
                                        // we will store a negative literal (paint type M) using the paint number negated, e.g. 2M is stored as -2
                                        int storedCustomerPref = currPaint;
                                        if (currPaintType.equals("M")) {
                                            storedCustomerPref = -storedCustomerPref;
                                        }
                                        if (!customerSet.contains(storedCustomerPref)) {
                                            customerSet.add(storedCustomerPref);
                                        }
                                    }
                                    i++;
                                }

                            } catch (NumberFormatException e) {
                                invalidInput = true;
                                System.out.println("Error parsing number from input line: " + e.getLocalizedMessage());
                            }
                            if (!invalidInput) {
                                // add the preferences for this customer to the list of boolean clauses
                                clausesList.add(new Clause(customerSet));
                            }
                        }
                    }

                }

                // run the weighted sat solver on the set of boolean clauses
                WSatSolver wSatSolver = new WSatSolver(clausesList, numberOfPaints);
                String solution = wSatSolver.solve();
                if (solution != null) {
                    System.out.println(solution);
                } else {
                    System.out.println("No solution exists");
                }

            } catch (IOException e) {
                System.out.println("Error reading input file: " + e.getLocalizedMessage());
            }
        }
    }

}
