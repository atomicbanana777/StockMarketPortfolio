package org.example.portfolio.factory;

import org.example.portfolio.interfaceClass.OptionFormula;
import org.example.simpleOptionFormula.SimpleOptionFormula;

public class OptionFormulaFactory {
    public OptionFormula geOptionFormula(String name){
        OptionFormula res = null;
        switch (name) {
            case "SimpleOptionFormula":
                res = new SimpleOptionFormula();
                break;
            default:
                throw new IllegalArgumentException("no such formula");
        }
        return res;
    }
}
