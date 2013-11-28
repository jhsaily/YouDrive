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
					<c:forEach var="i" begin="1" end="2">
						<form action="cancel.jsp" method="POST">
							Reservation Number: <c:out value="${i}"/>
							<br />
							Reservation start date: XX/XX/XXXX at XX:XX pm.
							<br />
							Reservation end date: XX/XX/XXXX at XX:XX pm.
							<br />
							Vehicle Type: X
							<br />
							Total: $XX.XX
							<br /><br />
							<input type="hidden" name="reservationnumber" value="${i}">
							<input type="submit" value="Cancel Reservation?">
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