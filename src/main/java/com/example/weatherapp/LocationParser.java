package com.example.weatherapp;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class LocationParser {

    private final String source = "src/main/resources/com/example/weatherapp/locations.txt";

    public List<Location> getLocations () throws FileNotFoundException {

        File file = new File(source);
        Scanner scanner = new Scanner(file);
        List<Location> locationList = new ArrayList<>();
        while (scanner.hasNextLine()){
            String[] locationData = scanner.nextLine().split(" ");
            Location location = new Location(locationData[0], locationData[1], locationData[2]);
            locationList.add(location);
        }
        return locationList;
    }
}
