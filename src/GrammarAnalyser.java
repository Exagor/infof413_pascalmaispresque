import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;


public class GrammarAnalyser {
    private Map<Integer, Map<State, ArrayList<Object>>> grammar = new HashMap<>();
    

    public GrammarAnalyser() {
        try{
        BufferedReader grammarfile = new BufferedReader(new FileReader("doc/grammar_doc/grammar.csv"));
        processgrammar(grammarfile);
        }catch (IOException e){
            System.out.println("Error while reading the grammar file");
        }
        
    }

    public Map<Integer, Map<State, ArrayList<Object>>> Getgrammar(){
        return grammar;
    }

    public Object getRuleElem(Integer rule, State state, Integer elem){
        return grammar.get(rule).get(state).get(elem);
    }

    public ArrayList<Object> getRuleElems(Integer rule, State state){
        return grammar.get(rule).get(state);
    }

    private ArrayList<Object> setRuleElem(String[] split) {
        ArrayList<Object> ruleElem = new ArrayList<>();
        for(String elem : split){
            if (Character.isUpperCase(elem.charAt(0)) && !elem.equals("VARNAME") && !elem.equals("NUMBER")){
                ruleElem.add(getState(elem));
            }
            else{                ruleElem.add(getLexicalUnit(elem));
            }
        }
        return ruleElem;
    }

    

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
