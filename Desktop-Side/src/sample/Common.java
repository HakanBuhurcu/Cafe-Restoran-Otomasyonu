package sample;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by furkankaplan on 16.07.2017.
 */
public class Common {

    protected static String baseURL = "http://localhost/petronetServerSide/";

    public StringBuffer doHttpPost(@NamedArg("url") String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode +" for => "+ url);

        StringBuffer response = null;
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
             response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("Response message of "+url+" => "+response);
        return response;
    }

    public void createLog(@NamedArg("logContent") String logContent) throws IOException {
        URL url = new URL(baseURL+"createLog.php?logContent="+URLEncoder.encode(logContent,"utf8"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) { // fail
            System.out.println("GET request not worked");
        }

    }

    public static Alert createAlert(@NamedArg("message") String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bilgilendirme!");
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert;
    }

    ////

    private static String EMPLOYEE_TC_NO = null;
    private static String EMPLOYEE_NAME = null;
    private static int EMPLOYEE_STATU = 0;

    public static String getEmployeeTcNo() {
        return EMPLOYEE_TC_NO;
    }

    public static void setEmployeeTcNo(String employeeTcNo) {
        EMPLOYEE_TC_NO = employeeTcNo;
    }

    public static String getEmployeeName() {
        return EMPLOYEE_NAME;
    }

    public static void setEmployeeName(String employeeName) {
        EMPLOYEE_NAME = employeeName;
    }

    public static int getEmployeeStatu() {
        return EMPLOYEE_STATU;
    }

    public static void setEmployeeStatu(int employeeStatu) {
        EMPLOYEE_STATU = employeeStatu;
    }

}

