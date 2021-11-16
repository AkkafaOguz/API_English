package post_http_request_method;

import base_urls.AgroMonitoringBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import test_Data.AgroMonitoringTaskData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Post03 extends AgroMonitoringBaseUrl {

     /*
       Given
		"http://api.agromonitoring.com/agro/1.0/polygons?appid=f4ffe3b2ef1fcb3600ab1d7fbc88c2f0"
            {
               "name":"Polygon Sample",
               "geo_json":{
                  "type":"Feature",
                  "properties":{},
                  "geometry":{
                     "type":"Polygon",
                     "coordinates":[
                        [
                           [-121.1958,37.6683],
                           [-121.1779,37.6687],
                           [-121.1773,37.6792],
                           [-121.1958,37.6792],
                           [-121.1958,37.6683]
                        ]
                     ]
                  }
               }
            }
	When
		 I send POST Request to the Url
	Then
		Assert Status Code (201)
		And Response Body should be like
		{
        "id": "5fd8c383714b523b2ce1f154",
        "geo_json": {
            "geometry": {
                "coordinates": [
                    [
                        [
                            -121.1958,
                            37.6683
                        ],
                        [
                            -121.1779,
                            37.6687
                        ],
                        [
                            -121.1773,
                            37.6792
                        ],
                        [
                            -121.1958,
                            37.6792
                        ],
                        [
                            -121.1958,
                            37.6683
                        ]
                    ]
                ],
                "type": "Polygon"
            },
            "type": "Feature",
            "properties": {
            }
        },
        "name": "Polygon Sample",
        "center": [
            -121.1867,
            37.67385
        ],
        "area": 190.9484,
        "user_id": "5fd8c02a3da20c000759e0f8",
        "created_at": 1608041347
    }
     */

    @Test
    public void test() {
        //1.Step: Set the URL

        ///agro/1.0
        spec.pathParams("first", "agro", "second", 1.0, "third", "polygons").
                queryParam("appid", "f4ffe3b2ef1fcb3600ab1d7fbc88c2f0");

        //2.Step: Set the expected data

        AgroMonitoringTaskData requestBody = new AgroMonitoringTaskData();
        Map<String,Object> requestBodyMap = requestBody.requestBodySetUp();

        //3.Step: Send the request and get the response

        Response response = given().spec(spec).body(requestBodyMap).contentType(ContentType.JSON).when().post("/{first}/{second}/{third}");
        response.prettyPrint();

        //Add more key-values into the request body

        //        "center": [
        //            -121.1867,
        //            37.67385
        //        ],
        //        "area": 190.9484,
        //        "user_id": "5fd8c02a3da20c000759e0f8",
        //        "created_at": 1608041347
        //    }

        requestBodyMap.put("area",190.9484);
        requestBodyMap.put("user_id","5fd8c02a3da20c000759e0f8");
        requestBodyMap.put("created_at","1608041347");



        //Use GSON to convert response to a Map

        Map <String,Object> responseBody = response.as(HashMap.class);
        System.out.println(responseBody);

        //4.Step: Do the assertion

        assertEquals(requestBodyMap.get("area"),responseBody.get("area"));
        assertEquals(requestBodyMap.get("name"),responseBody.get("name"));
        assertEquals(requestBody.geometrySetUp().get("type"),((Map)((Map)responseBody.get("geo_json")).get("geometry")).get("type"));
        assertEquals(String.valueOf(requestBody.coordinates[0][1][0]),((Map)((Map)responseBody.get("geo_json")).get("geometry")).get("coordinates").toString().substring(25,34));



    }
}
