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
				<span class="dbpaneltitle">Edit ${locationName}.</span>
				<br /><br />
				<form action="LocationAdminManager" method="POST">
					<input type="hidden" name="locationid" value="${locationID}">
					<br />
					<input type="text" name="locationname" value="${locationName}" placeholder="Location Name" required>
					<br />
					<input type="number" name="capacity" value="${locationCap}" placeholder="Capacity" required>
					<br />
					<input type="text" name="addressline1" value="${addrLine1}" placeholder="Address Line 1" required>
					<br />
					<input type="text" name="addressline2" value="${addrLine2}" placeholder="Address Line 2">
					<br />
					<input type="text" name="city" value="${city}" placeholder="City" required>
					<br />
					<input type="text" name="zip" value="${zip}" placeholder="Zip Code" required>
					<br />
					<input type="text" name="state" value="${state}" placeholder="State" required
					><br />
					<input type="text" name="country" value="${country}" placeholder="Country" required>
					<br />
					<input type="checkbox" name="removelocation" value="yes">Remove Location from system?
					<br /><br />
					<input type="submit" name="updatelocation" value="Submit">
				</form>
				
				<br /><hr /><br />
				<span class="dbpaneltitle">Associated Vehicles.</span>
				<br /><br />
				<a href="LocationAdminManager?clicked=add&id=${locationID}">Add Vehicle?</a>
				<br /><br />
				<c:forEach items="${listOfVehicles}" var="vehicle">
					Vehicle ID: ${vehicle.id}
					<br />
					Vehicle Type: ${vehicle.vehicleType.description}
					<br />
					Vehicle Model: ${vehicle.year} ${vehicle.make} ${vehicle.model}
					<br /><br />
					<a href="VehicleAdminManager?clicked=edit&id=${vehicle.id}">Edit Vehicle?</a>
					<br />
					<a href="VehicleAdminManager?clicked=remove&id=${vehicle.id}">Remove Vehicle?</a>
					<br /><hr style="width:50%;"/><br />
				</c:forEach>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>