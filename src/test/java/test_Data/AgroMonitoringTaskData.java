package test_Data;

import java.util.*;

public class AgroMonitoringTaskData {

//    {
//        "name":"Polygon Sample",
//            "geo_json":{
//        "type":"Feature",
//                "properties":{},
//        "geometry":{
//            "type":"Polygon",
//                    "coordinates":[
//                        [
//                           [-121.1958,37.6683],
//                           [-121.1779,37.6687],
//                           [-121.1773,37.6792],
//                           [-121.1958,37.6792],
//                           [-121.1958,37.6683]
//                        ]
//                     ]
//        }
//    }
//    }

    public float coordinates[][][] = {{{-121.1958f, 37.6683f},
            {-121.1779f, 37.6687f},
            {-121.1773f, 37.6792f},
            {-121.1958f, 37.6792f},
            {-121.1958f, 37.6683f}}};

    public Map<String, Object> geometrySetUp() {

        Map<String, Object> geometryMap = new HashMap<>();
        geometryMap.put("type", "Polygon");
        geometryMap.put("coordinates", coordinates);

        return geometryMap;
    }

    Map<String, Object> propertiesMap = new HashMap<>();

    public Map<String, Object> geo_jsonSetUp() {
        Map<String, Object> geo_jsonMap = new HashMap<>();
        geo_jsonMap.put("type", "Feature");
        geo_jsonMap.put("properties", propertiesMap);
        geo_jsonMap.put("geometry", geometrySetUp());

        return geo_jsonMap;
    }

    public Map<String, Object> requestBodySetUp() {

        Map<String, Object> expectedData = new HashMap<>();
        expectedData.put("name", "Polygon Sample");
        expectedData.put("geo_json", geo_jsonSetUp());

        return expectedData;
    }
}
