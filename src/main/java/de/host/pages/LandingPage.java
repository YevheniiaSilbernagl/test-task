package de.host.pages;


import de.host.model.BasePage;
import de.host.model.Env;
import de.host.model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by yevheniia on 04.08.17
 */
public class LandingPage extends BasePage {
    @FindBy(id = "name")
    public WebElement nameInput;

    @FindBy(id = "email")
    public WebElement emailInput;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(id = "confirmationPassword")
    public WebElement confirmationPasswordInput;

    @FindBy(css = ".form-actions a")
    public WebElement allUsersLink;

    @FindBy(css = ".form-actions button")
    public WebElement submitButton;

    @FindBy(css = ".alert-error")
    public List<WebElement> validationMessages;

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public void formFillIn(User user) {
        Optional.ofNullable(user.getName()).map(name -> {
            element(nameInput).sendKeys(name);
            return name;
        });
        Optional.ofNullable(user.getEmail()).map(email -> {
            element(emailInput).sendKeys(email);
            return email;
        });
        Optional.ofNullable(user.getPassword()).map(pass -> {
            element(passwordInput).sendKeys(pass);
            return pass;
        });
        Optional.ofNullable(user.getConfirmationPassword()).map(pass -> {
            element(confirmationPasswordInput).sendKeys(pass);
            return pass;
        });
    }

    public void submitNewUser() {
        element(submitButton).click();
    }

    public void openAllUsers() {
        element(allUsersLink).click();
    }

    public void open(Env env) {
        driver.navigate().to(env.landingPage());
    }

    public Map<String, String> getValidationMessages() {
        return elements(validationMessages).stream().filter(WebElement::isDisplayed)
                .collect(Collectors.toMap(elt -> elt.getAttribute("id"), WebElement::getText));
    }

    public Boolean confirmationDialogueShown() {
        return false;//here should be dialogue about not saved data
    }
}
