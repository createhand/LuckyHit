function getSubGameList(no, select) {
	$('#subGame_'+no).text(select);
}

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
	    var subRatio = $('#subRatio').text();
	    
	    if(elem.find("div").attr("id") != "ratio") return;
	    
	    var nowRatio = $('#myRatio').text() * (selectedRatio/100);
	    
	    //픽 선택 취소
	    if(parent.attr("class") == "off") {
	    	$('#myRatio').text(nowRatio);
	    	$('#subRatio').text(nowRatio);
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
	    			$('#subRatio').text(myRatio / (prvRatio/100));
	    			
	    			if(selectedRatio == prvRatio) return;
	    			
	    		    $('#myRatio').text(nowRatio);
	    		    $('#subRatio').text(nowRatio);
	    		    
	    		    elem.attr("class", "clickOn");
	    		    parent.attr("class", "on");
	    		    return;	    			
	    		}
	    	}
	    }
	    
	  });
	});