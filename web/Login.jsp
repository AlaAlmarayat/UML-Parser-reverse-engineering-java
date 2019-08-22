<!DOCTYPE html> 
<%-- 
    Document   : ForgetPassword
    Created on : 01-Aug-2018, 02:09:34
    Author     : AlaaAlmrayat
// Created By :: Ala'a Almarayat 
// Email   :: ALLA3LA2@gmail.com
--%> 
<html lang="en">
    <head>
        <title>UML Parser | Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--===============================================================================================-->	
        <link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">
        <!--===============================================================================================--> 
        <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="vendor/daterangepicker/daterangepicker.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="css/util.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <!--===============================================================================================-->
    </head>
    <body>
        <%
            response.setHeader("Cashe-Control", "no-cashe,no-store, must-revalidate");
            response.setHeader("Parama", "no-cashe");
            response.setHeader("Expires", "0");
//            response.setHeader("Cashe-Control", "no-store");

//            response.setDateHeader("Expires", -1);
//            response.setHeader("Parama", "no-cashe");
//            session.invalidate();
%>
        <div class="limiter">
            <div class="container-login100" style="background-image: url('images/bg-01.jpg');">
                <div class="wrap-login100">
                    <form class="login100-form validate-form" action="${pageContext.request.contextPath}/Login" method="post">
                        <span class="login100-form-logo">
                            <i class="zmdi zmdi-landscape"></i>
                        </span>


                        <span class="login100-form-title p-b-34 p-t-27">
                            Login
                        </span>

                        <div class="wrap-input100 validate-input" data-validate = "Enter username">
                            <input class="input100" type="text" name="username" placeholder="Username" required="">
                            <span class="focus-input100" data-placeholder="&#xf207;"></span>
                        </div>

                        <div class="wrap-input100 validate-input" data-validate="Enter password">
                            <input class="input100" type="password" name="pass" placeholder="Password" required>
                            <span class="focus-input100" data-placeholder="&#xf191;"></span>
                        </div>

                        <!--					<div class="contact100-form-checkbox">
                                                                        <input class="input-checkbox100" id="ckb1" type="checkbox" name="remember-me">
                                                                        <label class="label-checkbox100" for="ckb1">
                                                                                Remember me
                                                                        </label>
                                                                        <div class="text-warning p-t-90">
                                                                            <span class="form-control-label" >
                                                                                ada
                                                                               
                                                                          </span>
                                                                        </div>
                                                                </div>-->




                        <div class="container-login100-form-btn">

                            <button class="login100-form-btn" value="submit" type="submit">
                                Login
                            </button>


                        </div>


                        <span class="form-text alert-danger" >
                            ${errMsg}

                        </span>



<!--                        <div class="text-center p-t-90">
                            <a class="txt1" href="ForgetPassword.jsp">
                                Forgot Password?
                            </a>
                        </div>-->
                    </form>
                </div>

            </div>
        </div>
    </div>


    <div id="dropDownSelect1"></div>

    <!--===============================================================================================-->
    <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
    <!--===============================================================================================-->
    <script src="vendor/animsition/js/animsition.min.js"></script>
    <!--===============================================================================================-->
    <script src="vendor/bootstrap/js/popper.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <!--===============================================================================================-->
    <script src="vendor/select2/select2.min.js"></script>
    <!--===============================================================================================-->
    <script src="vendor/daterangepicker/moment.min.js"></script>
    <script src="vendor/daterangepicker/daterangepicker.js"></script>
    <!--===============================================================================================-->
    <script src="vendor/countdowntime/countdowntime.js"></script>
    <!--===============================================================================================-->
    <script src="js/main.js"></script>

</body>
</html>