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
				<span class="dbpaneltitle">New Vehicle.</span>
				<br /><br />
				<form action="VehicleAdminManager" method="POST">
					<input type="text" name="vehiclemake" placeholder="Vehicle Make" required>
					<br />
					<input type="text" name="vehiclemodel" placeholder="Vehicle Model" required>
					<br />
					<input type="number" name="vehicleyear" placeholder="Vehicle Year" required>
					<br />
					<input type="text" name="vehicletag" placeholder="Vehicle Tag" required>
					<br />
					<input type="text" name="vehiclemileage" placeholder="Vehicle Mileage" required>
					<br />
					Last Service Date: 
					<input type="text" name="serviceday" placeholder="Service Day"> / <input type="text" name="servicemonth" placeholder="Service Month"> / <input type="text" name="serviceyear" placeholder="Service Year">
					<br />
					Vehicle Condition: <select name="vehiclecondition">
						<option value="awesome" selected>Awesome</option>
						<option value="good">Good</option>
						<option value="bad">Bad</option>
						<option value="shit">Shit</option>
					</select> 
					<br />
					Vehicle Type: <select name="vehicletype">
						<c:forEach items="${vehicleTypes}" var="type">
							<option value="${type.id}">${type.description}</option>
						</c:forEach>
					</select>
					<br /><br />
					<input type="submit" name="addVehicle" value="Submit">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>