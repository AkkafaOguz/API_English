package get_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class Get06 extends HerOkuAppBaseUrl {

     /*
        Given
            https://restful-booker.herokuapp.com/booking/5
        When
            User send a GET request to the URL
        Then
            HTTP Status Code should be 200
        And
            Response content type is “application/json”
        And
            Response body should be like;
            {
                "firstname": "Mary",
                "lastname": "Jackson",
                "totalprice": 111,
                "depositpaid": false,
                "bookingdates": { "checkin": "2017-05-23",
                                  "checkout":"2019-07-02" }
            }
     */

    @Test
    public void test() {

        spec.pathParams("first", "booking", "second", 2);

        Response response = given().spec(spec).when().get("/{first}/{second}");

        response.prettyPrint();


        // 1.Way:

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("firstname", equalTo("Mary"),
                        "lastname", equalTo("Smith"),
                        "totalprice", equalTo(147),
                        "depositpaid", equalTo(true),
                        "bookingdates.checkin", equalTo("2016-09-24"),
                        "bookingdates.checkout", equalTo("2017-10-09"));



        // 2.Way:  Use JsonPath (More secure)

        // JsonPath is a class, and it has many useful methods "to navigate inside the Json Data"

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON);

        // Create JsonPath object from response object (json object has everything what response object has)

        JsonPath json = response.jsonPath();



        assertEquals("Eric", json.getString("firstname"));
        assertEquals("Jones", json.getString("lastname"));
        assertEquals(853, json.getInt("totalprice"));
        assertEquals(false, json.getBoolean("depositpaid"));
        assertEquals("2016-06-15", json.getString("bookingdates.checkin"));
        assertEquals("2020-04-02", json.getString("bookingdates.checkout"));


        // 3.Way: Soft Assertion

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON);

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(json.getString("firstname"), "Eric");
        softAssert.assertEquals(json.getString("lastname"), "Jones");
        softAssert.assertEquals(json.getInt("totalprice"), 853);
        softAssert.assertEquals(json.getBoolean("depositpaid"), false);
        softAssert.assertEquals(json.getString("bookingdates.checkin"), "2016-06-15");
        softAssert.assertEquals(json.getString("bookingdates.checkout"), "2020-04-02");


    }
}
