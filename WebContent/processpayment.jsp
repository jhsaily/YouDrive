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
				<span class="dbpaneltitle">Payment Summary/Confirmation.</span>
				<br /><br />
				<form action="PaymentManager" method="POST">
					Payment Amount: <fmt:formatNumber value="${paymentAmount}" type="currency"/>
					<br />
					Reason : ${paymentReason}
					<br /><br /><hr /><br />
					<span class="dbpaneltitle" style="font-size:16px;">Payment Information on File.</span>
					<br /><br />
					First Name: ${firstName}
					<br />
					Last Name: ${lastName}
					<br />
					Card Number: ${cardNumber}
					<br />
					Card Expiration Date: ${cardExpMonth} / ${cardExpYear}
					<br />
					Billing Address:
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${addrLine1}
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${addrLine2}
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${city}, ${state} ${zip}
					<br />
					&nbsp;&nbsp;&nbsp;&nbsp;${country}
					<br /><br />
					<input type="submit" name="confirm" value="Submit Payment">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>