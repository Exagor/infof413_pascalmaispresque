import java.io.FileWriter;
import java.io.IOException;

public class LlvmWriter {

    private StringBuilder llvmCode;

    public LlvmWriter() {
        this.llvmCode = new StringBuilder();
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
        llvmCode.append("define i32 @main() {\n");
        llvmCode.append("entry:\n");
        //code();
        //
        llvmCode.append("ret i32 0\n");
        llvmCode.append("}\n");
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