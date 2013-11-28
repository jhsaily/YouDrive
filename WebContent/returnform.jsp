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
				<form action="returnvehicle" method="POST">
					If you would like to return the vehicle, please fill out the following information before continuing.
					<br /><br />
					To reasonable standards, when you recieved the vehicle it was in a
					<select name="prevstate">
						<option value="awesome">Awesome</option>
						<option value="good">Good</option>
						<option value="bad">Bad</option>
						<option value="shit">Shit</option>
					</select> 
					state.
					<br /><br />
					Now that you are returning it, it is in a
					<select name="currentstate">
						<option value="awesome">Awesome</option>
						<option value="good">Good</option>
						<option value="bad">Bad</option>
						<option value="shit">Shit</option>
					</select> 
					state.
					<br /><br />
					<textarea name="comments" style="width:100%;" rows="20" placeholder="Further comments go in here."></textarea>
					<input type="submit" value="Return!">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>