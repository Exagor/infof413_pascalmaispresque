all: 
	jflex src/LexicalAnalyzer.flex
	javac src/*.java
	cd src/ && java Main ../test/test2.pmp
jar:
	jflex src/LexicalAnalyzer.flex
	javac src/*.java
	jar cfm dist/part1.jar more/Main.txt -C src .
run:
	jflex src/LexicalAnalyzer.flex
	javac src/*.java
	cd src/ && java Main ../test/test2.pmp
clean:
	rm src/*.class
	rm src/LexicalAnalyzer.java~