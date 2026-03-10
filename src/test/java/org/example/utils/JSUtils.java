package org.example.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.example.stepdefinitions.Hooks;
import org.openqa.selenium.WebElement;

public class JSUtils {

    public static Object click(Object element) {
        return ((JavascriptExecutor) Hooks.getDriver()).executeScript("arguments[0].click();", element);
    }

    public static void scrollAndClick(Object element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", element);
    }

    public static void scrollTo(WebElement element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        Thread.sleep(500);
    }
}
