import java.io.FileReader;
import java.io.File;

class Main {

    public static void main(String[] argv){
        if (argv.length != 1){
            System.out.println("Usage: java Main <input_file>");
            System.exit(1);
        }
        File input = new File(argv[0]);
        if (!input.exists()){
            System.out.println("File not found: "+argv[0]);
            System.exit(1);
        }
        try{
            FileReader reader = new FileReader(input);
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(reader);
            Symbol symbol = lexicalAnalyzer.nextSymbol();
            while(symbol.getType() != LexicalUnit.EOS){
                System.out.println(symbol.toString());
                symbol = lexicalAnalyzer.nextSymbol();
            }
        }
        catch(Exception e){
            System.out.println("Error while opening file: "+argv[0]);
            System.exit(1);
        }

        

        
    
    }
}