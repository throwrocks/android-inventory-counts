package rocks.athrow.android_inventory_counts.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ParseJSON
 */


class ParseJSON {
    private static final String DATA = "data";

    static JSONArray getJSONArray(String JSON) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(JSON);
            if (jsonObject.has(DATA)) {
                jsonArray = jsonObject.getJSONArray(DATA);
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}