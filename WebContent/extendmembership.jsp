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
    	<div id="hiddenVar" style="display:none">${hasBeenMember}</div>
		<div id="mainbody">
			<%@ include file="resources/html/header.html" %>
			<%@ include file="resources/html/leftnav.html" %>
			<div id="dashboardpanel">
			<!-- TODO: script to automatically calculate cost based on aquired price per month values -->
				<span class="dbpaneltitle">Extend your Membership.</span>
				<br /><br />
				Here you can extend your membership for YouDrive.
				<br /><br />
				Just enter the number of months to renew by and we'll handle the rest.
				<br /><br />
				<form action="MembershipManager" method="POST">
					Renew your membership for
					<input type="number" name="months" placeholder="# of Months">
					months, and then you pay $<span id="ppm">${membershipMonthlyPrice}</span> per month.
					<br /><br />
					That comes to a total of $<span id="ptotal">0</span>
					<br /><br />
					<input type="submit" name="extend" value="Submit">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
			
			$( "input[name='months']" ).change(function () {
				var str = $('.hiddenVar').text().toLowerCase();
				var temp = $( "input[name='months']" ).val();
				var ppm = $('#ppm').text().toLowerCase();
				if(str == "false" || true)
				{
					if (temp < 6) {
						window.alert("Since you've never been a member, you need to choose a minimum of 6 months.");
						$( "input[type='submit']" ).attr("disabled", "disabled");
					} else {
						$( "input[type='submit']" ).removeAttr("disabled");  
					}
				} else {
					$( "input[type='submit']" ).removeAttr("disabled");
				}
				
				$('#ptotal').text(temp * ppm)
				
			});
		</script>
	</body>
</html>