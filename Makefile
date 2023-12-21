JAVA_PROGRAM = Main
SRC_DIR = src
TEST_DIR = test/test_scanner_parser
LLMV_TEST_DIR = test/test_llvm
# Get the files from the test directory
TEST_FILES := $(wildcard $(TEST_DIR)/*.pmp)
LLVM_TEST_FILES := $(wildcard $(LLMV_TEST_DIR)/*.pmp)
FILE = euclid
SOURCE-CODE-llvm = more/euclid
.SILENT: all jar compile tests clean

all:compile
	java -cp $(SRC_DIR) $(JAVA_PROGRAM) $(TEST_DIR)/$(FILE).pmp

jar:compile
	jar cfm dist/part3.jar more/Main.txt -C $(SRC_DIR) .

compile:
	jflex $(SRC_DIR)/LexicalAnalyzer.flex
	javac $(SRC_DIR)/*.java

tests: $(TEST_FILES) compile
	for test_file in $(TEST_FILES); do \
		echo "\nTesting $$test_file\n"; \
		echo "------------------\n"; \
		java -cp $(SRC_DIR) $(JAVA_PROGRAM) "$$test_file"; \
	done
	echo "Done testing"

test-llvm: $(LLVM_TEST_FILES) compile
	echo "Compiling LLVM files"
	for test_file in $(LLVM_TEST_FILES); do \
		echo "Compiling $$test_file to LLVM IR"; \
		echo "------------------\n"; \
		java -cp $(SRC_DIR) $(JAVA_PROGRAM) "$$test_file"; \
	done

run:jar
	java -jar dist/part3.jar test/test_llvm/$(FILE).pmp

llvm:
	llvm-as more/$(FILE).ll -o more/$(FILE).bc 
	lli more/$(FILE).bc
	
clean:
	echo "Cleaning..."
	rm $(SRC_DIR)/*.class
	rm $(SRC_DIR)/LexicalAnalyzer.java~
	rm more/*.ll
	rm more/*.bc
	echo "Cleaning done"

.PHONY: tests clean
