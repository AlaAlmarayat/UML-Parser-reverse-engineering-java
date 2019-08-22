<%-- 
    Document   : Dashboard
    Created on : 01-Aug-2018, 01:07:41
    Author     : AlaaAlmrayat
// Created By :: Ala'a Almarayat 
// Email   :: ALLA3LA2@gmail.com
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>UML Parser | Dashboard</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.7 -->
        <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="bower_components/Ionicons/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">
        <!-- Morris chart -->
        <link rel="stylesheet" href="bower_components/morris.js/morris.css">
        <!-- jvectormap -->
        <link rel="stylesheet" href="bower_components/jvectormap/jquery-jvectormap.css">
        <!-- Date Picker -->
        <link rel="stylesheet" href="bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
        <!-- Daterange picker -->
        <link rel="stylesheet" href="bower_components/bootstrap-daterangepicker/daterangepicker.css">
        <!-- bootstrap wysihtml5 - text editor -->
        <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">


        <!-- iCheck for checkboxes and radio inputs -->
        <link rel="stylesheet" href="./plugins/iCheck/all.css">

        <!-- Select2 -->
        <link rel="stylesheet" href="./bower_components/select2/dist/css/select2.min.css">

        <!-- DataTables -->
        <link rel="stylesheet" href="bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">




        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

        <!-- Google Font -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
    </head>
    <body class="hold-transition skin-blue sidebar-mini">

        <%
            response.setHeader("Cashe-Control", "no-cashe,no-store, must-revalidate");
            response.setHeader("Parama", "no-cashe");
            response.setHeader("Expires", "0");
            if (session != null) {
                if (session.getAttribute("UserName") != null) {
                    String name = (String) session.getAttribute("UserName");
                    //				out.print("Hello, " + name + "  Welcome to ur Profile");
                } else {
                    response.sendRedirect("Login.jsp");
                }
            }
        %> 
        <div class="wrapper">

            <header class="main-header">
                <!-- Logo -->
                <a href="#" class="logo">
                    <!-- mini logo for sidebar mini 50x50 pixels -->
                    <span class="logo-mini"><b>UML</b>Parser</span>
                    <!-- logo for regular state and mobile devices -->
                    <span class="logo-lg"><b>UML</b>Parser</span>
                </a>
                <!-- Header Navbar: style can be found in header.less -->
                <nav class="navbar navbar-static-top">
                    <!-- Sidebar toggle button-->
                    <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                        <span class="sr-only">Toggle navigation</span>
                    </a>

                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">
                            <!-- Messages: style can be found in dropdown.less-->

                            <!-- User Account: style can be found in dropdown.less -->
                            <li class="dropdown user user-menu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <img src="dist/img/avatar5.png  " class="user-image" alt="User Image">
                                    <span class="hidden-xs"><%
                                        if (session != null) {
                                            if (session.getAttribute("UserName") != null) {
                                                out.print(session.getAttribute("UserName").toString());
                                                //				out.print("Hello, " + name + "  Welcome to ur Profile");
                                            } else {
                                                out.print("");
                                            }
                                        }
