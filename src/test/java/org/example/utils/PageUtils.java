package org.example.utils;

import org.example.stepdefinitions.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageUtils {
    private static final Logger log = LoggerFactory.getLogger(PageUtils.class);

    public static void acceptAllCookies() {
        try {
            WebElement button = Hooks.getWait()
                    .until(ExpectedConditions
                            .presenceOfElementLocated(
                                    By.id("onetrust-accept-btn-handler")));
            JSUtils.click(button);
        } catch (Exception e) {
            log.error("Cannot accept cookies: {}", e.getMessage(), e);
        }
    }

}
