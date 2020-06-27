package de.fim.wad.eventplanning.services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class WeatherAPI {
    final static String BASE_URL = "https://www.metaweather.com/api/location/";
    final static String SEARCHBYNAME_URL = "https://www.metaweather.com/api/location/search/?query=";
    final static String SEARCHBYLATLNG_URL = "https://www.metaweather.com/api/location/search/?lattlong=";

    public void weatherRequest(int woeid) throws IOException {
        String url = BASE_URL + woeid;
        URL urlForGetRequest = new URL(url);
        String readLine;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            }
            in .close();
            System.out.println("JSON String Result " + response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }
    }

    public int searchByName(String name) throws IOException{
        String urlString = SEARCHBYNAME_URL + name;
        String result = getJson(urlString);
        weatherRequest(getWoeidFromJson(result));
        return getWoeidFromJson(result);
    }

    public int searchByLocation(double lat, double lng) throws IOException{
        String urlString = SEARCHBYLATLNG_URL + lat + "," + lng;
        String result = getJson(urlString);
        weatherRequest(getWoeidFromJson(result));
        return getWoeidFromJson(result);
    }

    private String getJson(String urlString) throws IOException {
        String result;
        URL url = new URL(urlString);
        String readLine;
        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode==HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((readLine = in.readLine()) != null){
                response.append(readLine);
            }
            in.close();
            System.out.println(response.toString());
        }
        result = response.toString();
        return result;
    }

    private int getWoeidFromJson(String json){
        int woeid = -1;
        JSONArray array = new JSONArray(json);
        JSONObject obj = null;
        if(array.length()!=0){
            obj = (JSONObject) array.get(0);
        }
        if(obj != null){
            woeid = obj.getInt("woeid");
        }
        return woeid;
    }
}
