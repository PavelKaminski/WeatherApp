package com.example.weatherapp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javafx.scene.control.TextField;
import org.json.*;
public class HelloController {

    @FXML
    private Label welcomeText;
    @FXML
    private TextField textField;
    @FXML
    protected void onHelloButtonClick() throws IOException {

        LocationParser parser = new LocationParser();
        List<Location> locations = parser.getLocations();

        String locationName = textField.getText();
        String lat = "";
        String lon = "";
        String location = "";
        for (Location loc : locations) {
            if (loc.getName().equals(locationName)) {
                lat = loc.getLat();
                lon = loc.getLon();
                location = loc.getName();
            }
        }
        if (lat != "" && lon != "") {
           URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=d5fd33b7d811007d87b3dc27999cba47");

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String jsonString = reader.readLine();
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray weatherObject = (JSONArray) jsonObject.get("weather");
            JSONObject skyObject = weatherObject.getJSONObject(0);
            String sky = String.valueOf(skyObject.get("main"));

            JSONObject mainObject = (JSONObject) jsonObject.get("main");
            int temp = toCelsius(mainObject.getDouble("temp"));
            int feelsLikeTemp = toCelsius(mainObject.getDouble("feels_like"));
            float pressure = (mainObject.getFloat("pressure") / 1000);
            int humidity = mainObject.getInt("humidity");

            JSONObject windObject = (JSONObject) jsonObject.get("wind");
            float windSpeed = windObject.getFloat("speed");
            String direction = getWindDirection(windObject.getInt("deg"));

            StringBuilder builder = new StringBuilder("The weather in " + location + " is:" + "\n");
            builder.append("real temperature: " + temp + "\n");
            builder.append("feels like: " + feelsLikeTemp + "\n");
            builder.append(sky + "\n");
            builder.append("pressure: " + pressure + "Bar" + "\n");
            builder.append("humidity: " + humidity + "%" + "\n");
            builder.append("wind speed: " + windSpeed + "m/s" + "\n");
            builder.append("wind direction: " + direction);

            welcomeText.setText(builder.toString());
        } else {
            welcomeText.setText("No such location available. Try again");
        }
    }

    private int toCelsius(double temp) {
        return (int) Math.round(temp - 273);
    }

    private String getWindDirection(int degree) {
        if (degree == 0 || degree == 360)
            return "N";
        else if (degree > 0 && degree < 90)
            return "NO";
        else if (degree == 90)
            return "O";
        else if (degree > 90 && degree < 180)
            return "SO";
        else if (degree == 180)
            return "S";
        else if (degree > 180 && degree < 270)
            return "SW";
        else if (degree == 270)
            return "W";
        else if (degree > 270 && degree < 360)
            return "NW";
        else
            return null;
    }
}
