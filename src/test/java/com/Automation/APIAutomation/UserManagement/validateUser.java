package com.Automation.APIAutomation.UserManagement;

import com.Automation.APIAutomation.ApiAutomationApplication;
import com.Automation.APIAutomation.enums.StatusCode;
import com.Automation.APIAutomation.helper.AssertHelper;
import com.Automation.APIAutomation.helper.SoftAssertionUtil;
import com.Automation.APIAutomation.helper.TestListeners;
import com.Automation.APIAutomation.utils.JsonReader;
import com.Automation.APIAutomation.utils.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author psawale
 */
@SpringBootTest(classes = ApiAutomationApplication.class)
@Slf4j
@Listeners(TestListeners.class)
public class validateUser {
//    We can use @Value annotation to read value from properties file ,using just for practice
    String serverAddress = PropertyReader.propertyReader("config.properties", "server");
    String endpoint=JsonReader.getTestData("endpoint");
    String URL = serverAddress + endpoint;




    /**
     * API to get the users list ,and validate response
     */
    @Test
    public void getUserData() {

        Response response =
                given().
                when().get(URL).
                then().extract().response();

        AssertHelper.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code,"response status not matched");

    }

    /**
     * API to get response body and validate response not empty
     */
    @Test()
    public void validateGetResponseBody() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and validate the response body using 'then'
        given()
                .when()
                .get("/todos/1")
                .then().extract().response().then()
                .assertThat()
                .statusCode(200)
                .body(not(isEmptyString()));

    }

    /**
     * validate API using HasItems
     */
    @Test(description = "validateResponseHasItems" )
    public void validateResponseHasItems() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response =
                given()
                        .when()
                        .get("/posts")
                        .then()
                        .extract()
                        .response();

        // Use Hamcrest to check that the response body contains specific items
        assertThat(response.jsonPath().getList("title"),
                hasItems("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "qui est esse"));
    }
    /*
     * [
     *   {
     *     "postId": 1,
     *     "id": 1,
     *     "name": "id labore ex et quam laborum",
     *     "email": "Eliseo@gardner.biz",
     *     "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
     *   },
     *   {
     *     "postId": 1,
     *     "id": 2,
     *     "name": "quo vero reiciendis velit similique earum",
     *     "email": "Jayne_Kuhic@sydney.com",
     *     "body": "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"
     *   },
     *   {
     *     "postId": 1,
     *     "id": 3,
     *     "name": "odio adipisci rerum aut animi",
     *     "email": "Nikita@garfield.biz",
     *     "body": "quia molestiae reprehenderit quasi aspernatur\naut expedita occaecati aliquam eveniet laudantium\nomnis quibusdam delectus saepe quia accusamus maiores nam est\ncum et ducimus et vero voluptates excepturi deleniti ratione"
     *   },
     *   {
     *     "postId": 1,
     *     "id": 4,
     *     "name": "alias odio sit",
     *     "email": "Lew@alysha.tv",
     *     "body": "non et atque\noccaecati deserunt quas accusantium unde odit nobis qui voluptatem\nquia voluptas consequuntur itaque dolor\net qui rerum deleniti ut occaecati"
     *   },
     *   {
     *     "postId": 1,
     *     "id": 5,
     *     "name": "vero eaque aliquid doloribus et culpa",
     *     "email": "Hayden@althea.biz",
     *     "body": "harum non quasi et ratione\ntempore iure ex voluptates in ratione\nharum architecto fugit inventore cupiditate\nvoluptates magni quo et"
     *   }
     * ]
     */


//    validate single field from  list of map - It will check first email from the above response
    @Test
    public void testGetUserList() {

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        given()
                .when()
                .get("/comments?postId=1")
                .then()
                .statusCode(200)
                .body("email[0]", containsString("Eliseo@gardner.biz"));
    }

    /**
     * API to get the users by passing queryParam
     */
    @Test
    public void testGetUsersWithQueryParameters() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert that the response contains 6 users
        response.then().body("data", hasSize(5));

        // Assert that the first user in the list has the correct values


        response.then().body("data[1].id", is(2));
        response.then().body("data[1].email", is("Jayne_Kuhic@sydney.com"));

    }

    @Test()
    public void validateStatusCodeGetUser() {
        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get("https://reqres.in/api/users");
        int actualStatusCode = resp.statusCode();
        AssertHelper.assertEquals(actualStatusCode, StatusCode.SUCCESS,"status code not matched"); //Testng
    }

    /**
     * Get API by passing multiple query param
     * query param is used with get request for filters
     */
    @Test
    public void testGetUsersWithMultipleQueryParams() {
        Response response =
                given()
                        .queryParam("page", 2)
                        .queryParam("per_page", 3)
                        .queryParam("rtqsdr", 4)
                        .when()
                        .get("https://reqres.in/api/users")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    /**
     * Get API by passing multiple form param
     * form param is used with POST request for filters
     */
    @Test
    public void testCreateUserWithFormParam() {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "John Doe")
                .formParam("job", "Developer")
                .when()
                .post("https://reqres.in/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Assert that the response contains the correct name and job values
        response.then().body("name", equalTo("John Doe"));
        response.then().body("job", equalTo("Developer"));
    }

    /**
     * API to demonstrate how we can pass the headers to API request
     */
    @Test
    public void testGetUserListWithHeader() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
       log.info("testGetUserListWithHeader Executed Successfully");
    }
    /**
     * API to demonstrate how we can pass the multiple headers to API request
     */
    @Test
    public void testWithTwoHeaders() {
        given()
                .header("Authorization", "bearer ywtefdu13tx4fdub1t3ygdxuy3gnx1iuwdheni1u3y4gfuy1t3bx")
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
        System.out.println("testWithTwoHeaders Executed Successfully");
    }

    /**
     * API to demonstrate how we can pass the multiple headers to API request using MAP
     */
    @Test
    public void testTwoHeadersWithMap() {

        // Create a Map to hold headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "bearer ywtefdu13tx4fdub1t3ygdxuy3gnx1iuwdheni1u3y4gfuy1t3bx");

        // Send a GET request with headers
        given()
                .headers(headers)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);

    }

    @Test
    public void testFetchHeaders() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract().response();

        Headers headers = response.getHeaders();
        //if you want to print all headers then comment 244,246,247
        for (Header h : headers) {
            if (h.getName().contains("Server")) {
                System.out.println(h.getName() + " : " + h.getValue());
                AssertHelper.assertEquals(h.getValue(), "cloudflare","");
                       }
        }
    }

    /**
     * API to delete user
     */
    @Test(groups = {"SmokeSuite", "RegressionSuite"})
    public void verifyStatusCodeDelete() {
        Response resp = given()
                .delete("https://reqres.in/api/users/2");
        AssertHelper.assertEquals(resp.getStatusCode(), StatusCode.SUCCESS,"Status not matched");
        System.out.println("verifyStatusCodeDelete executed successfully");
    }


    @Test
    public void hardAssertion() {
        System.out.println("hardAssert");
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        System.out.println("hardAssert");
    }

    @Test
    public void softAssertion() {

        System.out.println("softAssert");
        SoftAssertionUtil.assertTrue(true, "");
        SoftAssertionUtil.assertAll();
    }




    @DataProvider(name = "testdata")
    public Object[][] testData() {
        return new Object[][]{
                {"1", "John"},
                {"2", "Jane"},
                {"3", "Bob"}
        };
    }

    /**
     * this API will execute 3 times as we provide 3 testDataSize
     * @param id
     * @param name
     */
    @Test(dataProvider = "testdata")
    @Parameters({"id", "name"})
    public void testEndpoint(String id, String name) {
        given()
                .queryParam("id", id)
                .queryParam("name", name)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200);
    }


}

