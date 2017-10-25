function getSubGameList(obj, no, select) {
	
	//화면표시 및 픽률 계산
	var elem = $(obj);
	var parent = elem.parent();
	var selectedRatio = elem.find("div").text();
	var totalBetCnt = $('#totalBetCnt').text();
	var myRatio = $('#myRatio').text();

   	if(elem.find("div").attr("id") != "ratio") return;
    
   	//ratio
   	var nowRatio;
   	var expResultList = document.getElementsByName("expResult");
   	
   	//현재 선택한 픽 적용
	var nowBet = expResultList[no-1].value;
	var chgBet = expResultList[no-1].value;
	var totalPickCnt = 1;
	
	if(chgBet.indexOf(select) < 0) {
		chgBet += select;
	} else {
		chgBet = replaceStr(chgBet, select, "");
	}
	expResultList[no-1].value = chgBet;
   	
	//현재 픽 갯수
   	for(var i=0;i<expResultList.length;i++) {
   		if(expResultList[i].value.length == 0) {
   			continue;
   		}
   		totalPickCnt  = expResultList[i].value.length * totalPickCnt;
	}
	
	//96개픽 제한
   	if(totalPickCnt > 96) {
   	expResultList[no-1].value = nowBet;
   	chgBet = nowBet;
   		alert("96가지 조합을 초과하여 픽을 선택할 수 없습니다.");
		return;
   	}
	   	
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

	$('#subGame_'+no).html("<div style='display:inline; color:red;font-size:13px;'>"+chgBet+"</div>");
	$('#totalPickCnt').html("<div style='display:inline; color:red;font-size:15px;'>"+totalPickCnt+"</div>");
}

function getResult(obj) {
	searchFrm.action = 'gameDetailList.do';
	searchFrm.submit();
}

function checkPick() {
	
	var expResultList = document.getElementsByName("expResult");
	
	for(var i=0;i<expResultList.length;i++) {
		if(isBlank(expResultList[i].value)) {
			alert((i+1)+"번째 픽이 선택되지 않았습니다.");
			return;
		}
	}
	
	if(isBlank(document.getElementById("gmPostTitle").value)) {
		alert("하단에 픽 제목을 입력해야 합니다.");
		document.getElementById("gmPostTitle").focus();
		return;
	}
	
	if(!isNull(document.getElementById("pubYnChk"))
			&& document.getElementById("pubYnChk").checked) {
		document.getElementById("pubYn").value = "1";
	}
	document.frm.submit();
}

function viewDetail(no) {
	
	//var dateObj = document.getElementById("mcDetail"+no);
	var dateObj = document.getElementById("mcDate"+no);
	var view = "none";
	
	if(dateObj.style.display == "none") {
		view = "block";
	}
	
	//document.getElementById("mcDetail"+no).style.display = view;
	
	document.getElementById("mcDate"+no).style.display = view;
//	document.getElementById("mcTeam"+no).style.display = view;
//	document.getElementById("mcLatest"+no).style.display = view;
//	document.getElementById("mcVersus"+no).style.display = view;
//	document.getElementById("mcAmaz"+no).style.display = view;
//	document.getElementById("mcNews"+no).style.display = view;
//	document.getElementById("mcResult"+no).style.display = view;
	
}


//복수 픽 선택
$(function() {
	location.href = "#match1";
});
