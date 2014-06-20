Example WebDriver tests using TestNG, WebDriver and Maven
==================

This example WebDriver test uses the PageObject pattern (via PageFactory). The test does the following:

1. Opens a Chrome browser
2. Navigates to [ask.com]
3. Searches for the term "tidemark"
4. Grabs all the results in the *main* section and prints out the link, associated title and description. The information is stored in a collection. The test then asserts that only 10 results are returned.
5. Using the navigation at the bottom of the page, goes to the "Next" page.
6. Then grabs all results in the *main* section and prints out the links, associated title and descriptions. The information in a collection and assert that only 10 links are returned.
7. From all the *descriptions* that were stored previously, count the number of times "tidemark" string appears. Print this number.
 
The following criteria were used in developing:

1. Create a new project. Do not use any existing project
2. You can run this against any browser of your choice
3. You *must* use Java and Selenium / WebDriver
4. Use your judgment and design the APIs as you see fit
 
*Extra credit:*
Doing the following is not a requirement but they are nice-to-haves that we would like to see:

1. Modular code with OO design
2. Use of Page Object Pattern
3. Code comments / Java docs
4. Logging using a logging framework instead of System.out.println(). (No need for a configuration file)
5. Using *TestNG's* configuration/test methods to run the program

# Running using Maven
To run using Maven command-line:
```bash
$ mvn integration-test
```

# Running using Eclipse > TestNG plugin

1. Open Run > Run Configurations...
2. Right-click on "TestNG" and select "New"
3. Select "Suite" and check the box for "testng.xml"
4. Click Save 
5. Click Run
