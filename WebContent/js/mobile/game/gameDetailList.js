function getSubGameList(no, select) {
	var expResultList = document.getElementsByName("expResult");
	var nowBet = expResultList[no-1].value;
	
	if(nowBet.indexOf(select) < 0) {
		nowBet += select;
	} else {
		nowBet = replaceStr(nowBet, select, "");
	}
	
	expResultList[no-1].value = nowBet;
	$('#subGame_'+no).text(nowBet);
}

function getResult(obj) {
	searchFrm.action = 'gameDetailList.do';
	searchFrm.submit();
}

function checkPick() {
	
	var expResultList = document.getElementsByName("expResult");
	
	for(var i=0;i<expResultList;i++) {
		if(isNull(expResultList[i]) || expResultList[i] == "") {
			alert((i+1)+"번째 픽이 선택되지 않았습니다.");
			return;
		}
	}
	document.frm.submit();
}


//복수 픽 선택
$(function() {
	$('#mcTbl td').click(function() {
		var elem = $(this);
		var parent = elem.parent();
		var selectedRatio = elem.find("div").text();
		var totalBetCnt = $('#totalBetCnt').text();
		var myRatio = $('#myRatio').text();

	   	if(elem.find("div").attr("id") != "ratio") return;
	    
	   	//ratio
	   	var nowRatio;
	   	
	   	if(elem.attr("class") == "clickOff") {
	   		//class on
	   		elem.attr("class", "clickOn");
	   		nowRatio = $('#myRatio').text() * (selectedRatio/100);
	   	} else if(elem.attr("class") == "clickOn") {
	   		//class off
	    	elem.attr("class", "clickOff");
	    	nowRatio = $('#myRatio').text() / (selectedRatio/100);
	   	}
	   	
	   	$('#myRatio').text(nowRatio);
	   	$('#subRatio').text(nowRatio);
	   	
	 });
});
