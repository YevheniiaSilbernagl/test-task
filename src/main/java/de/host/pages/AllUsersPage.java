package de.host.pages;

import de.host.model.BasePage;
import de.host.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yevheniia on 04.08.17
 */
public class AllUsersPage extends BasePage {
    @FindBy(xpath = "//tbody/tr")
    public List<WebElement> rows;

    @FindBy(tagName = "a")
    public WebElement createUserButton;

    public AllUsersPage(WebDriver driver) {
        super(driver);
    }

    public List<User> getUsers() {
        return elements(rows).stream().map(row -> {
            String pass = row.findElement(By.xpath(".//td[3]")).getText();
            return new User(
                    row.findElement(By.xpath(".//td[1]")).getText(),
                    row.findElement(By.xpath(".//td[2]")).getText(),
                    pass, pass);
        }).collect(Collectors.toList());
    }

    public void createNewUser() {
        element(createUserButton).click();
    }

    public Boolean contains(User user) {
        return getUsers().contains(user);
    }
}
