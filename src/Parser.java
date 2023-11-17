import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    final LexicalAnalyzer lexicalAnalyzer;
    private Symbol currentSymbol;
    private Symbol nextSymbol;
    private State currentState = State.Program;
    private ArrayList<Integer> usedrules = new ArrayList<Integer>();
    ActionTableReader actionTableReader = new ActionTableReader();
    GrammarAnalyser grammarAnalyser = new GrammarAnalyser();


    public Parser(FileReader reader) {
        lexicalAnalyzer = new LexicalAnalyzer(reader);
        try{
            currentSymbol = lexicalAnalyzer.nextSymbol();
            nextSymbol = lexicalAnalyzer.nextSymbol();
        }catch (IOException e){
            System.out.println("Error while getting first symbols");
        }

    }

    private Symbol getNextSymbol(){
        try{
            currentSymbol = nextSymbol;
            nextSymbol = lexicalAnalyzer.nextSymbol();
            System.out.println("Next symbol: " + currentSymbol.getType());
        }catch (IOException e){
            System.out.println("Error while getting next symbol");
        }
        return currentSymbol;
    }
    
    public ArrayList<Integer> getUsedRules(){
        return usedrules;
    }


    public void parse(State etat){
        currentState = etat;
        Integer rule = actionTableReader.getRuleNumber(currentState, currentSymbol.getType());
        usedrules.add(rule);

        System.out.println("Current state: " + currentState);
        System.out.println("Current symbol: " + currentSymbol.getType());
        System.out.println("Rule" + rule);
        Integer elemNumber = 0;
        
        if (rule == null){
            System.out.println("Error: no rule found for state " + currentState + " and symbol " + currentSymbol.getType());
            System.exit(1);
        }
        int i = 0;
        System.out.println("Rule elems: " + grammarAnalyser.getRuleElems(rule, etat));
        for(Object elem : grammarAnalyser.getRuleElems(rule, etat)){
            System.out.println("Elem: " + elem);
            if (elem instanceof State){
                parse((State) elem);
            }
            else if (elem instanceof LexicalUnit){
                if (currentSymbol.getType() == elem){

                    getNextSymbol();
                }
                else if (elem == LexicalUnit.EPSILON){
                    continue;
                }
                else{
                    System.out.println("Error: expected " + elem + " but got " + currentSymbol.getType());
                    System.exit(1);
                }
            }
        }


    }

    public State getType(String input){
        State state = null;
        state = grammarAnalyser.getState(input);
        return state;
    }
}
