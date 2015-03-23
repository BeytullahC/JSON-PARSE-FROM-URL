package com.beto.test.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

/**
 * Created by BeytullahC on 23.03.2015.
 */
public class JsonParserFromUrl {
    public static void main(String[] args) {
        DefaultHttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();
            HttpResponse response = getResponse("http://jsonplaceholder.typicode.com/posts", httpClient);
            String outPut = readData(response);
            System.out.println(outPut);
            Gson gson = new Gson();
            List<Data> fromJson = gson.fromJson(outPut, new TypeToken<List<Data>>(){}.getType());
            System.out.println("DATA SIZE : "+fromJson.size());
            System.out.println("GET FIRST DATA : "+fromJson.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

    public static HttpResponse getResponse(String url, DefaultHttpClient httpClient) throws IOException {
        try {

            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("accept", "application/json");
            HttpResponse response = httpClient.execute(httpGet);
            return response;
        } catch (IOException e) {
            throw e;
        }
    }

    public static String readData(HttpResponse response) throws Exception {
        BufferedReader reader = null;
        try {


            reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            StringBuffer data = new StringBuffer();
            char[] dataLength = new char[1024];
            int read;
            while (((read = reader.read(dataLength)) != -1)) {
                data.append(dataLength, 0, read);
            }
            return data.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
