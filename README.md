# infof413_pascalmaispresque

Project for the "introduction to theory language and compiling" course.

## How to use the compiler

Use the Makefile then execute the .jar file with the test file as argument.

## Makefile

 To compile all jflex and java class and create a .jar in the dist/ folder.

 ```shell
make jar
```

To launch all tests.

 ```shell
make tests
```

To remove all compiled files.

 ```shell
make clean
```

## How to execute

Once you've created the .jar file, to run it, run the following command with the correct test file name (make sure you're still in the root directory). The program will automatically generate llvm code in the more/ directory.

```shell
java -jar dist/part3.jar test/testfile.pmp 
```

Replace test/testfile.pmp with the path to the file you want to run.

You can add the -wt flag to display the parse tree of your code followed by the file.tex where you want it to be saved.

```shell
java -jar dist/part3.jar -wt file.tex test/testfile.pmp
```

## How to generate javadoc

Generate the javadoc.

```shell
javadoc -d javadoc -sourcepath src/ src/Main.java
```

After the "-d" flag, specify the path to the javadoc folder (it will create the folder if it doesn't exist).
