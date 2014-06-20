package com.tidemark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tidemark.pageobjects.AskResultsPage;
import com.tidemark.pageobjects.AskSearchPage;

/**
 * WebDriver test that searches for the term "tidemark".
 */
public class AskSearchTest {
  private static final Logger logger = Logger.getLogger(AskSearchTest.class
      .getName());

  private static WebDriver driver;

  @BeforeClass
  public void setUp() {
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
  }

  @AfterClass
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void SearchForTidemarkOnAsk() throws Exception {
    String query = "tidemark";
    List<String> descriptionsWithTidemark = new ArrayList<String>();
    
    // Go to ask.com and search for the term "tidemark"
    AskResultsPage results = new AskSearchPage(driver).search(query);

    // Grab all results in the main section and print out the links, associated
    // title and associated descriptions.
    results.logResultsToReporter();

    // Store the descriptions in a collection.
    descriptionsWithTidemark.addAll(getDescriptionsThatContain(query, results));

    // Assert that only 10 results are returned.
    Assert.assertEquals(results.getResults().size(), 10);

    // Using the navigation at the bottom of the page, goto "Next" page.
    results.goToNext();

    // Grab all results in the main section and print out the links, associated
    // titles and associated descriptions.
    results.logResultsToReporter();

    // Store the descriptions in a collection.
    descriptionsWithTidemark.addAll(getDescriptionsThatContain(query, results));

    // Assert that only 10 links are returned.
    Assert.assertEquals(results.getLinkCount(), 10);

    // From all the descriptions, count the number of times "tidemark" string
    // appears. Print this number.
    logger.info(String.format("Found %d descriptions with the term \"%s\".",
        descriptionsWithTidemark.size(), query));
  }

  /**
   * Returns a list of all search result descriptions that contains the query
   * string. Useful for finding a subset of descriptions that match a specific
   * query.
   * 
   * @param query
   *          the string to search for in the collection of descriptions.
   * @return list of all descriptions that contains the query string
   */
  private List<String> getDescriptionsThatContain(String query,
      AskResultsPage results) {
    List<String> allDescriptions = new ArrayList<String>();
    for (WebElement result : results.getResults()) {
      if (results.isDictionaryOneBox(result)) {
        continue;
      }
      String description = results.getDescriptionFromSearchResult(result);
      if (description.toLowerCase().contains(query)) {
        allDescriptions.add(description);
      }
    }
    return allDescriptions;
  }

}