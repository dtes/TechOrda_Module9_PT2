package org.example.utils;

public class XPathUtils {

    /**
     * Generates a case-insensitive XPath contains expression.
     * XPath 1.0 doesn't have lower-case() function, so we use translate().
     *
     * @param element XPath element to check (e.g., ".", "text()", "@class")
     * @param searchText Text to search for (case-insensitive)
     * @return XPath expression for case-insensitive contains check
     */
    public static String containsIgnoreCase(String element, String searchText) {
        return "contains(translate(" + element + ", 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + searchText.toLowerCase() + "')";
    }

    /**
     * Generates a case-insensitive XPath contains expression for text content.
     * Convenience method for searching in text().
     *
     * @param searchText Text to search for (case-insensitive)
     * @return XPath expression for case-insensitive text contains check
     */
    public static String textContainsIgnoreCase(String searchText) {
        return containsIgnoreCase("text()", searchText);
    }

    /**
     * Generates a case-insensitive XPath contains expression for current element.
     * Convenience method for searching in current element (.).
     *
     * @param searchText Text to search for (case-insensitive)
     * @return XPath expression for case-insensitive element contains check
     */
    public static String elementContainsIgnoreCase(String searchText) {
        return containsIgnoreCase(".", searchText);
    }
}