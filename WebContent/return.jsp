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
				<span class="dbpaneltitle">Return a Vehicle.</span>
				<br /><br />
				<div class="returnlist">
					You currently have (2) active reservations.
					<br /><br />
					To return a vehicle, please click on it in the list below.
					<br />A series of questions will follow to finalize the process.
					<br /><br />
					<c:forEach var="i" begin="1" end="2">
						<form action="returnform.jsp" method="POST">
							Reservation Number: <c:out value="${i}"/>
							<br />
							Vehicle Type: X
							<br />
							Reservation start date: XX/XX/XXXX at XX:XX pm.
							<br />
							<input type="hidden" name="reservationnumber" value="${i}">
							<input type="submit" value="Return?">
						</form>
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