import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.io.IOException;


public class GrammarAnalyser {
    private Map<State, Map<Integer, ArrayList<String>>> grammar;
    

    public GrammarAnalyser() {
        try{
        BufferedReader grammarfile = new BufferedReader(new FileReader("doc/grammar_doc/grammar.txt"));
        processgrammar(grammarfile);
        }catch (IOException e){
            System.out.println("Error while getting next symbol");
        }
        
    }

    private void processgrammar(BufferedReader grammarfile){
        
    }
}
