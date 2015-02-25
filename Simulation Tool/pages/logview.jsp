<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page
	import="java.io.*,java.util.*,com.smr.sessionutility.CustomSession"%>

<% 
CustomSession cS = new CustomSession();
String user = cS.getCurrentUser(request);

if (!cS.IsOpen(request)) {
	response.setStatus(response.SC_MOVED_PERMANENTLY);
	response.setHeader("Location", "/pages/login.html");
}

%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>SMR Simulation Tool</title>

<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Timeline CSS -->
<link href="../dist/css/timeline.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="../bower_components/morrisjs/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    

		<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.5/css/jquery.dataTables.css">
		
		<link rel="stylesheet" type="text/css" href="http://www.datatables.net/release-datatables/media/css/jquery.dataTables.css">
		<link rel="stylesheet" type="text/css" href="http://www.datatables.net/release-datatables/extensions/TableTools/css/dataTables.tableTools.css">
		
													 
		<script type="text/javascript" language="javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="//cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" language="javascript" src="//cdn.datatables.net/plug-ins/f2c75b7247b/integration/bootstrap/3/dataTables.bootstrap.js"></script>
	</head>

</head>

<body>

    <div id="wrapper">

 <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">
                <strong>Welcome </strong>
                <%
                if (user == null) {
                    out.print("Anonymous");
                } else {
                    out.print(user);
                }
        		 %>
                
                </a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="/logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
						<li><a href="index.jsp"><i class="fa fa-dashboard fa-fw"></i>
								Dashboard</a></li>
						<li><a href="/enqueue"><i class="fa fa-gears fa-fw"></i>
								Start Simulation</a></li>
						<li><a href="/pages/simulationstat.jsp"><i
								class="fa fa-bar-chart-o fa-fw"></i> Statistics</a></li>
						<li><a href="/pages/logview.jsp"><i
								class="fa fa-list-alt fa-fw"></i> Daily Log</a></li>
					</ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>



        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Daily Log</h1>
                        <div>
                        
                         <table id="valueTable" class="display" cellspacing="0" width="100%"> 
 				        <thead> 
 				            <tr> 
 				                <th>Identifier</th> 
 				                <th>IP</th> 
 				                <th>Method</th> 
 				                <th>Resource</th> 
 				                <th>Date</th> 
 				                <th>Agent</th> 
 				                <th>Latency</th> 
 				            </tr> 
 				        </thead> 
				 
 				        <tfoot> 
 				            <tr> 
 				                <th>Identifier</th> 
 				                <th>IP</th> 
 				                <th>Method</th> 
 				                <th>Resource</th> 
 				                <th>Date</th> 
 				                <th>Agent</th> 
 				                <th>Latency</th> 
 				            </tr> 
 				        </tfoot> 
 				    </table> 
                        </div>
                        
                        
                        
                        
                        
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

	<script type="text/javascript"> 
 		// Load Data table log information
 		$(document).ready(function() {
 		    $('#valueTable').dataTable( {
 		        "ajax": "/getLogInformation"
 		    } );
 		} );
	</script> 


	<!-- Bootstrap Core JavaScript -->
	<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	

	

</body>

</html>
