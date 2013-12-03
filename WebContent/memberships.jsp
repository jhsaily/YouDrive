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
		<link rel="stylesheet" type="text/css" href="resources/css/accordion.css" />
		<script src="resources/jquery/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="resources/js/format.js"></script>
		<title>Locations & Vehicles</title>
	</head>
	<body>
		<div id="mainbody">
			<%@ include file="resources/html/header.html" %>
			<div id="infopanel">
            	<span class="dbpaneltitle">YouDrive.</span>
                <br /><br />
                If you actually need a reason to join us, just look at our numbers!
                <br /><br />
				Membership Monthly Price: ${membershipPrice}<br />
				# of Users: ${numberOfUsers}<br />
				# of Locations: ${numberOfLocations}<br />
				# of Vehicles: ${numberOfVehicles}<br />
				<br />
				Look at these numbers. Don't you want to join in on the fun?
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>