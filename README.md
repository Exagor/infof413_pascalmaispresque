# infof413_pascalmaispresque

Project for the "introduction to theory language and compiling" course.

## How to use the compiler

Use the Makefile then execute the .jar file with the test file as argument.

## Makefile

To compile all jflex and java class and run the program with a given test file.

```shell
make
```

 To compile all jflex and java class and create a .jar in the dist/ folder.

 ```shell
make jar
```

To remove every all compiled files.

 ```shell
make clean
```

## How to execute

Once you've created the .jar file, to run the .jar execute the folow commande with the right test file name.

```shell
java -jar dist/part1.jar test/testfile.pmp 
```

The output of the programm will be placed in the corresponding .out file in the test folder.

## How to generate javadoc

Generate the javadoc.

```shell
javadoc -d javadoc -sourcepath src/ src/Main.java
```
