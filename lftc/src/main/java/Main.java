import model.Pif;
import model.SymbolTable;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> input = Arrays.asList("int", "a", ",", "b", ";", "a", "=", "3", ";", "if", " ", "a", ">", "1", "b", "=");
        SymbolTable symbolTableConst = new SymbolTable(input, false);
        SymbolTable symbolTableId = new SymbolTable(input, true);
        System.out.println(symbolTableConst.toString());
        System.out.println(symbolTableId.toString());
        Pif pif = new Pif(input);
        System.out.println(pif.toString());

        System.out.println(symbolTableConst.getElementFromHashTable("3"));
        symbolTableConst.addElementToHashTable("c");
    }
}
