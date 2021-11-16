package delete_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class Delete01 extends JsonPlaceHolderBaseUrl {

        /*
        Given
            https://jsonplaceholder.typicode.com/todos/198
        When
	 		I send DELETE Request to the Url
	 	Then
		 	Status code is 200
		 	And Response body is { }
     */

    @Test
    public void test () {

        //1.Step: Set the URL
        spec.pathParams("first","todos","second",198);

        //2.Step: Set the expected data

        Map<String,Object> expectedMap = new HashMap<>();

        //3.Step: Send the request and get the response

        Response response = given().spec(spec).contentType(ContentType.JSON).when().delete("/{first}/{second}");
        response.prettyPrint();

        //4.Step: Do assertion

        //GSON:

        Map <String,Object> actualMap = response.as(HashMap.class);

        response.then().assertThat().statusCode(200);
        assertEquals(expectedMap,actualMap);

        //This is not a good logic, actually.
        //If you use delete method, you do not have you create an expected data

        assertTrue(actualMap.size()==0);

        //This is a better logic.

    }
}
