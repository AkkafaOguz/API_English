package post_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingPojo;
import pojos.JsonPlaceHolderPojo;
import test_Data.JsonPlaceHolderTaskData;
import utils.JsonUtils;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class PostWithObjectMapperAndPojo extends JsonPlaceHolderBaseUrl {

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
            response body is like {
                                    "userId": 55,
                                    "title": "Tidy your room",
                                    "completed": false,
                                    "id": 201
                                    }
     */


    @Test
    public void test() {

        //1.Step: Set the Url

        spec.pathParam("first","todos");

        //2.Step: Set the expected data

        JsonPlaceHolderTaskData expected = new JsonPlaceHolderTaskData();
        String expectedData = expected.expectedDataInString(55,"Tidy your room",false);

        JsonPlaceHolderPojo expectedDataPojo = JsonUtils.convertJsonToJava(expectedData, JsonPlaceHolderPojo.class);
        System.out.println(expectedDataPojo);

        //3.Step: Send the request and get the response

        Response response = given().spec(spec).contentType(ContentType.JSON).body(expectedDataPojo).when().post("/{first}");
        response.prettyPrint();

        JsonPlaceHolderPojo actualDataPojo = JsonUtils.convertJsonToJava(response.asString(),JsonPlaceHolderPojo.class);

        System.out.println(actualDataPojo);

        //4.Step: Do assertion

        assertEquals(201,response.getStatusCode());
        assertEquals(expectedDataPojo.getUserId(),actualDataPojo.getUserId());
        assertEquals(expectedDataPojo.getTitle(),actualDataPojo.getTitle());
        assertEquals(expectedDataPojo.getCompleted(),actualDataPojo.getCompleted());
    }

}
