package org.example.stepdefinitions;

import io.cucumber.java.en.Then;

import static org.junit.Assert.assertEquals;

public class SampleSteps {

    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        String actualTitle = Hooks.getDriver().getTitle();
        assertEquals(expectedTitle, actualTitle);
    }
}
