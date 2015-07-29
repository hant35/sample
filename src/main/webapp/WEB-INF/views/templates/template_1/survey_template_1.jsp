<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <script src="<c:url value="/template_assets/template_1/assets/js/jquery.min_v2.1.3.js" />"></script>
        <script src="<c:url value="/template_assets/template_1/assets/js/jquery-ui_v1.11.4.js" />"></script>
        <script src="<c:url value="/template_assets/template_1/assets/js/bootstrap.min_v3.3.4.js" />"></script>

        <script src="<c:url value="/template_assets/template_1/assets/js/jquery.ui.widget_v1.11.1.js" />"></script>
        <script src="<c:url value="/template_assets/template_1/assets/js/jquery.iframe-transport_v9.10.0.js" />"></script>
        <script src="<c:url value="/template_assets/template_1/assets/js/jquery.fileupload_v9.10.0.js" />"></script>

        <script src="<c:url value="/template_assets/template_1/assets/js/formwizard.js" />"></script>
        <script src="<c:url value="/template_assets/template_1/assets/js/template_1.js" />"></script>

        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/jquery.mCustomScrollbar_v3.0.2.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/jquery.fancybox_v2.1.5.css" />">
        <link rel="stylesheet" href="<c:url value="/template_assets/template_1/assets/css/jquery-ui_v1.11.4.css" />">
        <link rel="stylesheet" href="<c:url value="/template_assets/template_1/assets/css/bootstrap.min_v3.3.4.css" />">
        <link rel="stylesheet" href="<c:url value="/template_assets/template_1/assets/css/jquery.fileupload_v1.11.1.css" />">
        <link rel="stylesheet" href="<c:url value="/template_assets/template_1/assets/css/bootstrap-theme.min_v3.3.4.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/template_assets/template_1/assets/css/formwizard.css" />">

        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/home.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/template_assets/template_1/assets/css/template_1.css" />">

        <title>PANPAGES PROPOSAL SURVEY</title>
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
                <form:form method="POST" modelAttribute="surveyForm"  enctype="multipart/form-data"
                           action="survey_1.html"  id="surveyForm">
                    <div id="form-sections">
                        <fieldset class="sectionwrap" id="section-1">
                            <div class="step1 row">
                                <jsp:include page="section_1_template_1.jsp"></jsp:include>
                                </div>
                            </fieldset>
                            <fieldset class="sectionwrap" id="section-2">
                                <div class="step2 row">
                                <jsp:include page="section_2_template_1.jsp"></jsp:include>
                                </div>
                            </fieldset>
                            <fieldset class="sectionwrap" id="section-3">
                                <div class="step3 row">
                                <jsp:include page="section_3_template_1.jsp"></jsp:include>
                                </div>
                            </fieldset>
                            <fieldset class="sectionwrap" id="section-4">
                                <div class="step4 row">
                                <jsp:include page="section_4_template_1.jsp"></jsp:include>
                                </div>
                            </fieldset>
                            <fieldset class="sectionwrap" id="section-5">
                                <div class="step5 row">
                                <jsp:include page="section_5_template_1.jsp"></jsp:include>
                                </div>
                            </fieldset>
                            <fieldset class="sectionwrap" id="section-6">
                                <div class="step6 row">
                                <jsp:include page="section_6_template_1.jsp"></jsp:include>
                                </div>
                            </fieldset>
                            <fieldset class="sectionwrap" id="section-7">
                                <div class="step7 row">
                                <jsp:include page="section_7_template_1.jsp"></jsp:include>
                                </div>
                            </fieldset>
                            <fieldset class="sectionwrap" id="section-8">
                                <div class="step8 row">
                                <jsp:include page="section_8_template_1.jsp"></jsp:include>
                                </div>
                            </fieldset>
                            <fieldset class="sectionwrap" id="section-9">
                                <div class="step9 row">
                                <jsp:include page="section_9_template_1.jsp"></jsp:include>
                                </div>
                            </fieldset>
                             <fieldset class="sectionwrap" id="section-10">
                                <div class="step10 row">
                                <jsp:include page="section_10_template_1.jsp"></jsp:include>
                                </div>
                            </fieldset>

                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <input type="hidden" name="countSurvey" id="countSurvey"  value="10" />
                </form:form>
            </div>
        </div>

        <div id="footer">
            <p>Copyright <strong>Panpages</strong> &copy; 2015 All Rights Reserved</p>
        </div>

    </body>
</html>