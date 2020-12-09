package guiClasses;

import java.net.*;
import java.io.*;
import java.util.*;

public class ImTheMap {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a postal code: ");
        String code = in.next();
        double x = 0, y = 0;

        //sending http response
        URL url = new URL("https://geocoder.ca/?locate="+code+"&json=1");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

        //reading http response
        BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        String arr[] = br.readLine().split("[,]"); //splitting response by commas using ReGeX
        for (int i=0;i<arr.length;i++) {
            //parsing JSON into something I can read
            arr[i] = arr[i].replaceAll("[^a-z0-9:.-]", ""); //ReGeX op
            //tldr; ^ replaces everything that isn't a to z, 0 to 9, :, ., - with "" aka nothing

            if(arr[i].startsWith("longt")) {
                System.out.println(arr[i]);
                x = Double.parseDouble(arr[i].split(":")[1]);
            }
            if(arr[i].startsWith("latt")) {
                System.out.println(arr[i]);
                y = Double.parseDouble(arr[i].split(":")[1]);
            }
        }
        //lat = N/S
        //long = E/W
        System.out.println("Boss to Carleton: "+calculateDistanceInKilometer(y, x, 45.3876, -75.6960));
    }
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    static double calculateDistanceInKilometer(double userLat, double userLng,
                                            double venueLat, double venueLng) {
        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }
}
