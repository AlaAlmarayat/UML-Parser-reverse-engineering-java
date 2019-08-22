/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import DB.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author AlaaAlmrayat
 * // Created By :: Ala'a Almarayat 
// Email   :: ALLA3LA2@gmail.com
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    DBManager dBManager;
    DirectoryCreation directoryCreation;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *  
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        doGet(request, response);
        PrintWriter out = response.getWriter();
        String uname = request.getParameter("username");
        String upass = request.getParameter("pass");
//        uname="Admin";
        ResultSet rs;
//    response.sendRedirect("./Dashboard.html");
        HttpSession session = request.getSession(true);
        request.setAttribute("errMsg", "");
        String filePath = getServletContext().getRealPath("input");
        directoryCreation = new DirectoryCreation(filePath);

        String filePath2 = getServletContext().getRealPath("output");
        directoryCreation = new DirectoryCreation(filePath2);

        String outPath = directoryCreation.getFilePath();
        try {
            dBManager = new DBManager();
            rs = dBManager.Login(uname, upass);

//      Class.forName("com.mysql.jdbc.Driver");
//      Connection con = DriverManager.getConnection("jdbc:mysql://localhost/umldb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","Admin@1234");
//      Statement stmt = con.createStatement();
//      ResultSet rs = stmt.executeQuery("select UserName,Password,UserTypeID from users where UserName='"+uname+"' and Password='"+upass+"'");
            if (rs.next()) {
                response.sendRedirect("./Dashboard.jsp?name=" + rs.getString("UserName"));
//        response.sendRedirect("./Dashboard.html");            
                session = request.getSession();
                session.setAttribute("UserID", rs.getString("UserID"));
                session.setAttribute("UserName", uname);
                session.setAttribute("Password", rs.getString("Password"));
                session.setAttribute("UserStatusID", rs.getString("UserStatusID"));
                session.setAttribute("UserTypeID", rs.getString("UserTypeID"));
                session.setAttribute("LastLoginTime", rs.getString("LastLoginTime"));
                session.setAttribute("filePath", filePath);
                session.setAttribute("OutPath", outPath);
                session.setMaxInactiveInterval(15 * 60); // 5 min

                dBManager.SetLoginTime(Integer.parseInt(rs.getString("UserID")));

            } else {
                request.setAttribute("errMsg", "Invalid username or password");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
                dispatcher.forward(request, response);
//              out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js' > </script>");
//              out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js' ></script>");
//              out.println("<script>");
//              out.println("$(document).ready(function(){");
//              out.println("swal ('Ops' ,'Wrong User Name or password', 'error'); ");
//              out.println("});");
//              out.println("</script>");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // TODO Auto-generated catch block
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
