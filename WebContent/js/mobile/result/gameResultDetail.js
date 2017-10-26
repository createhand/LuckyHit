function writeReply() {
	
	if(isBlank(document.getElementById("replyContent").value)) {
		alert("댓글 내용을 입력해야 합니다.");
		return;
	}
	
	frm.action = '/replyRegistProc.do';
	frm.submit();
}