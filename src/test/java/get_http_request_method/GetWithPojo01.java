package get_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class GetWithPojo01 extends HerOkuAppBaseUrl {

    /*
        Given
            https://restful-booker.herokuapp.com/booking/2
        When
 		    I send GET Request to the URL
 	    Then
 		    Status code is 200
 		And
 		    Response body is like
        {
            "firstname": "Mary",
            "lastname": "Smith",
            "totalprice": 647,
            "depositpaid": false,
            "bookingdates": {
                "checkin": "2016-02-05",
                "checkout": "2021-01-16"
             }
             "additionalneeds": "Breakfast"
          }
     */

    @Test
    public void test() {

        //In API testing the biggest challenge isi data type

        //1.Step: Set the URL
        spec.pathParams("first","booking","second",2);

        //2.Step: Set the expected data
        BookingDatesPojo bookingDatesPojo = new BookingDatesPojo("2017-09-04","2020-05-03");
        BookingPojo expectedData = new BookingPojo("Jim","Wilson",313,false,bookingDatesPojo);

        //3.Step: Send the request and get the response
        Response response = given().spec(spec).when().get("/{first}/{second}");
        response.prettyPrint();

        //4.Step: Do assertion

        BookingPojo actualData = response.as(BookingPojo.class);

        System.out.println(actualData);

        assertEquals(200,response.statusCode());
        assertEquals(expectedData.getFirstname(),actualData.getFirstname());
        assertEquals(expectedData.getLastname(),actualData.getLastname());
        assertEquals(expectedData.getTotalprice(),actualData.getTotalprice());
        assertEquals(expectedData.getDepositpaid(),actualData.getDepositpaid());
        assertEquals(expectedData.getBookingdates().getCheckin(),actualData.getBookingdates().getCheckin());
        assertEquals(expectedData.getBookingdates().getCheckout(),actualData.getBookingdates().getCheckout());
    }
}
