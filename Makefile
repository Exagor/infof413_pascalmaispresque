JAVA_PROGRAM = Main
SRC_DIR = src
TEST_DIR = test
# Récupère la liste des fichiers de test dans le répertoire "test/"
TEST_FILES := $(wildcard $(TEST_DIR)/*.pmp)

all: 
	jflex $(SRC_DIR)/LexicalAnalyzer.flex
	javac $(SRC_DIR)/*.java
	cd $(SRC_DIR) && java $(JAVA_PROGRAM) ../$(TEST_DIR)/test_normal.pmp

jar:
	jflex $(SRC_DIR)/LexicalAnalyzer.flex
	javac $(SRC_DIR)/*.java
	jar cfm dist/part1.jar more/Main.txt -C $(SRC_DIR) .

run:
	jflex $(SRC_DIR)/LexicalAnalyzer.flex
	javac $(SRC_DIR)/*.java
	cd $(SRC_DIR) && java $(JAVA_PROGRAM) ../$(TEST_DIR)/test_normal.pmp

tests: $(TEST_FILES)
	jflex $(SRC_DIR)/LexicalAnalyzer.flex
	javac $(SRC_DIR)/*.java
	for test_file in $(TEST_FILES); do \
		java -cp $(SRC_DIR) $(JAVA_PROGRAM) "$$test_file"; \
	done

clean:
	rm $(SRC_DIR)/*.class
	rm $(SRC_DIR)/LexicalAnalyzer.java~

.PHONY: tests clean
