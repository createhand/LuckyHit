var TIMER;
	/*
$(document).ready(function() {
	
	$('#pop_handling').ajaxStart(function() {
		if (progress)
			show_handling();
	});
	$('#pop_handling').ajaxStop(function() {
		hidden_handling();
	});	

	// 프레임으로 접근하지 않은 경우 로그인페이지로 이동시킨다.
	if (top.frames['secure'] == undefined) {
		parentRedirect('/');
		return;
	}

	if ($('#sessId', top.frames['secure'].document).val() != '') {
		// 세션 타이머
		resetTimer();
		TIMER = window.setInterval('showTimer()', 1000);
	}
	
});
*/

/**
 * 세션 타임아웃을 리셋한다.
 */
function resetTimer() {
	/*
	var secureDoc = top.frames['secure'].document;
	$('#timer', secureDoc).val($('#interval', secureDoc).val());
	*/
}

/**
 * 세션 타임아웃에 대한 안내팝업을 출력한다. 
 */
function showTimer() {
	/*
	var guideTime = $('#guideTime', top.frames['secure'].document).val();
	var timer = $('#timer', top.frames['secure'].document);
	timer.val(timer.val() - 1);
	$('#session_timer').html(timer.val()); 
	if (timer.val() == guideTime) {
		$.ajax({
			url: CTX + '/71000001.html',
			data: 'guideTime='+guideTime,
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", "true");
			},
			success: function(response, status, xhr) {
				if (xhr.getResponseHeader('REQUIRES_AUTH') == '1') {
					redirect("/710003.html?stat=1");
				}
				else if (xhr.getResponseHeader('REQUIRES_AUTH') == '2') {
					redirect("/710003.html?stat=2");
				}
				else if (xhr.getResponseHeader('NOT_AFRS_TIME') == '1') {
					// 업무시간 종료
					redirect("/999991.html");
				}
				else {
					$('#pop_content').html(response);
					show_pop('pop', '', '664');
					
					$('#btnRenew').click(function() {
						Ajax.request({url: '/100000.html', success: function() {
							resetTimer();
							hidden_pop('pop');
						}});
					});
					
					$('#btnLgout').click(function() {
						redirect("/710003.html");
					});
				}
			}
		});
	}
	
	if (timer.val() <= 0) {
		redirect("/710003.html?stat=1");
	}
	*/
}

var progress = true;
Ajax = function() {
	return {
		request: function(param) {
			var type = 'post';
			if (param.type != undefined) {
				type = param.type;
			}
			var data = '';
			if (param.data != undefined) {
				data = param.data;
			}
			if (param.progress != undefined)
				progress = param.progress;
			else 
				progress = true;
			
			$.ajax({
				type: type,
				url: CTX + param.url,
				data: data,
				beforeSend: function(xhr) {
					xhr.setRequestHeader("AJAX", "true");
				},
				success: function(response, status, xhr) {
					resetTimer();
					if (xhr.getResponseHeader('REQUIRES_AUTH') == '1') {
						redirect("/710003.html?stat=1");
					}
					else if (xhr.getResponseHeader('REQUIRES_AUTH') == '2') {
						redirect("/710003.html?stat=2");
					}
					else if (xhr.getResponseHeader('NOT_AFRS_TIME') == '1') {
						// 업무시간 종료
						redirect("/999991.html");
					}
					else {
						if (param.success != undefined)
							param.success(response, status, xhr);
					}
				},
				error: function(xhr, statusText, error) {
					resetTimer();
					if (param.error != undefined)
						param.error(xhr, statusText, error);
				}
			});
		},
		json: function(param) {
			var type = 'post';
			if (param.type != undefined) {
				type = param.type;
			}
			var data = '';
			if (param.data != undefined) {
				data = param.data;
			}
			if (param.progress != undefined)
				progress = param.progress;
			else 
				progress = true;
			
			$.ajax({
				type: type,
				url: CTX + param.url,
				data: data,
				dataType: 'json',
				beforeSend: function(xhr) {
					xhr.setRequestHeader("AJAX", "true");
				},
				success: function(response, status, xhr) {
					resetTimer();
					if (xhr.getResponseHeader('REQUIRES_AUTH') == '1') {
						redirect("/710003.html?stat=1");
					}
					else if (xhr.getResponseHeader('REQUIRES_AUTH') == '2') {
						redirect("/710003.html?stat=2");
					}
					else if (xhr.getResponseHeader('NOT_AFRS_TIME') == '1') {
						// 업무시간 종료
						redirect("/999991.html");
					}
					else {
						if (param.success != undefined)
							param.success(response, status, xhr);
					}
				},
				error: function(xhr, statusText, error) {
					resetTimer();
					if (param.error != undefined)
						param.error(xhr, statusText, error);
				}
			});
		},
		requestForm: function(form, url, success, error) {
			$(form).ajaxForm({
				type: 'post',
				url: CTX + url,
				beforeSend: function(xhr) {
					xhr.setRequestHeader("AJAX", "true");
				},
				success: function(response, status, xhr) {
					resetTimer();
					if (success != undefined)
						success(response, status, xhr);
				},
				error: function(xhr, statusText, error) {
					resetTimer();
					if (error != undefined)
						error(xhr, statusText, error);
				}
			});
		}
	};
}();

