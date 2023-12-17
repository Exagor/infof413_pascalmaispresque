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
    //help to write llvm with nice variables
    private int varCounter = -1; //Begin at -1 because of the first ++
    // create a map of variable names
    private Map<String, Integer> varMap = new HashMap<String, Integer>();

    /**
     * Constructor for LlvmWriter class.
     * @param usedRules ArrayList of used rules.
     */
    public LlvmWriter(ArrayList<Integer> usedRules) {
        this.llvmCode = new StringBuilder();
        this.rules = usedRules;

        beginRules(); //we launch the traduction of the rules in the constructor
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
            llvmCode.append("%1 = alloca i32, align 4\n");
            llvmCode.append("store i32 %x, i32* %1, align 4\n");
            llvmCode.append("%2 = load i32, i32* %1, align 4\n");
            llvmCode.append("%3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.strP, i32 0, i32 0), i32 %2)\n");
            llvmCode.append("ret void\n");
            llvmCode.append("}\n");
            llvmCode.append("\n");
            llvmCode.append("declare i32 @printf(i8*, ...) #1\n");
        }
        if (rules.contains(44)){
            llvmCode.append("@.strR = private unnamed_addr constant [3 x i8] c\"%d\\00\", align 1\n");
            llvmCode.append("define i32 @readInt() #0 {\n");
            llvmCode.append("%x = alloca i32, align 4\n");
            llvmCode.append("%1 = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strR, i32 0, i32 0), i32* %x)\n");
            llvmCode.append("%2 = load i32, i32* %x, align 4\n");
            llvmCode.append("ret i32 %2\n");
            llvmCode.append("}\n");
            llvmCode.append("\n");
            llvmCode.append("declare i32 @__isoc99_scanf(i8*, ...) #1\n");
        }

        llvmCode.append("define i32 @main() {\n");
        llvmCode.append("entry:\n");
        
        code();

        llvmCode.append("ret i32 0\n");
        llvmCode.append("}\n");
    }

    private void code(){//2
        //Code llvm avec append 
        //TODO code llvm a écrire
        if (rules.get(ruleCounter) == 2) {
            ruleCounter++;
            instList();
            llvmCode.append("rule 2\n");
        }
        else if (rules.get(ruleCounter) == 3) {//epsilon
            ruleCounter++;
            llvmCode.append("rule 3\n");
        }
        //TODO code llvm a écrire
    }

    private void instList(){//4
        //Code llvm avec append 
        //TODO code llvm a écrire
        if (rules.get(ruleCounter) == 4) {//Instruction
            ruleCounter++;
            llvmCode.append("rule 4\n");
            instruction();
            instructionEnd();
        }
        //TODO code llvm a écrire
    }
    private void instructionEnd(){
        //TODO code llvm
        if (rules.get(ruleCounter)==5){//epsilon
            ruleCounter++;
            llvmCode.append("rule 5\n");
        }
        else if (rules.get(ruleCounter)==6){//instruction
            ruleCounter++;
            llvmCode.append("rule 6\n");
            instList();
        }
    }
    private void instruction(){//7
        //TODO code llvm
        switch (rules.get(ruleCounter)){
            case 7:
                ruleCounter++;
                llvmCode.append("rule 7\n");
                assign();
                break;
            case 8:
                ruleCounter++;
                llvmCode.append("rule 8\n");
                instIf();
                break;
            case 9:
                ruleCounter++;
                llvmCode.append("rule 9\n");
                instWhile();
                break;
            case 10:
                ruleCounter++;
                llvmCode.append("rule 10\n");
                instPrint();
                break;
            case 11:
                ruleCounter++;
                llvmCode.append("rule 11\n");
                instRead();
                break;
            case 12:
                ruleCounter++;
                llvmCode.append("rule 12\n");
                //TODO llvm code
                instList();
                break;
        }
    }
    
    private void assign(){//13
        varCounter++; //new variable name
        int actualVarName=varCounter; //marche pas
        
        if (rules.get(ruleCounter)==13){
            ruleCounter++;
            llvmCode.append("rule 13\n");
            llvmCode.append("%").append(actualVarName).append(" = alloca i32, align 4\n");
            exprArith();
        }
    }
    private void exprArith(){//14
        //TODO code llvm
        if (rules.get(ruleCounter)==14){
            ruleCounter++;
            llvmCode.append("rule 14\n");
            multExpr();
            exprArithPrime();
        }
    }
    private void exprArithPrime(){
        if (rules.get(ruleCounter)==15){
            ruleCounter++;
            llvmCode.append("rule 15\n");
            addOp();
            multExpr();
            exprArithPrime();
        }
        else if (rules.get(ruleCounter)==16){//epsilon
            ruleCounter++;
            llvmCode.append("rule 16\n");
        }
    }
    private void multExpr(){//17
        //TODO code llvm
        if (rules.get(ruleCounter)==17){
            ruleCounter++;
            llvmCode.append("rule 17\n");
            term();
            multExprPrime();
        }
    }
    private void multExprPrime(){
        if (rules.get(ruleCounter)==18){
            ruleCounter++;
            llvmCode.append("rule 18\n");
            multOp();
            term();
            multExprPrime();
        }
        else if (rules.get(ruleCounter)==19){//epsilon
            ruleCounter++;
            llvmCode.append("rule 19\n");
        }
    }
    private void term(){//20
        //TODO code llvm
        switch (rules.get(ruleCounter)){
            case 20:
                ruleCounter++;
                llvmCode.append("rule 20\n");
                exprArith();
                break;
            case 21:
                ruleCounter++;
                llvmCode.append("rule 21\n");
                exprArith();
                break;
            case 22:
                ruleCounter++;
                //TODO code llvm
                llvmCode.append("rule 22\n");
                break;
            case 23:
                ruleCounter++;
                //TODO code llvm
                llvmCode.append("rule 23\n");
                break;
        }
    }
    private void addOp(){
        //TODO code llvm
        if (rules.get(ruleCounter)==24){
            ruleCounter++;
            llvmCode.append("rule 24\n");
        }
        else if (rules.get(ruleCounter)==25){
            ruleCounter++;
            llvmCode.append("rule 25\n");
        }
    }
    private void multOp(){
        //TODO code llvm
        if (rules.get(ruleCounter)==26){
            ruleCounter++;
            llvmCode.append("rule 26\n");
        }
        else if (rules.get(ruleCounter)==27){
            ruleCounter++;
            llvmCode.append("rule 27\n");
        }
    }
    private void instIf(){//28
        //TODO code llvm
        if (rules.get(ruleCounter)==28){
            ruleCounter++;
            llvmCode.append("rule 28\n");
            cond();
            instruction();
            ifTail();
        }
    }
    private void ifTail(){
        //TODO code llvm
        if (rules.get(ruleCounter)==29){//epsilon
            ruleCounter++;
            llvmCode.append("rule 29\n");
        }
        else if (rules.get(ruleCounter)==30){
            ruleCounter++;
            llvmCode.append("rule 30\n");
            instruction();
        }
    }
    private void cond(){
        //TODO code llvm
        if (rules.get(ruleCounter)==31){
            ruleCounter++;
            llvmCode.append("rule 31\n");
            andCond();
            orCond();
        }
    }
    private void orCond(){
        //TODO code llvm
        if (rules.get(ruleCounter)==32){
            ruleCounter++;
            llvmCode.append("rule 32\n");
            andCond();
            orCond();
        }
        else if (rules.get(ruleCounter)==33){//epsilon
            ruleCounter++;
            llvmCode.append("rule 33\n");
        }
    }
    private void andCond(){
        //TODO code llvm
        if (rules.get(ruleCounter)==34){
            ruleCounter++;
            llvmCode.append("rule 34\n");
            endCond();
            andCondPrime();
        }
    }
    private void andCondPrime(){
        //TODO code llvm
        if (rules.get(ruleCounter)==35){
            ruleCounter++;
            llvmCode.append("rule 35\n");
            endCond();
            andCondPrime();
        }
        else if (rules.get(ruleCounter)==36){//epsilon
            ruleCounter++;
            llvmCode.append("rule 36\n");
        }
    }
    private void endCond(){
        //TODO code llvm
        if (rules.get(ruleCounter)==37){
            ruleCounter++;
            llvmCode.append("rule 37\n");
            cond();
        }
        else if (rules.get(ruleCounter)==38){
            ruleCounter++;
            llvmCode.append("rule 38\n");
            simpleCond();
        }
    }
    private void simpleCond(){
        //TODO code llvm
        if (rules.get(ruleCounter)==39){
            ruleCounter++;
            llvmCode.append("rule 39\n");
            exprArith();
            comp();
            exprArith();
        }
    }
    private void comp(){
        //TODO code llvm
        if (rules.get(ruleCounter)==40){
            ruleCounter++;
            llvmCode.append("rule 40\n");
        }
        else if (rules.get(ruleCounter)==41){
            ruleCounter++;
            llvmCode.append("rule 41\n");
        }
    }
    private void instWhile(){//42
        //TODO code llvm
        if (rules.get(ruleCounter)==42){
            ruleCounter++;
            llvmCode.append("rule 42\n");
            cond();
            instruction();
        }
    }

    private void instPrint(){//43
        if (rules.get(ruleCounter)==43){
            ruleCounter++;
            llvmCode.append("rule 43\n");
        }
    }

    private void instRead(){//44
        if (rules.get(ruleCounter)==44){
            ruleCounter++;
            llvmCode.append("Read cool\n");
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