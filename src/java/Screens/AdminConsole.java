package Screens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import UMLParcer.ParseEngine;
import Screens.*;
import DB.DBManager;
import DB.UMLDiagrams;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
@WebServlet(name = "AdminConsole", urlPatterns = {"/AdminConsole"})
public class AdminConsole extends HttpServlet {

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

        request.setAttribute("display", "none");
        getData(request, response);
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
        ResultSet rsClass1;
        ResultSet rsClass2;
        ResultSet rsImageType1;
        ResultSet rsImageType2;
        String imgpath = "https://static.parastorage.com/services/wixapps/2.486.0/javascript/wixapps/apps/blog/images/no-image-icon.png";
        String diagramType1 = "";
        String diagramType2 = "";
        String resultClassPath1 = "";
        String resultClassPath2 = "";
        String classImgpath1 = "";
        String classImgpath2 = "";
        String rowData = request.getParameter("compareData11");
        rowData = rowData.replace(" ", "");
        String[] allRowData;
        allRowData = rowData.split("#");
        int rowData1 = Integer.parseInt(allRowData[0]);
        int rowData2 = Integer.parseInt(allRowData[1]);
//        doGet(request, response);
        HttpSession session = request.getSession(true);

        request.setAttribute("display", "none");

        dBManager = new DBManager();
        try {
            rsImageType1 = dBManager.GetDiagramTypeByID(rowData1);
            if (rsImageType1.next()) {
                diagramType1 = rsImageType1.getString("ImageType");
            }
            rsImageType2 = dBManager.GetDiagramTypeByID(rowData2);
            if (rsImageType2.next()) {
                diagramType2 = rsImageType2.getString("ImageType");
            }
            session = request.getSession();

            if (diagramType1.equals(diagramType2) && diagramType1.equals("Class")) {

                rsClass1 = dBManager.GetClasImageByID(rowData1);
                rsClass2 = dBManager.GetClasImageByID(rowData2);

                System.out.println(rowData1 + " " + rowData2);
                if (rsClass1.next()) {
                    resultClassPath1 = rsClass1.getString("ImagePath");
                }

                if (rsClass2.next()) {
                    resultClassPath2 = rsClass2.getString("ImagePath");
                }

//            if (rsClass1.next() && rsClass2.next()) {  
                classImgpath1 = compareDiagrams(resultClassPath1, resultClassPath2);
                classImgpath2 = compareDiagrams(resultClassPath2, resultClassPath1);
                request.setAttribute("ImagePath1", classImgpath1);
                request.setAttribute("ImagePath2", classImgpath2);

            } else if (diagramType1.equals(diagramType2) && diagramType1.equals("Seq")) {

                rsClass1 = dBManager.GetSeqImageByID(rowData1);
                rsClass2 = dBManager.GetSeqImageByID(rowData2);

                System.out.println(rowData1 + " " + rowData2);
                if (rsClass1.next()) {
                    resultClassPath1 = getBinaryImage(rsClass1);
                }

                if (rsClass2.next()) {
                    resultClassPath2 = getBinaryImage(rsClass2);
                }

//            if (rsClass1.next() && rsClass2.next()) {  
//                classImgpath1 = compareSeqDiagrams(resultClassPath1, resultClassPath2);
//                classImgpath2 = compareSeqDiagrams(resultClassPath2, resultClassPath1);
                request.setAttribute("ImagePathSeq1", resultClassPath1);
                request.setAttribute("ImagePathSeq2", resultClassPath2);

            } else {
                request.setAttribute("ImagePathSeq1", "");
                request.setAttribute("ImagePathSeq2", "");
                request.setAttribute("display", "block");
                request.setAttribute("ErrDiagrams", "Please select same diagram type!");
            }

            session.setMaxInactiveInterval(5 * 60); // 5 min
            getData(request, response);
//             RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminConsole.jsp");
//            dispatcher.forward(request, response);
//            doGet(request, response);
//            }
//              if (!rsClass1.next() || !rsClass2.next())
//            {
//                request.setAttribute("ImagePath", imgpath);
//                RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
//                dispatcher.forward(request, response);
////        out.println("Wrong id and password");
//            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

//        processRequest(request, response);
        // TODO Auto-generated catch block
        // TODO Auto-generated catch block
    }

    private String getBinaryImage(ResultSet rsSeq) throws SQLException, IOException {

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

        return base64Image;
    }

