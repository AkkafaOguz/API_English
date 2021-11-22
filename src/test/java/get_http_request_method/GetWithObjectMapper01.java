package get_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.response.Response;
import org.junit.Test;
import test_Data.JsonPlaceHolderTaskData;
import utils.JsonUtils;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class GetWithObjectMapper01 extends JsonPlaceHolderBaseUrl {

    /*
        Given
	        https://jsonplaceholder.typicode.com/todos/198
        When
	 		I send GET Request to the URL
	 	Then
	 		Status code is 200
	 		And response body is like
	 		{
                "userId": 10,
                "id": 198,
                "title": "quis eius est sint explicabo",
                "completed": true
              }
     */

    @Test
    public void test() {

        //1.Step: set the UrL
        spec.pathParams("first","todos", "second", 198);

        //2.Step: Set the expected data

        JsonPlaceHolderTaskData expected = new JsonPlaceHolderTaskData();
        String expectedData = expected.expectedDataInString(10,"quis eius est sint explicabo",true);
        HashMap<String, Object> expectedDataMap = JsonUtils.convertJsonToJava(expectedData, HashMap.class);

        //3.Step: Send the request and get the response

        Response response = given().spec(spec).when().get("/{first}/{second}");

        HashMap<String,Object> actualDataMap = JsonUtils.convertJsonToJava(response.asString(),HashMap.class);

        //4.Step: Do the assertion

        assertEquals(200,response.statusCode());
        assertEquals(expectedDataMap.get("userId"),actualDataMap.get("userId"));
        assertEquals(expectedDataMap.get("title"),actualDataMap.get("title"));
        assertEquals(expectedDataMap.get("completed"),actualDataMap.get("completed"));

    }
}
