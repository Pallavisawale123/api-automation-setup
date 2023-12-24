package com.Automation.APIAutomation.UserManagement;

import com.Automation.APIAutomation.ApiAutomationApplication;
import com.Automation.APIAutomation.automationReport.BaseTestExtentReport;
import com.Automation.APIAutomation.automationReport.ExtentReport;
import com.Automation.APIAutomation.enums.StatusCode;
import com.Automation.APIAutomation.helper.AssertHelper;
import com.Automation.APIAutomation.helper.TestListeners;
import com.Automation.APIAutomation.utils.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
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
public final class postUsers extends BaseTestExtentReport {
    private static FileInputStream fileInputStream;
    /**
     * we can use @Value annotation to read value from property file
     * @Value("${serverAddress}") private String serverAdd
     */
    String serverAddress = PropertyReader.propertyReader("src//test//resources//application.properties",
            "serverAddress");

    ResponseSpecBuilder builder;
    ResponseSpecification responseSpecification;

    /**
     * Common method to save response
     *
     * @return
     */
    public ResponseSpecification responseSpec() {
        ResponseSpecBuilder builder1 = new ResponseSpecBuilder();
        builder1.expectContentType(ContentType.JSON);
        return builder1.build();
    }

    /**
     * Common method to send a request
     *
     * @return
     */
    public RequestSpecification requestSpec() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(serverAddress);
        builder.setContentType(ContentType.JSON);
        return builder.build();
    }

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
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "pallavi");
        requestParams.put("job", "QA");

//        Response response = given()
//                .header("Content-Type", "application/json")
//                .body(requestParams.toJSONString())
//                .when()
//                .post("https://reqres.in/api/users");

        Response response = (Response) given().spec(requestSpec())
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .post().then().spec(responseSpec()).extract();

        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.CREATED.code, "Verifying status code " +
                "for POST API");
        log.info("validatePostWithString executed successfully");
        log.info(response.getBody().asString());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.getString("job");
        log.info(jsonPath.getString("job"));
        AssertHelper.assertEquals(jsonPath.getString("job"), "QA",
                "Verifying job profile ");

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
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "pallavisawale");
        requestParams.put("job", "SDET");

//        Response response = given()
//                .header("Content-Type", "application/json")
//                .body(requestParams.toJSONString())
//                .when()
//                .put("https://reqres.in/api/users/2");
        Response response = (Response) given().spec(requestSpec())
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .put("/2").then().spec(responseSpec()).extract();

        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "");
        log.info("validatePutWithString executed successfully");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.getString("job");
        log.info(jsonPath.getString("job"));
        AssertHelper.assertEquals(jsonPath.getString("job"), "SDET",
                "Verifying job profile ");
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
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "SawalePallavi");

//        Response response = given()
//                .header("Content-Type", "application/json")
//                .body(requestParams.toJSONString())
//                .when()
//                .patch("https://reqres.in/api/users/2");

        Response response = (Response) given().spec(requestSpec())
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .patch("/2").then().spec(responseSpec()).extract();

        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "");
        log.info("validatePatchWithString executed successfully");
        log.info(response.getBody().asString());
        log.info("validatePutWithString executed successfully");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.getString("name");
        log.info(jsonPath.getString("name"));
        AssertHelper.assertEquals(jsonPath.getString("name"), "SawalePallavi",
                "Verifying name of employee");
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
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .body(IOUtils.toString(fileInputStreamMethod("postRequestBody.json")))
//                .when()
//                .post("https://reqres.in/api/users");

        Response response = (Response) given().spec(requestSpec())
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("postRequestBody.json")))
                .post().then().spec(responseSpec()).extract();

        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.CREATED.code,
                "Verifying POST API status code");
        log.info("validatePostWithJsonFile executed successfully");
        log.info(response.getBody().asString());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.getString("job");
        log.info(jsonPath.getString("job"));
        AssertHelper.assertEquals(jsonPath.getString("job"), "SoftwareQA",
                "Verifying job profile ");
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
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .body(IOUtils.toString(fileInputStreamMethod("patchRequestBody.json")))
//                .when()
//                .patch("https://reqres.in/api/users" + "/2");
        Response response = (Response) given().spec(requestSpec())
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("patchRequestBody.json")))
                .patch("/2").then().spec(responseSpec()).extract();

        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code,
                "Verifying PATCH API status code");
        log.info("validatePatchWithJsonFile executed successfully");
        log.info(response.getBody().asString());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.getString("name");
        log.info(jsonPath.getString("name"));
        AssertHelper.assertEquals(jsonPath.getString("name"), "pallavisawale",
                "Verifying name of employee ");
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
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .body(IOUtils.toString(fileInputStreamMethod("putRequestBody.json")))
//                .when()
//                .put("https://reqres.in/api/users" + "/2");
        Response response = (Response) given().spec(requestSpec())
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("putRequestBody.json")))
                .put("/2").then().spec(responseSpec()).extract();

        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code,
                "Verifying PUT API status code");
        log.info("validatePutWithJsonFile executed successfully");
        log.info(response.getBody().asString());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        jsonPath.getString("job");
        log.info(jsonPath.getString("job"));
        AssertHelper.assertEquals(jsonPath.getString("job"), "SDET",
                "Verifying job profile ");
    }
}