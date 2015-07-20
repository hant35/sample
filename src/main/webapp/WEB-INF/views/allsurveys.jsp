<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>PANPAGES's SURVEYs</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/assets/css/jquery.mCustomScrollbar_v3.0.2.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/assets/css/jquery.fancybox_v2.1.5.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/assets/css/bootstrap.min_v3.2.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/assets/css/home.css" />">
<script src="<c:url value="/assets/js/jquery_v1.8.2.js" />"></script>
<script src="<c:url value="/assets/js/bootstrap.min_v3.2.0.js" />"></script>
<script src="<c:url value="/assets/js/jquery.fancybox_v2.1.5.js" />"></script>
<script
	src="<c:url value="/assets/js/jquery.mCustomScrollbar_v3.0.2.js" />"></script>
<script src="<c:url value="/assets/js/home.js" />"></script>
</head>
<body>
	<div class="container" id="main-content">
		<div id="header">
			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 logo">
				<a href="<c:url value="/index.html" />"><img class="img-responsive" src="<c:url value="/assets/images/logo.jpg" />" /></a>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 main-menu">
				 
				 <span class="menu-item"><a href="<c:url value="/customers.html" />">ALL SURVEYS</a></span>
				 &frasl; 
				 <span class="menu-item"><a href="http://about.panpages.com/">ABOUT PANPAGES</a></span>
			</div>
		</div>
		<div class="separator"></div>
		<div id="content">
		<br/><br/><br/>			
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<p style="font-size: 20pt; text-align: justify;">
					This survey tool will help you to generate proposal for new and existing customers. In this survey, the system will collect your advertiser's information, goals, objectives and your propose keywords. Once the data is collected, the system will tabulate based on a sophisticated algorithm to suggest a suitable budget for your advertisers.
				</p>
		<br/><br/><br/>		
			</div>
		
			
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="join-survey">
				
				<a href="customers.html"><img alt="Click to Join" class="img-responsive" src="assets/images/start.png" /></a>
			</div>
		</div>
	</div>
	
	<div id="footer">
		<p>Copyright <strong>Panpages</strong> &copy; 2015 All Rights Reserved</p>
	</div>
	<script type="text/javascript">
		$(window).load(function() {
			$("#content").mCustomScrollbar();
		});
		$(document).ready(function() {
			$(".fancybox").fancybox({
				"type" : "image"
			});
		});
	</script>
</body>
</html>