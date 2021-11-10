package get_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Get09 extends HerOkuAppBaseUrl {

    /*
        Given
            https://restful-booker.herokuapp.com/booking/1
        When
	 		I send GET Request to the url
	 	Then
	 		Response body should be like that;
	 		{
			    "firstname": "Eric",
			    "lastname": "Smith",
			    "totalprice": 555,
			    "depositpaid": false,
			    "bookingdates": {
			        "checkin": "2016-09-09",
			        "checkout": "2017-09-21"
			     }
			}
     */

    @Test
    public void test () {

        //1.Step:
        spec.pathParams("first", "booking","second", 2);

        //2.Step:

        //Start with inner Json

        Map<String,String> expectedBookingdates = new HashMap<>(); // This is for inner Map

        expectedBookingdates.put("checkin","2019-11-01");
        expectedBookingdates.put("checkout","2020-04-08");

        Map<String,Object> expectedData = new HashMap<>(); // This is for outer Map

        expectedData.put("firstname","Jim");
        expectedData.put("lastname","Smith");
        expectedData.put("totalprice",577);
        expectedData.put("depositpaid",true);
        expectedData.put("bookingdates",expectedBookingdates);
        System.out.println(expectedData);


        //3.Step:
        Response response = given().spec(spec).when().get("/{first}/{second}");
        Map <String,Object> actualData = response.as(HashMap.class);
        System.out.println(actualData);


        //4.Step:
        assertEquals(expectedData.get("firstname"),actualData.get("firstname"));
        assertEquals(expectedData.get("lastname"),actualData.get("lastname"));
        assertEquals(expectedData.get("totalprice"),actualData.get("totalprice"));
        assertEquals(expectedData.get("depositpaid"),actualData.get("depositpaid"));
        assertEquals(expectedBookingdates.get("checkin"),((Map)actualData.get("bookingdates")).get("checkin"));
        assertEquals(expectedBookingdates.get("checkout"),((Map)actualData.get("bookingdates")).get("checkout"));

    }
}
