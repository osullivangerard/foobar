package giltchallengejava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author osullivangerard
 */
public class WSatSolver {

    private final ArrayList<Clause> clausesList;
    private final int numberOfVariables;

    public WSatSolver(ArrayList<Clause> clausesList, int numberOfVariables) {
        this.clausesList = clausesList;
        this.numberOfVariables = numberOfVariables;
    }

    public String solve() {
        Boolean solved = false;
        
        Set<Integer> currentAssignments = new HashSet<>();
        String currAssignStr = "";
        for (int i=1; i <= numberOfVariables; i++) {
            currentAssignments.add(i);
            currAssignStr = currAssignStr + " " + i;
        }
        
        for (int j=0; j < clausesList.size(); j++) {
            if (clausesList.get(j).satisfiesClause(currentAssignments)) {
                System.out.println("Clause " + clausesList.get(j).toString() + " satisfied by assignment " + currAssignStr);
            } else {
                System.out.println("Clause " + clausesList.get(j).toString() + " NOT satisfied by assignment " + currAssignStr);
            }
        }
        
        return "";
    }
}
