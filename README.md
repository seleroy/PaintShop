# Paint Shop Challenge

An Implementation of the following Paint Shop challenge

## Problem

You run a paint shop, and there are a few different colors of paint you can prepare.  Each color can be either "gloss" or "matte".

You have a number of customers, and each have some colors they like, either gloss or matte.  No customer will like more than one color in matte.

You want to mix the colors, so that:
   * There is just one batch for each color, and it's either gloss or matte.
   * For each customer, there is at least one color they like.
   * You make as few mattes as possible (because they are more expensive).

Your program should accept an input file as a command line argument, and print a result to standard out.  An example input file is:

5  
1 M 3 G 5 G  
2 G 3 M 4 G  
5 M

The first line specifies how many colors there are.

Each subsequent line describes a customer.  For example, the first customer likes color 1 in matte, color 3 in gloss and color 5 in gloss.

Your program should read an input file like this, and print out either that it is impossible to satisfy all the customer, or describe, for each of the colors, whether it should be made gloss or matte.

The output for the above file should be:

G G G G M

...because all customers can be made happy by every paint being prepared as gloss except number 5.

An example of a file with no solution is:

1  
1 G  
1 M  

Your program should print

No solution exists

A slightly richer example is:

5  
2 M  
5 G  
1 G  
5 G 1 G 4 M  
3 G  
5 G  
3 G 5 G 1 G  
3 G  
2 M  
5 G 1 G  
2 M  
5 G  
4 M  
5 G 4 M  

...which should print:

G M G M G

One more example.  The input:

2  
1 G 2 M  
1 M

...should produce

M M

### Solution

As we need to keep the number of Matt finishes to the minimum and not only to find a combination that works for all customers, the idea is to start from the ideal scenario with only glossy paints and iterate by adding Matt one by one until we reach a combination that satisfies all the customers:

* Start with a list of glossy paint
* Browse each customer:
* If the customer is satisfied with the existing list, move to the next customer
* If no requested paint is satisfied, provide a Matt finish to this customer and reiterate to check if all previous customers are still happy
* If the customer is not happy and that we cannot swap the finish, then no solution exists.

In the worst case scenario, we need to iterate through all colours to switch the Glossy to Matt, and we we reach a time complexity of O(colours * customers).
Pros: we have a reduced space complexity by not using any temporary collection of possible combinations and we are sure to have an optimized number of Matts at the end.

### Project Hierarchy

The following files and folder are part of this zip file:

- README.MD : the current documentation file
- pom.xml : 			maven build file
- src/main/java : 		source files
- src/main/resources :  folder with default input and output files
- src/test/java: 		junit tests
- src/test/resources :  test input and output files used by the junit tests
- bin : compiled classes
- target : folder where the jar file will be created

### Prerequisites

You will need maven installed to be able to compile and run the tests for this application

### Installing

Test and build the application:

```
maven clean package
```

## Running the tests

Unit tests have been implemented using JUnit 5. The following command runs the unit tests:

```
maven clean test
```

## Executing the jar file

The execution of the application requires an input file and generates an output file that can be
passed as argument of the execution.
If no arguments are passed, the default behaviour of the application is to:
- read the input file from src/main/resources/input_sample_ok.txt
- write the ouput file in src/main/resources/output_sample_ok.txt

```
java -jar ./target/PaintShop-0.0.1-SNAPSHOT.jar [<absolute_path_to_input_file> <absolute_path_to_output_file>]
```


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contact

In case of question or issue, please contact me : seleroy [at] gmail [dot] com


