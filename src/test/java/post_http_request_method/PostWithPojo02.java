package post_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;
import pojos.BookingPostResponseBodyPojo;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class PostWithPojo02 extends HerOkuAppBaseUrl {

     /*
        Given
            https://restful-booker.herokuapp.com/booking
            {
                "firstname": "Oguzhan",
                "lastname": "Akkafa",
                "totalprice": 999,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2021-12-16",
                    "checkout": "2021-12-18"
                 }
                 "additionalneeds": "Breakfast with white tea, Dragon juice"
             }
        When
 		    I send POST Request to the URL
 	    Then
 		    Status code is 200
 		And
 		    Response body is like {
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
    public void test(){
        //1.Step: Set the URL
        spec.pathParam("first","booking");
        //2.Step: Set the expected data
        BookingDatesPojo bookingDatesPojo = new BookingDatesPojo("2021-12-16","2021-12-18");
        BookingPojo expectedData = new BookingPojo("Oguzhan","Akkafa",999,true,bookingDatesPojo);
        //3.Step: Send the request and get the response
        Response response = given().spec(spec).contentType(ContentType.JSON).body(expectedData).when().post("/{first}");
        response.prettyPrint();
        //4.Step: Do assertion
        assertEquals(200,response.statusCode());
        BookingPostResponseBodyPojo actualData = response.as(BookingPostResponseBodyPojo.class);
        System.out.println(actualData);
        assertEquals("First name are not matching",expectedData.getFirstname(),actualData.getBooking().getFirstname());
        assertEquals("Last name are not matching",expectedData.getLastname(),actualData.getBooking().getLastname());
        assertEquals("Total price are not matching",expectedData.getTotalprice(),actualData.getBooking().getTotalprice());
        assertEquals("Deposit paid are not matching",expectedData.getDepositpaid(),actualData.getBooking().getDepositpaid());
        assertEquals("Check-in date are not matching",expectedData.getBookingdates().getCheckin(),actualData.getBooking().getBookingdates().getCheckin());
        assertEquals("Check-out date are not matching",expectedData.getBookingdates().getCheckout(),actualData.getBooking().getBookingdates().getCheckout());

    }

    @Test
    public void test02(){
        //1.Step: Set the URL
        spec.pathParam("first","booking");
        //2.Step: Set the expected data
        BookingDatesPojo bookingDatesPojo = new BookingDatesPojo("2021-12-16","2021-12-18");
        BookingPojo expectedData = new BookingPojo("Oguzhan","Akkafa",999,true,bookingDatesPojo);
        //3.Step: Send the request and get the response
        Response response = given().spec(spec).contentType(ContentType.JSON).body(expectedData).when().post("/{first}");
        response.prettyPrint();

        JsonPath json = response.jsonPath();
        Integer bookingId = json.get("bookingid");
        System.out.println(bookingId);

        //4. Do assertion

        //In this way, we send a GET request to check if the data we have sent is saved into database or not
        //In order to do this, we need the id of created data in the database
        //We can get it from the response body of POST request
        //By using JsonPath we have got the id, let's use it and get the data we have sent

        //Set the URL for DELETE request
        spec.pathParams("first","booking","second",bookingId);
        //Send the DELETE request
        Response response2 = given().spec(spec).contentType(ContentType.JSON).when().get("/{first}/{second}");
        response2.prettyPrint();

        //Because the response body is the same with our POJO class, we do not need to create another POJO
        //for the additional information which API put on the response body of POST method (bookingId)
        //So we can directly do de-serialization

        BookingPojo actualData = response2.as(BookingPojo.class);

        assertEquals(expectedData.getFirstname(),actualData.getFirstname());
        assertEquals(expectedData.getLastname(),actualData.getLastname());
        assertEquals(expectedData.getTotalprice(),actualData.getTotalprice());
        assertEquals(expectedData.getDepositpaid(),actualData.getDepositpaid());
        assertEquals(expectedData.getBookingdates().getCheckin(),actualData.getBookingdates().getCheckin());
        assertEquals(expectedData.getBookingdates().getCheckout(),actualData.getBookingdates().getCheckout());

    }
}
