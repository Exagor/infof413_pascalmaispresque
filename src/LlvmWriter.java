import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LlvmWriter {

    //Relate to rules
    private StringBuilder llvmCode;
    private int ruleCounter = 0;
    private ArrayList<Integer> rules = new ArrayList<Integer>();
    //help to write llvm with nice variables
    private int varCounter = 0;

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
        llvmCode.append("define i32 @main() {\n");
        llvmCode.append("entry:\n");
        
        if (rules.get(ruleCounter) == 2) {
            ruleCounter++;
            code();   
        }
        else if (rules.get(ruleCounter) == 3) {//epsilon
            ruleCounter++;
        }

        llvmCode.append("ret i32 0\n");
        llvmCode.append("}\n");
    }

    private void code(){//2
        //Code llvm avec append 
        //TODO code llvm a écrire
        if (rules.get(ruleCounter) == 4) {
            ruleCounter++;
            instList();
            
        }
        //TODO code llvm a écrire
    }

    private void instList(){//4
        //Code llvm avec append 
        //TODO code llvm a écrire
        if (rules.get(ruleCounter) == 7) {//Instruction
            ruleCounter++;
            instruction();
            
        }

        if (rules.get(ruleCounter)== 5){//epsilon
            ruleCounter++;
        }
        //TODO code llvm a écrire
    }

    private void instruction(){//7
        //TODO code llvm
        switch (rules.get(ruleCounter)){
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
                //TODO llvm code
                instList();
                break;
        }
    }

    private void instIf(){//28

    }

    private void instWhile(){//42

    }

    private void instPrint(){//43

    }

    private void instRead(){//44

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