    private void getData(HttpServletRequest request, HttpServletResponse response) {
        try {
//            String diagramType = request.getParameter("diagramType");
//            String diagramName = "Capture";//request.getParameter("diagramName");
//            String className = request.getParameter("className");
//            String functionName = request.getParameter("functionName");
//            String path = request.getParameter("classesPath");
//            String path2 = request.getParameter("path");
//            final String dir = System.getProperty("user.dir");

            ResultSet rsAllImages;
            List<UMLDiagrams> listUMLDiagrams = new ArrayList<UMLDiagrams>();
            UMLDiagrams uMLDiagrams;

            HttpSession session = request.getSession(true);

            if (session == null) {
                response.sendRedirect("./Login.jsp");

            }
            String imgpath = "https://static.parastorage.com/services/wixapps/2.486.0/javascript/wixapps/apps/blog/images/no-image-icon.png";

            dBManager = new DBManager();
            rsAllImages = dBManager.GetAllImages();

            while (rsAllImages.next()) {
                uMLDiagrams = new UMLDiagrams();

                uMLDiagrams.setId(rsAllImages.getInt("ImageID"));
                uMLDiagrams.setSequence(rsAllImages.getInt("Sequnce"));
                uMLDiagrams.setReleaseVersion(rsAllImages.getString("ReleaseVersion"));

                if (rsAllImages.getString("imagetype").equals("Seq")) {
                    uMLDiagrams.setUmlType("UML Sequence Diagram");
                } else {
                    uMLDiagrams.setUmlType("UML Class Diagram");
                }

                listUMLDiagrams.add(uMLDiagrams);
//        response.sendRedirect("./Dashboard.html");            

            }

            session = request.getSession();
            session.setMaxInactiveInterval(5 * 60); // 5 min

            request.setAttribute("ListOfAllImages", listUMLDiagrams);
//            request.setAttribute("ImagePath1", "");
//            request.setAttribute("ImagePath2", "");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminConsole.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(AdminConsole.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private String compareDiagrams(String oldDiagram, String newDiagram) {

        diff_match_patch diff = new diff_match_patch();
        Object arr[] = diff.diff_main(oldDiagram, newDiagram).toArray();
        String test[] = new String[arr.length];
        String status;
        String result = "";
        String color;
        boolean isChanged;
        String insert = "";
        String delete = "";
        String equal = "";

        for (int i = 0; i < arr.length; i++) {
            status = arr[i].toString();
            color = "";
            isChanged = false;
            if (status.contains("INSERT")) {
                color = "{bg:blue}]";
                isChanged = true;
                status = status.replace("Diff(INSERT,\"", "");
                status = status.replace("\")", "");

                insert = result.concat("*I*" + status + "*I*");
                result = result.concat("*I*" + status + "*I*");

            } else if (status.contains("DELETE")) {
                color = "{bg:blue}]";
                isChanged = true;
                status = status.replace("Diff(DELETE,\"", "");
                status = status.replace("\")", "");

                delete = result.concat("*D*" + status + "*D*");
//                result = result.concat("*D*"+status+"*D*");

            } //            else if (status.contains("INSERT")) {
            //                color = "{bg:blue}]";
            //                status = status.replace("Diff(INSERT,\"", "");
            //            }
            else if (status.contains("EQUAL")) {
//                color = "{bg:blue}]";
                status = status.replace("Diff(EQUAL,\"", "");
                status = status.replace("\")", "");
                equal = equal.concat(status);
                result = result.concat(status);
            }

        }

        System.out.println("insert " + insert);
        System.out.println("delete " + delete);

        if (!insert.isEmpty() && result.charAt(result.indexOf("]", insert.indexOf("*I*")) - 1) != '[') {
            result = replaceCharAt(result, result.indexOf("]", insert.indexOf("*I*")), "{bg:blue}]");
        }

        if (!delete.isEmpty() && result.charAt(result.indexOf("]", delete.indexOf("*D*")) - 1) != '[') {
            result = replaceCharAt(result, result.indexOf("]", delete.indexOf("*D*")), "{bg:red}]");
        }

        result = result.replace("*I*", "");
        result = result.replace("*D*", "");

        return result;
    }

    public String compareSeqDiagrams(String oldDiagram, String newDiagram) {

        oldDiagram = oldDiagram.replace("\n", "*L*");
        newDiagram = newDiagram.replace("\n", "*L*");
        diff_match_patch diff = new diff_match_patch();
        Object arr[] = diff.diff_main(oldDiagram, newDiagram).toArray();
        String test[] = new String[arr.length];
        String status;
        String result = "";
        String color;
        boolean isChanged;
        String insert = "";
        String delete = "";
        String equal = "";

        for (int i = 0; i < arr.length; i++) {
            status = arr[i].toString();
            color = "";
            isChanged = false;
            if (status.contains("INSERT")) {
                color = "{bg:blue}]";
                isChanged = true;
                status = status.replace("Diff(INSERT,\"", "");
                status = status.replace("\")", "");

                insert = result.concat("*I*" + status + "*I*");
                result = result.concat("*I*" + status + "*I*");

            } else if (status.contains("DELETE")) {
                color = "{bg:blue}]";
                isChanged = true;
                status = status.replace("Diff(DELETE,\"", "");
                status = status.replace("\")", "");

                delete = result.concat("*D*" + status + "*D*");
//                result = result.concat("*D*"+status+"*D*");

            } //            else if (status.contains("INSERT")) {
            //                color = "{bg:blue}]";
            //                status = status.replace("Diff(INSERT,\"", "");
            //            }
            else if (status.contains("EQUAL")) {
//                color = "{bg:blue}]";
                status = status.replace("Diff(EQUAL,\"", "");
                status = status.replace("\")", "");
                equal = equal.concat(status);
                result = result.concat(status);
            }

        }

        System.out.println("insert " + insert);
        System.out.println("delete " + delete);

        if (!insert.isEmpty() && result.charAt(result.indexOf("->", insert.indexOf("*I*")) - 1) != '[') {
            result = replaceCharAt(result, result.indexOf("->", insert.indexOf("*I*")), "[#blue]->");
        }

        if (!delete.isEmpty() && result.charAt(result.indexOf("->", delete.indexOf("*D*")) - 1) != '[') {
            result = replaceCharAt(result, result.indexOf("->", delete.indexOf("*D*")), "[#red]->");
        }

        result = result.replace("*I*", "");
        result = result.replace("*D*", "");
        result = result.replace("*L*", "\n");

        return result;
    }

    private String replaceCharAt(String s, int pos, String c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
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
