package com.Automation.APIAutomation.helper;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.springframework.stereotype.Component;

/**
 * @author psawale
 */
@Slf4j
@Component
public class AssertHelper {

    public static void assertTrue(boolean condition, String message) {
        try {
            Assert.assertTrue(condition);
            log.info("assert true success");
        } catch (AssertionError error) {
            String errorMsg = "assert true failure";
            log.error("assert true failure: {}", message);
            Assert.fail(errorMsg);
        }
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        try {
            Assert.assertEquals(expected, actual);
            log.info("assert equals success expected : {}, found: {}", expected, actual);
        } catch (AssertionError error) {
            String errorMsg = String.format("assert true failure expected: %s, actual: %s",
                    expected, actual);
            log.error("assert true failure: {}", message);
            Assert.fail(errorMsg);
        }
    }

    public static void assertFalse(boolean condition, String message) {
        try {
            Assert.assertFalse(condition);
            log.info("assert false success");
        } catch (AssertionError error) {
            String errorMsg = "assert false failure";
            log.error("assert false failure: {}", message);
            Assert.fail(errorMsg);
        }
    }
}

