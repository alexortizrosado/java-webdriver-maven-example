Example WebDriver test using TestNG, WebDriver and Maven
==================

This example WebDriver test uses the PageObject pattern (via PageFactory). The test does the following:

1. Opens a Chrome browser
2. Navigates to [ask.com]
3. Searches for the term "tidemark"
4. Grabs all the results in the *main* section and prints out the link, associated title and description. The information is stored in a collection. The test then asserts that only 10 results are returned.
5. Using the navigation at the bottom of the page, goes to the "Next" page.
6. Then grabs all results in the *main* section and prints out the links, associated title and descriptions. The information in a collection and assert that only 10 links are returned.
7. From all the *descriptions* that were stored previously, count the number of times "tidemark" string appears. Print this number.

# Run test using Maven
To run using Maven command-line:
```bash
$ mvn integration-test
```

# Run test using Eclipse & TestNG plugin

1. Open Run > Run Configurations...
2. Right-click on "TestNG" and select "New"
3. Select "Suite" and check the box for "testng.xml"
4. Click Save 
5. Click Run
