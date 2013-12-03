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
				<form action="ReservationManager" method="POST">
					If you would like to return the vehicle, with reservation number, ${reservationNum}, please fill out the following information before continuing.
					<br /><br />
					To reasonable standards, you are returning the vehicle in a(n) 
					<select name="currentstate">
						<option value="Awesome">Awesome</option>
						<option value="Good">Good</option>
						<option value="Bad">Bad</option>
						<option value="Horrible">Horrible</option>
					</select> 
					state.
					<br /><br />
					<textarea name="comments" style="width:100%;" rows="20" placeholder="Further comments go in here."></textarea>
					<input type="submit" name="submitReturn" value="Return!">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>