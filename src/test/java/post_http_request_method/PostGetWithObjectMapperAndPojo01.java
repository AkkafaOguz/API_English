package post_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;
import pojos.BookingPostResponseBodyPojo;
import pojos.JsonPlaceHolderPojo;
import utils.JsonUtils;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class PostGetWithObjectMapperAndPojo01 extends HerOkuAppBaseUrl {

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
        And
            I sent GET request to the URl
	 	Then
	 		Status code is 200
        And
            GET response body should be like

	 		 {
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
    public void test () {

        //1.Step: Set the Url
        spec.pathParam("first","booking");

        //2.Step: Set the request body

        BookingDatesPojo datesPojo = new BookingDatesPojo("2021-09-09","2021-09-21");
        BookingPojo expectedDataPojo = new BookingPojo("Selim","Ak",11111,true,datesPojo);
        System.out.println(expectedDataPojo);

        //3.Step: Send the request and get the response

        Response response = given().spec(spec).contentType(ContentType.JSON).body(expectedDataPojo).when().post("/{first}");
        response.prettyPrint();


                //CONVERT POST RESPONSE BODY TO JAVA OBJECT BY USING OBJECT MAPPER TO GET BOOKING ID

                BookingPostResponseBodyPojo postResponseBodyInPojo = JsonUtils.convertJsonToJava(response.asString(), BookingPostResponseBodyPojo.class);
                System.out.println(postResponseBodyInPojo);

                Integer bookingId = postResponseBodyInPojo.getBookingid();


            //BY USING ID WE SEND THE GET REQUEST

            //1.Step: Set the URL for the GET request

            spec.pathParams("first","booking","second",bookingId);

            //2.Step: Send the request and get the response

            Response response1 = given().spec(spec).when().get("/{first}/{second}");
            response1.prettyPrint();

            //WE CONVERT GET RESP0NSE BODY TO JAVA OBJECT BY USING OBJECT MAPPER

            BookingPojo getResponseBodyInPojo = JsonUtils.convertJsonToJava(response1.asString(),BookingPojo.class);


        //4.Step: Do assertions

        assertEquals(200,response1.getStatusCode());
        assertEquals(expectedDataPojo.getFirstname(),getResponseBodyInPojo.getFirstname());
        assertEquals(expectedDataPojo.getLastname(),getResponseBodyInPojo.getLastname());
        assertEquals(expectedDataPojo.getTotalprice(),getResponseBodyInPojo.getTotalprice());
        assertEquals(expectedDataPojo.getDepositpaid(),getResponseBodyInPojo.getDepositpaid());
        assertEquals(expectedDataPojo.getBookingdates().getCheckin(),getResponseBodyInPojo.getBookingdates().getCheckin());
        assertEquals(expectedDataPojo.getBookingdates().getCheckout(),getResponseBodyInPojo.getBookingdates().getCheckout());

    }
}
