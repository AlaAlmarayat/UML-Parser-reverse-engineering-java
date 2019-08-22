package Screens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alaa
 * // Created By :: Ala'a Almarayat 
// Email   :: ALLA3LA2@gmail.com
 */
//import UMLParcer.*;
import DB.DBManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GenerateDiagram {

    public static Boolean generatePNG(String grammar, String outPath, String releaseVersion) {
        String imgPath;
        DBManager dBManager;

        String finalGrammer = grammar.replaceAll(" ", "%20");

        try {
            String webLink = "http://yuml.me/diagram/plain/class/" + finalGrammer
                    + ".png";
            
            URL url = new URL(webLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            System.out.println(webLink);

            String redirect = conn.getHeaderField("Location");

            imgPath = redirect;
            if (redirect != null) {
                conn = (HttpURLConnection) new URL(redirect).openConnection();
            }
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException(
                        "Failed : HTTP error code : " + conn.getResponseCode());
            }
            OutputStream outputStream = new FileOutputStream(new File(outPath));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = conn.getInputStream().read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
            conn.disconnect();

            dBManager = new DBManager();
            dBManager.InsertImage(imgPath, "Class",releaseVersion);
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/umldb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","Admin@1234");
////            Statement stmt = con.createStatement();
//            System.out.print(imgPath.length());
//            PreparedStatement rs = con.prepareStatement("INSERT INTO images (`ImageID`,`ImagePath`) VALUES ("+1655+", "+imgPath+");");
//            rs.executeUpdate();
        } catch (MalformedURLException e) { 
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } // TODO Auto-generated catch block
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
