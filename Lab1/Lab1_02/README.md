# 2022/2023 TQS Lab 1_2

In this exercise, the `JUnit` testing framework was used in combination with the `JaCoCo` library. The purpose of `JaCoCo` is to perform a software metric known as **Code Coverage**, which measures the number of code lines being executed during automated tests.

`JaCoCo` is a code coverage library for Java that provides information on how much of your codebase is being tested through automated testing. It generates reports that show the percentage of code coverage achieved by tests, highlighting which lines of code have been executed during the test runs and which ones have not. This information is valuable to developers, as it can help them identify areas of the code that are not being tested and may require additional testing or attention.

## JaCoCo Maven Configuration

In order to  use  JaCoCo, we need to specify a maven plugin in  the `pom.xml` file:

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.8</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

 ## Generate Report

To generate the JaCoCo report:

```
mvn test jacoco:report
```

## Results 

###### Opening the `index.html` file in the Browser, present on the  directory `target/site/jacoco `  we can see the folowing results:

![](https://github.com/Pjnp5/TQS_103234/blob/main/Lab1/Lab1_02/Screenshot%202023-02-21%20at%2013.25.39.png)

###### Clicking on `tqs.euromilions`:
https://github.com/Pjnp5/TQS_103234/blob/main/Lab1/Lab1_02/Screenshot%202023-02-21%20at%2013.50.03.png)

###### Cliking on `tqs.sets`:

![](https://github.com/Pjnp5/TQS_103234/blob/main/Lab1/Lab1_02/Screenshot%202023-02-21%20at%2013.53.18.png)

###  Which classes/methods offer less coverage? Are all possible decision branches being covered?

The `Hashcode()` method in the `Dip` class was not included in any of the unit tests written. As a result, the overall code coverage for the class was 89%, with the auto-generated methods such as`equals()` also remaining untested. In `equals()` case only the situation where all the if clauses fail is used. 

In the `CuponEuromilions` class,  the `format()` and `countDips()` methods have no unit test writen for them.

At `EuromilionsDraw` class the `generateRandomDraw()` and `getDrawResults()` methods also have no unit tests for them.

##### BoundedSetOfNaturals 

The set `BoundedSetOfNaturals` has an coverage of 54% of the code and only 50% of the branches form if clauses are checked.
The methods `fromArray(int[])`, `hashCode()`, `size()` and `intersects(BoundedSetOfNaturals)` have no unit tests written for them, for the method `add(int)`  only 56% of the code is used in tests and only 50% of the branches in if clauses are used, in the case of `equals(Object)` 76% of the code is checked by the tests and 50% of the branches from if clauses are checked.

### testAddElement()

In this test we should check if the the elements are added (or not) depending of certain conditions,  the first was a basic one, adding a number to a set were it was possible, see if the set contains it after and its size.

After that added a three checks:

​	1º: To see if an duplicated element is added, should throw an `IllegalArgumentException`.

​	2º: To see if an negative element is added, should throw an `IllegalArgumentException`.

​	3º: To see if an number was added to an already full set, should throw an `IllegalArgumentException`.

The second case was wrong and causing the test failure, we were trying to add a element to a set that was already full , sets created using the `fromArray()` method have a `maxsize` equal to the array passed, so no extra numbers are allowed to be added.
I rewrote the test to check if an `IllegalArgumentException` was thrown when you tried to add a number and if the number was not added, after i checked if the sets still had the same size.

### testAddFromBadArray()

Added an check that should return an `IllegalArgumentException`, when using an array to add elements, that array must not have duplicated numbers.

### testSetIsEmpty()

Added this test to see if the array was empty or not when created.

### testIntersection()

Added this test to check if the `intesects()` method was implemented as suposed, it is able to see if a set contains another set, if one of the elements of the second set is not on the first it should return false, if every all elements exists returns true.

## After changes

![](https://github.com/Pjnp5/TQS_103234/blob/main/Lab1/Lab1_02/Screenshot%202023-02-21%20at%2018.01.48.png)

After making changes to the tests and the class I was able to raise the coverage from 54% to 89%, only the `@overwrite` methods were not changed or got tests, if I added tests to them coverage probably would go up to 100%.

![](https://github.com/Pjnp5/TQS_103234/blob/main/Lab1/Lab1_02/Screenshot%202023-02-21%20at%2018.08.38.png)
