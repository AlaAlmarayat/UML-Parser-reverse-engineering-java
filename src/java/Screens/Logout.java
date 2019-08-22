/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "Logout", urlPatterns = {"/Logout"})
public class Logout extends HttpServlet {

   protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(false);
		// session.setAttribute("user", null);
		session.removeAttribute("UserName");
		session.getMaxInactiveInterval();
                response.sendRedirect(request.getContextPath() +"/Login.jsp");
//                out.println("thanq you!!, Your session was destroyed successfully!!");
	}
}
