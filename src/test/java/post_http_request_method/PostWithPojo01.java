package post_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.JsonPlaceHolderPojo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
        response.then().assertThat().statusCode(200).
                body("userId",equalTo(requestBody.getUserId()),
                        "title",equalTo(requestBody.getTitle()),
                        "completed",equalTo(requestBody.getCompleted()));


    }
}
