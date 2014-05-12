package Stocks;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Stefan Anders Mellem on 2/16/14.
 */

public final class YQLHistoricalDataParser {
    private static JsonParser parser;
    private static Gson gson;

    public static ArrayList<HistoricalStockData> parse(String json){
        //root object from json
        JsonElement rootNode = parser.parse(json);

        if (rootNode.isJsonObject()){
            JsonObject fullJSONresponse = rootNode.getAsJsonObject();
            JsonObject query = fullJSONresponse.getAsJsonObject("query");
            JsonObject results = query.getAsJsonObject("results");

            Type fillType = new TypeToken<ArrayList<HistoricalStockData>>(){}.getType();
            ArrayList<HistoricalStockData> ret = gson.fromJson(results.get("quote"), fillType);
            return ret;
        }
        else{
            return null;
        }
    }

    public static void init(){
        parser = new JsonParser();
        gson = new Gson();
    }
}