EncAjax = function() {
	return {
		request: function(param) {
			var type = 'post';
			if (param.type != undefined) {
				type = param.type;
			}
			var encData = '';
			if (param.data != undefined) {
				encData = Crypt.enc(param.data);
				if (encData == "") {
					return;
				}
			}
			if (param.progress != undefined)
				progress = param.progress;
			else 
				progress = true;
			
			$.ajax({
				type: type,
				url: CTX + param.url,
				data: 'p='+encData,
				beforeSend: function(xhr) {
					xhr.setRequestHeader("AJAX", "true");
				},
				success: function(response, status, xhr) {
					resetTimer();
					if (xhr.getResponseHeader('REQUIRES_AUTH') == '1') {
						redirect("/710003.html?stat=1");
					}
					else if (xhr.getResponseHeader('REQUIRES_AUTH') == '2') {
						redirect("/710003.html?stat=2");
					}
					else if (xhr.getResponseHeader('NOT_AFRS_TIME') == '1') {
						// 업무시간 종료
						redirect("/999991.html");
					}
					else {
						if (param.success != undefined)
							param.success(response, status, xhr);
					}
				},
				error: function(xhr, statusText, error) {
					resetTimer();
					if (param.error != undefined)
						param.error(xhr, statusText, error);
				}
			});
		},
		json: function(param) {
			var type = 'post';
			if (param.type != undefined) {
				type = param.type;
			}
			var encData = '';
			if (param.data != undefined) {
				encData = Crypt.enc(param.data);
				if (encData == "") {
					return;
				}
			}
			if (param.progress != undefined)
				progress = param.progress;
			else 
				progress = true;
			
			$.ajax({
				type: type,
				url: CTX + param.url,
				data: 'p='+encData,
				dataType: 'json',
				beforeSend: function(xhr) {
					xhr.setRequestHeader("AJAX", "true");
				},
				success: function(response, status, xhr) {
					resetTimer();
					if (xhr.getResponseHeader('REQUIRES_AUTH') == '1') {
						redirect("/710003.html?stat=1");
					}
					else if (xhr.getResponseHeader('REQUIRES_AUTH') == '2') {
						redirect("/710003.html?stat=2");
					}
					else if (xhr.getResponseHeader('NOT_AFRS_TIME') == '1') {
						// 업무시간 종료
						redirect("/999991.html");
					}
					else {
						if (param.success != undefined)
							param.success(response, status, xhr);
					}
				},
				error: function(xhr, statusText, error) {
					resetTimer();
					if (param.error != undefined)
						param.error(xhr, statusText, error);
				}
			});
		}
	};
}();

Crypt = function() {
	return {
		enc: function(data) {
			var certId = $('#certId', top.frames['secure'].document).val();
			var encData = encryptDataString(certId, data);
			if (encData == '') {
				if (getErrorCode() == 'NO_KEY_ID') {
					alert(_001000_4);
				}
				else if (getErrorCode() == 'NO_DATA_VALUE') {
					alert(_001000_5);
				}
				else {
					alert('보안모듈이 정상적이지 않습니다.('+getErrorMessage()+')\n다시 로그인 하셔야 합니다.');
					redirect('/710003.html');
					return;
				}
			}
			return encData;
		},
		dec: function() {
			
		}
	};
}();

/**
 * 암호화된 데이터를 동적으로 Form을 생성하여 submit을 수행한다.
 * @param target
 * @param dataString
 */
