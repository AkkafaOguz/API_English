package post_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.JsonPlaceHolderPojo;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class PostDeleteWithPojo03 extends JsonPlaceHolderBaseUrl {

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
            I send DELETE request to the Url
        Then
            response body is like { }
     */


    @Test
    public void test() {
        //1. Step:
        spec.pathParam("first", "todos");
        //2. Step:
        JsonPlaceHolderPojo requestBody = new JsonPlaceHolderPojo(55, "Tidy your room", false);
        //3. Step:
        Response response = given().spec(spec).contentType(ContentType.JSON).body(requestBody).when().post("/{first}");
        response.prettyPrint();

        //Get the id of created method
        JsonPath json = response.jsonPath();
        Integer id = json.get("id");

        //Set the Url for DELETE request
        spec.pathParams("first","todos","second",id);
        //Send the DELETE request
        Response response2 = given().spec(spec).when().delete("/{first}/{second}");
        response2.prettyPrint();

        Map<String,Object> actualData = response2.as(HashMap.class);

        assertTrue("DELETE request did not work properly",actualData.size()==0);
        assertTrue("DELETE request did not work properly",actualData.isEmpty());


    }
}
