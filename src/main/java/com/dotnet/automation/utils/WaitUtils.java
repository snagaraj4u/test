package com.dotnet.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for wait operations
 * 
 * @author QA Automation Team
 */
public final class WaitUtils {
    
    private static final Logger logger = LogManager.getLogger(WaitUtils.class);
    
    private WaitUtils() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Waits for specified duration
     * @param seconds seconds to wait
     */
    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            logger.debug("Waited for {} seconds", seconds);
        } catch (InterruptedException e) {
            logger.warn("Wait interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Waits for specified duration in milliseconds
     * @param milliseconds milliseconds to wait
     */
    public static void waitForMilliseconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
            logger.debug("Waited for {} milliseconds", milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Wait interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}