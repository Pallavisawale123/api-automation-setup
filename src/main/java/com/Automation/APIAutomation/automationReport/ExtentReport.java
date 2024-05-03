package com.Automation.APIAutomation.automationReport;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;

/**
 * @author psawale
 */
public class ExtentReport {
    public static ExtentReports extentreport = null;
    public static ExtentTest extentlog;

    public static void initialize(String extentConfigXmlpath) {

        if (extentreport == null) {

            extentreport = new ExtentReports(extentConfigXmlpath, true);
            extentreport.addSystemInfo("Environment", "QA");
            extentreport.loadConfig(new File(System.getProperty("user.dir") + "/resources/extent-config.xml"));

        }
    }
}

