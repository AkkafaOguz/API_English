package utils;


import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    //Useful methods for Json

    private static ObjectMapper mapper ;

    //private static ObjectMapper mapper = new ObjectMapper();
    //if you leave your object just like this, your object will be created after class created.
    //However, if you want to create your object before the class you should use static block.
    //In order to do that you should not initialize your object under the class name. Do it in static block.

    //1. Method: It is used to convert json data to java object. (De-serialization)

    static {

        mapper = new ObjectMapper();
    }

    public static <T> T convertJsonToJava (String json, Class <T> cls) {

        //<T> T -> this is called generic methods.
        //It works with every kind of data type.

        T javaResult = null;

        try {
           javaResult= mapper .readValue(json,cls);
        } catch (IOException e) {
            System.out.println("Json could not be converted to Java Object" + e.getMessage());
        }

        return javaResult;
    }


    //2.Method: It is used to convert Java object to Json Data. (Serialization)

    public static String convertJavaToJson (Object obj) {

        String jsonResult = null;

        //For local variables you have to initialize the variable.
        //For instance variable you do not have to initialize.

        try {
            jsonResult = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            System.out.println("Java Object could not be converted to Json" + e.getMessage());
        }

        return jsonResult;
    }

}
