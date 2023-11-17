import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;


/**
 * This class is used to analyse the grammar of a given language.
 */
public class GrammarAnalyser {
    // A map to store the grammar rules
    private Map<Integer, Map<State, ArrayList<Object>>> grammar = new HashMap<>();
    
    /**
     * Constructor for the GrammarAnalyser class.
     * It reads the grammar rules from a file and processes them.
     */
    public GrammarAnalyser() {
        try{
            // Read the grammar rules from a file
            BufferedReader grammarfile = new BufferedReader(new FileReader("doc/grammar_doc/grammar.csv"));
            // Process the grammar rules
            processgrammar(grammarfile);
        }catch (IOException e){
            System.out.println("Error while reading the grammar file");
        }
    }

    /**
     * This method returns the grammar rules.
     * @return A map containing the grammar rules.
     */
    public Map<Integer, Map<State, ArrayList<Object>>> Getgrammar(){
        return grammar;
    }

    /**
     * This method returns a specific rule element.
     * @param rule The rule number.
     * @param state The state.
     * @param elem The element number.
     * @return The rule element.
     */
    public Object getRuleElem(Integer rule, State state, Integer elem){
        return grammar.get(rule).get(state).get(elem);
    }

    /**
     * This method returns all the elements of a specific rule.
     * @param rule The rule number.
     * @param state The state.
     * @return A list containing the rule elements.
     */
    public ArrayList<Object> getRuleElems(Integer rule, State state){
        return grammar.get(rule).get(state);
    }

    /**
     * This method sets a rule element.
     * @param split The split string.
     * @return A list containing the rule elements.
     */
    private ArrayList<Object> setRuleElem(String[] split) {
        ArrayList<Object> ruleElem = new ArrayList<>();
        for(String elem : split){
            if (Character.isUpperCase(elem.charAt(0)) && !elem.equals("VARNAME") && !elem.equals("NUMBER")){
                ruleElem.add(getState(elem));
            }
            else{
                ruleElem.add(getLexicalUnit(elem));
            }
        }
        return ruleElem;
    }

    /**
     * This method processes the grammar rules.
     * @param grammarfile The file containing the grammar rules.
     * @throws IOException If an input or output exception occurred.
     */
    private void processgrammar(BufferedReader grammarfile) throws IOException{
        String line;
        int i = 1;
        while ((line = grammarfile.readLine()) != null) {
            String[] lineSplit = line.split(";");
            State state = getState(lineSplit[0]);

            if (state !=  null){
                ArrayList<Object> ruleElem = setRuleElem(lineSplit[1].split(","));   
                grammar.put(i, new HashMap<>());
                grammar.get(i).put(state, ruleElem);
            }
            i++;
        }
    }
    
    /**
     * This method returns the state corresponding to a given value.
     * @param value The value.
     * @return The state.
     */
    public State getState(String value) {
        State state = null;
        switch (value) {
            case "Program":
                state = State.Program;
                break;
            case "Code":
                state = State.Code;
                break;
            case "InstList":
                state = State.InstList;
                break;
            case "Instruction_end":
                state = State.Instruction_end;
                break;
            case "Instruction":
                state = State.Instruction;
                break;
            case "Assign":
                state = State.Assign;
                break;
            case "ExprArith":
                state = State.ExprArith;
                break;
            case "ExprArith'":
                state = State.ExprArithPrime;
                break;
            case "MultExpr":
                state = State.MultExpr;
                break;
            case "MultExpr'":
                state = State.MultExprPrime;
                break;
            case "Term":
                state = State.Term;
                break;
            case "AddOp":
                state = State.AddOp;
                break;
            case "MultOp":
                state = State.MultOp;
                break;
            case "If":
                state = State.If;
                break;
            case "If-tail":
                state = State.IfTail;
                break;
            case "Cond":
                state = State.Cond;
                break;
            case "OrCond":
                state = State.OrCond;
                break;
            case "AndCond":
                state = State.AndCond;
                break;
            case "AndCond'":
                state = State.AndCondPrime;
                break;
            case "EndCondition":
                state = State.EndCondition;
                break;
            case "SimpleCond":
                state = State.SimpleCond;
                break;
            case "Comp":
                state = State.Comp;
                break;
            case "While":
                state = State.While;
                break;
            case "Print":
                state = State.Print;
                break;
            case "Read":
                state = State.Read;
                break;
            default:
                return null;
        }
        return state;
    }

    /**
     * This method returns the lexical unit corresponding to a given element.
     * @param elem The element.
     * @return The lexical unit.
     */
    public LexicalUnit getLexicalUnit(String elem) {
        LexicalUnit lexicalUnit = null;
        switch (elem){
            case "begin":
                lexicalUnit = LexicalUnit.BEG;
                break;
            case "end":
                lexicalUnit = LexicalUnit.END;
                break;
            case "...":
                lexicalUnit = LexicalUnit.DOTS;
                break;
            case "VARNAME":
                lexicalUnit = LexicalUnit.VARNAME;
                break;
            case "NUMBER":
                lexicalUnit = LexicalUnit.NUMBER;
                break;
            case ":=":
                lexicalUnit = LexicalUnit.ASSIGN;
                break;
            case "if":
                lexicalUnit = LexicalUnit.IF;
                break;
            case "then":
                lexicalUnit = LexicalUnit.THEN;
                break;    
            case "else":
                lexicalUnit = LexicalUnit.ELSE;
                break;
            case "while":
                lexicalUnit = LexicalUnit.WHILE;
                break;
            case "do":
                lexicalUnit = LexicalUnit.DO;
                break;   
            case "print":
                lexicalUnit = LexicalUnit.PRINT;
                break;
            case "read":
                lexicalUnit = LexicalUnit.READ;
                break;
            case "+":
                lexicalUnit = LexicalUnit.PLUS;
                break;
            case "-":
                lexicalUnit = LexicalUnit.MINUS;
                break;
            case "*":
                lexicalUnit = LexicalUnit.TIMES;
                break;
            case "/":
                lexicalUnit = LexicalUnit.DIVIDE;
                break;
            case "(":
                lexicalUnit = LexicalUnit.LPAREN;
                break;
            case ")":
                lexicalUnit = LexicalUnit.RPAREN;
                break;
            case "and":
                lexicalUnit = LexicalUnit.AND;
                break;
            case "or":
                lexicalUnit = LexicalUnit.OR;
                break;
            case "{":
                lexicalUnit = LexicalUnit.LBRACK;
                break;
            case "}":
                lexicalUnit = LexicalUnit.RBRACK;
                break;
            case "=":
                lexicalUnit = LexicalUnit.EQUAL;
                break;
            case "<":
                lexicalUnit = LexicalUnit.SMALLER;
                break; 
            case "epsilon":
                lexicalUnit = LexicalUnit.EPSILON;
            default:
                break;
        }
        return lexicalUnit;
        
    }

}
