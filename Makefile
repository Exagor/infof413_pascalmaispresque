JAVA_PROGRAM = Main
SRC_DIR = src
TEST_DIR = test
# Get the files from the test directory
TEST_FILES := $(wildcard $(TEST_DIR)/*.pmp)
.SILENT: all jar compile tests clean

all:compile
	java -cp $(SRC_DIR) $(JAVA_PROGRAM) $(TEST_DIR)/test_normal.pmp

jar:compile
	jar cfm dist/part1.jar more/Main.txt -C $(SRC_DIR) .

compile:
	jflex $(SRC_DIR)/LexicalAnalyzer.flex
	javac $(SRC_DIR)/*.java

tests: $(TEST_FILES) compile
	for test_file in $(TEST_FILES); do \
		echo "Testing $$test_file"; \
		java -cp $(SRC_DIR) $(JAVA_PROGRAM) "$$test_file"; \
	done
	echo "Done testing"
clean:
	echo "Cleaning..."
	rm $(SRC_DIR)/*.class
	rm $(SRC_DIR)/LexicalAnalyzer.java~
	rm $(TEST_DIR)/*.out
	echo "Cleaning done"

.PHONY: tests clean
