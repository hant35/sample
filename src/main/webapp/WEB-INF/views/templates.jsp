<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>LIST OF SURVEYS</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
	src="<c:url value="/assets/js/jquery.mCustomScrollbar_v3.0.2 .js" />"></script>
<script src="<c:url value="/assets/js/home.js" />"></script>
</head>
<body>
	<div class="container" id="main-menu">
		<div id="header">
			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 logo">
				<a href="<c:url value="/index.html" />"><img class="img-responsive" src="<c:url value="/assets/images/logo.jpg" />" /></a>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 main-menu">
				 <span class="menu-item"><a href="<c:url value="/index.html" />">HOME</a></span>
				 &frasl;
				 <span class="menu-item"><a href="<c:url value="/templates.html" />">SURVEYS</a></span>
				 &frasl; 
				 <span class="menu-item"><a href="http://about.panpages.com/">ABOUT</a></span>
			</div>
		</div>
		<div class="separator"></div>
		<div id="content">
			<h3 style="  margin: 30px 0;">LIST OF SURVEYS</h3>
			<c:forEach items="${templates}" var="template">
				<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
					<div class ="survey-image" >
						<a><img src="assets/images/survey_1.png"/></a>
					</div>
					<div class = "survey-name">
						<a href="<c:url value="survey_${template.id}.html"/>">${template.name}</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<div id ="footer">
		<p>Copyright <strong>Panpages</strong> © 2015 All Rights Reserved</p>
	</div>
		<script type="text/javascript">
	$(window).load(function(){
	     $("#content").mCustomScrollbar();
	});
	</script>
</body>
</html>