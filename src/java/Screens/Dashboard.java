/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

//import UMLParcer.ParseEngine;
import DB.DBManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author AlaaAlmrayat
 * // Created By :: Ala'a Almarayat 
// Email   :: ALLA3LA2@gmail.com
 */
@WebServlet(name = "Dashboard", urlPatterns = {"/Dashboard"})
public class Dashboard extends HttpServlet {

    DBManager dBManager;
    DirectoryCreation directoryCreation;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);

        try {
            String diagramType = request.getParameter("diagramType");
            String diagramName = "Capture";//request.getParameter("diagramName");
            String className = request.getParameter("className");
            String functionName = request.getParameter("functionName");
            String releaseVersion = request.getParameter("releaseVersion");
            String path = request.getParameter("classesPath");
            String path2 = request.getParameter("path");
            final String dir = System.getProperty("user.dir");
            System.out.println("current dir = " + dir);
//            Part filePart = request.getPart("path");
//           String path2 = filePart.toString();
            ResultSet rsClass;
            ResultSet rsSeq;

             String filePath = getServletContext().getRealPath("input");
                        directoryCreation = new DirectoryCreation(filePath);
             
             
          path= directoryCreation.getFilePath();

         String filePath2 = getServletContext().getRealPath("output");
      directoryCreation = new DirectoryCreation(filePath2);
          
      String outPath = directoryCreation.getFilePath();
          
          
//        String defultImagePath ="dist/img/default-50x50.gif";
//        path ="C:\\Users\\Alaa Almrayat\\Desktop\\test2";
            HttpSession session = request.getSession(true);
            
            
            if(session ==null){
                 response.sendRedirect("./Login.jsp");
                
            }
            String imgpath = "https://static.parastorage.com/services/wixapps/2.486.0/javascript/wixapps/apps/blog/images/no-image-icon.png";
            switch (diagramType) {
                case "Class":
                    ParseEngine pe = new ParseEngine(path,outPath, diagramName,releaseVersion);

                    pe.start();

                    break;

                case "Sequence":
                    ParseSeqEngine pse = new ParseSeqEngine(path,outPath, className, functionName, diagramName,releaseVersion);

                    pse.start();

                    break;
                default:
                    System.out.println("Invalid diagram");
                    break;
            }

            dBManager = new DBManager();
            rsClass = dBManager.GetImage(diagramType);
            rsSeq = dBManager.GetSeqImage("Seq");

            if (diagramType.equals("Class")) {
                if (rsClass.next()) {

                    response.sendRedirect("./Dashboard.jsp");
//        response.sendRedirect("./Dashboard.html");            
                    session = request.getSession();

                    imgpath = rsClass.getString("ImagePath");
                    session.setAttribute("ImagePath", imgpath);
                    session.setMaxInactiveInterval(5 * 60); // 5 min

                } else {
                    request.setAttribute("ImagePath", imgpath);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
                    dispatcher.forward(request, response);
//        out.println("Wrong id and password");
                }
            }

            if (diagramType.equals("Sequence")) {
                if (rsSeq.next()) {

                    response.sendRedirect("./Dashboard.jsp");
//        response.sendRedirect("./Dashboard.html");            
                    session = request.getSession();
//      
//           imgpath = rsSeq.getString("ImagePath");

                    InputStream input = rsSeq.getBinaryStream("ImageBinary");

                    Blob blob = rsSeq.getBlob("ImageBinary");

                    InputStream inputStream = blob.getBinaryStream();

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    byte[] imageData = rsSeq.getBytes("ImageBinary");
                    String s = imageData.toString();
                    session.setAttribute("ImagePath", base64Image);
                      session.setAttribute("OutPath", outPath);
                    session.setMaxInactiveInterval(5 * 60); // 5 min

                } else {
                    request.setAttribute("ImagePath", imgpath);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
                    dispatcher.forward(request, response);
//        out.println("Wrong id and password");
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        // TODO Auto-generated catch block

    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= " + contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
