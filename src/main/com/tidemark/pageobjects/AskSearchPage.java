package com.tidemark.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * WebDriver PageObject for an Ask.com search page.
 */
public class AskSearchPage {

  private WebDriver driver;
  private String url = "http://www.ask.com";

  public AskSearchPage(WebDriver driver) {
    this.driver = driver;
    driver.get(url);
    PageFactory.initElements(driver, this);
  }

  @FindBy(name = "q")
  @CacheLookup
  private WebElement searchBox;

  /**
   * Search for a particular query.
   * 
   * @param query
   *          query term to search for on Ask.com.
   * @return search results page.
   * @throws Exception
   *           if query is an empty string.
   */
  public AskResultsPage search(String query) throws Exception {
    if (query == "" || query.isEmpty()) {
      throw new Exception("Query cannot be an empty string.");
    }
    searchBox.sendKeys(query);
    searchBox.submit();
    return new AskResultsPage(driver);
  }

}
