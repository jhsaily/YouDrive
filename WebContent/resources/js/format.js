//Start Window Formatting options
function setWidth()
{
	var winW = $('#header').width();
	var SmWinW = winW/3;
	SmWinW = Math.floor(SmWinW);
	$('.topnavlink').width(SmWinW);
	var temp = winW - SmWinW*2;
	$('.topnavlinkright').width(temp-2);
}
$(document).ready(function(){
	$('#cssmenu > ul > li:has(ul)').addClass("has-sub");
	$('#cssmenu > ul > li > a').click(function() {

	var checkElement = $(this).next();

	$('#cssmenu li').removeClass('active');
	$(this).closest('li').addClass('active');	

	if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
		$(this).closest('li').removeClass('active');
		checkElement.slideUp('normal');
	}

	if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
		$('#cssmenu ul ul:visible').slideUp('normal');
		checkElement.slideDown('normal');
	}

	if (checkElement.is('ul')) {
		return false;
	} else {
		return true;	
	}
});
});