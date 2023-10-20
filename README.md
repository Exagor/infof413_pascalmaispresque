# infof413_pascalmaispresque

Project for the "introduction to theory language and compiling" course.

## How to use the compiler

Available in Part 2.

## Makefile

To compile all jflex and java class and create an executable .jar in the dist folder.

```shell
make
```

 To compile all jflex and java class and run the program with a given test file.

 ```shell
make run
```

To remove every all compiled files.

 ```shell
make clean
```

## How to execute

To run the program execute the folow commande with the right test file name.

```shell
java -jar dist/part1.jar test/testfile.pmp 
```

The output of the programm will be placed in the corresponding .out file in the test folder.

## How to generate javadoc in .pdf file

Generate the javadoc.

```shell
javadoc -d javadoc -sourcepath src/ src/Main.java
```
