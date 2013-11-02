package giltchallengejava;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author osullivangerard
 */
public class Clause {

    private final ArrayList<Integer> literals;

    public Clause(ArrayList literals) {
        this.literals = literals;
    }

    public void print() {
        Iterator<Integer> literalsIter = literals.iterator();
        Boolean isFirst = true;
        while (literalsIter.hasNext()) {
            Integer currLiteral = literalsIter.next();
            if (isFirst) {
                isFirst = false;
                System.out.print(currLiteral);
            } else {
                System.out.print(" OR " + currLiteral);
            }
        }
        System.out.println();
    }
}
