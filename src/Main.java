import java.io.FileReader;
import java.io.FileWriter;
// import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static HashMap<String, Integer> symbolTable = new HashMap<>();

    
    /** 
     * @param symbol
     */
    public static void addSymbolToTable(Symbol symbol){
        if (symbol.getType() == LexicalUnit.VARNAME && !symbolTable.containsKey(symbol.getValue())) {
            String varName = (String) symbol.getValue();
            symbolTable.put(varName, symbol.getLine());
        }
    }

    /**
     * Print the symbol table
     */
    public static void printSymbolTable() {
        System.out.println("\nVariables :");
        for (String varName : symbolTable.keySet()) {
            System.out.println(varName + "   " + symbolTable.get(varName));
        }
    }
    /**
     * Run the main
     * @param argv
     */
    public static void main(String[] argv) throws FileNotFoundException, IOException, SecurityException{
        if (argv.length < 1){ //Check if there is an argument
            System.out.println("Usage: java Main <input_file>");
            System.exit(1);
        }
        File input = new File(argv[0]);//on peut juste renseigner le chemin du fichier
        if (!input.exists()){
            System.out.println("File not found: "+argv[0]);
            System.exit(1);
        }
        FileReader reader = new FileReader(input);
        Parser parser = new Parser(reader);
        ParseTree parsetree = parser.parse(State.Program);
        System.out.println(parser.getUsedRules());

        //Check for the flags
        for (int i = 1; i<argv.length; i++){//we skip the first argument because it's the input file
            String arg = argv[i];
            if (arg.equals("-wt")){
                if (i+1>=argv.length){
                    System.out.println("Usage: java Main <input_file> -wt <output_file>");
                    System.exit(1);
                }
                FileWriter output = new FileWriter(argv[i+1]+"parseTree.tex");
                output.write(parsetree.toLaTeX());
                output.close();
            }
        } 
    }
}
