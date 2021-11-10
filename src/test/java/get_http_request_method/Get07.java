package get_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class Get07 extends JsonPlaceHolderBaseUrl {

        /*
        Given
	   	    https://jsonplaceholder.typicode.com/todos
		When
			 I send GET Request to the URL
		Then
			 1)Status code is 200
			 2)Print all ids greater than 190 on the console
			   Assert that there are 10 ids greater than 190
			 3)Print all userIds less than 5 on the console
			   Assert that maximum userId less than 5 is 4
			 4)Print all titles whose ids are less than 5
			   Assert that "delectus aut autem" is one of the titles whose id is less than 5
     */

    @Test
    public void test() {

        spec.pathParam("first", "todos");

        Response response = given().spec(spec).when().get("/{first}");

        response.then().assertThat().statusCode(200);

        JsonPath json = response.jsonPath();

        //2)Print all ids greater than 190 on the console
        //Assert that there are 10 ids greater than 190

        List<Integer> idList = json.getList("findAll{it.id>190}.id"); // Groovy Language (works under Java)

        System.out.println("All ids greater than 190: " + idList);
        assertEquals("Id list does not have expected size", 10, idList.size());

        //3)Print all userIds less than 5 on the console
        //Assert that maximum userId less than 5 is 4

        List<Integer> userIdList = json.getList("findAll{it.userId<5}.userId");

        System.out.println("All user ids less than 5: " + userIdList);

        Collections.sort(userIdList);

        assertTrue(userIdList.get(userIdList.size() - 1) < 5);
        // OR
        assertEquals((Integer) 5, userIdList.get(userIdList.size() - 1));

        //4)Print all titles whose ids are less than 5
        //Assert that "delectus aut autem" is one of the titles whose id is less than 5

        List<String> titlesList = json.getList("findAll{it.id<5}.title");

        System.out.println(titlesList);

        assertTrue(titlesList.contains("delectus aut autem"));
        // OR
        assertTrue(titlesList.stream().anyMatch(t-> t.equals("delectus aut autem")));

    }
}
