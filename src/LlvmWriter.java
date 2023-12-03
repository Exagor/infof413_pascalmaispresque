
public class LlvmWriter {

    private String llvmCode = "";

    public LlvmWriter() {
    }
    public void writeLlvmCode(String rule) {
        switch (rule) {
            case "Program":
                program();
                break;
            case "Code":
                
                break;

            default:
                break;
        }
    }

    public void program(){
        llvmCode += "define i32 @main() {\n";
        llvmCode += "entry:\n";
        llvmCode += "ret i32 0\n";
        llvmCode += "}\n";
    }


    public void writeInFile(String llvmCode) {
        
    }
     
}