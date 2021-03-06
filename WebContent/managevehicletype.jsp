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
				<span class="dbpaneltitle">Manage Vehicle Types.</span>
				<br /><br />
				To edit or remove a vehicle type, select it from the list below.
				<br /><br />
				<form action="VehicleAdminManager" class="formreserve" method="POST">
					<select class="reserveselect" style="width:100%;" name="vehicletype" size="15">
						<c:forEach items="${VehicleTypes}" var="vehicletype">
							<option value="${vehicletype.id}">${vehicletype.description}</option>
						</c:forEach>
					</select>
					<br /><br />
					<input type="submit" name="manageVehicleType" value="Edit Vehicle Type">
					<input type="submit" name="removeVehicleType" value="Remove Vehicle Type">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>