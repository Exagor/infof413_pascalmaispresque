import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
     * Print the result of the parsing
     * @param parser
     */
    private static void printParseResult(Parser parser){
        for (int rule : parser.getUsedRules()) {
            System.out.print(rule+" ");
        }
        System.out.println();
    }
    /**
     * Return the name of the file without the path
     * @param sourceFilePmp
     */
    private static String getNameFile(String sourceFilePmp){
        String[] parts = sourceFilePmp.split("/");
        String nameFile = parts[parts.length-1];
        return nameFile;
    }

    /**
     * Run the default option without flags
     * @param sourceFilePmp
     */
    private static void defaultOption(String sourceFilePmp) throws FileNotFoundException, IOException, SecurityException{
        File input = new File(sourceFilePmp);//on peut juste renseigner le chemin du fichier
        if (!input.exists()){
            System.out.println("File not found: "+sourceFilePmp);
            System.exit(1);
        }
        FileReader reader = new FileReader(input);
        Parser parser = new Parser(reader);
        parser.parse(State.Program);
        //printParseResult(parser);
        //Write to llvm code
        LlvmWriter llvmWriter = new LlvmWriter(parser.getUsedRules(), parser.getVarList(), parser.getNbList());
        llvmWriter.writeInFile("more/"+getNameFile(sourceFilePmp).replace(".pmp",".ll"));
    }

    /**
     * Run the -wt option
     * @param sourceFileTex
     * @param sourceFilePmp
     */
    private static void wtOption(String sourceFileTex, String sourceFilePmp) throws FileNotFoundException, IOException, SecurityException{
        File input = new File(sourceFilePmp);//on peut juste renseigner le chemin du fichier
        if (!input.exists()){
            System.out.println("File not found: "+sourceFilePmp);
            System.exit(1);
        }
        FileReader reader = new FileReader(input);
        Parser parser = new Parser(reader);
        ParseTree parsetree = parser.parse(State.Program);
        //printParseResult(parser);
        FileWriter output = new FileWriter(sourceFileTex);
        output.write(parsetree.toLaTeX());
        output.close();
        //Write to llvm code
        LlvmWriter llvmWriter = new LlvmWriter(parser.getUsedRules(), parser.getVarList(), parser.getNbList());
        llvmWriter.writeInFile("more/"+getNameFile(sourceFilePmp).replace(".pmp",".ll"));
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

        if (argv.length >= 1) {
            String flag = argv[0];

            switch (flag) {
                case "-wt":
                    if (argv.length >= 3) {
                        String sourceFileTex = argv[1];
                        String sourceFilePmp = argv[2];
                        wtOption(sourceFileTex, sourceFilePmp);
                    } else {
                        System.out.println("Error : file missing for -wt option");
                    }
                    break;

                default:
                    String sourceFilePmp = argv[0];
                    defaultOption(sourceFilePmp);
                    break;
            }
        } else {
            System.out.println("Error : argument missing");
        }
    }
}
