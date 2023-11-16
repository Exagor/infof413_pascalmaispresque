import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    final LexicalAnalyzer lexicalAnalyzer;
    private Symbol currentSymbol;
    private Symbol nextSymbol;
    private State currentState;
    private ArrayList<Integer> usedrules = new ArrayList<Integer>();
    ActionTableReader actionTableReader = new ActionTableReader();


    public Parser(FileReader reader) {
        lexicalAnalyzer = new LexicalAnalyzer(reader);

    }

    private void getNextSymbol(){
        try{
            currentSymbol = nextSymbol;
            nextSymbol = lexicalAnalyzer.nextSymbol();
        }catch (IOException e){
            System.out.println("Error while getting next symbol");
        }
    }
    
    public void parseProgramm(){
        try{
            currentSymbol = lexicalAnalyzer.nextSymbol();
            nextSymbol = lexicalAnalyzer.nextSymbol();
        }catch (IOException e){
            System.out.println("Error while getting next symbol");
        }
        currentState = State.Program;
        Integer rule = actionTableReader.getRuleNumber(currentState, currentSymbol.getType());
        
        if (rule == null){
            System.out.println("Error: no rule found for state " + currentState + " and symbol " + currentSymbol.getType());
            System.exit(1);
        }
        else{
            usedrules.add(rule);
            System.out.println("Rule");
        }
    }


    public void parse(State etat){
        
        



    }

}
