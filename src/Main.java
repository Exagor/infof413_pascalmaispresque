import java.io.FileReader;
// import java.io.PrintStream;
import java.io.File;
import java.util.HashMap;

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
    public static void main(String[] argv){
        if (argv.length != 1){
            System.out.println("Usage: java Main <input_file>");
            System.exit(1);
        }
        File input = new File(argv[0]);//on peut juste renseigner le chemin du fichier
        if (!input.exists()){
            System.out.println("File not found: "+argv[0]);
            System.exit(1);
        }
        try(FileReader reader = new FileReader(input)){
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(reader);

            // Redirige la sortie standard vers le fichier avec l'extension .out
            // PrintStream originalOut = System.out;
            // PrintStream fileOut = new PrintStream(argv[0].replace("pmp","out"));
            // System.setOut(fileOut);

            Symbol symbol = lexicalAnalyzer.nextSymbol();
            while(symbol.getType() != LexicalUnit.EOS){
                System.out.println(symbol.toString());
                addSymbolToTable(symbol);
                symbol = lexicalAnalyzer.nextSymbol();
            }
            printSymbolTable(); //print the symbol table
            // Rétablit la sortie standard
            // System.setOut(originalOut);
        }
        catch(Exception e){
            System.out.println("Error while opening file: "+argv[0]);
            System.exit(1);
        }
    
    }
}