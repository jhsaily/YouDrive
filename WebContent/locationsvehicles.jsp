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
		<script type="text/javascript" src="resources/js/format.js"></script>
		<title>Locations & Vehicles</title>
	</head>
	<body>
		<div id="mainbody">
			<%@ include file="resources/html/header.html" %>
			<div id="infopanel">
				<span class="dbpaneltitle">Our Locations and Vehicles.</span>
				<br /><br />
				<c:forEach items="${locations}" var="location">
					Location:
					<br />
					${location.name}
					<br /><br />
					${location.locationAddress.streetAddrLine1}
					<br />
					${location.locationAddress.streetAddrLine2}
					<br />
					${location.locationAddress.city}, ${location.locationAddress.state} ${location.locationAddress.zipCode}
					<br />
					${location.locationAddress.country}
					<br /><br />
					
					<c:forEach items="${location.vehicles}" var="vehicle">
						<div class="sublist">
							Vehicle:
							<br />
							${vehicle.vehicleType.description}
							<br />
							<br />
							Make:
							<br />${vehicle.year}
							<br />${vehicle.make}
							<br />${vehicle.model}
							<br />
						</div>
					</c:forEach>
					
					<br /><br /><hr /><br />
				</c:forEach>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>