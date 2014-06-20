package com.tidemark.pageobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

/**
 * WebDriver PageObject for an Ask.com search results page.
 */
public class AskResultsPage {
  private static final Logger logger = Logger.getLogger(AskResultsPage.class
      .getName());

  private WebDriver driver;

  /**
   * Constructor for {@code AskResultsPage}.
   * 
   * @param driver
   *          instance of {@code WebDriver}.
   */
  public AskResultsPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  @FindBy(css = "#teoma-results div.wresult")
  private List<WebElement> results;

  @FindBy(id = "nextPageLink")
  private WebElement next;

  /**
   * Returns the number of result links in a search results page.
   * 
   * @return the number of search result links.
   */
  public int getLinkCount() {
    List<String> links = new ArrayList<String>();
    for (WebElement result : results) {
      links.add(getLinkFromSearchResult(result));
    }
    return links.size();
  }

  /**
   * Returns a list of all search results as {@code WebElement}s.
   * 
   * @return list of all search results as {@code WebElement}s.
   */
  public List<WebElement> getResults() {
    return results;
  }

  /**
   * Using the navigation at the bottom of the search results page, navigates to
   * the "Next" page of results.
   * 
   * @return next page of results;
   */
  public AskResultsPage goToNext() throws ElementNotVisibleException {
    if (next.isDisplayed()) {
      next.click();
      return new AskResultsPage(driver);
    } else {
      throw new ElementNotVisibleException(
          String
              .format(
                  "Navigation does not have a \"Next\" link. Check the search results page. [%s]",
                  driver.getCurrentUrl()));
    }
  }

  /**
   * Logs the links, titles and abstracts to the TestNG Reporter. After running
   * tests, access the results by opening the test-output HTML file and clicking
   * on the "Reporter output" link.
   */
  public void logResultsToReporter() {
    StringBuilder reporterData = new StringBuilder();
    reporterData.append("<h1>Web Results</h1>");
    reporterData.append("<ul>");
    for (WebElement result : results) {
      if (isDictionaryOneBox(result)) {
        continue;
      }
      String title = getTitleFromSearchResult(result);
      reporterData.append(String.format("<li><strong>Title:</strong> %s</li>",
          title));

      String link = getLinkFromSearchResult(result);
      reporterData.append(String.format("<li><strong>Link:</strong> %s</li>",
          link));

      String description = getDescriptionFromSearchResult(result);
      reporterData.append(String.format(
          "<li><strong>Description:</strong> %s</li>", description));

      logger.info(String.format("Adding result to Reporter: [%s] [%s] [%s]",
          link, title, description));

    }
    reporterData.append("</ul>");

    logger.info("Logging web results to Reporter...");
    Reporter.log(reporterData.toString());
  }

  /**
   * Returns the description from a search result.
   * 
   * @param result
   *          search result as a {@code WebElement}.
   * @return
   */
  public String getDescriptionFromSearchResult(WebElement result) {
    return result.findElement(By.className("abstract")).getText();
  }

  private String getTitleFromSearchResult(WebElement result) {
    return result.findElements(By.tagName("a")).get(0).getText();
  }

  private String getLinkFromSearchResult(WebElement result) {
    return result.findElements(By.tagName("a")).get(0).getAttribute("href");
  }

  /**
   * Check if the result is a dictionary one-box. The one-box results don't have
   * a title, link or abstract. Dictionary one-boxes generally appear on the
   * first results page, and not any any subsequent pages.
   * 
   * @param result
   *          an Ask.com search result.
   * @return returns true if result is a dictionary result, otherwise false.
   */
  public boolean isDictionaryOneBox(WebElement result) {
    if (result.getAttribute("class").contains("tsrc_SAS")) {
      logger
          .warning("Found a dictionary one-box. Not parsing for title, link or description...");
      return true;
    }
    return false;

  }
}
