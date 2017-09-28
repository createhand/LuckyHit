$(document).ready(function() {
	$('#loading').ajaxStart(function() {
		$(this).show();
		$('#mmiddleWrap').hide();
	});
	$('#loading').ajaxStop(function() {
		$(this).hide();
		$('#mmiddleWrap').fadeIn('slow');
	});	
	
	$.datepicker.setDefaults({
		monthNames: ['년 1월','년 2월','년 3월','년 4월','년 5월','년 6월','년 7월','년 8월','년 9월','년 10월','년 11월','년 12월'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		showMonthAfterYear:true,
		dateFormat: 'yy-mm-dd',
		buttonImageOnly: true,
		buttonImage: "/images/admin/btn_calendar.gif"
	});
});

function goMenu(menuNo) {
	switch (menuNo) {
	case '00000':
		document.location.href = "/11101.html";
		break;
	case '10000':
		break;
	case '20000':
		break;
	case '30000':
		break;
	case '40000':
		break;
	case '50000':
		break;
	case '60000':
		break;
	case '70000':
		break;
	case '80000':
		break;
	}
};



