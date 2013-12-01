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
				<span class="dbpaneltitle">Choose a Location.</span>
				<br /><br />
				<c:forEach items="${locations}" var="location">
					Location: ${location.name}
					<br /><br />
					Address:
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${location.locationAddress.streetAddrLine1}
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${location.locationAddress.streetAddrLine2}
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${location.locationAddress.city}, ${location.locationAddress.state} ${location.locationAddress.zipCode}
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${location.locationAddress.country}
					<br /><br />
					<a href="ReservationManager?clicked=chooseLocation&location=${location.id}&vehiclechosen=false">Choose Location?</a>
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