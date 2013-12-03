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
			<%@ include file="resources/html/leftnav.html" %>
			<div id="dashboardpanel">
				<span class="dbpaneltitle">Choose Reservation Info.</span>
				<br /><br />
				<form action="ReservationManager" method="POST">
					Pickup time:
					<br />
					<input type="hidden" name="pickupday" value="${day}">
					<input type="hidden" name="pickupmonth" value="${month}">
					<input type="hidden" name="pickupyear" value="${year}">
					<input type="hidden" name="pickuphour" value="${hour}">
					<input type="hidden" name="pickupmeridiem" value="${meridiem}">
					${day} <span class="datedivider">/</span> ${month} <span class="datedivider">/</span> ${year} at ${hour} ${meridiem}.
					<br /><br />
					
					Rental Type:
					<br />
					<input type="hidden" name="rentaltype" value="${rentalType}">
					<input type="hidden" name="rentallength" value="${rentalLength}">
					${rentalType} rental for ${rentalLength} <span class="timeType"></span>
					<br /><br />
					
					Vehicle Type:
					<br />
					<input type="hidden" name="vehicletype" value="${vehicleType}">
					<input type="hidden" name="location" value="${location}">
					${description}
					<br />
					Hourly Rate:
					<br />
					${hourlyRate}
					<br />
					Daily Rate:
					<br />
					${dailyRate}
					<br /><br />
					Location: 
					<br />
					${locationname}
					<br /><br />
					Address:
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${streetAddrLine1}
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${streetAddrLine2}
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${city}, ${state} ${zipCode}
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${country}
					<br /><br />
					
					<span class="dbpaneltitle" style="font-size:16px;">Choose Vehicle.</span>
					<br /><br />
					<c:forEach items="${availableVehicles}" var="vehicle">
						Vehicle Make: 
						<br />
						${vehicle.year} ${vehicle.make} ${vehicle.model}
						<br /><br />
						Condition:
						<br />
						${vehicle.condition}
						<br /><br />
						Mileage:
						<br />
						${vehicle.mileage}
						<br /><br />
						<input type="radio" name="vehicle" value="${vehicle.id}"> 
						Choose this vehicle?
						<br /><br /><hr /><br />
					</c:forEach>
				<input type="submit" name="placeReservationFinal" value="Reserve">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>