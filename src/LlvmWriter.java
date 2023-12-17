import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LlvmWriter {

    //Relate to rules
    private StringBuilder llvmCode;
    private int ruleCounter = 0;
    private ArrayList<Integer> rules = new ArrayList<Integer>();
    private ArrayList<String> varList = new ArrayList<String>();
    private ArrayList<String> nbList = new ArrayList<String>();
    private Map<String, Boolean> varMap = new HashMap<String, Boolean>();
    //help to write llvm with nice variables
    private int varCounter = -1; //Begin at -1 because of the first ++
    private int tempVarCounter = -1;
    private int nbCounter = -1;
    private int labelCounter = 0;
    // create a map of variable names
    
    

    /**
     * Constructor for LlvmWriter class.
     * @param usedRules ArrayList of used rules.
     */
    public LlvmWriter(ArrayList<Integer> usedRules, ArrayList<String> varList, ArrayList<String> nbList) {
        this.llvmCode = new StringBuilder();
        this.rules = usedRules;
        this.varList = varList;
        this.nbList = nbList;
        initializeVarMap();
        beginRules(); //we launch the traduction of the rules in the constructor
        System.out.println(varList.toString());
        System.out.println(nbList.toString());
    }

    private void initializeVarMap(){
        for (String var : varList){
            varMap.put(var, false);
        }
    }

    /**
     * Method for launching the traduction of the rules in llvm code.
     */
    public void beginRules() {
        if (rules.get(ruleCounter) == 1) {
            ruleCounter++;
            program();
        }
    }

    private void program(){//1
        if (rules.contains(43)) {// add the println definition if needed
            llvmCode.append("@.strP = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1\n");
            llvmCode.append("define void @println(i32 %x) #0 {\n");
            llvmCode.append("\t%1 = alloca i32, align 4\n");
            llvmCode.append("\tstore i32 %x, i32* %1, align 4\n");
            llvmCode.append("\t%2 = load i32, i32* %1, align 4\n");
            llvmCode.append("\t%3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.strP, i32 0, i32 0), i32 %2)\n");
            llvmCode.append("\tret void\n");
            llvmCode.append("}\n");
            llvmCode.append("\n");
            llvmCode.append("declare i32 @printf(i8*, ...) #1\n");
        }
        if (rules.contains(44)){
            llvmCode.append("@.strR = private unnamed_addr constant [3 x i8] c\"%d\\00\", align 1\n");
            llvmCode.append("define i32 @readInt() #0 {\n");
            llvmCode.append("\t%x = alloca i32, align 4\n");
            llvmCode.append("\t%1 = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strR, i32 0, i32 0), i32* %x)\n");
            llvmCode.append("\t%2 = load i32, i32* %x, align 4\n");
            llvmCode.append("\tret i32 %2\n");
            llvmCode.append("}\n");
            llvmCode.append("\n");
            llvmCode.append("declare i32 @__isoc99_scanf(i8*, ...) #1\n");
        }

        llvmCode.append("define i32 @main() {\n");
        llvmCode.append("entry:\n");
        
        code();

        llvmCode.append("\tret i32 0\n");
        llvmCode.append("}\n");
    }

    private void code(){//2
        //Code llvm avec append 
        if (rules.get(ruleCounter) == 2) {
            ruleCounter++;
            instList();
        }
        else if (rules.get(ruleCounter) == 3) {//epsilon
            ruleCounter++;
        }
    }

    private void instList(){//4
        //Code llvm avec append 
        if (rules.get(ruleCounter) == 4) {//Instruction
            ruleCounter++;
            instruction();
            instructionEnd();
        }
    }
    private void instructionEnd(){
        if (rules.get(ruleCounter)==5){//epsilon
            ruleCounter++;
        }
        else if (rules.get(ruleCounter)==6){//instruction
            ruleCounter++;
            instList();
        }
    }
    private void instruction(){//7
        switch (rules.get(ruleCounter)){
            case 7:
                ruleCounter++;
                assign();
                break;
            case 8:
                ruleCounter++;
                instIf();
                break;
            case 9:
                ruleCounter++;
                instWhile();
                break;
            case 10:
                ruleCounter++;
                instPrint();
                break;
            case 11:
                ruleCounter++;
                instRead();
                break;
            case 12:
                ruleCounter++;
                instList();
                break;
        }
    }
    
    private void assign(){//13
        
        if (rules.get(ruleCounter)==13){
            ruleCounter++;
            if(!varMap.get(varList.get(varCounter+1))){ // if the variable is not already declared (false
                varMap.put(varList.get(varCounter+1), true);
                llvmCode.append("\t%" + varList.get(++varCounter) + " = alloca i32, align 4\n");
            }
            else{
                varCounter++;
            }
            int actualVarCounter = varCounter; // permet de garder la valeur de varCounter pour la suite
            exprArith();
            llvmCode.append("\tstore i32 %" + tempVarCounter + ", i32* %" + varList.get(actualVarCounter) + ", align 4\n");
        }
    }
    private void exprArith(){//14
        if (rules.get(ruleCounter)==14){
            ruleCounter++;
            multExpr();
            exprArithPrime();
        }
    }
    private void exprArithPrime(){
        if (rules.get(ruleCounter)==15){
            ruleCounter++;
            String operator;
            if(rules.get(ruleCounter)== 24){ //These conditions replace the AddOp
                ruleCounter++;
                operator = "add";
            }
            else if(rules.get(ruleCounter)== 25){
                ruleCounter++;
                operator = "sub";
            }
            else{
                operator = "error";
                System.out.println("error");
            }
            int firstTempVarCounter = tempVarCounter;
            multExpr();
            exprArithPrime();

            llvmCode.append("\t%" + ++tempVarCounter + " = " + operator + " i32 %" + (firstTempVarCounter) + ", %" + (tempVarCounter-1) + "\n");
        }
        else if (rules.get(ruleCounter)==16){//epsilon
            ruleCounter++;
        }
    }
    private void multExpr(){//17
        if (rules.get(ruleCounter)==17){
            ruleCounter++;
            term();
            multExprPrime();
        }
    }
    private void multExprPrime(){
        if (rules.get(ruleCounter)==18){
            ruleCounter++;
            // save the operator
            String operator;
            if(rules.get(ruleCounter)== 26){// these conditions replace the MultOp
                ruleCounter++;
                operator = "mul";
            }
            else if(rules.get(ruleCounter)== 27){
                ruleCounter++;
                operator = "sdiv";
            }
            else{
                operator = "error";
                System.out.println("error");
            }

            int firstTempVarCounter = tempVarCounter; // keep the value of tempVarCounter for the next exprArithPrime

            term();
            multExprPrime();

            llvmCode.append("\t%" + ++tempVarCounter + " = " + operator + " i32 %" + (firstTempVarCounter) + ", %" + (tempVarCounter-1) + "\n");
        }
        else if (rules.get(ruleCounter)==19){//epsilon
            ruleCounter++;
        }
    }
    private void term(){//20
        switch (rules.get(ruleCounter)){
            case 20:
                ruleCounter++;
                exprArith();
                break;
            case 21:
                ruleCounter++;
                exprArith();
                // change the sign of the last tempVarCounter
                llvmCode.append("\t%" + ++tempVarCounter + " = sub i32 0, %" + (tempVarCounter-1) + "\n");
                break;
            case 22:
                ruleCounter++;
                llvmCode.append("\t%" + ++tempVarCounter + " = load i32, i32* %" + varList.get(++varCounter) + ", align 4\n");
                break;
            case 23:
                ruleCounter++;
                llvmCode.append("\t%" + ++tempVarCounter + " = add i32 0, " + nbList.get(++nbCounter) + "\n");
                break;

        }
    }
    private void instIf(){//28
        if (rules.get(ruleCounter)==28){
            ruleCounter++;
            cond();
            llvmCode.append("\tbr i1 %" + (tempVarCounter-1) + ", label %if.then" + ++labelCounter + ", label %if.else" + labelCounter + "\n");
            llvmCode.append("if.then" + labelCounter + ":\n");
            instruction();
            llvmCode.append("\tbr label %if.end" + labelCounter + "\n");
            llvmCode.append("if.else" + labelCounter + ":\n");
            ifTail();
            llvmCode.append("if.end" + labelCounter + ":\n");
            
        }
    }
    private void ifTail(){
        if (rules.get(ruleCounter)==29){//epsilon
            ruleCounter++;
            llvmCode.append("\tbr label %if.end" + labelCounter + "\n");
        }
        else if (rules.get(ruleCounter)==30){
            ruleCounter++;
            instruction();
            llvmCode.append("\tbr label %if.end" + labelCounter + "\n");
        }
    }
    private void cond(){
        if (rules.get(ruleCounter)==31){
            ruleCounter++;
            andCond();
            orCond();
        }
    }
    private void orCond(){
        if (rules.get(ruleCounter)==32){
            ruleCounter++;
            int firstTempVarCounter = tempVarCounter;
            andCond();
            orCond();
            llvmCode.append("\t%" + ++tempVarCounter + " = or i1 %" + (firstTempVarCounter) + ", %" + (tempVarCounter-1) + "\n");
        }
        else if (rules.get(ruleCounter)==33){//epsilon
            ruleCounter++;
        }
    }
    private void andCond(){
        if (rules.get(ruleCounter)==34){
            ruleCounter++;
            endCond();
            andCondPrime();
        }
    }
    private void andCondPrime(){
        if (rules.get(ruleCounter)==35){
            ruleCounter++;
            int firstTempVarCounter = tempVarCounter;
            endCond();
            andCondPrime();
            llvmCode.append("\t%" + ++tempVarCounter + " = and i1 %" + (firstTempVarCounter) + ", %" + (tempVarCounter-1) + "\n");
        }
        else if (rules.get(ruleCounter)==36){//epsilon
            ruleCounter++;
        }
    }
    private void endCond(){
        if (rules.get(ruleCounter)==37){
            ruleCounter++;
            cond();
        }
        else if (rules.get(ruleCounter)==38){
            ruleCounter++;
            simpleCond();
        }
    }
    private void simpleCond(){
        if (rules.get(ruleCounter)==39){
            ruleCounter++;
            exprArith();
            int firstTempVarCounter = tempVarCounter;
            String compOperator;
            if (rules.get(ruleCounter)==40){
                compOperator = "eq";
            }
            else if (rules.get(ruleCounter)==41){
                compOperator = "slt";
            }
            else{
                compOperator = "error";
            }
            comp();
            exprArith();
            llvmCode.append("\t%" + ++tempVarCounter + " = icmp " + compOperator + " i32 %" + (firstTempVarCounter) + ", %" + (tempVarCounter-1) + "\n");
        }
    }
    private void comp(){
        if (rules.get(ruleCounter)==40){
            ruleCounter++;
        }
        else if (rules.get(ruleCounter)==41){
            ruleCounter++;
        }
    }
    private void instWhile(){//42
        if (rules.get(ruleCounter)==42){
            ruleCounter++;
            llvmCode.append("\tbr label %while.cond" + ++labelCounter + "\n");
            llvmCode.append("while.cond" + labelCounter + ":\n");
            int saveLabelCounter = labelCounter;
            cond();
            llvmCode.append("\tbr i1 %" + tempVarCounter + ", label %while.body" + labelCounter + ", label %while.end" + labelCounter + "\n");
            llvmCode.append("while.body" + saveLabelCounter + ":\n");
            instruction();
            llvmCode.append("\tbr label %while.cond" + saveLabelCounter + "\n");
            llvmCode.append("while.end" + saveLabelCounter + ":\n");
        }
    }

    private void instPrint(){//43
        if (rules.get(ruleCounter)==43){
            ruleCounter++;
            llvmCode.append("\t%" + ++tempVarCounter + " = load i32, i32* %" + varList.get(++varCounter) + ", align 4\n");
            llvmCode.append("\tcall void @println(i32 %" + tempVarCounter + ")\n");
        }
    }

    private void instRead(){//44
        if (rules.get(ruleCounter)==44){
            ruleCounter++;
            llvmCode.append("\t%"+ ++tempVarCounter + " = call i32 @readInt()\n");
        
            if(!varMap.get(varList.get(varCounter+1))){ // if the variable is not already declared (false)
                varMap.put(varList.get(varCounter+1), true);
                llvmCode.append("\t%" + varList.get(++varCounter) + " = alloca i32, align 4\n");
            }
            else{
                varCounter++;
            }
            llvmCode.append("\tstore i32 %"+ tempVarCounter + ", i32* %" + varList.get(varCounter) + ", align 4\n");
        }
    }

    public void writeInFile(String fileName) {
        try {
        FileWriter writer = new FileWriter(fileName);
        writer.write(llvmCode.toString());
        writer.close();
    } catch (IOException e) {
        System.out.println("An error occurred while writing to file.");
        e.printStackTrace();
    }
    }
     
}