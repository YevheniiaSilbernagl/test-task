package de.host.model;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yevheniia on 04.08.17
 */
public class BasePage {
    private static final Logger LOGGER = Logger.getLogger(WebElementFacade.class);
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElementFacade element(WebElement webElement) {
        return WebElementFacade.wrapWebElement(webElement);
    }

    public List<WebElementFacade> elements(List<WebElement> webElements) {
        return webElements.stream().map(this::element).collect(Collectors.toList());
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}