//                                        if (session !=null){}
//                                        out.print(session.getAttribute("UserName").toString());

                                        %></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <!-- User image -->
                                    <li class="user-header">
                                        <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                                        <p>
                                            <%  if (session != null) {
                                                    if (session.getAttribute("UserName") != null) {
                                                        out.print(session.getAttribute("UserName").toString());
                                                        //				out.print("Hello, " + name + "  Welcome to ur Profile");
                                                    } else {
                                                        out.print("");
                                                    }
                                                }%>
                                            <small> </small>
                                        </p>
                                    </li>
                                    <!-- Menu Body -->
                                    <!--                                    <li class="user-body">
                                                                            <div class="row">
                                                                                <div class="col-xs-4 text-center">
                                                                                    <a href="#">Followers</a>
                                                                                </div>
                                                                                <div class="col-xs-4 text-center">
                                                                                    <a href="#">Sales</a>
                                                                                </div>
                                                                                <div class="col-xs-4 text-center">
                                                                                    <a href="#">Friends</a>
                                                                                </div>
                                                                            </div>
                                                                             /.row 
                                                                        </li>-->
                                    <!-- Menu Footer-->
                                    <li class="user-footer">
                                        <div class="pull-left">
                                            <a href="#" class="btn btn-default btn-flat">Profile</a>
                                        </div>
                                        <div class="pull-right">
                                            <a href="#" class="btn btn-default btn-flat">Sign out</a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                            <!-- Control Sidebar Toggle Button -->
                            <li>
                                <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>
            <!-- Left side column. contains the logo and sidebar -->
            <aside class="main-sidebar">
                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">
                    <!-- Sidebar user panel -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="dist/img/avatar5.png" class="img-circle" alt="User Image">
                        </div>
                        <div class="pull-left info">
                            <p><% if (session != null) {
                                    if (session.getAttribute("UserName") != null) {
                                        out.print(session.getAttribute("UserName").toString());
                                        //				out.print("Hello, " + name + "  Welcome to ur Profile");
                                    } else {
                                        out.print("");
                                    }
                                }%></p>
                            <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>
                    <!-- search form -->
                    <!--      <form action="#" method="get" class="sidebar-form">
                            <div class="input-group">
                              <input type="text" name="q" class="form-control" placeholder="Search...">
                              <span class="input-group-btn">
                                    <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                                    </button>
                                  </span>
                            </div>
                          </form>-->
                    <!-- /.search form -->
                    <!-- sidebar menu: : style can be found in sidebar.less -->
                    <ul class="sidebar-menu" data-widget="tree">
                        <li class="header">MAIN NAVIGATION</li>
                        <li class="active treeview">
                            <a href="#">
                                <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li class="active"><a href="Dashboard.jsp"><i class="fa fa-circle-o"></i> Parser Menu </a></li>
                                <li class="active"><a href="${pageContext.request.contextPath}/AdminConsole"><i class="fa fa-circle-o"></i> Admin Console </a></li>
                                <!--<li><a href="index2.html"><i class="fa fa-circle-o"></i> Dashboard v2</a></li>-->
                            </ul>
                        </li>

                    </ul>
                </section>
                <!-- /.sidebar -->
            </aside>

            <!--  <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Admin Console
                        <small>Chose java source code to convert to Class or Sequence Diagram</small>
                    </h1>

                </section>

                <!-- Main content -->
                <section class="content">


                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Data Table With Full Features</h3>
                                </div>
                                <form class="login100-form validate-form" action="${pageContext.request.contextPath}/AdminConsole" method="post">
                                    <input class="form-text" id="compareData1" name="compareData11" style="display: none">
                                    <!--<p class="form-text" id="compareData2" name="compareData22" style="display: none"></p>-->
                                    <div class="container-login100-form-btn">

                                        <div class="box-footer">
                                            <button type="submit" class="btn btn-primary" onclick="getValues()">Compare</button>
                                        </div>
                                    </div>
                                </form>

                                <!-- /.box-header -->
                                <div class="box-body">
                                    <div class="alert alert-danger alert-dismissible" style="display: <%= request.getAttribute("display")%>">
                                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                        <h4><i class="icon fa fa-ban"></i> Alert!</h4>
                                        ${ErrDiagrams}
                                    </div>
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th style="width: 50px;">Select..</th>
                                                <th>ID</th>
                                                <th>Sequence</th>
                                                <th>UML Type</th>
                                                <th>Release Version</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <!-- loop over and get our list of images -->
                                            <c:forEach var="listOfImages" items="${ListOfAllImages}">


                                                <tr id="rowData" onclick="getRowIndex(this)">
                                                    <td>
                                                        <label>
                                                            <input type="checkbox" class="minimal"  >                                                             
                                                        </label>
                                                    </td>
                                                    <td id="rowData2" > ${listOfImages.id} </td>
                                                    <td> ${listOfImages.sequence} </td>
                                                    <td> ${listOfImages.umlType} </td>
                                                    <td> ${listOfImages.releaseVersion} </td>
                                                    
                                                </tr>

                                            </c:forEach>


                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th style="width: 50px;">Select..</th>
                                                <th>ID</th>
                                                <th>Sequence</th>
                                                <th>UML Type</th>
                                                <th>Release Version</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <!-- Box Comment -->
                            <div class="box box-widget">
                                <div class="box-header with-border">
                                    <div class="user-block">
                                        <img class="img-circle" src="dist/img/UML-Logo.png" alt="User Image">
                                        <span class="username"> UML Diagram - Old Release</span>
                                        <!--<span class="description">Shared publicly - 7:30 PM Today</span>-->
                                    </div>

                                    <!-- /.box-tools -->
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body"> 
                                   <img src="${ImagePath1}" class="img-responsive pad" alt="Photo">
                                    <img src="data:image/png;base64,${ImagePathSeq1}"class="img-responsive pad" alt="Photo"/>
                                    <!--              <p>I took this photo this morning. What do you guys think?</p>
                                                  <button type="button" class="btn btn-default btn-xs"><i class="fa fa-share"></i> Share</button>
                                                  <button type="button" class="btn btn-default btn-xs"><i class="fa fa-thumbs-o-up"></i> Like</button>
                                                  <span class="pull-right text-muted">127 likes - 3 comments</span>-->
                                </div>
                            </div>


                        </div>
                        <div class="col-md-6">
                            <!-- Box Comment -->
                            <div class="box box-widget">
                                <div class="box-header with-border">
                                    <div class="user-block">
                                        <img class="img-circle" src="dist/img/UML-Logo.png" alt="User Image">
                                        <span class="username"> UML Diagram - New Release</span>
                                        <!--<span class="description">Shared publicly - 7:30 PM Today</span>-->
                                    </div>
                                    <!-- /.user-block -->

                                    <!-- /.box-tools -->
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body"> 
                                  <img src="${ImagePath2}" class="img-responsive pad" alt="Photo">
                                    <img src="data:image/png;base64,${ImagePathSeq2}"class="img-responsive pad" alt="Photo"/>
                                    <!--              <p>I took this photo this morning. What do you guys think?</p>
                                                  <button type="button" class="btn btn-default btn-xs"><i class="fa fa-share"></i> Share</button>
                                                  <button type="button" class="btn btn-default btn-xs"><i class="fa fa-thumbs-o-up"></i> Like</button>
                                                  <span class="pull-right text-muted">127 likes - 3 comments</span>-->
                                </div>
                            </div>
                        </div>

                    </div>


                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->

            <footer class="main-footer">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 2.0.0
                </div>
                <strong>Copyright &copy; 2018 Ala'a Al-Mrayat.</strong> All rights
                reserved.
            </footer>

            <!-- Control Sidebar -->
            <aside class="control-sidebar control-sidebar-dark">
                <!-- Create the tabs -->
                <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
                    <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>

                    <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
                </ul>
                <!-- Tab panes -->
                <div class="tab-content">
                    <!-- Home tab content -->
                    <div class="tab-pane" id="control-sidebar-home-tab">
                        <h3 class="control-sidebar-heading">Recent Activity</h3>
                        <ul class="control-sidebar-menu">
                            <li>
                                <a href="javascript:void(0)">
                                    <i class="menu-icon fa fa-birthday-cake bg-red"></i>

                                    <div class="menu-info">
                                        <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

                                        <p>Will be 23 on April 24th</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <i class="menu-icon fa fa-user bg-yellow"></i>

                                    <div class="menu-info">
                                        <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>

                                        <p>New phone +1(800)555-1234</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>

                                    <div class="menu-info">
                                        <h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>

                                        <p>nora@example.com</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <i class="menu-icon fa fa-file-code-o bg-green"></i>

                                    <div class="menu-info">
                                        <h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>

                                        <p>Execution time 5 seconds</p>
                                    </div>
                                </a>
                            </li>
                        </ul>
                        <!-- /.control-sidebar-menu -->

                        <h3 class="control-sidebar-heading">Tasks Progress</h3>
                        <ul class="control-sidebar-menu">
                            <li>
                                <a href="javascript:void(0)">
                                    <h4 class="control-sidebar-subheading">
                                        Custom Template Design
                                        <span class="label label-danger pull-right">70%</span>
                                    </h4>

                                    <div class="progress progress-xxs">
                                        <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <h4 class="control-sidebar-subheading">
                                        Update Resume
                                        <span class="label label-success pull-right">95%</span>
                                    </h4>

                                    <div class="progress progress-xxs">
                                        <div class="progress-bar progress-bar-success" style="width: 95%"></div>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <h4 class="control-sidebar-subheading">
                                        Laravel Integration
                                        <span class="label label-warning pull-right">50%</span>
                                    </h4>

                                    <div class="progress progress-xxs">
                                        <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <h4 class="control-sidebar-subheading">
                                        Back End Framework
                                        <span class="label label-primary pull-right">68%</span>
                                    </h4>

                                    <div class="progress progress-xxs">
                                        <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
                                    </div>
                                </a>
                            </li>
                        </ul>
                        <!-- /.control-sidebar-menu -->

                    </div>
                    <!-- /.tab-pane -->
                    <!-- Stats tab content -->
                    <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
                    <!-- /.tab-pane -->
                    <!-- Settings tab content -->
                    <div class="tab-pane" id="control-sidebar-settings-tab">
                        <form method="post">
                            <h3 class="control-sidebar-heading">General Settings</h3>

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Report panel usage
                                    <input type="checkbox" class="pull-right" checked>
                                </label>

                                <p>
                                    Some information about this general settings option
                                </p>
                            </div>
                            <!-- /.form-group -->

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Allow mail redirect
                                    <input type="checkbox" class="pull-right" checked>
                                </label>

                                <p>
                                    Other sets of options are available
                                </p>
                            </div>
                            <!-- /.form-group -->

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Expose author name in posts
                                    <input type="checkbox" class="pull-right" checked>
                                </label>

                                <p>
                                    Allow the user to show his name in blog posts
                                </p>
                            </div>
                            <!-- /.form-group -->

                            <h3 class="control-sidebar-heading">Chat Settings</h3>

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Show me as online
                                    <input type="checkbox" class="pull-right" checked>
                                </label>
                            </div>
                            <!-- /.form-group -->

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Turn off notifications
                                    <input type="checkbox" class="pull-right">
                                </label>
                            </div>
                            <!-- /.form-group -->

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Delete chat history
                                    <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
                                </label>
                            </div>
                            <!-- /.form-group -->
                        </form>
                    </div>
                    <!-- /.tab-pane -->
                </div>
            </aside>
            <!-- /.control-sidebar -->
            <!-- Add the sidebar's background. This div must be placed
                 immediately after the control sidebar -->
            <div class="control-sidebar-bg"></div>
        </div>
        <!-- ./wrapper -->

        <script type="text/javascript">
            function getfolder(e) {
                var files = e.target.files;
                var path = files[0].webkitRelativePath;
                var Folder = path.split("/");
                var fil = document.getElementById("inputFile").value;


                document.getElementById("classValue").value = fil;
                $('input[type=file]').change(function () {
                    console.log(this.files[0].mozFullPath);
                });

                //    alert(Folder[0]);
            }


        </script>


        <!-- jQuery 3 -->
        <script src="./vendor/jquery/jquery-3.3.1.js"></script>
        <!-- jQuery 3 -->
        <script src="./vendor/jquery/dataTables.min.js"></script>
        <!-- jQuery 3 -->
        <script src="bower_components/jquery/dist/jquery.min.js"></script>
        <!-- jQuery UI 1.11.4 -->
        <script src="bower_components/jquery-ui/jquery-ui.min.js"></script>
        <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
        <script type="text/javascript" >
            function show(aval) {
                if (aval == "Sequence") {
                    Classdiv.style.display = 'inline';
                    Functiondiv.style.display = 'inline';
                    //        Form.fileURL.focus();
                } else {
                    Classdiv.style.display = 'none';
                    Functiondiv.style.display = 'none';
                }
            }
        </script>
        <script>
            $.widget.bridge('uibutton', $.ui.button);

        </script>
        <!-- Bootstrap 3.3.7 -->
        <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <!-- Morris.js charts -->
        <script src="bower_components/raphael/raphael.min.js"></script>
        <script src="bower_components/morris.js/morris.min.js"></script>
        <!-- Sparkline -->
        <script src="bower_components/jquery-sparkline/dist/jquery.sparkline.min.js"></script>
        <!-- jvectormap -->
        <script src="plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
        <script src="plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
        <!-- jQuery Knob Chart -->
        <script src="bower_components/jquery-knob/dist/jquery.knob.min.js"></script>
        <!-- daterangepicker -->
        <script src="bower_components/moment/min/moment.min.js"></script>
        <script src="bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
        <!-- datepicker -->
        <script src="bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
        <!-- Bootstrap WYSIHTML5 -->
        <script src="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
        <!-- Slimscroll -->
        <script src="bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="bower_components/fastclick/lib/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="dist/js/adminlte.min.js"></script>
        <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
        <script src="dist/js/pages/dashboard.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="dist/js/demo.js"></script>

        <!-- jQuery 3 -->
        <script src="bower_components/jquery/dist/jquery.min.js"></script>
        <!-- Bootstrap 3.3.7 -->
        <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <!-- DataTables -->
        <script src="bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
        <script src="bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
        <!-- SlimScroll -->
        <script src="bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="bower_components/fastclick/lib/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="dist/js/adminlte.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="dist/js/demo.js"></script>
        <!-- page script -->

        <!-- iCheck 1.0.1 -->
        <script src="./plugins/iCheck/icheck.min.js"></script>
        <script>
            $(function () {
                $('#example1').DataTable()
                $('#example2').DataTable({
                    'paging': true,
                    'lengthChange': false,
                    'searching': false,
                    'ordering': true,
                    'info': true,
                    'autoWidth': false
                })
            })

            $(function () {
                //Initialize Select2 Elements
                $('.select2').select2()

                //Datemask dd/mm/yyyy
                $('#datemask').inputmask('dd/mm/yyyy', {'placeholder': 'dd/mm/yyyy'})
                //Datemask2 mm/dd/yyyy
                $('#datemask2').inputmask('mm/dd/yyyy', {'placeholder': 'mm/dd/yyyy'})
                //Money Euro
                $('[data-mask]').inputmask()

                //Date range picker
                $('#reservation').daterangepicker()
                //Date range picker with time picker
                $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'})
                //Date range as a button
                $('#daterange-btn').daterangepicker(
                        {
                            ranges: {
                                'Today': [moment(), moment()],
                                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                                'This Month': [moment().startOf('month'), moment().endOf('month')],
                                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                            },
                            startDate: moment().subtract(29, 'days'),
                            endDate: moment()
                        },
                        function (start, end) {
                            $('#daterange-btn span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'))
                        }
                )

                //Date picker
                $('#datepicker').datepicker({
                    autoclose: true
                })

                //iCheck for checkbox and radio inputs
                $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                    checkboxClass: 'icheckbox_minimal-blue',
                    radioClass: 'iradio_minimal-blue'
                })
                //Red color scheme for iCheck
                $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
                    checkboxClass: 'icheckbox_minimal-red',
                    radioClass: 'iradio_minimal-red'
                })
                //Flat red color scheme for iCheck
                $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
                    checkboxClass: 'icheckbox_flat-green',
                    radioClass: 'iradio_flat-green'
                })

                //Colorpicker
                $('.my-colorpicker1').colorpicker()
                //color picker with addon
                $('.my-colorpicker2').colorpicker()

                //Timepicker
                $('.timepicker').timepicker({
                    showInputs: false
                })
            })