function encSubmit(target, dataString) {
	if (progress)
		show_handling();

	// 데이타를 암호화한다
	var encData = Crypt.enc(dataString);
	
	// 동적으로 Form을 생성하여 submit을 수행한다.
	var form = $('<form id="__encFrm" method="post" action="'+CTX+target+'"></form>');
	var param = $('<input type="hidden" id="p" name="p" value="'+encData+'"/>');
	$(form).append(param);
	$('body').append(form);
	form.submit();
}

/**
 * 동적으로 Form을 생성하여 submit을 수행한다.
 * 
 * @param target
 * @param key
 * @param val
 */
function submit(target, key, val) {
	if (progress)
		show_handling();

	// 동적으로 Form을 생성하여 submit을 수행한다.
	var form = $('<form id="__frm" method="post" action="'+CTX+target+'"></form>');
	var param = $('<input type="hidden" id="'+key+'" name="'+key+'" value="'+val+'"/>');
	$(form).append(param);
	$('body').append(form);
	form.submit();
}

function check_encryptoData(strKeyID) {
//	var strServerKmCert = document.input_form.ServerKmCert.value;
	// 사용자 입력 값
	var strLoginPassword = '로그인 암호';
//	if (strServerKmCert == "") {
//		alert("암호화에 사용할 인증서가 없습니다.\n서버 인증서 경로를 확인하십시오.");
//	}
	var strEncryptedLoginPassword = encryptDataString(strKeyID,
			strLoginPassword);
	if (strEncryptedLoginPassword == "") {
		alert(getErrorFunctionName() + ": " + getErrorString());
		return;
	}
}


/**
 * Base64문자열을 디코딩한다. 
 * @param str Base64문자열
 * @returns 디코딩된 문자열
 */
function Base64Decode(str) {
	var temp = Base64.decode(str);
	return decodeURIComponent(temp.replace(/\+/g, '%20'));
}

/**
 * 어플리케이션 컨텍스트 ROOT를 포함한 페이지를 리턴한다.
 * @param page 페이지
 * @returns 컨텍스트를 포함한 페이지
 */
function getUrl(page) {
	return CTX + page;
}


/**
 * 어플리케이션 컨텍스트 ROOT를 포함한 페이지로 이동한다.
 * @param page 페이지
 */
function redirect(page) {
	if (progress)
		show_handling();
	location.href = CTX + page;
}

/**
 * 어플리케이션 컨텍스트 ROOT를 포함한 페이지로 이동한다.
 * @param page 페이지
 */
function parentRedirect(page) {
	parent.location.href = CTX + page;
}


/**
 * 숫자를 문자열로 변환하여 출력하기 위한 함수
 * @returns {String}
 */
Number.prototype.convertCharacter = function() {
	// phiDel (http://blog.foxb.kr)

	if (isNaN(this)) {
		return;
	}
	if ((__NUMBER_CHARACTER_SET__ == undefined)
			|| (__NUMBER_CHARACTER_POS__ == undefined)) {
		alert('__NUMBER_CHARACTER_SET__ and __NUMBER_CHARACTER_POS__ value is not set');
		return;
	}
	var n = String(Math.round(this)), c = '', re = '', cl = n.length, is_skip = true, as = __NUMBER_CHARACTER_SET__
			.split(','), ap = __NUMBER_CHARACTER_POS__.split(',');
	for ( var i = 0; i < cl; i++) {
		c = Number(n.substr(i, 1));
		if (c < 1 && (((cl - i - 1) % 4) || is_skip))
			continue;
		is_skip = ((cl - i - 1) % 4) ? false : true;
		re += (c > 0 ? as[c - 1] : '') + ap[cl - i - 1];
	}
	return re;
};


/**
 * 주어진 금액을 문자로 출력한다.
 * @param amt 금액
 * @param target 출력될 대상
 */
function showText(amt, target) {
	var obj = document.getElementById(target);
	if (amt.value == '') {
		amt.value = '';
		if (LANG != 'en')
			obj.innerHTML = '';
	} 
	else {
		var currVal = JslInput.toNumber(amt.value);
//		var num = parseInt(currVal, 10);
		var num = Number(currVal);
		if (num == 0) amt.value = '';
		else {
			amt.value = JslInput.toMoney(num);;
			if (LANG != 'en')
				obj.innerHTML = num.convertCharacter() + _WON;
		}
	}
}

/**
 * 금액포멧으로 출력한다.
 * @param amt 금액항목
 */
