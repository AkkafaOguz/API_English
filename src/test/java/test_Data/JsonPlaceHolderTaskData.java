package test_Data;

import java.util.HashMap;
import java.util.Map;

public class JsonPlaceHolderTaskData {

    public Map<String, Object> expectedDataSetUp(Integer userId, String title, Boolean completed) {
        Map<String, Object> expectedData = new HashMap<>();

        expectedData.put("userId", userId);
        expectedData.put("title", title);
        expectedData.put("completed", completed);
        return expectedData;
    }

    public Map<String, Object> expectedDataSetUpForPatch(Integer userId, String title, Boolean completed) {
        Map<String, Object> expectedData = new HashMap<>();

        if (userId == (null) && title.equals(null)) {
            expectedData.put("completed", completed);
        } else if (userId == (null) && completed == (null)) {
            expectedData.put("title", title);
        } else if (title.equals(null) && completed == (null)) {
            expectedData.put("userId", userId);
        } else if (userId == (null)) {
            expectedData.put("title", title);
            expectedData.put("completed", completed);
        } else if (title.equals(null)) {
            expectedData.put("userId", userId);
            expectedData.put("completed", completed);
        } else if (completed == (null)) {
            expectedData.put("userId", userId);
            expectedData.put("title", title);
        }

        return expectedData;
    }
}
