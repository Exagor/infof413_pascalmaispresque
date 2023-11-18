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

Once you've created the .jar file, to run it, run the following command with the correct test file name (make sure you're still in the root directory).

```shell
java -jar dist/part2.jar test/testfile.pmp 
```

Replace test/testfile.pmp by the path to the file you want to execute.

You can add the -wt flag to display the derivation tree followed by the folder where you want it to be saved.

```shell
java -jar dist/part2.jar test/testfile.pmp -wt more/
```

Replace more/ by the path where you want the derivation tree to be saved.

(The output of the programm will be placed in the corresponding .out file in the same folder as the testfile.pmp.)

## How to generate javadoc

Generate the javadoc in the current directory.

```shell
javadoc -d javadoc -sourcepath src/ src/Main.java
```

After the "-d" flag, specify the path to the javadoc folder (it creates the folder if it doesn't exist).
