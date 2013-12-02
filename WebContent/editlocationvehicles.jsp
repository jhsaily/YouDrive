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
				<span class="dbpaneltitle">${locationName}'s Associated Vehicles.</span>
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