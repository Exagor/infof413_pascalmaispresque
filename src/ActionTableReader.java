import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class reads an action table from a file.
 */
public class ActionTableReader {
    private ArrayList<LexicalUnit> lexicalUnits;
    private ArrayList<State> states;
    private Map<State, Map<LexicalUnit, Integer>> actionTable;

    /**
     * Constructor for ActionTableReader.
     * @param file The file to read the action table from.
     */
    public ActionTableReader(String file) {
        lexicalUnits = new ArrayList<>();
        states = new ArrayList<>();
        actionTable = new HashMap<>();
        initializeActionTable();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            processFile(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for ActionTableReader with default file being "doc/grammar_doc/action_table.csv".
     */
    public ActionTableReader() {
        lexicalUnits = new ArrayList<>();
        states = new ArrayList<>();
        actionTable = new HashMap<>();
        initializeActionTable();

        try (BufferedReader br = new BufferedReader(new FileReader("doc/grammar_doc/action_table.csv"))) {
            processFile(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the action table.
     * @return The action table.
     */
    public Map<State, Map<LexicalUnit, Integer>> GetActionTable(){
        return actionTable;
    }

    /**
     * Returns the rule number for a given state and lexical unit.
     * @param etat The state.
     * @param lu The lexical unit.
     * @return The rule number.
     */
    public Integer getRuleNumber(State etat, LexicalUnit lu) {
        return actionTable.get(etat).get(lu);
    }

    /**
     * Initializes the action table with empty maps for each state.
     */
    private void initializeActionTable() {
        for (State state : State.values()) {
            actionTable.put(state, new HashMap<LexicalUnit, Integer>()); // Initialize the map for each state
        }
    }

    /**
     * Processes the file line by line.
     * @param br The BufferedReader to read the file.
     * @throws IOException If an I/O error occurs.
     */
    private void processFile(BufferedReader br) throws IOException {
        String line;
        setLexicalUnits(br.readLine()); // First line is the lexical units


        while ((line = br.readLine()) != null) {
            processLine(line);
        }
    }

    /**
     * Processes a line from the file.
     * @param line The line to process.
     */
    private void processLine(String line) {
        String[] values = line.split(";");
        int columnNumber = count(";", line) + 1; 

        values = fillEmptyValues(values, columnNumber);

        processValues(values);
    }

    /**
     * Fills in empty values in the array with an empty string.
     * @param values The array of values.
     * @param columnNumber The number of columns.
     * @return The array with empty values filled in.
     */
    private String[] fillEmptyValues(String[] values, int columnNumber) {
        if (values.length != columnNumber) {
            String[] newValues = new String[columnNumber];
            for (int i = 0; i < columnNumber; i++) {
                newValues[i] = i < values.length ? values[i] : "";
            }
            values = newValues;
        }
        return values;
    }

    /**
     * Processes the values from a line.
     * @param values The values to process.
     */
    private void processValues(String[] values) {
        State state = addState(values[0]);
        for (int i = 1; i < values.length; i++) {
            String strValue = values[i];
            if (strValue == null || strValue.isEmpty()) {
                strValue = "0"; // Empty values are 0
            }
            Integer value = Integer.parseInt(strValue);
            if (value != 0) {
                actionTable.get(state).put(lexicalUnits.get(i - 1), value);
            }
        }
    }

    /**
     * Counts the occurrences of a string in a line.
     * @param str The string to count.
     * @param line The line to count in.
     * @return The count of the string in the line.
     */
    private int count(String str, String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == str.charAt(0)) {
                count++;
            }
        }
        return count;
    }


    /**
     * Adds a state to the list of states based on a string value.
     * @param value The string value.
     * @return The added state.
     */
    private State addState(String value) {
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
            case "SimpleCondition":
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
        states.add(state);
        return state;
    }

    /**
     * Sets the lexical units based on the first line of the file.
     * @param firstline The first line of the file.
     */
    public void setLexicalUnits(String firstline) {
        String[] terminaux = firstline.split(";");
        for (String terminal : terminaux) {
            switch (terminal){
                case "begin":
                    lexicalUnits.add(LexicalUnit.BEG);
                    break;
                case "end":
                    lexicalUnits.add(LexicalUnit.END);
                    break;
                case "...":
                    lexicalUnits.add(LexicalUnit.DOTS);
                    break;
                case "VARNAME":
                    lexicalUnits.add(LexicalUnit.VARNAME);
                    break;
                case "NUMBER":
                    lexicalUnits.add(LexicalUnit.NUMBER);
                    break;
                case ":=":
                    lexicalUnits.add(LexicalUnit.ASSIGN);
                    break;
                case "if":
                    lexicalUnits.add(LexicalUnit.IF);
                    break;
                case "then":
                    lexicalUnits.add(LexicalUnit.THEN);
                    break;    
                case "else":
                    lexicalUnits.add(LexicalUnit.ELSE);
                    break;
                case "while":
                    lexicalUnits.add(LexicalUnit.WHILE);
                    break;
                case "do":
                    lexicalUnits.add(LexicalUnit.DO);
                    break;   
                case "print":
                    lexicalUnits.add(LexicalUnit.PRINT);
                    break;
                case "read":
                    lexicalUnits.add(LexicalUnit.READ);
                    break;
                case "+":
                    lexicalUnits.add(LexicalUnit.PLUS);
                    break;
                case "-":
                    lexicalUnits.add(LexicalUnit.MINUS);
                    break;
                case "*":
                    lexicalUnits.add(LexicalUnit.TIMES);
                    break;
                case "/":
                    lexicalUnits.add(LexicalUnit.DIVIDE);
                    break;
                case "(":
                    lexicalUnits.add(LexicalUnit.LPAREN);
                    break;
                case ")":
                    lexicalUnits.add(LexicalUnit.RPAREN);
                    break;
                case "and":
                    lexicalUnits.add(LexicalUnit.AND);
                    break;
                case "or":
                    lexicalUnits.add(LexicalUnit.OR);
                    break;
                case "{":
                    lexicalUnits.add(LexicalUnit.LBRACK);
                    break;
                case "}":
                    lexicalUnits.add(LexicalUnit.RBRACK);
                    break;
                case "=":
                    lexicalUnits.add(LexicalUnit.EQUAL);
                    break;
                case "<":
                    lexicalUnits.add(LexicalUnit.SMALLER);
                    break; 
                default:
                    break;
            }
        }
    }
}
