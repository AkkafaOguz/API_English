package get_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.response.Response;
import org.junit.Test;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class GetWithObjectMapper02 extends HerOkuAppBaseUrl {

        /*
        Given
	            https://restful-booker.herokuapp.com/booking/2
        When
		 		I send GET Request to the URL
		Then
		 		Status code is 200
            {
                "firstname": "Mark",
                "lastname": "Ericsson",
                "totalprice": 726,
                "depositpaid": true,
                "bookingdates": {
                "checkin": "2015-08-07",
                "checkout": "2020-10-25"
                },
                "additionalneeds": "Breakfast"
            }
     */

    @Test
    public void test () {

        //1.Step: Set the Url
        spec.pathParams("first","booking","second",2);

        //2.Step: Set the expected data

        //1.Way:

        String expectedData = "{\n" +
                "\"firstname\": \"Mark\",\n" +
                "\"lastname\": \"Ericsson\",\n" +
                "\"totalprice\": 308,\n" +
                "\"depositpaid\": false,\n" +
                "\"bookingdates\": {\n" +
                                    "\"checkin\": \"2017-03-24\",\n" +
                                    "\"checkout\": \"2019-12-29\"\n" +
                                    "},\n" +
                "\"additionalneeds\": \"Breakfast\"\n" +
                "}";

        //2.Way: Create a setUp method to convert Json data to String dynamically --> Homework

        HashMap <String,Object> expectedDataMap = JsonUtils.convertJsonToJava(expectedData, HashMap.class);

        //3.Step: Send the request get the response

        Response response = given().spec(spec).when().get("/{first}/{second}");

        response.prettyPrint();

        HashMap <String,Object> actualDataMap = JsonUtils.convertJsonToJava(response.asString(), HashMap.class);

        //4.Step: Do the assertion

        assertEquals(200,response.statusCode());
        assertEquals(expectedDataMap.get("firstname"),actualDataMap.get("firstname"));
        assertEquals(expectedDataMap.get("lastname"),actualDataMap.get("lastname"));
        assertEquals(expectedDataMap.get("totalprice"),actualDataMap.get("totalprice"));
        assertEquals(expectedDataMap.get("depositpaid"),actualDataMap.get("depositpaid"));
        assertEquals(((Map)expectedDataMap.get("bookingdates")).get("checkin"),((Map)actualDataMap.get("bookingdates")).get("checkin"));
        assertEquals(((Map)expectedDataMap.get("bookingdates")).get("checkout"),((Map)actualDataMap.get("bookingdates")).get("checkout"));

    }

}
