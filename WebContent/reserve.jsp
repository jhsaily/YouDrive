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
				<span class="dbpaneltitle">Reserve a Vehicle.</span>
				<br /><br />
				<form action="ReservationManager" class="formreserve" method="POST">
					<select class="reserveselect" name="location" size="15">
						<c:forEach var="i" begin="1" end="20">
							<option value="location${i}">Location <c:out value="${i}"/></option>
						</c:forEach>
					</select>
					<select class="reserveselect right" name="vehicle" size="15">
						<c:forEach var="i" begin="1" end="20">
							<!-- TODO: Some magic to filter out this list based on selection in previous list -->
							<option value="type${i}">Vehicle Type <c:out value="${i}"/></option>
						</c:forEach>
					</select>
					<br /><br />
					<input type="submit" name="placeReservation" value="Submit">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>