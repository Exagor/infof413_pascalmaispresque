all: 
	jflex src/LexicalAnalyzer.flex
	javac src/*.java
	cd src/ && java Main euclid.pmp
clean:
	rm src/*.class