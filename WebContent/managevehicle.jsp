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
				<span class="dbpaneltitle">Manage Vehicle.</span>
				<br /><br />
				Vehicle ID: ${vehicle.id}
				<br />
				<form action="VehicleAdminManager" method="POST">
					<input type="hidden" name="vehicleid" value="${vehicle.id}">
					<br />
					<input type="text" name="vehiclemake" value="${vehicle.make}" placeholder="Vehicle Make" required>
					<br />
					<input type="text" name="vehiclemodel" value="${vehicle.model}" placeholder="Vehicle Model" required>
					<br />
					<input type="number" name="vehicleyear" value="${vehicle.year}" placeholder="Vehicle Year" required>
					<br />
					<input type="text" name="vehicletag" value="${vehicle.tag}" placeholder="Vehicle Tag" required>
					<br />
					<input type="text" name="vehiclemileage" value="${vehicle.mileage}" placeholder="Vehicle Mileage" required>
					<br />
					Current Service Date: ${vehicle.serviceDate}
					<br />
					<input type="text" name="serviceday" placeholder="New Service Day"> / <input type="text" name="servicemonth" placeholder="New Service Month"> / <input type="text" name="serviceyear" placeholder="New Service Year">
					<br />
					Vehicle Condition: <select name="vehiclecondition">
						<option value="${vehicle.condition}" selected>${vehicle.condition}</option>
						<option value="awesome">Awesome</option>
						<option value="good">Good</option>
						<option value="bad">Bad</option>
						<option value="shit">Shit</option>
					</select> 
					<br />
					Vehicle Type: <select name="vehicletype">
						<option value="${type.id}" selected>${vehicle.vehicleType.description}</option>
						<c:forEach items="${vehicleTypes}" var="type">
							<option value="${type.id}">${type.description}</option>
						</c:forEach>
					</select>
					<br />
					<input type="checkbox" name="removevehicle" value="yes">Remove Vehicle from system?
					<br /><br />
					<input type="submit" name="updateVehicle" value="Submit">
				</form>
				
				<br /><hr /><br />
				<span class="dbpaneltitle">Associated Comments.</span>
				<br /><br />
	
				<c:forEach items="${vehicle.comments}" var="comment">
					<p class="vehiclecomment">
						Comment ID: ${comment.id}
						<br />
						Comment: <c:out value="${comment.content}" />
					</p><hr style="width:50%;" />
				</c:forEach>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>