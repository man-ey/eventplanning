package de.fim.wad.eventplanning.services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAPI {
    final static String BASE_URL = "https://www.metaweather.com/api/location/";
    final static String SEARCHBYNAME_URL = "https://www.metaweather.com/api/location/search/?query=";
    final static String SEARCHBYLATLNG_URL = "https://www.metaweather.com/api/location/search/?lattlong=";

    public void MyGETRequest() throws IOException {
        String url = BASE_URL + "676757";
        URL urlForGetRequest = new URL(url);
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
        //connection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();
            // print result
            System.out.println("JSON String Result " + response.toString());
            //GetAndPost.POSTRequest(response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }
    }

    public String searchByName(String name) throws IOException{
        String result = "error";
        String urlString = SEARCHBYNAME_URL + name;
        URL url = new URL(urlString);
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode==HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null){
                response.append(readLine);
            }
            in.close();
            System.out.println(response.toString());
        }
        return result;
    }

    public String searchByLocation(double lat, double lng) throws IOException{
        String result = "error";
        String urlString = SEARCHBYLATLNG_URL + lat + "," + lng;
        URL url = new URL(urlString);
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode==HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null){
                response.append(readLine);
            }
            in.close();
            System.out.println(response.toString());
        }
        return result;
    }

    private String getWoeidFromJson(String json){
        String woeid = "";
        return woeid;
    }
}
