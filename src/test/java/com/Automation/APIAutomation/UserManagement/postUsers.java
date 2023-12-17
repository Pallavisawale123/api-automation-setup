package com.Automation.APIAutomation.UserManagement;

import com.Automation.APIAutomation.ApiAutomationApplication;
import com.Automation.APIAutomation.automationReport.ExtentReport;
import com.Automation.APIAutomation.enums.StatusCode;
import com.Automation.APIAutomation.helper.AssertHelper;
import com.Automation.APIAutomation.helper.TestListeners;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static io.restassured.RestAssured.given;

/**
 * @author psawale
 */
@SpringBootTest(classes = ApiAutomationApplication.class)
@Slf4j
@Listeners(TestListeners.class)

public final class postUsers {
    private static FileInputStream fileInputStream;

    /**
     * Method to return the given json file
     *
     * @param requestBodyFileName
     * @return
     */
    private static FileInputStream fileInputStreamMethod(String requestBodyFileName) {
        try {
            fileInputStream = new FileInputStream(
                    new File(System.getProperty("user.dir") + "/resources/TestData/" + requestBodyFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileInputStream;
    }

    /**
     * API to validate post request using string in body part
     */
    @Test
    public void validatePostWithString() {

        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("validatePostWithString",
                                "ValidatevalidatePostWithString");
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"name\":\"pallavi\",\"job\":\"QA\"}")
                .when()
                .post("https://reqres.in/api/users");
        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.CREATED.code, "");
        log.info("validatePostWithString executed successfully");
        log.info(response.getBody().asString());
    }

    /**
     * API to validate PUT request using string in body part
     */
    @Test
    public void validatePutWithString() {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("validatePutWithString",
                                "validatePutWithString");
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"name\":\"pallavisawale\",\"job\":\"SDET\"}")
                .when()
                .put("https://reqres.in/api/users/2");
        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "");
        log.info("validatePutWithString executed successfully");
        log.info(response.getBody().asString());
    }

    /**
     * API to validate PATCH request using string in body part
     */
    @Test
    public void validatePatchWithString() {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("validatePatchWithString",
                                "validatePatchWithString");
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"name\":\"SawalePallavi\"}")
                .when()
                .patch("https://reqres.in/api/users/2");
        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "");
        log.info("validatePatchWithString executed successfully");
        log.info(response.getBody().asString());
    }

    /**
     * API to validate post request using JSON file in body part
     */
    @Test
    public void validatePostWithJsonFile() throws IOException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("validatePostWithJsonFile",
                                "validatePostWithJsonFile");
        Response response = given()
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("postRequestBody.json")))
                .when()
                .post("https://reqres.in/api/users");

        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.CREATED.code, "");
        log.info("validatePostWithJsonFile executed successfully");
        log.info(response.getBody().asString());
    }

    /**
     * API to validate PATCH request using JSON in body part
     */
    @Test
    public void validatePatchWithJsonFile() throws IOException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("validatePatchWithJsonFile",
                                "validatePatchWithJsonFile");
        Response response = given()
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("patchRequestBody.json")))
                .when()
                .patch("https://reqres.in/api/users" + "/2");
        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "");
        log.info("validatePatchWithJsonFile executed successfully");
        log.info(response.getBody().asString());
    }
    /**
     * API to validate PUT request using JSON in body part
     */
    @Test
    public void validatePutWithJsonFile() throws IOException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("validatePutWithJsonFile",
                                "validatePutWithJsonFile");
        Response response = given()
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("putRequestBody.json")))
                .when()
                .put("https://reqres.in/api/users" + "/2");
        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "");
        log.info("validatePutWithJsonFile executed successfully");
        log.info(response.getBody().asString());
    }
}