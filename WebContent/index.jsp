<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="resources/css/layout.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/form.css" />
		<script src="resources/jquery/js/jquery-1.9.1.js"></script>
		<script src="resources/js/format.js"></script>
		<title>Login to YouDrive</title>
	</head>
	<body>
		<div id="mainbody">
			<%@ include file="resources/html/header.html" %>
			<%@ include file="resources/html/login.html" %>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
			
			$( ".forgotpass" ).click(function() {
				$( "#loginpanel" ).fadeOut( "slow", function() {
					// Animation complete.
				});
				$( "#registerpanel" ).fadeOut( "slow", function() {
					// Animation complete.
				});
				$( "#forgotpasspanel" ).fadeIn( "slow", function() {
					// Animation complete.
				});
			});
			$( ".login" ).click(function() {
				$( "#forgotpasspanel" ).fadeOut( "slow", function() {
					// Animation complete.
				});
				$( "#registerpanel" ).fadeOut( "slow", function() {
					// Animation complete.
				});
				$( "#loginpanel" ).fadeIn( "slow", function() {
					// Animation complete.
				});
			});
			$( ".register" ).click(function() {
				$( "#forgotpasspanel" ).fadeOut( "slow", function() {
					// Animation complete.
				});
				$( "#loginpanel" ).fadeOut( "slow", function() {
					// Animation complete.
				});
				$( "#registerpanel" ).fadeIn( "slow", function() {
					// Animation complete.
				});
			});
		</script>
	</body>
</html>