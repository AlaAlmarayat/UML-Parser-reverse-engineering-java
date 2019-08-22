<%-- 
    Document   : ForgetPassword
    Created on : 01-Aug-2018, 02:09:34
    Author     : AlaaAlmrayat
// Created By :: Ala'a Almarayat 
// Email   :: ALLA3LA2@gmail.com
--%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            response.setHeader("Cashe-Control", "no-cashe,no-store, must-revalidate");
            response.setHeader("Parama", "no-cashe");
            response.setHeader("Expires", "0");
//            response.setHeader("Parama", "no-cashe");
//            response.setHeader("Cashe-Control", "no-store");
//            response.setHeader("Expires", "0");
//            response.setDateHeader("Expires", -1);
//            response.setHeader("Parama", "no-cashe");
            session.invalidate();
        %>
        <h1>Hello World!</h1>
    </body>
</html>
