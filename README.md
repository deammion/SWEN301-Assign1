## SWEN301 Assignment 1 Template

Please refer to the assignment brief for details. 
a) as this is an implementation only package, the jdepend report states
   an abstract of 0%, instability of 80%(100% for the CLI), and a distance of 20% (0% CLI), it shows the correctness
   of the dependencies for this type of package
b) the CLI can be run from the terminal accessed through the directory of the generated .jar file
   using java -jar "filename".jar. To get the CLI to function first all the necessary dependencies, 
   such as classes and libraries needed to be referenced so that it would not stop the execution of
   the jar file
c) My program design is prone to memory leaks due to the use of static methods. since these methods 
   persist in the memory any time the program is executed. Since reference to these methods cannot be
   removed by the garbage collector, this will result in a memory leak. more over, resources such as
   the connections and result sets can cause memory leaks, this is mitigated by closing each
   resource once the method has completed its function.
