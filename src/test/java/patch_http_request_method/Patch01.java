package patch_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import test_Data.JsonPlaceHolderTaskData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class Patch01 extends JsonPlaceHolderBaseUrl {

      /*
        Given
	        https://jsonplaceholder.typicode.com/todos/198
	        {
                "title": "Wash the dishes",
            }
        When
	 		I send PATCH Request to the Url
	    Then
	   	   Status code is 200
	   	   And response body is like
	   	   {
                "userId": 10,
                "title": "Wash the dishes",
                "completed": true,
                "id": 198
           }
     */


    @Test
    public void test() {
        //1.Step: Set the URL
        spec.pathParams("first", "todos", "second", 198);
        //2.Step: Set the expected data
        JsonPlaceHolderTaskData requestBody = new JsonPlaceHolderTaskData();
        Map<String, Object> requestBodyMap = requestBody.expectedDataSetUpForPatch(null, "Wash the dishes", null);
        //Primitive data type do not accept "null" as a value!

        //3.Step: Send the request and get the response
        Response response = given().spec(spec).contentType(ContentType.JSON).body(requestBodyMap).when().patch("/{first}/{second}");
        response.prettyPrint();


        //4.Step: Do assertion

        //1.Logic: No need to verify the data that you did not touch

        response.
                then().
                assertThat().
                statusCode(200).
                body("title", equalTo(requestBodyMap.get("title")));

        //2.Logic:

        Map <String, Object> expectedDataMap = requestBody.expectedDataSetUp(10,"Wash the dishes",true);
        expectedDataMap.put("statusCode",200);
        response.then().assertThat().statusCode(200).
                body("userId", equalTo(expectedDataMap.get("userId")),
                        "title" ,equalTo(expectedDataMap.get("title")),
                        "completed",equalTo(expectedDataMap.get("completed")));

        // Doing assertion with GSON:

        HashMap <String, Object> actualBodyMap = response.as(HashMap.class);

        assertEquals(expectedDataMap.get("statusCode"),response.statusCode());
        assertEquals(expectedDataMap.get("userId"),actualBodyMap.get("userId"));
        assertEquals(expectedDataMap.get("title"),actualBodyMap.get("title"));
        assertEquals(expectedDataMap.get("completed"),actualBodyMap.get("completed"));


    }

}
