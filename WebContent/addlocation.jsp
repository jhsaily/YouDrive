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
		<script src="resources/js/format.js"></script>
		<title>YouDrive Dashboard</title>
	</head>
	<body>
		<div id="mainbody">
			<%@ include file="resources/html/header.html" %>
			<%@ include file="resources/html/leftnav_admin.html" %>
			<div id="dashboardpanel">
				<span class="dbpaneltitle">New Location.</span>
				<br /><br />
				<p>${output}</p>
				<form action="LocationAdminManager" method="POST">
					<input type="text" name="locationname" placeholder="Location Name" required>
					<br />
					<input type="number" name="capacity" placeholder="Capacity" required>
					<br />
					<input type="text" name="addressline1" placeholder="Address Line 1" required>
					<br />
					<input type="text" name="addressline2" placeholder="Address Line 2">
					<br />
					<input type="text" name="city" placeholder="City" required>
					<br />
					<input type="text" name="state" placeholder="State" required>
                    <br />
					<input type="text" name="zip" placeholder="Zip Code" required>
                    <br />
					<input type="text" name="country" placeholder="Country" required>
					<br /><br />
					<input type="submit" name="addLocation" value="Submit">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>