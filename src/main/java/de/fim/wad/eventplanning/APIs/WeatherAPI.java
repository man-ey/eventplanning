package de.fim.wad.eventplanning.APIs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.json.*;

public class WeatherAPI {
    final static String BASE_URL = "https://www.metaweather.com/api/location/";
    final static String SEARCHBYNAME_URL = "https://www.metaweather.com/api/location/search/?query=";
    final static String SEARCHBYLATLNG_URL = "https://www.metaweather.com/api/location/search/?lattlong=";

    private WeatherForecast[] weatherRequest(int woeid) throws IOException {
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

            JSONObject json = new JSONObject(response.toString());
            JSONArray weatherArray = json.getJSONArray("consolidated_weather");
            WeatherForecast[] weatherObjects = new WeatherForecast[weatherArray.length()];
            for(int i = 0; i<weatherArray.length(); i++){
                JSONObject responseObject = weatherArray.getJSONObject(i);
                WEATHER weather;
                switch(responseObject.getString("weather_state_abbr")){
                    case "c": weather = WEATHER.CLEAR; break;
                    case "lc": weather = WEATHER.LIGHT_CLOUD; break;
                    case "hc": weather = WEATHER.HEAVY_CLOUD; break;
                    case "s": weather = WEATHER.SHOWERS; break;
                    case "lr": weather = WEATHER.LIGHT_RAIN; break;
                    case "hr": weather = WEATHER.HEAVY_RAIN; break;
                    case "t": weather = WEATHER.THUNDERSTORM; break;
                    case "h": weather = WEATHER.HAIL; break;
                    case "sl": weather = WEATHER.SLEET; break;
                    case "sn": weather = WEATHER.SNOW; break;
                    default: weather = WEATHER.CLEAR;
                }
                weatherObjects[i] = new WeatherForecast(
                        responseObject.getString("applicable_date"),
                        (int) responseObject.getDouble("min_temp"),
                        (int) responseObject.getDouble("max_temp"),
                        weather);
            }
        return weatherObjects;
        } else {
            System.out.println("GET NOT WORKED");
            System.out.println(url);
        }
        return new WeatherForecast[0];
    }

    public WeatherForecast[] getWeatherByName(String name) throws IOException{
        String urlString = SEARCHBYNAME_URL + name;
        String result = getJson(urlString);
        return weatherRequest(getWoeidFromJson(result));
    }

    public WeatherForecast[] getWeatherByLatLng(double lat, double lng) throws IOException{
        String urlString = SEARCHBYLATLNG_URL + lat + "," + lng;
        String result = getJson(urlString);
        return weatherRequest(getWoeidFromJson(result));
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

    enum WEATHER{
        CLEAR,
        LIGHT_CLOUD,
        HEAVY_CLOUD,
        SHOWERS,
        LIGHT_RAIN,
        HEAVY_RAIN,
        THUNDERSTORM,
        HAIL,
        SLEET,
        SNOW
    }

    public class WeatherForecast{
        private final String date; //YYYY-MM-DD
        private final int tempMax;
        private final int tempMin;
        private final WEATHER weather;

        public WeatherForecast(String date, int tempMin, int tempMax, WEATHER weather) {
            this.date = date;
            this.tempMax = tempMax;
            this.tempMin = tempMin;
            this.weather = weather;
        }

        public String getDate() {
            return date;
        }

        public int getTempMax() {
            return tempMax;
        }

        public int getTempMin() {
            return tempMin;
        }

        public WEATHER getWeather() {
            return weather;
        }

        public String getWeatherTextDe(){
            String result = "Keine Wetterdaten verfügbar";
            switch (weather){
                case CLEAR:
                    result = "Sonnig";
                    break;
                case LIGHT_CLOUD:
                    result = "Leicht bewöklt";
                    break;
                case HEAVY_CLOUD:
                    result = "Bewölkt";
                    break;
                case SHOWERS:
                    result = "Regen";
                    break;
                case LIGHT_RAIN:
                    result = "Leicher Regen";
                    break;
                case HEAVY_RAIN:
                    result = "Starker Regen";
                    break;
                case THUNDERSTORM:
                    result = "Gewitter";
                    break;
                case HAIL:
                    result = "Hagel";
                    break;
                case SLEET:
                    result = "Schneeregen";
                    break;
                case SNOW:
                    result = "Schnee";
                    break;
            }
            return result;
        }
    }
}
