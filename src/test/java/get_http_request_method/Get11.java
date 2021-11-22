package get_http_request_method;

import base_urls.GoRestApiBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Get11 extends GoRestApiBaseUrl {

    /*
    Given
        https://gorest.co.in/public/v1/users
    When
        User send GET request
    Then
        The value of "pagination total" is 1552
    And
        The "current link" is "https://gorest.co.in/public/v1/users?page=1"
    And
        The number if users should be 31
    And
        The number of "active users" should e 9
    And
        "Karthik Johar" , "Ganapati Singh" and "Manisha Johar" are among the users
    And
        The female users are less than male users

     */

    @Test
    public void test() {

        //1.Step: Set the Url

        spec.pathParam("first","users");

        //2.Step: Set the expected data

        //3.Step: Send the request and get the response

        Response response = given().spec(spec).when().get("/{first}");
        response.prettyPrint();

        //4.Step: Do assertion

        response.then().assertThat().statusCode(200).
                //"meta.pagination.total",equalTo(1744)
                body("meta.pagination.links.current",equalTo("https://gorest.co.in/public/v1/users?page=1"),
                        "data.id",hasSize(20),
                        "data.status",hasItem("active"),
                        "data.name",hasItems("Satyen Bhat" , "Daevika Menon", "Fr. Hari Kaniyar"));

        JsonPath json = response.jsonPath();

        assertEquals(200,response.getStatusCode());
        assertEquals(20,json.getInt("meta.pagination.limit"));
        assertEquals("https://gorest.co.in/public/v1/users?page=1",json.getString("meta.pagination.links.current"));
        assertEquals(20,json.getList("data.id").size());
        assertTrue(json.getList("data.status").contains("active"));
        List<String> expectedNames = Arrays.asList("Satyen Bhat" , "Daevika Menon", "Fr. Hari Kaniyar");
        assertTrue(json.getList("data.name").containsAll(expectedNames));

        //Number of females are more than number of males
        //1.Way: Loop
        List <String> genderList = json.getList("data.gender");

        int maleCounter = 0;
        int femaleCounter = 0;

        for (String w: genderList) {
            if (w.equals("male")){
                maleCounter++;
            } else {
                femaleCounter++;
            }
        }
        assertTrue(femaleCounter<maleCounter);


        //WHEN YOU USE GROOVY WITH ARRAY TYPE JSON, THIS IS THE SYNTAX -> "data.findAll{it.gender=='female'}.gender"
        //BEFORE FIND ALL YOU SHOULD PUT THE NAME OF ARRAY

        //2.Way: Groovy

        List<String> numberOfFemale = json.getList("data.findAll{it.gender=='female'}.gender");
        List<String> numberOfMale = json.getList("data.findAll{it.gender=='male'}.gender");
        assertTrue(numberOfFemale.size()<numberOfMale.size());



        //The number of active status is more than 5
        //1.Way: Loop
        List<String> statusList = json.getList("data.status");

        int statusCounter = 0;
        for (String w: statusList) {
            if (w.equals("active")){
                statusCounter++;
            }
        }
        assertTrue(statusCounter>5);

        //2.Way: Groovy
        List <String> listOfActiveStatus = json.getList("data.findAll{it.status=='active'}.status");

        assertTrue(listOfActiveStatus.size()>5);
    }
}
