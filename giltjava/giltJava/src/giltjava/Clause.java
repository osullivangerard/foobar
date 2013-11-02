/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giltjava;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author gerard
 */
public class Clause {

    private final ArrayList<String> literals;

    public Clause(ArrayList literals) {
        this.literals = literals;
    }

    public void print() {
        Iterator<String> literalsIter = literals.iterator();
        while (literalsIter.hasNext()) {
            String currLiteral = literalsIter.next();
            System.out.println(currLiteral);
        }
    }
}