function toMoney(amt) {
	if (amt.value == '') {
		amt.value = '';
	} 
	else {
		var currVal = JslInput.toNumber(amt.value);
//		var num = parseInt(currVal, 10);
		var num = Number(currVal);
//		if (num == 0) amt.value = '';
//		else {
			amt.value = JslInput.toMoney(num);
//		}
	}
}

/**
 * 금액을 더한다.
 * 
 * @param id 현재금액항목
 * @param val 더할 금액 
 * @param target 출력항목
 */
function addAmount(id, val, target) {
	var obj = document.getElementById(id);
	var currVal = JslInput.toNumber(obj.value);
//	var amt = parseInt(currVal == '' ? '0': currVal, 10);
	var amt = Number(currVal == '' ? '0': currVal);
	obj.value = amt + val;
	showText(obj, target);
}

/**
 * 금액을 지운다.
 * @param id 금액항목
 * @param target 출력항목
 */
function delAmount(id, target) {
	var obj = document.getElementById(id);
	obj.value = '';
	showText(obj, target);
}


/**
 * 에러내용을 출력한다.
 * 
 * @param time 발생일자
 * @param code 에러코드
 * @param message 에러메시지
 */
function viewError(time, code, message) {
	var data = 'time='+time+'&code='+code+'&message='+ encodeURIComponent(message);
	EncAjax.request({url: '/12000007.html', 
		data: data,
		success: function(response) {
			$('#pop_content').html(response);
			show_pop('pop', '');
	}});
}

/**
 * 이체화면에서 사용되는 출금정보의 이벤트를 처리한다.
 * 
 * 1. 예약일자 관련 처리
 * 2. 출금계좌 관련 처리
 * 3. 잔액조회
 * 4. 이체한도조회
 */
function wdrInfoEvent() {
	// 예약일자 관련 초기화
	var minDate = new Date(SVR_TODAY);
	var maxDate = new Date();
	minDate.setDate(minDate.getDate() + 1);
	maxDate.setDate(minDate.getDate() + 183);

	$('#bkgTranDt').datepicker({minDate: minDate, maxDate: maxDate});
	$('#bkgTranDt').datepicker("setDate", minDate);

	// 이벤트 정의
	$('#aoneTran').click(function() {
		$('#bkgTranYn').val('N');
		$('#tranDtArea').hide();
	});
	
	// 예약이체를 선택했을때 예약일자입력영역이 활성화된다.
	$('#bkgTran').click(function() {
		$('#bkgTranYn').val('Y');
		$('#tranDtArea').show();
	});
	
	$('#bkgTranDt').focus(function() {
		$('#bkgTranDt').blur();
	});
	
	// 출금계좌 선택이 변경되면 내부계좌번호와 출력(신/구계좌)를 히든정보에 설정한다.
	$('#wdrAcct').change(function() {
		$('#acctPwd').val('');
		var wdrAcct = $('#wdrAcct').val();
		if (wdrAcct == '') {
			$('#wdrAcctNo').val('');
			$('#outWdrAcctNo').val('');
			return;
		}
		var wdrAcctArr = wdrAcct.split("|");
		$('#wdrAcctNo').val(wdrAcctArr[0]);
		$('#outWdrAcctNo').val(wdrAcctArr[1]);
	});
	
	// 잔액조회 버튼 클릭이벤트
	$('#btnBalInq').click(function() {
		var jForm = new JslJForm();
		jForm.add(new JslJSelect(_WDR_ACCT, $('#wdrAcct')));
		if (!jForm.validate())
			return;
		
		var wdrAcctNo = $('#wdrAcctNo').val();
		var params = 'wdrAcctNo='+wdrAcctNo;
		EncAjax.json({url:'/120011.html', 
			data: params,
			success: function(response, status, xhr) {
				if (response.code != '000000') {
					alert(response.message);
					return;
				}
				$('#pbkBal').html(response.params.pbkBal);
				$('#payCanBal').html(response.params.payCanBal);
				$('#time').html(response.params.time);
				
				$('#balArea').show();
		}});
	});
	
	// 이체가능한도조회 버튼 클릭이벤트
	$('#btnTranCanLmtInq').click(function() {
		var jForm = new JslJForm();
		jForm.add(new JslJSelect(_WDR_ACCT, $('#wdrAcct')));
		if (!jForm.validate())
			return;

		var wdrAcctNo = $('#wdrAcctNo').val();
		var outWdrAcctNo = $('#outWdrAcctNo').val();
		var params = 'wdrAcctNo='+wdrAcctNo+'&outWdrAcctNo='+outWdrAcctNo;
		EncAjax.request({url: '/12000001.html', 
			data: params,
			success: function(response) {
			$('#pop_content').html(response);
			show_pop('pop', '');
		}});
	});
}

