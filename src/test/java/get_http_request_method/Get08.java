package get_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Get08 extends JsonPlaceHolderBaseUrl {

    /*
        The biggest challenge in API Testing is data types
        1)Java uses Objects and Primitives as data type, API uses XML, Json, etc.
          Java and API are using different data types, but they should communicate with each other.
          Communication is impossible with different data types.
          We have 2 options:
          i)Convert Json to Java Object ==> De-Serialization
          ii)Convert Java Object to Json ==> Serialization
          For Serialization and De-Serialization, we have 2 option
          a)GSON --> Google Created it
          b)Object Mapper --> More popular
    */


    /*
        Given
            https://jsonplaceholder.typicode.com/todos/2
        When
	  		I send GET Request to the URL
	    Then
	  		Status code is 200
	  		And "completed" is false
	  		And "userId" is 1
	  		And "title" is "quis ut nam facilis et officia qui"
	  		And header "Via" is "1.1 vegur"
	  		And header "Server" is "cloudflare"

	  		{
                "userId": 1,
                "id": 2,
                "title": "quis ut nam facilis et officia qui",
                "completed": false
            }
     */

    @Test
    public void test(){

        // 1. Step:
        spec.pathParams("first","todos","second",2);

        // 2. Step:
        Map<String,Object> expectedData = new HashMap<>();
        expectedData.put("userId",1);
        expectedData.put("id",2);
        expectedData.put("title","quis ut nam facilis et officia qui");
        expectedData.put("completed",false);
        expectedData.put("Status Code", 200);
        expectedData.put("Via","1.1 vegur");
        expectedData.put("Server","cloudflare");

        System.out.println(expectedData);

        // 3. Step:
        Response response = given().spec(spec).when().get("/{first}/{second}");

        response.prettyPrint();

        //By using GSON, we are able to convert Json data which are coming from API to Java Object
        //To be able to use GSON, we need to add dependency
        Map <String,Object> actualData = response.as(HashMap.class); // as method is coming from GSON
        System.out.println(actualData);

        // 4. Step:

        assertEquals(expectedData.get("userId"),actualData.get("userId"));
        assertEquals(expectedData.get("id"),actualData.get("id"));
        assertEquals(expectedData.get("title"),actualData.get("title"));
        assertEquals(expectedData.get("completed"),actualData.get("completed"));
        assertEquals(expectedData.get("Status Code"),response.getStatusCode());
        assertEquals(expectedData.get("Via"),response.getHeader("Via"));
        assertEquals(expectedData.get("Server"),response.getHeader("Server"));

    }
}
