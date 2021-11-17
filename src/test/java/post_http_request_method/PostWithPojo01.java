package post_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.JsonPlaceHolderPojo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PostWithPojo01 extends JsonPlaceHolderBaseUrl {

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
        //1. Step:
        spec.pathParam("first","todos");
        //2. Step:
        JsonPlaceHolderPojo requestBody = new JsonPlaceHolderPojo(55,"Tidy your room",false);
        //3. Step:
        Response response =given().spec(spec).contentType(ContentType.JSON).body(requestBody).when().post("/{first}");
        //4. Step:

        //1.Way:
        response.then().assertThat().statusCode(201).
                body("userId",equalTo(requestBody.getUserId()),
                        "title",equalTo(requestBody.getTitle()),
                        "completed",equalTo(requestBody.getCompleted()));

        //2.Way: De-serialization
        //Map is a class in Java, and JsonPlaceHolderPojo is also a class in Java
        //If GSON can convert the Json Object to a Map, it should also do it for the JsonPlaceHolderPojo

        JsonPlaceHolderPojo responseBody = response.as(JsonPlaceHolderPojo.class);

        //When we send the request, the response would be not same as the request
        //For instance: API can add additional information to the response body like "id".
        //That's why, when we convert the Json to the Pojo Classes out Pojo Classes would not include these additional information, so it will throw an exception
        //UnrecognizedPropertyException
        //To solve this issue, we need to add an annotation at the top of the Pojo Class:
        //JsonIgnoreProperties (ignoreUnknown = true)

        assertEquals(requestBody.getUserId(),responseBody.getUserId());
        assertEquals(requestBody.getTitle(),responseBody.getTitle());
        assertEquals(requestBody.getCompleted(),responseBody.getCompleted());
    }
}