/**
 * 자주쓰는 입금 계좌를 등록한다.
 * @param depBankCd 은행코드
 * @param depAcctNo 계좌번호
 */
function registDepAcct(depBankCd, depAcctNo) {
	var data = "depBankCd=" + depBankCd + "&depAcctNo=" + depAcctNo;
	EncAjax.request({url: '/12000006.html', 
		data: data,
		success: function(response) {
			$('#pop_content').html(response);
			$('#popFrm').find('#depBankCd').change(function() {
				$('#popFrm').find('#depAcctNo').removeAttr('maxlength');
				if ($('#popFrm').find('#depBankCd').val() == '000') {
					$('#popFrm').find('#depAcctNo').attr('maxlength', '17');
				}
				else {
					$('#popFrm').find('#depAcctNo').attr('maxlength', '16');
				}
			});
			$('#btnPopCfm').click(function() {
				var jForm = new JslJForm();
				jForm.add(new JslJSelect(_DEP_BANK, $('#popFrm').find('#depBankCd')));
				if ($('#popFrm').find('#depBankCd').val() == '000') {
					jForm.add(new JslJText(_DEP_ACCT_NO, $('#popFrm').find('#depAcctNo')));
				}
				else {
					jForm.add(new JslJNumber(_DEP_ACCT_NO, $('#popFrm').find('#depAcctNo')));
				}
				jForm.add(new JslJText(_ACCT_NCNM, $('#popFrm').find('#depAcctNcnm')));

				if (!jForm.validate()) return;
				
				EncAjax.json({url:'/12000006a.html', 
					data: $('#popFrm').serialize(),
					success: function(response, status, xhr) {
						if (response.code != '000000') {
							alert(response.message);
							return;
						}
						alert(_121101_1/*'등록되었습니다.'*/);
						hidden_pop('pop');
				}});
				
			});
			$('#btnPopCanc').click(function() {
				hidden_pop('pop');
			});
		}
	});
}


/**
 * 홈페이지 공지사항으로 이동한다.
 * 
 * @param lang 언어
 */
function goNoti(lang) {
	if (lang == 'ja') {
		top.location.href = '/jp310001.html';
	}
	else if (lang == 'en') {
		top.location.href = '/en310001.html';
	}
	else {
		top.location.href = '/ko310001.html';
	}
}

/**
 * 홈페이지 FAQ로 이동한다.
 * 
 * @param lang 언어
 */
function goFaq(lang) {
	if (lang == 'ja') {
		top.location.href = '/jp330001.html';
	}
	else if (lang == 'en') {
		top.location.href = '/en330001.html';
	}
	else {
		top.location.href = '/ko330001.html';
	}
}


function validationOtp(gbn) {
	var jForm = new JslJForm();
	
	jForm.add(new JslJNumber(_OTP_VALUE, $('#otpValue')).limitLength(6, 6));
	if($('#pwdYn').val() == 'Y') 
		jForm.add(new JslJText(_ACCT_NO_PWD, $('#acctNoPwd')));
	return jForm.validate();
}

function generateSign(message) {

	if (message == "" || message == null) {
		alert(_001000_1);
		return;
	}
	
	var strCertID = $('#certId', top.frames['secure'].document).val();
	
	clearCertificateInfo(strCertID);
	var bReturn = reselectCertificate( strCertID ); // 인증서 선택창  ( 최초한 선택한 인증서의 정책에 해당되는 인증서만 보여준다.)
	if ( !bReturn ) {
		clearCertificateInfo(strCertID);
		return;
	}

	var strUserSignCert = getUserSignCert(strCertID);
	if (strUserSignCert == "") {
		var strError = getErrorString();
		if (strError == "") {
			alert(_001000_2);
		} else {
			alert(strError);
		}
		clearCertificateInfo(strCertID);
		return;
	} 
	
	var strUserSignValue = generateDigitalSignature(strCertID,message);
	if (strUserSignValue == "") {
		var strError = getErrorString();
		if (strError == "") {
			alert(_001000_2);
		} else {
			alert(strError);
		}
		clearCertificateInfo(strCertID);
		return;
	} 
	
	var strUserSignCert = getUserSignCert(strCertID);
	if (strUserSignCert == "") {
		clearCertificateInfo(strCertID);
		return;
	} 
	
	var dn = getCertSubjectDN(strUserSignCert);
	var certDn = $('#certDn', top.frames['secure'].document).val();

	if (dn != certDn) {
		alert(_001000_3/*"선택된 인증서가  로그인인증서와 다릅니다! "*/);
		clearCertificateInfo(strCertID);
		return;
	}

	var dataString = 'scrnNo='+$('#_SCREEN_ID_').val()+'&userCert='+strUserSignCert+'&certVal='+strUserSignValue+'&encCertCont='+message;
	if ($('#certProcDs') != undefined) {
		dataString += '&certProcDs='+$('#certProcDs').val();
	}
	EncAjax.request({
		url: '/100021.html',
		data: dataString,
		progress:true,
		success: function(data) {
			if(data.code == "000000") {
				successOtpCheck();
			}else{
				alert(data.message);
			}
		}
	});
}

