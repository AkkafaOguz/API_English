package get_http_request_method;

import base_urls.GoRestApiBaseUrl;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.GoRestDataPojo;
import pojos.GoRestPojo;
import utils.JsonUtils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


public class Get10 extends GoRestApiBaseUrl {

    /*
    Given
        https://gorest.co.in/public/v1/users/13
    When
        User snd GET Request to the URL
    Then
        Status code should be 200
    And
        Response body should be like

    {
    "meta": null,
        "data": {
                "id": 13,
                "name": "Bahula Shah CPA",
                "email": "shah_bahula_cpa@denesik.name",
                "gender": "male",
                "status": "active"
                }
    }

     */

    @Test
    public void test () {

        //1.Step: Set the URL

        spec.pathParams("first","users","second",13);

        //2.Step: Set the expected data

        GoRestDataPojo dataPojo = new GoRestDataPojo("Bahula Shah CPA","shah_bahula_cpa@denesik.name","male","active");

        GoRestPojo expectedDataPojo = new GoRestPojo(null,dataPojo);


        //3.Step: Send the request and get the response

        Response response = given().spec(spec).when().get("/{first}/{second}");

        GoRestPojo actualDataPojo = JsonUtils.convertJsonToJava(response.asString(),GoRestPojo.class);

        //4.Step: Do assertion

        assertEquals(200,response.getStatusCode());
        assertEquals(expectedDataPojo.getMeta(),actualDataPojo.getMeta());
        assertEquals(expectedDataPojo.getData().getName(),actualDataPojo.getData().getName());
        assertEquals(expectedDataPojo.getData().getEmail(),actualDataPojo.getData().getEmail());
        assertEquals(expectedDataPojo.getData().getGender(),actualDataPojo.getData().getGender());
        assertEquals(expectedDataPojo.getData().getStatus(),actualDataPojo.getData().getStatus());

    }
}
