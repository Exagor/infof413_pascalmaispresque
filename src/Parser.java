import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Parser class for parsing the input file.
 */
public class Parser {
    final LexicalAnalyzer lexicalAnalyzer;
    private Symbol currentSymbol;
    private Symbol nextSymbol;
    private State currentState = State.Program;
    private ArrayList<Integer> usedrules = new ArrayList<Integer>();
    ActionTableReader actionTableReader = new ActionTableReader();
    GrammarAnalyser grammarAnalyser = new GrammarAnalyser();

    /**
     * Constructor for Parser class.
     * @param reader FileReader object for reading the input file.
     */
    public Parser(FileReader reader) {
        lexicalAnalyzer = new LexicalAnalyzer(reader);
        try{
            currentSymbol = lexicalAnalyzer.nextSymbol();
            nextSymbol = lexicalAnalyzer.nextSymbol();
        }catch (IOException e){
            System.out.println("Error while getting first symbols");
        }
    }

    /**
     * Method for getting the next symbol from the lexical analyzer.
     * @return The next symbol.
     */
    private Symbol getNextSymbol(){
        try{
            currentSymbol = nextSymbol;
            nextSymbol = lexicalAnalyzer.nextSymbol();
            //System.out.println("Next symbol: " + currentSymbol.getType());
        }catch (IOException e){
            System.out.println("Error while getting next symbol");
        }
        return currentSymbol;
    }
    
    /**
     * Method for getting the used rules.
     * @return An ArrayList of used rules.
     */
    public ArrayList<Integer> getUsedRules(){
        return usedrules;
    }

    /**
     * Method for parsing the input file.
     * @param etat The current state.
     */
    public ParseTree parse(State etat){

        //System.out.println("Current state: " + currentState);
        //System.out.println("Current symbol: " + currentSymbol.getType());
        currentState = etat;
        Integer rule = actionTableReader.getRuleNumber(currentState, currentSymbol.getType());
        usedrules.add(rule);
        //System.out.println("Rule" + rule);

        
        // If no rule is found for the current state and symbol, exit the program.
        if (rule == null){
            System.out.println("Error: no rule found for state " + currentState + " and symbol " + currentSymbol.getType());
            System.exit(1);
        }
        System.out.println("Rule elems: " + grammarAnalyser.getRuleElems(rule, etat) + "\n");

        ArrayList<ParseTree> cldn = new ArrayList<ParseTree>();
        ParseTree tree = new ParseTree(new Symbol(currentState), cldn);
        

        for(Object elem : grammarAnalyser.getRuleElems(rule, etat)){
            System.out.println("Elem: " + elem);
            System.out.println("Current symbol: " + currentSymbol.getType());
            // If the element is a state, recursively parse it.
            if (elem instanceof State){
                ParseTree child = parse((State) elem);
                cldn.add(child);
            }
            // If the element is a lexical unit, check if it matches the current symbol.
            else if (elem instanceof LexicalUnit){
                if (currentSymbol.getType() == elem){
                    ParseTree child = new ParseTree(currentSymbol);
                    cldn.add(child);
                    getNextSymbol();           
                }
                else if (elem == LexicalUnit.EPSILON){
                    ParseTree child = new ParseTree(new Symbol(LexicalUnit.EPSILON));
                    cldn.add(child);
                    continue;
                }
                else{
                    System.out.println("Error: expected " + elem + " but got " + currentSymbol.getType());
                    System.exit(1);
                }
            }

        }
        //System.out.println("End of rule " + rule + "\n");
        return tree;
    }

    /**
     * Method for getting the state type.
     * @param input The input string.
     * @return The state type.
     */
    public State getType(String input){
        State state = null;
        state = grammarAnalyser.getState(input);
        return state;
    }
}