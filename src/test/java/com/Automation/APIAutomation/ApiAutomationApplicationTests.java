package com.Automation.APIAutomation;

import com.Automation.APIAutomation.automationReport.BaseTestExtentReport;
import com.Automation.APIAutomation.automationReport.ExtentReport;
import com.Automation.APIAutomation.helper.AssertHelper;
import com.Automation.APIAutomation.helper.FailRetry;
import com.Automation.APIAutomation.helper.TestListeners;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@SpringBootTest(classes = ApiAutomationApplication.class)
@Slf4j
@Listeners(TestListeners.class)
@Configuration
@PropertySource("classpath:application.properties")
class ApiAutomationApplicationTests extends BaseTestExtentReport {
		@Test(description = "validate first demo test case",retryAnalyzer = FailRetry.class)
	void demoTestCase() {
		ExtentReport.extentlog =
				ExtentReport.extentreport.
						startTest("demoTestCase",
								"Validate first demo test case");
		AssertHelper.assertEquals(2,2,"values not equals.... ");
		}

}
