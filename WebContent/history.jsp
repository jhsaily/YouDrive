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
				<span class="dbpaneltitle">Reservation History.</span>
				<br /><br />
				<div class="returnlist">
					<!-- Tanya Hey Jan, this is how a I'm going to pass in the linked list of reservations for a particular customer. 
					Anywhere you want to display an attribute just do reservation.WhateverTheAttributeFromReservationClass
					I'm currently using a dummy linked list that has 2 reservations with like no info in them. Change the format/whatever
					as you want, but that's how I'm giving you the info.-->
					<c:forEach items="${listOfActiveReservations}" var="reservation">
						<form action="ReservationManager" method="POST">
							Reservation Number: ${reservation.id}
							<br />
							Reservation start date: ${reservation.pickupTime}
							<br />
							Reservation end date: ${reservation.timeDue}
							<br />
							Vehicle Type: ${reservation.vehicle.vehicleType.description}
							<br /><br />
							<input type="hidden" name="reservationnumber" value="${reservation.id}">
							<input type="submit" name="cancel" value="Cancel Reservation?">
						</form>
						<br />
						<hr /><br />
					</c:forEach>
					<c:forEach items="${listOfInactiveReservations}" var="reservation">
						Reservation Number: ${reservation.id}
						<br />
						Reservation start date: ${reservation.pickupTime}
						<br />
						Reservation end date: ${reservation.timeDue}
						<br />
						Vehicle Type: ${reservation.vehicle.vehicleType.description}
						<br />
						<br />
						<hr /><br />
					</c:forEach>
				</div>
			</div>
		</div>

		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>