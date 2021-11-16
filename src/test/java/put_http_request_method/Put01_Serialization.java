package put_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import test_Data.JsonPlaceHolderTaskData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class Put01_Serialization extends JsonPlaceHolderBaseUrl {

        /*
        Given
	        https://jsonplaceholder.typicode.com/todos/198
	        {
                "userId": 21,
                "title": "Wash the dishes",
                "completed": false
            }
        When
	 		I send PUT Request to the Url
	    Then
	   	   Status code is 200
	   	   And response body is like
	   	   {
                "userId": 21,
                "title": "Wash the dishes",
                "completed": false
           }
     */


    @Test
    public void test() {
        //1.Step: Set the URL
        spec.pathParams("first", "todos", "second", 198);

        //2.Step: Set the expected data
        JsonPlaceHolderTaskData requestBody = new JsonPlaceHolderTaskData();
        Map<String, Object> requestBodyMap = requestBody.expectedDataSetUp(21, "Wash the dishes", false);


        //3.Step: Send the request and get the response
        Response response = given().spec(spec).body(requestBodyMap).contentType(ContentType.JSON).when().put("/{first}/{second}");
        response.prettyPrint();

        requestBodyMap.put("statusCode", 200);

        //4.Step: Do assertion

        //1. Way:

        response.then().assertThat().statusCode(200).
                body("userId", equalTo(requestBodyMap.get("userId")),
                        "title", equalTo(requestBodyMap.get("title")),
                        "completed", equalTo(requestBodyMap.get("completed")));

        //2. Way: GSON

        Map<String, Object> actualBodyMap = response.as(HashMap.class);

        assertEquals(requestBodyMap.get("statusCode"), response.getStatusCode());
        assertEquals(requestBodyMap.get("userId"), actualBodyMap.get("userId"));
        assertEquals(requestBodyMap.get("title"), actualBodyMap.get("title"));
        assertEquals(requestBodyMap.get("completed"), actualBodyMap.get("completed"));


        //How to use GSON in serialization : Java Object -> Json Data
        Map <String,Integer> ages = new HashMap<>();
        ages.put("Ali Can", 13);
        ages.put("Veli Can", 15);
        ages.put("Ayse Kan", 21);
        ages.put("Mary Star", 65);

        System.out.println(ages); // {Veli Can=15, Mary Star=65, Ayse Kan=21, Ali Can=13}

        //Convert ages map to Json data

        //1.Step: Create Gson Object

        Gson gson = new Gson();

        //2.Step: By using GSON Object, select method to convert Java Object to Json Data

        String jsonFromMap = gson.toJson(ages);
        System.out.println(jsonFromMap); // {"Veli Can":15,"Mary Star":65,"Ayse Kan":21,"Ali Can":13}


    }


}
