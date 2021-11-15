package post_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class Post01  extends HerOkuAppBaseUrl {

        /*
        Given
    https://restful-booker.herokuapp.com/booking
    {
        "firstname": "Selim",
        "lastname": "Ak",
        "totalprice": 11111,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2021-09-09",
            "checkout": "2021-09-21"
         }
      }
        When
	 		I send POST Request to the Url
	 	Then
	 		Status code is 200
	 		And response body should be like {
            "bookingid": 11,
            "booking": {
            "firstname": "Selim",
            "lastname": "Ak",
            "totalprice": 11111,
            "depositpaid": true,
            "bookingdates": {
            "checkin": "2020-09-09",
            "checkout": "2020-09-21"
                }
            }
         }
     */

    @Test
    public void test(){

        //1.Step: Set the URL

        spec.pathParam("first","booking");

        //2.Step: Set the expected data

        Map<String,String> expectedBookingdates = new HashMap<>(); // This is for inner Map

        expectedBookingdates.put("checkin","2021-09-09");
        expectedBookingdates.put("checkout","2021-09-21");

        Map<String,Object> expectedData = new HashMap<>(); // This is for outer Map

        expectedData.put("firstname","Selim");
        expectedData.put("lastname","Ak");
        expectedData.put("totalprice",11111);
        expectedData.put("depositpaid",true);
        expectedData.put("bookingdates",expectedBookingdates);
        System.out.println(expectedData);

        //3.Step: Send the post request and get the response

        Response response = given().spec(spec).contentType(ContentType.JSON).body(expectedData).when().post("/{first}");

        response.prettyPrint();

        //4.Step: Do assertion

        Map <String,Object> actualData = response.as(HashMap.class); // Deserialization

        assertEquals(expectedData.get("firstname"),((Map)actualData.get("booking")).get("firstname"));
        assertEquals(expectedData.get("lastname"),((Map)actualData.get("booking")).get("lastname"));
        assertEquals(expectedData.get("totalprice"),((Map)actualData.get("booking")).get("totalprice"));
        assertEquals(expectedData.get("depositpaid"),((Map)actualData.get("booking")).get("depositpaid"));
        assertEquals(expectedBookingdates.get("checkout"),((Map)((Map)actualData.get("booking")).get("bookingdates")).get("checkout"));
        assertEquals(expectedBookingdates.get("checkin"),((Map)((Map)actualData.get("booking")).get("bookingdates")).get("checkin"));

    }
}
