<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>

<% 
HttpSession ses = request.getSession();
String user = (String) ses.getAttribute("user");

if (ses.getAttribute("user") == null) {
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

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

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
		
		<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script>
<script src="http://code.highcharts.com/highcharts-more.js"></script>

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
                <a class="navbar-brand" href="index.jsp">
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
                       <li>
                            <a href="index.jsp"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                        </li>
                        <li>
                            <a href="/enqueue"><i class="fa fa-gears fa-fw"></i> Start Simulation</a>
                        </li>
                        <li>
                            <a href="/pages/simulationstat.jsp"><i class="fa fa-bar-chart-o fa-fw"></i> Statistics</a>
                        </li>
                        <li>
                            <a href="/pages/logview.jsp"><i class="fa fa-list-alt fa-fw"></i> Daily Log</a>
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>


        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Statistics</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
			<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h3>Dominio</h3>
                            <div id="differentNum"><p>Loading...</p></div>
                        </div>
                    </div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h3>Frequenza</h3>
                            <p>Visualizza il numero maggiormente generato durante tutta la simulazione.
                            <div id="gauge" style="min-width: 310px; height: 400px; margin: 0 auto">Loading...</div> 
                        </div>
                    </div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h3>Tempo Simulazione</h3>
                            <div id="elapseSimul"><p>Loading...</p></div>
                        </div>
                    </div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
			
			<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h3>Percentuali</h3>
                            <p>Visualizza la percentuale per ogni elemento casuale generato.</p>
                            <div class="table-responsive">
								<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto">Loading...</div>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

	<script type="text/javascript"> 
		//$('#differentNum').load(("/showData #differentNum")).innerHTML;
		//$('#tdBodyStat').load(("/showData #percentageApp")).innerHTML;
		//$('#mostApp').load(("/showData #mostApp")).innerHTML;
		//$('#elapseSimul').load(("/showData #elapseSimul")).innerHTML;
	$(function () {
		$(document).ready(function() {

		    var options = {
		        chart: {
		            renderTo: 'container',
		        },
		        series: [{}]
		    };

		    $.getJSON('/showData', function(chartData) {
		    	//console.log(data);
		        //options.series[0].data = data["graph"];
		        
		        $('#elapseSimul').html(chartData["elapseSimul"]);
		        $('#differentNum').html(chartData["differentNum"]);
		        
		        $('#container').highcharts({
			        title: {
			            text: 'Percentuale Numeri',
			            x: -20 //center
			        },
			        subtitle: {
			            text: 'Generatore Numeri Casuali',
			            x: -20
			        },
			        xAxis: {
			            categories: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50]
			        },
			        yAxis: {
			            title: {
			                text: 'Frequena'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },

			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Percentuale',
			            data: chartData["graph"]
			        }]


			    });
		        
		        
		        $('#gauge').highcharts({

		            chart: {
		                type: 'gauge',
		                plotBackgroundColor: null,
		                plotBackgroundImage: null,
		                plotBorderWidth: 0,
		                plotShadow: false
		            },
		            title: {
		                text: ''
		            },
		            pane: {
		                startAngle: -150,
		                endAngle: 150,
		                background: [{
		                    backgroundColor: {
		                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
		                        stops: [
		                            [0, '#FFF'],
		                            [1, '#333']
		                        ]
		                    },
		                    borderWidth: 0,
		                    outerRadius: '109%'
		                }, {
		                    backgroundColor: {
		                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
		                        stops: [
		                            [0, '#333'],
		                            [1, '#FFF']
		                        ]
		                    },
		                    borderWidth: 1,
		                    outerRadius: '107%'
		                }, {
		                    // default background
		                }, {
		                    backgroundColor: '#DDD',
		                    borderWidth: 0,
		                    outerRadius: '105%',
		                    innerRadius: '103%'
		                }]
		            },

		            // the value axis
		            yAxis: {
		                min: 0,
		                max: 50,

		                minorTickInterval: 'auto',
		                minorTickWidth: 1,
		                minorTickLength: 10,
		                minorTickPosition: 'inside',
		                minorTickColor: '#666',

		                tickPixelInterval: 30,
		                tickWidth: 2,
		                tickPosition: 'inside',
		                tickLength: 10,
		                tickColor: '#666',
		                labels: {
		                    step: 2,
		                    rotation: 'auto'
		                },
		                title: {
		                    text: 'Maggior Numero Generato'
		                },
		                plotBands: [{
		                    from: 0,
		                    to: 5,
		                    color: '#DF5353' // red
		                }, {
		                    from: 5,
		                    to: 10,
		                    color: '#DDDF0D' // yellow
		                }, {
		                    from: 10,
		                    to: 40,
		                    color: '#55BF3B' // green
		                }, {
		                    from: 40,
		                    to: 45,
		                    color: '#DDDF0D' // yellow
		                }, {
		                    from: 45,
		                    to: 50,
		                    color: '#DF5353' // red
		                }]
		            },

		            series: [{
		                name: 'Numero',
		                data: chartData["gauge"]		                
		            }]

		        });
		        
		        
			});  //end function GetJSON
		    
		    });

		    
		    
		    
		});
		
		
	</script> 
    <!-- jQuery -->
    <!-- <script src="../bower_components/jquery/dist/jquery.min.js"></script> -->

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
</body>

</html>
