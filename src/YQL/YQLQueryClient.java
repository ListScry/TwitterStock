package YQL;

import com.google.gson.*;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Stefan Anders Mellem on 2/16/14.
 */
public final class YQLQueryClient{
    static String baseURL = "http://query.yahooapis.com/v1/public/yql?q=";
    static String endURL = "&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
    static HttpClient client;

    public static String queryJSON(String query){
        String fullURL;
        try {
            fullURL = baseURL + URLEncoder.encode(query, "UTF-8") + endURL;
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
        HttpUriRequest mtd = new HttpGet(fullURL);
        HttpResponse res;
        InputStream rstream;
        try {
            res = client.execute(mtd);
            rstream = res.getEntity().getContent();
        }
        catch (ClientProtocolException cpe){
            cpe.printStackTrace();
            return null;
        }
        catch (IOException ioe){
            ioe.printStackTrace();
            return null;
        }

        StringWriter writer = new StringWriter();
        try{
            IOUtils.copy(rstream, writer, "UTF-8");
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        return writer.toString();
    }

    public static void init(){
        client = HttpClientBuilder.create().build();
    }
}
