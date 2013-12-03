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
				Vehicle ID: ${id}
				<br />
				<form action="VehicleAdminManager" method="POST">
					<input type="hidden" name="vehicleid" value="${id}">
					<br />
					<input type="text" name="vehiclemake" value="${make}" placeholder="Vehicle Make" required>
					<br />
					<input type="text" name="vehiclemodel" value="${model}" placeholder="Vehicle Model" required>
					<br />
					<input type="number" name="vehicleyear" value="${year}" placeholder="Vehicle Year" required>
					<br />
					<input type="text" name="vehicletag" value="${tag}" placeholder="Vehicle Tag" required>
					<br />
					<input type="text" name="vehiclemileage" value="${mileage}" placeholder="Vehicle Mileage" required>
					<br />
					Current Service Date: ${serviceDate}
					<br />
					<input type="text" name="serviceday" placeholder="New Service Day"> / <input type="text" name="servicemonth" placeholder="New Service Month"> / <input type="text" name="serviceyear" placeholder="New Service Year">
					<br />
					Vehicle Condition: <select name="vehiclecondition">
						<option value="${condition}" selected>${condition}</option>
						<option value="Awesome">Awesome</option>
						<option value="Good">Good</option>
						<option value="Bad">Bad</option>
						<option value="Horrible">Horrible</option>
					</select> 
					<br />
					Vehicle Type: <select name="vehicletype">
						<option value="${type.id}" selected>${vehicle.vehicleType.description}</option>
						<c:forEach items="${vehicleTypes}" var="type">
							<option value="${type.id}">${type.description}</option>
						</c:forEach>
					</select>
					<br />
					Vehicle Location: <select name="vehiclelocation">
						<option value="${currentLocation.id}" selected>${currentLocation.name}</option>
						<c:forEach items="${locations}" var="location">
							<option value="${location.id}">${location.name}</option>
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
	
				<c:forEach items="${comments}" var="comment">
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