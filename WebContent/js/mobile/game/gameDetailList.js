function getResult(obj) {
	searchFrm.action = 'gameDetailList.do';
	searchFrm.submit();
}

$(function() {
	  $('#mcTbl td').click(function() {
	    var elem = $(this);
	    var parent = elem.parent();
	    var selectedRatio = elem.find("div").text();	    
	    var totalBetCnt = $('#totalBetCnt').text();
	    var myRatio = $('#myRatio').text();
	    
	    if(elem.find("div").attr("id") != "ratio") return;
	    
	    if(parent.attr("class") == "off") {	    
	    	
	    	$('#myRatio').text($('#myRatio').text() * (selectedRatio/100));
		    elem.attr("class", "clickOn");
		    parent.attr("class", "on");
		    
	    } else {	    	
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