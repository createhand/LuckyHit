function getResult(obj) {
	
	var gmInfo = obj.value.split("@");
	document.getElementById("gmCd").value = gmInfo[0];
	document.getElementById("gmPostNo").value = gmInfo[1];
	
	frm.action = '/gameResult.do';
	frm.submit();
}