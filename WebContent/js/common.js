function isNull(obj) {
	if(obj == null || obj == undefined || obj == "null" || obj == "undefined") {
		return true;
	} else {
		return false;
	} 
}

function isBlank(obj) {
	if(obj == null || obj == undefined || obj == "null" || obj == "undefined" || obj == "") {
		return true;
	} else {
		return false;
	} 
}

function returnBlank(obj) {
	if(obj == null || obj == undefined || obj == "null" || obj == "undefined" || obj == "") {
		return "";
	} else {
		return obj;
	} 
}

function replaceStr(src, des, str) {
	//src : original string
	//des : target string
	//str : substitution string
	
	var re = new RegExp(des,"g");
	return src.replace(re, str);
}