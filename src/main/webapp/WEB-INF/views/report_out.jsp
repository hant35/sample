<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page session="true"%>
<html>
<head>
<title>Admin</title>
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
<link rel="stylesheet" type="text/css"
	href="<c:url value="/assets/css/bootstrap-datepicker.css" />">
<script src="<c:url value="/assets/js/jquery_v1.8.2.js"/>"></script>
<script src="<c:url value="/assets/js/bootstrap.min_v3.2.0.js"/>"></script>
<script src="<c:url value="/assets/js/jquery.fancybox_v2.1.5.js"/>"></script>
<script src="<c:url value="/assets/js/bootstrap-datepicker.js"/>"></script>
<script
	src="<c:url value="/assets/js/jquery.mCustomScrollbar_v3.0.2.js"/>"></script>
<script src="<c:url value="/assets/js/home.js" />"></script>
</head>
<body>
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

	<div class="container" id="logined-top-name">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<h4>
					Welcome : ${pageContext.request.userPrincipal.name} | <a
						href="javascript:formSubmit()"> Logout</a>
				</h4>
			</c:if>
		</div>
	</div>

	<div class="container" id="main-content">
		<div id="header">
			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 logo">
				<a href="<c:url value="/index.html" />"><img
					class="img-responsive"
					src="<c:url value="/assets/images/logo.jpg" />" /></a>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 main-menu">

				<span class="menu-item"><a
					href="<c:url value="/customers.html" />">ALL SURVEYS</a></span> &frasl; <span
					class="menu-item"><a href="http://about.panpages.com/">ABOUT
						PANPAGES</a></span>
			</div>
		</div>
		<div class="separator"></div>
		<div id="content">


			
			<form:form action="report_out.html?submit" method="POST"
				modelAttribute="report" id="reportForm">
				<br /> <br />

			<div class="alert alert-info" role="alert" >${report.result}</div>

			<br /> <br />
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"></div>
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
					<table>
					<tr>
					<td width="100px">
					From date:
					</td>
					<td>
					<fmt:formatDate var="reportFrom" value="${report.fromDate}"
							pattern="dd/MM/yyyy" />
						<input type="text" class="form-control" id="fromDatePk"
							name="sFromDate" value="${reportFrom}" required="required"/>
					</td>
					</tr>
					</table>
						
					</div>
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"></div>
				</div>

				<br />
				<br />
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"></div>
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
					<table>
					<tr>
					<td  width="100px">To date:</td>
					<td>
					<fmt:formatDate var="reportTo" value="${report.toDate}"
							pattern="dd/MM/yyyy" />
						<input type="text" class="form-control" id="toDatePk"
							name="sToDate" value="${reportTo}" required="required"/>
					</td>
					</tr>
					</table>
						
					</div>
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"></div>
				</div>
				<br />
				<br />
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"></div>
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
<table>
					<tr>
					<td  width="100px">Action</td>
					<td>
					<select id="action" name="action" class="form-control">
							<option value="dl">Download</option>
							<option value="mail">Send Mail</option>
							<option value="all">Download + Email</option>
						</select>
					</td>
					</tr>
					</table>
						
					</div>
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"></div>
				</div>
				<br>
				<br>
				<br>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"></div>
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
						<input type="submit" value="Submit" />
					</div>
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"></div>
				</div>
			</form:form>
		</div>
	</div>

	<div id="footer">
		<p>
			Copyright <strong>Panpages</strong> &copy; 2015 All Rights Reserved
		</p>
	</div>
	<script type="text/javascript">
		$('#fromDatePk').datepicker({
			format : "dd/mm/yyyy"
		});
		$('#toDatePk').datepicker({
			format : "dd/mm/yyyy"
		});
	</script>
</body>
</html>