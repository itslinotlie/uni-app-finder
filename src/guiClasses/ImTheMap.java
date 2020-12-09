package guiClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ImTheMap {
    static StringTokenizer st;
    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a postal code: ");
        String code = in.next();
        URL url = new URL("https://geocoder.ca/?locate="+code+"&json=1");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        String arr[] = br.readLine().split("[,]");
        for (int i=0;i<arr.length;i++) {
            arr[i] = arr[i].replaceAll("[^a-z0-9:.-]", ""); //ReGeX op
            if(arr[i].startsWith("longt")) System.out.println(arr[i]);
            if(arr[i].startsWith("latt")) System.out.println(arr[i]);
        }
    }
    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine().trim());
        return st.nextToken();
    }
    static String readLine() throws IOException {return br.readLine().trim();}
    static int readInt() throws IOException {return Integer.parseInt(next());}
    static long readLong() throws IOException {return Long.parseLong(next());}
    static double readDouble() throws IOException {return Double.parseDouble(next());}
}
