package post_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import test_Data.JsonPlaceHolderTaskData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Post02_Authorization extends JsonPlaceHolderBaseUrl {

     /*
        Given
            https://jsonplaceholder.typicode.com/todos
            {
            "userId": 55,
            "title": "Tidy your room",
            "completed": false
            }
        When
            I send POST Request to the Url
        Then
            Status code is 201
        And
            response body is like
             {
                "userId": 55,
                "title": "Tidy your room",
                "completed": false,
                "id": 201
                }
     */

    @Test
    public void test () {

        spec.pathParam("first","todos");

        //In the testing method, data creation is not good.
        //It is better to separate them, we can manage the data better.
        //It made test method shorter

        JsonPlaceHolderTaskData expectedData = new JsonPlaceHolderTaskData();
        Map<String,Object> expectedDataMap = expectedData.expectedDataSetUp(55,"Tidy your room",false);

        // Authorization : after spec() -> auth() -> basic ("username","password")
        // Get request has no risk, but the methods except GET has risk. That's why, we need authorization.

        Response response = given().spec(spec).auth().basic("admin","1234").contentType(ContentType.JSON).body(expectedDataMap).when().post("/{first}");

        // 200 is used GET request mostly, 201 is used for POST request

        expectedDataMap.put("statusCode",201);
        response.prettyPrint();

        Map <String,Object> actualData = response.as(HashMap.class);

        System.out.println(actualData);

        assertEquals(expectedDataMap.get("userId"),actualData.get("userId"));
        assertEquals(expectedDataMap.get("title"),actualData.get("title"));
        assertEquals(expectedDataMap.get("completed"),actualData.get("completed"));
        assertEquals(expectedDataMap.get("statusCode"),response.getStatusCode());


    }
}
