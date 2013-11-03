package giltchallengejava;

import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author osullivangerard
 */
public class Clause {

    private final Set<Integer> literals;

    public Clause(Set literals) {
        this.literals = literals;
    }

    public boolean satisfiesClause(Set<Integer> literalsAssignment) {
        Boolean satisfied = false;
        Boolean foundOverlap = false;
        Iterator<Integer> literalsAssignmentIter = literalsAssignment.iterator();
        while (literalsAssignmentIter.hasNext() && !satisfied) {
            Integer currAssignment = literalsAssignmentIter.next();
            Iterator<Integer> literalsIter = literals.iterator();
            while (literalsIter.hasNext() && !satisfied) {
                Integer currLiteral = literalsIter.next();
                if (currAssignment.equals(currLiteral)) {
                    satisfied = true;
                } else if ((currAssignment.intValue() * -1) == currLiteral.intValue()) {
                    foundOverlap = true;
                }
            }
        }
        if (!foundOverlap) {
            satisfied = true;
        }
        return satisfied;
    }

    public String toString() {
        Iterator<Integer> literalsIter = literals.iterator();
        Boolean isFirst = true;
        String retString = "";
        while (literalsIter.hasNext()) {
            Integer currLiteral = literalsIter.next();
            if (isFirst) {
                isFirst = false;
                retString = currLiteral.toString();
            } else {
                retString = retString + " OR " + currLiteral;
            }
        }
        return retString;
    }
}
