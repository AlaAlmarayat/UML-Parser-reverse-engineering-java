/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author AlaaAlmrayat
 *  
 * // Created By :: Ala'a Almarayat 
// Email   :: ALLA3LA2@gmail.com
 */
public class DBManager {

    Connection con;

    public DBManager() {

        this.getConnection();
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/umldb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Admin@1234");
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ResultSet Login(String uName, String Pass) throws SQLException {
        ResultSet rs = null;
        try {

            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select UserID,UserName,Password,UserTypeID,UserStatusID,LastLoginTime from users where UserName='" + uName + "' and Password='" + Pass + "'");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rs;
    }

    public void SetLoginTime(int userID) throws SQLException {
        ResultSet rs = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            
            Statement stmt = con.createStatement();
              System.out.println("update users set LastLoginTime ='" + dateFormat.format(date) + "'  where UserID =" + userID);
             stmt.executeUpdate("update users set LastLoginTime ='" + dateFormat.format(date) + "'  where UserID =" + userID);
             
           
             
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ResultSet GetImage(String imageType) throws SQLException {
        ResultSet rs = null;
        int imageID = 0;
        int sequence = 0;
        try {
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select Max(ImageID),Max(Sequnce) from images where  ImageType='" + imageType + "';");

            if (rs1.next()) {
                imageID = rs1.getInt("Max(ImageID)");
                sequence = rs1.getInt("Max(Sequnce)");

            }
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select ImagePath from images where ImageId=" + imageID + " and  Sequnce =" + sequence + ";");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rs;
    }
    
    public ResultSet GetDiagramTypeByID (int imageID ) throws SQLException {
        ResultSet rs = null;
        
        try {
            
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select ImageType from images where ImageId=" + imageID + ";");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rs;
    }
    
    public ResultSet GetClasImageByID(int imageID ) throws SQLException {
        ResultSet rs = null;
        
        try {
            
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select ImagePath from images where ImageId=" + imageID + ";");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet GetSeqImage(String imageType) throws SQLException {
        ResultSet rs = null;
        int imageID = 0;
        int sequence = 0;
        try {
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select Max(ImageID),Max(Sequnce) from images where  ImageType='" + imageType + "';");

            if (rs1.next()) {
                imageID = rs1.getInt("Max(ImageID)");
                sequence = rs1.getInt("Max(Sequnce)");

            }
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select ImageBinary from images where ImageId=" + imageID + " and  Sequnce =" + sequence + ";");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rs;
    }
     public ResultSet GetSeqImageByID(int imageID ) throws SQLException {
        ResultSet rs = null;
        
        try {
           
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select ImagePath,ImageBinary from images where ImageId=" + imageID + ";");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rs;
    }
    
    public ResultSet GetAllImages() throws SQLException {
        ResultSet rs = null;
       
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select ImageID,Sequnce,imagetype,ReleaseVersion from images order by ImageID desc;");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rs;
    }

    private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    public void InsertImage(String imgPath, String imageType,String releaseVersion) throws SQLException {

        int imageID = 0;
        int sequence = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost/umldb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Admin@1234");
            Statement stmt = con2.createStatement();

            ResultSet rs = stmt.executeQuery("select Max(ImageID),Max(Sequnce) from images where  ImageType='" + imageType + "';");

            if (rs.next()) {
                imageID = rs.getInt("Max(ImageID)");
                sequence = rs.getInt("Max(Sequnce)");

                imageID++;
                sequence++;
            }

            PreparedStatement ps = con2.prepareStatement("INSERT INTO images (ImagePath,ImageType,ReleaseVersion,Sequnce) VALUES ( '" + imgPath + "', '" + imageType + "', '" + releaseVersion + "', " + sequence + " );");
            ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void InsertSeqImage(String imgPath, String imageType, String filename, String releaseVersion) throws SQLException {

        int imageID = 0;
        int sequence = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost/umldb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Admin@1234");
            Statement stmt = con2.createStatement();

            ResultSet rs = stmt.executeQuery("select Max(ImageID),Max(Sequnce) from images where  ImageType='" + imageType + "';");

            if (rs.next()) {
                imageID = rs.getInt("Max(ImageID)");
                sequence = rs.getInt("Max(Sequnce)");

                imageID++;
                sequence++;
            }

//             PreparedStatement ps = con2.prepareStatement("INSERT INTO images (ImagePath,ImageType,Sequnce) VALUES ( '"+imgPath+"', '"+imageType+"', "+sequence+" );");
            PreparedStatement ps = con2.prepareStatement("INSERT INTO images (ImagePath,ImageType,Sequnce,ImageBinary,ReleaseVersion) VALUES ( ?, ?, ?, ?, ?  );");

            ps.setString(1, imgPath);
            ps.setString(2, imageType);
            ps.setInt(3, Integer.parseInt("" + sequence));
            ps.setBytes(4, readFile(filename));
            ps.setString(5, releaseVersion);

            ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void closeConnection() throws SQLException {
        try {
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
