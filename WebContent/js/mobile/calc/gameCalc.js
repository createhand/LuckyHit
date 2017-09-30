function getResult(obj) {
	frm.action = 'gameCalc.do';
	frm.submit();
}

/*
$(document).ready(function() {
	$("#div").click(function() {
		var elem = $(this);
		alert('test');
	});
});
*/

//단일 픽 선택
$(function() {
	  $('#mcTbl td').click(function() {
	    var elem = $(this);
	    var parent = elem.parent();
	    var selectedRatio = elem.find("div").text();	    
	    var myRatio = $('#myRatio').text();
	    
	    if(elem.find("div").attr("id") != "ratio") return;
	    
	    //픽 선택 취소
	    if(parent.attr("class") == "off") {
	    	$('#myRatio').text($('#myRatio').text() * (selectedRatio/100));
		    elem.attr("class", "clickOn");
		    parent.attr("class", "on");
		    
	    } else {
	    //픽 선택
	    	var len = parent.children().length;
	    	for(var i=0;i<len;i++) {
	    		var chObj = parent.children().get(i);
	    		if(chObj.className == "clickOn") {
	    			var prvRatio = chObj.getElementsByTagName("div")[0].childNodes[0].nodeValue;
	    			chObj.className = "clickOff";
	    			parent.attr("class", "off");	    			
	    			$('#myRatio').text(myRatio / (prvRatio/100));
	    			
	    			if(selectedRatio == prvRatio) return;
	    			
	    		    $('#myRatio').text($('#myRatio').text() * (selectedRatio/100));
	    		    
	    		    elem.attr("class", "clickOn");
	    		    parent.attr("class", "on");
	    		    return;	    			
	    		}
	    	}
	    }
	    
	  });
	});