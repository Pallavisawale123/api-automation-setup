package com.Automation.APIAutomation.UserManagement;

import com.Automation.APIAutomation.BooksStoarePOJO.Book;
import com.Automation.APIAutomation.BooksStoarePOJO.BookList;
import com.Automation.APIAutomation.BooksStoarePOJO.CollectionOfIsbn;
import com.Automation.APIAutomation.BooksStoarePOJO.PostBook;
import com.Automation.APIAutomation.helper.AssertHelper;
import com.Automation.APIAutomation.utils.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * @author psawale
 */
@Slf4j
@SpringBootTest
public class bookStoare {

    /**
     * we can use @Value annotation to read value from property file
     *
     * @Value("${serverAddress}") private String serverAdd
     */
    String serverAddress = PropertyReader.propertyReader("src//test//resources//application.properties",
            "bookStoreAdd");
    String bookAdd = PropertyReader.propertyReader("src//test//resources//application.properties",
            "bookList");
    String userId = PropertyReader.propertyReader("src//test//resources//application.properties",
            "bookuserId");
    String isbn = PropertyReader.propertyReader("src//test//resources//application.properties",
            "bookisbn");
    static String authToken;
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
     * Method to generate authorization token
     */
    @Test
    private String generateAuthToken() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", "rbhamare");
        requestParams.put("password", "Saisai@1234");

        Response response = (Response) given().spec(requestSpec())
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .post("/GenerateToken").then().spec(responseSpec()).extract();


        log.info("generateAuthToken() executed successfully");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        authToken = jsonPath.getString("token");
        log.info("Auth token is :: " + authToken);
        return authToken;
    }


    public ResponseSpecification responseSpecForBook() {
        ResponseSpecBuilder builder1 = new ResponseSpecBuilder();
        builder1.expectContentType(ContentType.JSON);

        return builder1.build();
    }

    /**
     * Common method to send a request
     *
     * @return
     */
    public RequestSpecification requestSpecForBook() {
        authToken = generateAuthToken();
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(bookAdd);
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Authorization", "Bearer " + authToken);
        builder.addHeader("Content-Type", "application/json");
        return builder.build();
    }

    /**
     * Method to get list of books by passing auth token to header
     */
    @Test
    public void getBookList() {

        BookList getRequest = new BookList();
        Response response = (Response) given().spec(requestSpecForBook())
                .body(getRequest)
                .get("/Books").then().spec(responseSpecForBook()).extract();


        BookList responseBody = response.as(BookList.class);
        AssertHelper.assertEquals(String.valueOf(responseBody.books.get(0).getAuthor()), "Richard E. Silverman",
                "book author of first object not matched");
        AssertHelper.assertEquals(String.valueOf(responseBody.books.get(1).title), "Learning JavaScript Design Patterns",
                "book title of second object not matched");
        log.info(String.valueOf(responseBody.getBooks().size()));
        AssertHelper.assertNotNull(String.valueOf(responseBody.books.get(0)), "booklist empty");
        AssertHelper.assertNotNull(String.valueOf(responseBody.books.get(0).getAuthor()), "booklist empty");
    }

    /**
     * Method to add new book in book Store App
     */
    @Test
    public void addNewBookUsingIsbn() {
        CollectionOfIsbn collectionOfIsbn = new CollectionOfIsbn();
        collectionOfIsbn.setIsbn(isbn);

        List<CollectionOfIsbn> collectionOfIsbnList = new ArrayList<>();
        collectionOfIsbnList.add(collectionOfIsbn);
        PostBook postBook = new PostBook();
        postBook.setUserId(userId);
        postBook.setCollectionOfIsbns(collectionOfIsbnList);

        Response response = (Response) given().spec(requestSpecForBook())
                .body(postBook)
                .post("/Books").then().spec(responseSpecForBook()).extract();

        AssertHelper.assertEquals(postBook.getCollectionOfIsbns().get(0).getIsbn(), isbn,
                "isbn matched");
    }

    /**
     * Method to get newly added book in book Store App
     */
    @Test
    public void getAddedBookUsingIsbn() {
        Book getRequest = new Book();
        log.info(isbn);
        Response response = (Response) given().spec(requestSpecForBook()).header("Content-Type", "application/json")
                .queryParam("ISBN", isbn)
                .body(getRequest)
                .get("/Book").then().spec(responseSpecForBook()).extract();
        Book responseBody = response.as(Book.class);
        AssertHelper.assertEquals(responseBody.getIsbn(), isbn,
                "isbn matched");
        AssertHelper.assertNotNull(responseBody.getAuthor(), "author empty");
    }

    /**
     * Method to delete  book in book Store App
     */
    @Test
    public void deleteBookUsingIsbn() {

        JSONObject requestParams = new JSONObject();
        requestParams.put("isbn", isbn);
        requestParams.put("userId", userId);
        log.info(String.valueOf(requestParams));
        Response response = (Response) given().spec(requestSpecForBook())
                .body(requestParams)
                .delete("/Book").then().spec(responseSpecForBook()).extract();

        log.info(response.prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        authToken = jsonPath.getString("token");
        AssertHelper.assertEquals(response.getStatusCode(), "204",
                "failed to delete..");
    }
}

