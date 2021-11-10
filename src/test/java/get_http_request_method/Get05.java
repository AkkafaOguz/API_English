package get_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertTrue;

public class Get05 extends HerOkuAppBaseUrl {

    // Path param is for making the resource small, query param is for selecting a specific data

    /*
        Given
            https://restful-booker.herokuapp.com/booking
        When
            User send a request to the URL
        Then
            Status code is 200
	  	And
	  		Among the data there should be someone whose firstname is "Mary" and lastname is "Ericsson"
     */

    @Test
    public void test () {

        spec.pathParam("first","booking").
                queryParams("firstname","Mary","lastname","Ericsson");

        Response response = given().spec(spec).when().get("/{first}");

        response.prettyPrint();

        response.then().assertThat().statusCode(200);

        assertTrue("Test for Query is failed!",response.asString().contains("bookingid"));

    }
}
