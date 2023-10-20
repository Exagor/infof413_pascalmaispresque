all: 
	jflex src/LexicalAnalyzer.flex
	javac src/*.java
	cd src/ && java Main test2.pmp
clean:
	rm src/*.class