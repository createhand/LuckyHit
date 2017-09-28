function goView(param) {
	var frm = document.frm;
	frm.view.value = param;
	frm.latest.value = 'N';
	frm.action = 'gamePickList.do';
	frm.submit();
}

function goDetail(tmCd, tmCdA) {
	var frm = document.frm;
	frm.tmCd.value = tmCd;
	frm.tmCdA.value = tmCdA;
	frm.action = 'gamePickDetail.do';
	frm.submit();
}