function  showOtp(obj){
	$("#otpValue").val('');
	show_pop('pop_otp',obj.id);
		
}

function doOtpCheck() {
    if (typeof isMykeydefence!="undefined" && isMykeydefence) aos_copy_to_form( document.otp_frm );  // MKD added by Kang Seog 2007.06.27
    
	var dataString = $('#otp_frm').serialize() + '&_screen_id_='+$('#_SCREEN_ID_').val();
	
	if(!validationOtp()) return;
	
	hidden_pop('pop_otp');
	EncAjax.request({
		url: '/100012.html',
		data: dataString,
		progress:true,
		success: function(data) {
			if(data.code == "000000") {
				generateSign($('#encCertCont').val());
			}else{
				alert(data.message);
				if(data.code == "990102") {
					redirect("/185001.html");
				}
			}
		}
	});
}

/**
 * 비밀번호 변경 체크 
 * @param userId
 * @param passwd
 * @param passwdRe
 * @returns {Boolean}
 */
function CheckPassWord(userId, passwd, passwdRe)
{
    if(passwd.value != passwdRe.value)
    {
    	if(passwd.value.length == 0) {
    		alert(_182001_5_);
            passwd.focus();
            return false;
    	}
    	
    	if(passwdRe.value.length == 0) {
    		alert(_182001_6_);
            passwd.focus();
            return false;
    	}
    	
        alert(_172004_5_);
        passwd.focus();
        return false;
    }
    if(passwd.value.length < 4 || passwd.value.length > 10)
    {
        alert(_182001_1_);
        passwd.focus();
        return false;
    }
    if(!passwd.value.match(/([0-9])/) || !passwd.value.match(/([a-zA-Z])/))
    {
        alert(_182001_1_);
        passwd.focus();
        return false;
    }
    
    if(userId != undefined && userId.value != '') {
	    if(passwd.value.toUpperCase().indexOf(userId.value.toUpperCase()) > -1)
	    {
	        alert(_182001_2_);
	        passwd.focus();
	        return false;
	    }
    }
    
    var SamePass_0 = 0; //동일문자 카운트
    var SamePass_1 = 0; //연속성(+) 카운드
    var SamePass_2 = 0; //연속성(-) 카운드

    
    var chr_pass_0;
    var chr_pass_1;
    var chr_pass_2;

    
    for(var i=0; i < passwd.value.length; i++)
    {
        chr_pass_0 = passwd.value.charAt(i);
        chr_pass_1 = passwd.value.charAt(i+1);
        
        //동일문자 카운트
        if(chr_pass_0 == chr_pass_1)
        {
            SamePass_0++;
        } else {
        	SamePass_0 = 0;
        }
        
        if(SamePass_0 > 1)
        {
            alert(_182001_3_);
            return false;
        }
        
       
        chr_pass_2 = passwd.value.charAt(i+2);
        //연속성(+) 카운드
        if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == 1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == 1)
        {
            SamePass_1 = SamePass_1 + 1;
        }
        
        //연속성(-) 카운드
        if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == -1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == -1)
        {
            SamePass_2 = SamePass_2 + 1;
        }
        
        if(SamePass_1 > 0 || SamePass_2 > 0)
        {
            alert(_182001_4_);
            return false;
        }        
    }
    return true;
 }

/**
 * enter key 무시하는 함수
 * @param e
 * @returns {Boolean}
 */
function disableEnter(e) {
	var key;
	if (window.event) {
		key = window.event.keyCode;
	}
	else {
		key = e.which;
	}
	if (key == 13) {
		return false;
	}
	else {
		return true;
	}
}