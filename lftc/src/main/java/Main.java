import model.Pif;
import model.SymbolTable;
import utils.Constants;


public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTableConst = new SymbolTable(Constants.FILENAME, Constants.TOKEN_FILE, false);
        SymbolTable symbolTableId = new SymbolTable(Constants.FILENAME, Constants.TOKEN_FILE, true);
        System.out.println(symbolTableConst.toString());
        System.out.println(symbolTableId.toString());

        Pif pif = new Pif(symbolTableConst.getInputData());
        System.out.println(pif.toString());

    }
}