//            $("input:checkbox").on('click', function () {
//                // in the handler, 'this' refers to the box clicked on
//                var $box = $(this);
//                var count =0;
//                if ($box.is(":checked") && count <=2) {
//                    // the name of the box is retrieved using the .attr() method
//                    // as it is assumed and expected to be immutable
//                    var group = "input:checkbox[name='" + $box.attr("name"+count) + "']";
//                    // the checked state of the group/box on the other hand will change
//                    // and the current value is retrieved using .prop() method
//                    $(group).prop("checked", false);
//                    $box.prop("checked", true);
//                } else {
//                    $box.prop("checked", false);
//                }
//                count++;
//                console.log(count);
//            })

            var rowIndex = 0;
            var compareData ="" ;
            function getRowIndex(x) {
                rowIndex = x.rowIndex;

            
//
//            $('.minimal').click(function () {
//                var Row = document.getElementById("rowData");
//                var rowdata = document.getElementById("example1").rows.item(rowIndex);
//
//                var txt = "";
//                var i;
//                for (i = 0; i < rowdata.length; i++) {
//                    txt =   " is: " + rowdata  + "<br>";
//                }
//                
//
//                var Cells = Row.getElementsByTagName("td");
//                alert(rowIndex);

                 compareData +=  document.getElementById("example1").rows[rowIndex].cells[1].innerHTML + "#";
                document.getElementById("compareData1").innetHTML = compareData;
//                document.getElementById("compareData2").innetHTML = compareData;
//                alert(compareData);
//                alert(Cells[rowIndex].innerText);
                var length = $('.minimal:checked').length;
                if (length > 1) {
//                    alert(length);
                    $('.minimal:not(:checked)').attr('disabled', true);
                } else {
                    $('.minimal:not(:checked)').attr('disabled', false);
                }
//            })
        }
        
        function  getValues(){
             document.getElementById("compareData1").value = compareData;
//                document.getElementById("compareData2").value = compareData;
        }

            $(document).ready(function () {
                var table = $('#example').DataTable();

                $('#example tbody').on('click', 'tr', function () {
                    $(this).toggleClass('selected');
                })

                $('#button').click(function () {
                    alert(table.rows('.selected').data().length + ' row(s) selected');
                })
            })
        </script>
    </body>
</html>

