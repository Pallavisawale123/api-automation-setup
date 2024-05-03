package com.Automation.APIAutomation.automationReport;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Date;

/**
 * @author psawale
 */
@Slf4j
public class BaseTestExtentReportHelper {
    /*
     * create folder
     */
    public static void CreateFolder(String path)  {
        //File is a class inside java.io package
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();//mkdir is used to create folder
        } else
            log.info("Folder already created");
    }

    /*
     * Return current time stamp
     */
    public static String Timestamp() {
        Date now = new Date();
        String Timestamp = now.toString().replace(":", "-");
        return Timestamp;
    }
}

