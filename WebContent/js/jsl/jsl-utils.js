/*****************************************************************************
 * 파일명 : ta-utils.js
 * 작성일 : 2009. 02. 06
 * 작성자 : 한향덕
 * 설   명 : 공통적으로 쓰이는 스크립트 함수
 *****************************************************************************/
var jsl = {};

jsl.Utils = function() {
	
	var formId = "";    // 폼 아이디를 저장하는곳
 	var targetId = "";  // 타겟 아이디를 저장하는곳
 	
	// RowIndex의 자동 재설정을 보여주기 위해 생성시마다 1씩 증가하는 변수.
	var liRowIdx = 0;
	/**
     * toMoney 함수에서 내부적으로 사용
     * 입력 변수에 3 자리마다 콤마(,)를 붙여 반환한다.
     * @param field 콤마를 붙일 값
     */
    var formatCommas = function(numString) {
        var re = /,|\s+/g;
        numString = numString.replace(re, "");
        re = /(-?\d+)(\d{3})/;
        while (re.test(numString)) {
            numString = numString.replace(re, "$1,$2");
        }
        return numString;
    };

    /**
     * toKoreanFromMoney 에서 내부적으로 사용
     * 콤마(,)를 제거하여 반환한다.
     * @param {Object} numString 콤마를 제거할 값
     */
    var stripCommas = function(numString) {
        var re = /,/g;
        return numString.replace(re, "");
    };
    
 return {
	 
	 /**
	  **************************************************************************************
      *********************************** html 대한 function 모음 ***************************
      **************************************************************************************
      */
	 html : {
	 	/**
	     * <div> 태그의 내용 중 특정 텍스트(before)를 특정 텍스트(after)로 변경한다.
	     * @param innerHTML 값
	     * @param before 변경 전 텍스트 
	     * @param after 변경 후 텍스트
	     * @return 변경된 값
	     */
	  	replaceTextInDivValue : function(valueText, before, after) {
	        var re = new RegExp(before, "g");
	        valueText = valueText.replace(re, after);
	        return valueText;
	  	},	 
	    /**
	     * <div> 태그의 내용 중 특정 텍스트(before)를 특정 텍스트(after)로 변경한다.
	     * @param id <div> 태그의 id 속성 값
	     * @param before 변경 전 텍스트 
	     * @param after 변경 후 텍스트
	     */
     	replaceTextInDiv : function(id, before, after) {
	       var element = document.getElementById(id).firstChild;
           var re = new RegExp(before, "g");
           element.nodeValue = element.nodeValue.replace(re, after);
           return false;
     	},
     	
     	/**
	     * 특정 노드가 가지고 있는 모든 속성을 TEXTAREA에 출력한다. 개발 편의를 위해서
	     * 제공되는 함수이다.
	     * @param obj 속성 값을 알고자 하는 노드
	     */
	    getListProperties : function(obj) {
	       var objName;
	
	       if (obj.nodeName) {
	           objName = obj.nodeName;
	       } else {
	           objName = "navigator";
	       }
	
	       var result = "";
	       for (var i in obj) {
	           result += objName + "." + i + "=" + obj[i] + "\n";
	       }
	
	       var area = document.createElement("textarea");
	       area.rows = 20;
	       area.cols = 50;
	       var body = document.getElementsByTagName("BODY");
	       if (body) {
	           body[0].appendChild(area);
	       } else {
	           alert("body 태그가 있어야 합니다.");
	           return false;
	       }
	       area.value = result;
	       return false;
	    },

	    /**
	     * DIV 를 숨긴다.
	     * @param {String} divId
	     */
	    hiddenDiv : function(divId) {
	       var objName;
           var divObj = $(divId);
           divObj.style.visibility = 'hidden';
           divObj.style.display = 'none';
           jsl.Utils.selectbox.selectBoxAllVisible(divId);
	    },

	    /**
	     * DIV 를 보여준다.
	     * @param {String} divId
	     */
	    showDiv: function(/*String*/divId){
	       var divObj = $(divId);
	       divObj.style.visibility = 'visible';
	       divObj.style.display = 'block';
	       jsl.Utils.selectbox.selectBoxHidden(divId);
	    }
	    
 	},		// [html] end
     /**
      **************************************************************************************
	  *********************************** html 대한 function 모음 끝 *************************
	  **************************************************************************************
	  */

	 /**
	  **************************************************************************************
	  *********************************** form :  대한 function 모음 *************************
	  **************************************************************************************
	  */
	form : {
 		/**
 		 * Form을 생성하여 리턴합니다.
 		 * @param {Array} data 히든필드에 저장할 데이터 [{key: value}]
 		 * @return {Object} 히든필드 정보가 들어있는 form Object
 		 */
 		createForm: function(data){
 			var form = document.createElement('form');
 			form.id = 'newForm';
 			form.method = 'post';
 			
 			for (var i = 0; i < data.length; i++){
 				var input = document.createElement('input');
 				
 				input.type = 'hidden';
 				input.id = data[i].id;
 				input.name = data[i].id;
 				input.value = data[i].value;
 				
 				form.appendChild(input); 
 			}
 			
 			return form;
 		}, 		
		/**
		 * 히든폼을 생성하여 지정한 Form에 추가합니다.
		 * @param {Object} formObj 폼 오브젝트
		 * @param {Object} fieldId 필드 아이디
		 * @param {Object} value 값
		 * @param {boolean} isArray 배열 허용여부
		 */
		createHiddenField: function(formObj, fieldId, value, isArray){
			var fieldObj = document.createElement('input');
			
			if(isArray != true){
				JslForm.removeHiddenField(formObj, fieldId);
			}
			
			fieldObj.type = 'hidden';
			fieldObj.id = fieldId;
			fieldObj.name = fieldId;
			fieldObj.value = value;
			
			formObj.appendChild(fieldObj);
		},
	
	    /**
	    * 지정한 폼 내에 히든필드를 제거합니다.
	    * @param {Object} formObj 폼 오브젝트
	    * @param {String} fieldId 삭제할 히든필드의 ID
	    */
	    removeHiddenField: function(formObj, fieldId){
			if(formObj.childNodes.length != null && formObj.childNodes.length != undefined){
		        for (var idx = 0; idx < formObj.childNodes.length; idx++){
		            if (formObj.childNodes[idx].id == fieldId) {
		                formObj.removeChild(formObj.childNodes[idx]);
		            }
		        }
			}
	    },
	    /**
	     * input 값을 json 타입으로 변환.
	     * @param {Object} formObj 폼 오브젝트
	     * 
	     * @author jk 
	     */
	    toJson: function(formObj){
	    	var params = new Array();
	    	if(formObj.elements.length != null && formObj.elements.length != undefined){
	    		for (var i = 0; i < formObj.elements.length; i++){
	    			var obj = formObj.elements[i];
	    			if (obj.type == "text" ||  obj.type == "hidden" ||  obj.type == "select-one") {
	    				if(obj.value != ''){
		    				obj.value = JslUtil.replaceAll(obj.value,".","");
		    				params[i] = {name : obj.id, value : obj.value};
	    				}
	    			}
	    		}
	    	}
	    	return params;
	    }	    
	},

    /**
     **************************************************************************************
	  *********************************** form 대한 function 모음 끝 *************************
	  **************************************************************************************
	  */
	
	
	
 	 /**
 	  **************************************************************************************
	  *********************************** input 대한 function 모음 **************************
	  **************************************************************************************
	  */
 	 input : {

 		/**
	     * 텍스트 필드에 입력한 값에 3자리마다 콤마(,)를 붙인다.
	     * 텍스트 필드에 아래를 기입한다. onkeyup="html.toMoney(this)"
	     * @param field 텍스트 필드
	     */
	    onToMoney : function(field) {
	       var value = field.value;
	       var indexOfPoint = value.indexOf(".");
	       
	       if (indexOfPoint == -1) {
	          field.value = formatCommas(value);
	       } else {
	          field.value = formatCommas(value.substring(0, indexOfPoint)) +
	                        value.substring(indexOfPoint, value.length);
	       }
	    },
 	
        /**
         * 콤마 붙은 금액을 숫자로 반환(콤마(,)를 제거하여 반환)
         * @param {Object} numString 콤마를 제거할 값
         */
        toNumber : function( numStr ){
           return stripCommas(numStr);
        },

        /**
         * 금액 표기 형식(콤마를 붙인다.) 
         * 입력 변수에 3 자리마다 콤마(,)를 붙여 반환한다.
         * @param field 콤마를 붙일 값
         * @param numStr
         */
        toMoney : function(numStr){
        	// String 값으로 넘기지 않을경우를 생각해서 변환해줌
           return formatCommas((numStr+""));
        },
		
        /**
         * 입력값이 일정한 length 가 되면 focus를 이동 시킵니다.
         * ex : <input type="text" ..... onkeyup="input.onAutoShift(this, 'nextElId', 3);">
         * @param {Object} fromObj this
         * @param {String} toFld   포커스 이동 시킬 element id
         * @param {int} satisfyFldLen 포커스 이동 조건을 만족시킬 입력 길이
         */
        onAutoShift : function(fromObj, toFld, satisfyFldLen){
           if(fromObj.value.length == satisfyFldLen){
              document.getElementById(toFld).focus();
              document.getElementById(toFld).select();
           }
        },
        
		/**
		 * 입력값을 마스킹한다. 
		 * ex) input.maskInputBox(str.value, 3, '*');
		 * @param {String} inputValue 원래 문자열
		 * @param {Number} orginCharlength 원래대로 보여줄 문자수
		 * @param {String} maskingChar masking할 character
		 * @return {String} 마스킹한 문자열
		 */
		maskInputBox : function(inputValue, orginCharlength, maskingChar){
		   var tempValue = inputValue;
		   var strtmp = "";
		   if (tempValue) {
		      if (tempValue.length > orginCharlength) {
			     strtmp = tempValue.substr(0, orginCharlength);
			     for (var i = orginCharlength; i < tempValue.length; i++) {
				    strtmp += maskingChar;
			     }
		      }
		   }
		   return 	strtmp;
        },
		
		/**
		 * 휴대번호를 split 하여 target Object에 할당한다. 
		 * input.splitMobilePhoneNo('01012341234',targetObj1,targetObj2,targetObj3)
		 * @param {String} sourceValue split 할 소스
		 * @param {Object} targetObj1 국번 Object
		 * @param {Object} targetObj2 중간번호 Object
		 * @param {Object} targetObj3 마지막번호 Object
		 */
		splitMobilePhoneNo : function(sourceValue,targetObj1,targetObj2,targetObj3) {
		   var fullChar = sourceValue;
		   if (null!=fullChar & fullChar !='') {
		      var firstStr = '';
		      var secodeStr = '';
		      var thirdStr = '';
		      var pos = fullChar.indexOf("-");
		      // 구분자가 있는 경우 
		      var count = 0;					
		      while ( pos != -1 ) {
		         count++;
		         pos = fullChar.indexOf("-", pos+1);
		      }
		      // 010-1234-1234 => 이러한 경우					
		      if (count == 2) {
			     var secondInx = fullChar.indexOf('-');
			     firstStr = fullChar.substring(0,secondInx);
			     secodeStr = fullChar.substring(secondInx+1,fullChar.lastIndexOf('-'));
			     thirdStr = fullChar.substring(fullChar.lastIndexOf('-')+1,fullChar.length);
			     targetObj1.value = firstStr;
			     targetObj2.value = secodeStr;
			     targetObj3.value = thirdStr;
		      }
		      // 010-12341234 => 이러한 경우         TODO 0101234-1234  
		      else if (count == 1){
		    	 var secondInx = fullChar.indexOf('-');
		    	 firstStr = fullChar.substring(0,secondInx);
		    	 if (null != firstStr && firstStr.length == 3) {
		    	    var tmpStr = fullChar.substring(secondInx + 1, fullChar.length);
					if (tmpStr.length == 8) {
					   secodeStr = tmpStr.substring(0,4);
					   thirdStr = tmpStr.substring(4,8);
					} else if(tmpStr.length == 7) {
					   secodeStr = tmpStr.substring(0,3);
					   thirdStr = tmpStr.substring(3,7);
					}
		    	 }
		    	 targetObj1.value = firstStr;
		    	 targetObj2.value = secodeStr;
		    	 targetObj3.value = thirdStr;						
		      }
		      // 01012341234 ==> 이러한 경우 
		      else {
		    	 firstStr = fullChar.substring(0,3);
				 var tmpStr = fullChar.substring(3, fullChar.length);
			 	 if (tmpStr.length == 8) {
			 	    secodeStr = tmpStr.substring(0,4);
			 	    thirdStr = tmpStr.substring(4,8);
			 	 } else if(tmpStr.length == 7) {
				    secodeStr = tmpStr.substring(0,3);
					thirdStr = tmpStr.substring(3,7);
				}
				targetObj1.value = firstStr;
				targetObj2.value = secodeStr;
				targetObj3.value = thirdStr;							
		      }
		   }
		},
		
		/**
 		 * 입력받을 수 있는 값을 필터링한다.
         * ex : <input type="text" ..... onkeypress="filterInputData('[0-9]', event)"> ; 숫자만 키입력이 가능한 text filed
         * ex : <input type="text" ..... onkeypress="filterInputData('[0-9a-zA-Z]', event)"> ; 영문,숫자만 키입력이 가능한 text filed
         * @param filter : 필터링할 정규표현식 ex) '[0-9]':0~9의 값만 허용, '[a-zA-Z]':알파벳만 허용
         * @return
         */
        onFilterInputData : function(filter, e) { 
            if (filter) {
                var evt = e || window.event;
                var kCode = evt.which || evt.keyCode;

                /* backspace ,tab(9),enter(13),shift(16),end(35),home(36),방향키(좌(37),상(38),우(49),하(40)),delete(46) 등등..*/
                var controlKeys = new Array(Event.KEY_BACKSPACE,Event.KEY_TAB,Event.KEY_RETURN,Event.KEY_ESC,Event.KEY_ESC,
                        Event.KEY_LEFT,Event.KEY_UP,Event.KEY_RIGHT,Event.KEY_DOWN,Event.KEY_DELETE,Event.KEY_HOME,
                        Event.KEY_END,Event.KEY_PAGEUP,Event.KEY_PAGEDOWN);

                /* 조작키이면 종료 */
                for (var i=0; i<controlKeys.length; i++) {
                    if (controlKeys[i] == kCode) return;
                }
                
                var sKey = String.fromCharCode(kCode);
                // 한글 체크
				/*
                var re = new RegExp('[ㄱ-ㅎ|ㅏ-ㅣ|가-힝]');
                if (!re.test(sKey)) {
                    Event.stop(evt);
                }
                */
            }
        },
        /*
         * 숫자키만 입력 받을 수 있도록 한다.
         */
        onlyNumberInput : function (ev) {
        	var code = ev.keyCode;

        	if ((code > 34 && code < 41) || (code > 47 && code < 58)
        			|| (code > 95 && code < 106) || code == 8 || code == 9
        			|| code == 13 || code == 46) {
        		window.event.returnValue = true;
        		return;
        	}
        	window.event.returnValue = false;
        },
        /**
         * 입력값의 byte 길이를 검사한다.
         * ex : <input type="text" ..... onkeyup="input.chkInputByte(this, 16);">
         * @param {Object} fromObj this
         * @param {int} maxByte 최대 byte 길이.
         */
        chkInputByte : function(inputObj, maxByte) {
            var i;
            var ch;
            var byteSize = 0;   // 바이트크기 
            var fullStr = inputObj.value.toString();
            var fullStrLength = fullStr.length;
            for (i=0;i<fullStrLength;i++) {
                ch = escape(fullStr.charAt(i)); //ISO-Latin-1 문자셋으로 변경
                
                byteSize += JslInput.strCharByte(ch);
                
                if(byteSize > maxByte){ // 최대 byte 길이 초과시 break!
//                    alert("입력문자 길이초과","입력값이 허용된 문자열의 최대값을 초과했습니다. 초과된 내용은 자동으로 삭제 됩니다.");
                    
                    inputObj.blur();    // 한글 입력시 기존 입력된 문자열이 사라지는 문제로 blur 처리한다.
                    inputObj.value = fullStr.substring(0, i);
                    inputObj.focus();   // blur 처리를 다시 focus 처리하여 재입력시 입력 오류 방지.
                    break;
                }
            }
        },
        
        strCharByte : function(chStr) {
            if (chStr.substring(0, 2) == '%u') {
                if (chStr.substring(2,4) == '00')
                    return 1;
                    else
                    return 2;        //한글
                } else if (chStr.substring(0,1) == '%') {
                if (parseInt(chStr.substring(1,3), 16) > 127)
                    return 2;        //한글
                else
                    return 1;
                } else {
                    return 1;
            }
        }
 	 },		// [input] end
 	 /**
 	  **************************************************************************************
	  *********************************** input 대한 function 모음 끝 ************************
	  **************************************************************************************
	  */

 	 /**
 	  **************************************************************************************
	  *********************************** checkbox 대한 function 모음 ***********************
	  **************************************************************************************
	  */
 	 checkbox : {
        /**
         * 지정한 그룹의 체크박스중 한개 이상이 Checked 되어 있는지 확인합니다.
         * @param {String} chkListName 체크박스 Name(ID아님)
         * @return {Boolean} 체크박스 선택유무
         */
        isGroupChecked: function(/*string*/chkListName){
           var chkList = document.getElementsByName(chkListName);
           var isChecked =  false;
           
           var checkboxLength = chkList.length;
//           alert(checkboxLength);
           
           if (checkboxLength == undefined) {
              if (checkBoxObj.checked) {
            	  isChecked = true;
              }
           }else{           
	           for (var idx = 0; idx < chkList.length; idx++){
	              if (chkList[idx].checked){
	                 isChecked = true;
	              }
	           }
           }
           return isChecked;
          },
			
		/**
         * 지정한 그룹의 체크박스중 한개만 Checked 되어 있는지 확인합니다.
         * @param {String} chkListName 체크박스 Name(ID아님)
         * @return {String} 선택된 내용의 value 값
         * @author Geumjo Park
         */
		setSingleChecked: function(chkListName){
		   var chkList = document.getElementsByName(chkListName);
		   var checkCnt = 0;
		   var checkValue = "";
		      for (var idx = 0; idx < chkList.length; idx++){
		   	     if (chkList[idx].checked){
				    checkCnt++;
				    checkValue = chkList[idx].value;
		   	     }
		      }
		      if (checkCnt == 0) {
		         // 메세지 창 호출
		    	 alert("체크박스 한건을 선택해주세요.");
//					kbl.extJS.messageBox.infoMsgBox({
//						isConfirm: false,
//						title: '체크박스 선택',
//						message: '체크박스 한건을 선택해주세요.',
//						callback: function(e){
//							if (e == true) { // 확인버튼 클릭시
//							}
//							else { // 창 닫기 또는 취소 버튼 클릭시
//							}
//						}
//					});
					return null;
		      }
		      if(checkCnt > 1){ // 2건이상
					// 메세지 창 호출
		   	     alert("체크박스 선택은 한건만 가능합니다.");
//					kbl.extJS.messageBox.infoMsgBox({
//						isConfirm: false,
//						title: '체크박스 선택',
//						message: '체크박스 선택은 한건만 가능합니다.',
//						callback: function(e){
//							if (e == true){	// 확인버튼 클릭시
//			
//							} else {	// 창 닫기 또는 취소 버튼 클릭시
//			
//							}
//						}
//					});
		   	     return null;
		      }
		      return checkValue;
        },

        /**
         * 체크박스 일괄 체크 토글기능
         * @param {String} toggle 기능을 수행 하는 checkbox id (NAME 아님)
         * @param {String} chkListName 체크박스 Name (ID아님)
         */
        setChkboxAllChecked: function(toggleCheckId, chkListName){
		   var isCheck = document.getElementById(toggleCheckId).checked;
		   if (chkListName == undefined) return;
           var chkAccountList = document.getElementsByName(chkListName);
           for (var idx = 0; idx < chkAccountList.length; idx++) {
              chkAccountList[idx].checked = isCheck;
           }
        },
       
		/**
         * 선택된 checkbox의 값을 배열로 리턴한다. 
         * 만약 value 가 없거만 선택 되지 않았으면 빈 Array 를 return 합니다.
         * @param checkBoxObj {Object} checkbox
         * @param formObj {Object} form
         * @param isAddHidden {boolean} input hidden 으로 생성 여부(true/false)
         * @param fieldId {String} input hidden id
         */
        getCheckedValue : function (checkBoxObj, formObj, isAddHidden, fieldId) {
           var returnArray = new Array();	
           if(!checkBoxObj){
              return new Array();
           }
           
           if(isAddHidden){
        	   JslForm.removeHiddenField(formObj, fieldId);
           }
           
           var checkboxLength = checkBoxObj.length;
           
           if (checkboxLength == undefined) {
              if (checkBoxObj.checked) {
                 returnArray.push(checkBoxObj.value);
                 if(isAddHidden){
                	 JslForm.createHiddenField(formObj,fieldId,checkBoxObj.value,false);
                 }
              }
           }
           else {
              for (var i = 0; i < checkboxLength; i++) {
			     if (checkBoxObj[i].checked) {
				    returnArray.push(checkBoxObj[i].value);
	                 if(isAddHidden){
	                	 JslForm.createHiddenField(formObj,fieldId,checkBoxObj[i].value,true);
	                 }
			     }
              }
           }	
           return returnArray;
        },
        /**
         * 선택된 checkbox의 값을 input hidden 으로 생성하고 배열로 리턴한다. 
         * 만약 value 가 없거만 선택 되지 않았으면 빈 Array 를 return 합니다.
         * @param checkBoxObj {Object} checkbox
         */
        getCheckedValueAddHidden : function (formObj, checkBoxObj, fieldId) {
        	return this.getCheckedValue(checkBoxObj,formObj,true,fieldId);
        }
 	 },		// [checkbox] end
 	 /**
 	  **************************************************************************************
	  *********************************** checkbox 대한 function 모음 끝 *********************
	  **************************************************************************************
	  */
 	 /**
 	  **************************************************************************************
	  *********************************** selectbox 대한 function 모음 **********************
	  **************************************************************************************
	  */
 	 selectbox : {
        /**
         * 이메일주소 도메인 직접입력 선택시 입력필드 활성화
         * @param {Object} selectObj selectBox Object
         * @param {String} fieldId InputField ID
         */
        setEmailToggle: function(selectObj, fieldId){
 		   // 직접입력 Value값
           var toggleMsg = "18";
           var fieldObj = document.getElementById(fieldId);
           if (selectObj.value == toggleMsg){
        	  fieldObj.value = '';
              fieldObj.style.display = 'inline';
              fieldObj.focus();
           } else {
       	      fieldObj.style.display = 'none';
           }
 	    },

        /**
         * TODO IE 6 에서 테스트 해야함.
         * 레이어 밑에 컨트롤을 숨긴다.
         * @param {Object} layer_id
         */
        selectBoxHidden : function(layer_id)  {
           var ly = $(layer_id);

           // 레이어 좌표
           var ly_left  = ly.offsetLeft;
           var ly_top    = ly.offsetTop;
           var ly_right  = ly.offsetLeft + ly.offsetWidth;
           var ly_bottom = ly.offsetTop + ly.offsetHeight;

           // 셀렉트박스의 좌표
           var el;

           for (var i=0; i<document.forms.length; i++) {
              for (var k=0; k<document.forms[i].length; k++) {
                 el = document.forms[i].elements[k];
                 if (el.type == "select-one") {
                    var el_left = el_top = 0;
                    var obj = el;
                    if (obj.offsetParent) {
                       while (obj.offsetParent) {
                          el_left += obj.offsetLeft;
                          el_top  += obj.offsetTop;
                          obj = obj.offsetParent;
                       }
                    }
                    el_left  += el.clientLeft;
                    el_top    += el.clientTop;
                    el_right  = el_left + el.clientWidth;
                    el_bottom = el_top + el.clientHeight;

                    // 좌표를 따져 레이어가 셀렉트 박스를 침범했으면 셀렉트 박스를 hidden 시킴
                    if ( (el_left >= ly_left && el_top >= ly_top && el_left <= ly_right && el_top <= ly_bottom) ||
              		(el_right >= ly_left && el_right <= ly_right && el_top >= ly_top && el_top <= ly_bottom) ||
                    (el_left >= ly_left && el_bottom >= ly_top && el_right <= ly_right && el_bottom <= ly_bottom) ||
                    (el_left >= ly_left && el_left <= ly_right && el_bottom >= ly_top && el_bottom <= ly_bottom) )
                   	   el.style.visibility = 'hidden';
                 }
              }
           }
 	    },

        /**
         * Layer로 인해 감추었던 셀렉트 박스를 모두 보이게 함
         */
        selectBoxAllVisible : function () {
           for (var i=0; i<document.forms.length; i++) {
              for (var k=0; k<document.forms[i].length; k++) {
                 el = document.forms[i].elements[k];
                 if (el.type == "select-one" && el.style.visibility == 'hidden' && el.style.display=='none')
                    el.style.visibility = 'visible';
                 	el.style.display="block";
              }
           }
 	    },
 	   /**
         * 년도생성 셀렉트 박스 자동생성 ( 현재 년도 +10 -10 21개의 목록을 보여줌)
         * @param tgId 셀렉트 박스를 생성할 아이디
         */
        createYearSelect : function (tgId) {
 	    	
           // 오늘 년도
 	    	var todayNow = new Date();
            var year = todayNow.getFullYear();
 	    	
            var first = parseInt(year) -10;
 	    	//var last = parseInt(year) +10;
 	    	var midCombo = $(tgId);
			midCombo.length = 22;
			//midCombo.options[0] = new Option("선택", "");
			for(var i=0;i<midCombo.length;i++) {
				midCombo.options[i] = new Option(first, first);
				first++;
			}
			midCombo.options[10].selected = true;
 	    },
 	    

	 	 /**
         * 년도 selectBox를 생성한다.
         * 조회시 년도가 필요할 경우 이 함수를 사용한다. 
         * @param {String} fmId	// 폼 아이디
         * @param {String} tgId	// 타겟 selectBox
         * @param {String} select	// check될 option
         */
 	    setYearSelect: function(fmId,tgId,select){
 	    	formId = "";
 	    	targetId = "";
 	    	selected = "";
 	    	
 	    	formId = fmId;
 	    	targetId = tgId;
 	    	selected = select;
 	    	
 	    	var selectBox = $(targetId);
 	    	selectBox.length = 10;
 	    	selectBox.options[0] = new Option("=선택=", "");
 	    	today = new Date();
 	    	if(selected == "") {
 	    		selected = today.getFullYear();
 	    	}
			for(var i = -1; i < 9; i++) {
				selectBox.options[i+2] = new Option((today.getFullYear() - i), (today.getFullYear() - i));
				if(selectBox.options[i+2].value == selected) {
					selectBox.options[i+2].selected = true;
				}
			}
		},
		/**
        * 월 selectBox를 생성한다.
        * 조회시 월이 필요할 경우 이 함수를 사용한다. 
        * @param {String} fmId	// 폼 아이디
        * @param {String} tgId	// 타겟 selectBox
        * @param {String} select	// check될 option
        */
	    setMonSelect: function(fmId,tgId,select){
	    	formId = "";
	    	targetId = "";
	    	selected = "";
	    	
	    	formId = fmId;
	    	targetId = tgId;
	    	selected = select;
	    	
	    	var selectBox = $(targetId);
	    	selectBox.length = 13;
	    	selectBox.options[0] = new Option("=선택=", "");
	    	today = new Date();
 	    	if(selected == "") {
 	    		selected = today.getMonth() + 1;
 	    	}
			for(var i = 0; i < 12; i++) {
				var mon = (i + 1) + "";
				mon.length < 2 ? mon = "0" + mon : mon;
				selectBox.options[i+1] = new Option(mon, mon);
				if(selectBox.options[i+1].value == selected) {
					selectBox.options[i+1].selected = true;
				}
			}
		}
 	 },		// [select] end
 	 /**
 	  **************************************************************************************
	  *********************************** selectbox 대한 function 모음 끝 ********************
	  **************************************************************************************
	  */
 	 
 	 /**
 	  **************************************************************************************
	  *********************************** radio 대한 function 모음 **************************
	  **************************************************************************************
	  */
 	 radio : {
        /**
         * 선택된 radio 버튼의 value 를 return 합니다.
         * 만약 value 가 없거만 선택 되지 않았으면 ""를 return 합니다.
         * @param radioObj {Object} radio 버튼 Obj
         */
        getCheckedValue : function (radioObj) {
 		   if(!radioObj){
              return "";
           }
           var radioLength = radioObj.length;
           if(radioLength == undefined){
              if(radioObj.checked){
                 return radioObj.value;
              }
              else{
            	 return "";
              }
           }
           for(var i = 0; i < radioLength; i++) {
              if(radioObj[i].checked) {
                 return radioObj[i].value;
              }
           }
           return "";
 	    },

        /**
         * newValue 값으로 radio 버튼을 선택 합니다.
         * 만약 radio 버튼 obj 가 없으면 아무것도 하지 않습니다.
         * 만약 해당 값이 없으면 radio 버튼의 check 를 unchecked 합니다.
         * @param radioObj
         * @param newValue
         */
        setCheckedValue : function(radioObj, newValue) {
           if(!radioObj)
              return;
           var radioLength = radioObj.length;
           if(radioLength == undefined) {
              radioObj.checked = (radioObj.value == newValue.toString());
              return;
           }
           for(var i = 0; i < radioLength; i++) {
              radioObj[i].checked = false;
              if(radioObj[i].value == newValue.toString()) {
                 radioObj[i].checked = true;
              }
           }
 	    }
 	 },		// [radio] end
 	 /**
 	  **************************************************************************************
	  *********************************** radio 대한 function 모음 끝 ************************
	  **************************************************************************************
	  */
 	 
 	 /**
 	  **************************************************************************************
	  *********************************** date 대한 function 모음 ***************************
	  **************************************************************************************
	  */
 	 date : {
         /**
          * 두 날짜 사이의 일수를 계산하여 반환한다.
          * @param date1 문자열 데이터로 '20041012' 형식
          * @param date2 문자열 데이터로 '20041012' 형식
          */
         getDaysBetween : function(date1, date2) {
             date1 = new Date(date1.substring(0, 4), date1.substring(4, 6)-1, date1.substring(6,8));
             date2 = new Date(date2.substring(0, 4), date2.substring(4, 6)-1, date2.substring(6,8));
             var DSTAdjust = 0;
             var oneMinute = 1000 * 60;
             var oneDay = oneMinute * 60 * 24;
             date1.setHours(0);
             date1.setMinutes(0);
             date1.setSeconds(0);
             date2.setHours(0);
             date2.setMinutes(0);
             date2.setSeconds(0);
             DSTAdjust = (date2.getTimezoneOffset( ) -
                              date1.getTimezoneOffset( )) * oneMinute;
             var diff = date2.getTime( ) - date1.getTime() - DSTAdjust;
             return Math.ceil(diff/oneDay);
         },

         /**
          * 현재시각을 time형식으로 반환
          * EX)200902122015 년월일시분
          */
         getCurrentTime : function(){
        	 return this.toTimeString(new Date());
         },

         /**
         * 자바스크립트 Date 객체를 Time 스트링으로 변환
         * parameter date: JavaScript Date Object
         */
         toTimeString : function(date) { //formatTime(date)
            var year  = date.getFullYear();
            var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
            var day   = date.getDate();
            var hour  = date.getHours();
            var min   = date.getMinutes();

            if (("" + month).length == 1) { month = "0" + month; }
            if (("" + day).length   == 1) { day   = "0" + day;   }
            if (("" + hour).length  == 1) { hour  = "0" + hour;  }
            if (("" + min).length   == 1) { min   = "0" + min;   }

            return ("" + year + month + day + hour + min);
         },
         
         /**
         * 자바스크립트 Date 객체를 스트링으로 Formatting
         * (ex. dateFormat_yyyyMMdd(date, '.')
         *      : yyyy.MM.dd )
         * @param String date   Ex)20090221
         */
         dateFormater_yyyyMMdd : function(date, formatChar) { 
        	var dateOb = new Date();
            if(!formatChar){
            		formatChar = "";
            }
            var yearInt = parseInt(date.substring(0,4), 10);
            var monthInt = parseInt(date.substring(4,6), 10);
            var dateInt = parseInt(date.substring(6,8), 10);
            
            dateOb.setFullYear(yearInt, monthInt-1, dateInt);
       	 	
            var year  = dateOb.getFullYear();
            var month = dateOb.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
            var day   = dateOb.getDate();
            
            if (("" + month).length == 1) { month = "0" + month; }
            if (("" + day).length   == 1) { day   = "0" + day;   }

            return ("" + year + formatChar + month + formatChar + day);
         },
         
         /**
          * 현재 年을 YYYY형식으로 리턴
          */
         getYear : function(){
            var todayNow = new Date();
            return todayNow.getFullYear();
         },

         /**
          * 현재 月을 MM형식으로 리턴
          */
         getMonth : function() {
            var todayNow = new Date();

            var month = todayNow.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
            if (("" + month).length == 1) { month = "0" + month; }

            return month;
         },

         /**
          * 현재 日을 DD형식으로 반환
          */
         getDay : function() {
            var todayNow = new Date();

            var day = todayNow.getDate();
            if (("" + day).length == 1) { day = "0" + day; }

            return day;
         },

         /**
          * YYYYMMDD 로 오늘 날짜 반환
          * @return today(20090212)
          */
         getToday : function(){
             return this.getYear() + this.getMonth() + this.getDay();
         },
         
         /**
          * YYYY + char + MM + char + DD 로 오늘 날짜 반환
          * @param String char   Ex)"."
          * @return today Ex) 2009.02.12
          */
         getFormatToday : function(char){
        	 return this.getYear() + char + this.getMonth() + char + this.getDay();
         },
         
         /**
          * YYYY년 MM월 DD일 로 오늘 날짜 반환
          * @return today Ex) 2009년 02월 12일
          */
         getFixFormatToday : function(){
        	 return this.getYear() + "년 " + this.getMonth() + "월 " + this.getDay() + "일";
         },

         /**
          * 현재일자에 일자를 더한 날짜를 구한다.
          * Ex) getAddDays("2)"   현재일 20090212  이면 return 값은 20090214 
          * @param (String) days
          * @return (String) yyyyMMdd 
          
         getAddDays : function( days ){
             var before = this.getToday();
             var date = new Date(before.substring(0,4),Number(before.substring(4,6))-1,Number(before.substring(6,8))+Number(days));
             var year=String(date.getFullYear());
             var month=String(date.getMonth()+1);
             var day=String(date.getDate());

             if(month.length==1) month = "0"+ month;
             if(day.length==1) day= "0" + day;

             return ''+year+''+month+''+day;
         },
        */
        /**
         * 특정날짜 더하기
         *
         * 사용법 : date.addDate('20041012', 'D', 3 )
         *
         * @param date1 문자열 데이터로 '20041012' 형식
         * @param type 문자열 데이터로 'Y','M','D' 형식
         * @param cnt 문자열 데이터로 '3' 형식
         */
        addDate : function(date1, type, cnt) {
        	
        	//var dt = Date.parseDate(date1, "yyyyMMdd");
		    
//		    var addType = Date.DAY;
//		    if( type == 'Y' ){
//		        addType = Date.YEAR;	
//		    }
//		    if( type == 'M' ){
//		        addType = Date.MONTH;
//		    }
//		    if( type == 'D' ){
//		        addType = Date.DAY;
//		    }
//		    dt = dt.add(addType, cnt);
		    
        	var date = "";
        	//var addType = Date.DAY;
 		    if( type == 'Y' ){
 		    	date = new Date(date1.substring(0,4)+Number(cnt),Number(date1.substring(4,6))-1,Number(date1.substring(6,8)));	
 		    }
 		    if( type == 'M' ){
 		    	date = new Date(date1.substring(0,4),Number(date1.substring(4,6))-1+Number(cnt),Number(date1.substring(6,8)));
 		    }
 		    if( type == 'D' ){
 		    	date = new Date(date1.substring(0,4),Number(date1.substring(4,6))-1,Number(date1.substring(6,8))+Number(cnt));
 		    }
 		    
            var year=String(date.getFullYear());
            var month=String(date.getMonth()+1);
            var day=String(date.getDate());

            if(month.length==1) month = "0"+ month;
            if(day.length==1) day= "0" + day;

            return ''+year+''+month+''+day;
            
		   // return dt.format('Ymd');
         },
         
         /**
          * 특정날짜의 요일을 구한다
          * Ex) return 값이 0 일요일, 1 월요일, 2 화요일, 3 수요일, 4 목요일, 5 금요일, 6 토요일
          * @param date1
          * @return number
          */
         getDayOfWeek: function(date){
             var d = new Date(date.substring(0,4), date.substring(4,6)-1, date.substring(6,8));
             var ww = d.getDay();
             return ww;
         },
         /**
          * 현재달의 첫 일자를 구한다.
          * 
          * @param String 년월일 구분자 Ex) "." 
          * @return 현재달의 첫 일자 Ex) 2009.04.01
          */
         getMonthFirstDay: function(gbn) {
        	   var d,d2 = "";           
        	   d = new Date();             
        	   d2 = new Date(d.getYear(),d.getMonth());
        	   var year = d2.getYear();
        	   var month = d2.getMonth() + 1;
        	   if (("" + month).length == 1) { month = "0" + month; }
        	   var date = d2.getDate();
        	   if (("" + date).length == 1) { date = "0" + date; }
        	   var firstDay = year + gbn + month + gbn + date;
        	   return firstDay;
         },
         
         /**
          * 현재달의 마지막 일자를 구한다.
          *   
          * @param String 년월일 구분자 Ex) "." 
          * @return 현재달의 마지막 일자 Ex) 2009.04.01
          */
         getMonthLastDay: function(gbn) {
        	   var d,d2, s = "";           
        	   d = new Date();             
        	   d2 = new Date(d.getYear(),d.getMonth()+1,"");                      
        	   var year = d2.getYear();
        	   var month = d2.getMonth() + 1;
        	   if (("" + month).length == 1) { month = "0" + month; }
        	   var date = d2.getDate();
        	   if (("" + date).length == 1) { date = "0" + date; }
        	   var lastDay = year + gbn + month + gbn + date;
        	   return lastDay; 
         },
         
         
         /**
          * 해당 월의 마지막일자를 구한다.
          *   
          * @param year 조회년, month 조회월
          * @return 해당월의 마지막 일자 
          */
         getLastDay: function(year, mon) {
        	 var month = mon - 1;
        	 var d = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
        	 if (((0 == (year%4)) && ( (0 != (year%100)) || (0 == (year%400)))) && month == 1) { 
        		 return 29; 
        	 } else { 
        		 return d[month]; 
        	 } 
         }
     },
 	 /**
 	  **************************************************************************************
	  *********************************** date 대한 function 모음 끝 *************************
	  **************************************************************************************
	  */
     
     /**
 	  **************************************************************************************
	  *********************************** util 대한 function 모음 ***************************
	  **************************************************************************************
	  */
      util : {
    	 /**
          * 포인트 제거 
          * @param {String} pointString
          */
         stripPoint : function(pointString){
    	 	var arrayString = pointString.split('.');
    	 	var changeString = "";
    	 	for(var i = 0; i< arrayString.length; i++){
    	 		changeString = changeString + arrayString[i];
    	 	}
    	 	return changeString;
         },
    	 /**
          * 날자 체크
          * @param {String} stdt
          * @param {String} endt
          */
         checkDt : function(stdt, endt){
    	 	var beforeString = stdt.split('.');
    	 	var afterString = endt.split('.');
    	 	
    	 	stdt = beforeString[0] + beforeString[1] + beforeString[2];
    	 	endt = afterString[0] + afterString[1] + afterString[2];
    	 	
    	 	stdt = parseInt(stdt);
    	 	endt = parseInt(endt);
    	 	
    	 	if(stdt > endt){
    	 		jsl.Dialog.alert("조회 시작일자가 종료일자 보다 이후 입니다.");
    			return false;
    	 	}
    	 	return true;
    	 	
         },
    	 /**
          * 날자 체크()
          * @param {String} stdtId
          * @param {String} endtId
          * @param {String} message
          */
         dateCheckM : function(stdtId, endtId, message){
        	
        	var beforeString = $F(stdtId); 
        	var afterString = $F(endtId); 
        
        	if(beforeString==""&&afterString!=""){
        		jsl.Dialog.alert( message + " 시작일자를 입력해 주세요");
        		return false;
        	}else if(afterString==""&&beforeString!=""){
        		jsl.Dialog.alert( message + " 종료일자를 입력해 주세요");
        		return false;
        	}
        	
    	 	beforeString = beforeString.split('.');
    	 	afterString = afterString.split('.');
    	 	
    	 	stdt = beforeString[0] + beforeString[1] + beforeString[2];
    	 	endt = afterString[0] + afterString[1] + afterString[2];
    	 	
    	 	stdt = parseInt(stdt);
    	 	endt = parseInt(endt);
    	 	
    	 	if(stdt > endt){
    	 		jsl.Dialog.alert( message + " 시작일자가 종료일자 보다 이후 입니다.");
    			return false;
    	 	}
    	 	return true;
    	 	
         },
    	 /**
          * 날자 체크
          * @param {String} stdt
          * @param {String} endt
          * @param {String} message
          */
         checkDtM : function(stdt, endt, message){
    	 	var beforeString = stdt.split('.');
    	 	var afterString = endt.split('.');
    	 	
    	 	stdt = beforeString[0] + beforeString[1] + beforeString[2];
    	 	endt = afterString[0] + afterString[1] + afterString[2];
    	 	
    	 	stdt = parseInt(stdt);
    	 	endt = parseInt(endt);
    	 	
    	 	if(stdt > endt){
    	 		jsl.Dialog.alert( message + " 시작일자가 종료일자 보다 이후 입니다.");
    			return false;
    	 	}
    	 	return true;
    	 	
         },
    	 /**
          * 숫자인지 체크('.'허용)
          * @param {Object} obj
          */
         onNumCheck_1 : function(obj){
        	 if ((event.keyCode < 48) || (event.keyCode > 57)) {
        		 if(event.keyCode != 46){
        			 event.returnValue = false;
        		 }
	    	 }
         },
    	 /**
          * 숫자인지 체크('-'허용)
          * @param {Object} obj
          */
         onNumCheck_2 : function(obj){
        	 if ((event.keyCode < 48) || (event.keyCode > 57)) {
        		 if(event.keyCode != 45){
        			 event.returnValue = false;
        		 }
	    	 }
         },
         /**
          * 숫자인지 체크
          * @param {Object} obj
          */
         onNumCheck : function(obj){
    	 //alert(event.keyCode);
//	    	 if((event.keyCode) == 9 ||(event.keyCode) == 8 || (event.keyCode == 46 )|| (event.keyCode == 35 )|| (event.keyCode == 36 )|| (event.keyCode == 37 )|| (event.keyCode == 39 )|| (event.keyCode > 95 &&( event.keyCode < 106) )){
//				 event.returnValue= true;
//			 }else 
        	 if ((event.keyCode < 48) || (event.keyCode > 57)) {
	    		 event.returnValue = false;
	    	 }
         },
         
  	    /**
  	     * 특수 문자 입력 막기( _-. 세가지 외에 전부 막기)
  	     * @param (String) inputId 
  	     */
  	 	check_str : function(inputId){
         	var val = $(inputId);
         	if(!this.containsChars(inputId,"abcdefghijklmnopqrstuvwxyz_-.1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
  	 		{
         		jsl.Dialog.alert("영문만 입력 가능하며, 특수 문자는( _ , - , . )이 세가지를 제외한 문자는 입력 하실수 없습니다.");
  	 			return false;
  	 		}
  	 		return true;
  	 	},
  	    /**
  	     * 특수 문자 입력 막기( _-. 세가지 외에 전부 막기)
  	     * @param (String) mesg 메세지
  	     * @param (String) inputId 
  	     */
  	 	check_strM : function(mesg,inputId){
         	var val = $(inputId);
         	if(!this.containsChars(inputId,"abcdefghijklmnopqrstuvwxyz_-.1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
  	 		{
         		jsl.Dialog.alert(mesg + " 영문만 입력 가능하며, 특수 문자는( _ , - , . )이 세가지를 제외한 문자는 입력 하실수 없습니다.");
  	 			return false;
  	 		}
  	 		return true;
  	 	},
         // 문자 검사
         containsChars : function(inputId, charss){
  	 		var inputOb = $(inputId);
          	for(var inx=0; inx < inputOb.value.length; inx++)
          	{
          		if(charss.indexOf(inputOb.value.charAt(inx))==-1){
          			return false;
          		}
          	}
          	return true;
          },
        
        /**
         * 대쉬 '-' 를 제거한다.
         */
        stripDash : function(numString) {
            var re = /-/g;
            return numString.replace(re, "");
        },
        
        /**
         * 공백을 제거 한다.(데이터의 앞자리에 들어간 공백만 제거)
         * @param strMsg
         */
        trim : function( stringToTrim ){
           return stringToTrim.replace(/(^\s*)|(\s*$)|($\s*)/g, "");
        },

        /**
         * 문장에 들어간 문자 변환
         * ex ) targetString="요즘에 한 요즘에" str1="요즘에" str2="옛날에" return="옛날에 한 옛날에"
         * @param targetString
         * @param str1
         * @param str2
         */
        replaceAll : function( targetString, oldStr, newStr ){
           var temp_str = "";

           if (this.trim( targetString ) != "" && oldStr != newStr)
           {
              temp_str = this.trim( targetString );

              while (temp_str.indexOf(oldStr) > -1)
              {
                 temp_str = temp_str.replace(oldStr, newStr);
              }
           }
           return temp_str;
        }
     },		// [util] end
     /**
 	  **************************************************************************************
	  *********************************** util 대한 function 모음 끝 *************************
	  **************************************************************************************
	  */
     
     /**
 	  **************************************************************************************
	  *********************************** format 대한 function 모음 ***************************
	  **************************************************************************************
	  */
      format : {
    	 /**
          * 계좌 번호 형식의 포맷으로 변환한 문자열을 반환합니다.
          * @param {String} acctNo 계좌번호
          */
         toAcctNo : function(acctNo){
             if(acctNo == null ) return "";
             if(acctNo.length == 17){
                 return acctNo.substring(0, 4) + "-" + acctNo.substring(4, 6) + "-" + acctNo.substring(6, 13) + "-" + acctNo.substring(13, 16) + "-" + acctNo.substring(16, 17);
             }
             else if(acctNo.length == 10){
                 return acctNo.substring(0, 2) + "-" + acctNo.substring(2, 9) + "-" + acctNo.substring(9, 10);
             }
             else{
            	 return acctNo;
             }
         },
         
        /**
         * 모든은행 계좌 번호 형식의 포맷으로 변환한 문자열을 반환합니다.
         * @param {String} acctNo
         * @param {String} bnkCd
         */
        toAcctNoExt : function(acctNo,bnkCd){
		    if(acctNo == null ) return "";
		    if(bnkCd == null ) return "";
		    if (bnkCd){
		    	// 당행일 경우
		    	if (bnkCd == '000') {	//
		    		toAcctNo(acctNo);
		    	} else {
		    		return acctNo;
		    	}
		    }				
        },
        /**
         * 사업자번호 형식의 포맷으로 변환한 문자열을 반환합니다.
         * @param {String} toBizNo 사업자번호
         */
        toBizNo : function(bizNo){
            if(bizNo == null ) return "";
            if(bizNo.length == 10){
                return bizNo.substring(0, 3) + "-" + bizNo.substring(3, 5) + "-" + bizNo.substring(5, 10);
            }
            else if(bizNo.length == 13){
                return bizNo.substring(3, 6) + "-" + bizNo.substring(6, 8) + "-" + bizNo.substring(8, 13);
            }
			else{
			    return bizNo;
		    }
        },
			
        /**
         * 카드번호 형식의 포맷으로 변환한 문자열을 반환합니다.
         * @param {String} toCardNo 사업자번호
         */
        toCardNo : function(cardNo){
            if(cardNo == null ) return "";
            if(cardNo.length == 16){
                return cardNo.substring(0, 4) + "-" + cardNo.substring(4, 8) + "-" + cardNo.substring(8, 12) + "-" + cardNo.substring(12, 16);
            }
            else{
                return cardNo;
            }
        }
     },		// [format] end 
     /**
 	  **************************************************************************************
	  *********************************** format 대한 function 모음 끝 *************************
	  **************************************************************************************
	  */
     
     /**
 	  **************************************************************************************
	  *********************************** json 대한 function 모음 ***************************
	  **************************************************************************************
	  */
     json : {
         /**
          * json데이터를 String으로 변환한다.
          * @param {Object} jsonObject jsonType의 Object
          */
         jsonToString : function(jsonObject) {
             var jsonText;
             jsonText = JSON.stringify(jsonObject);
             jsonText= jsonText.replace(/\\"/g,"\"").replace(/\\\\/g,"\\").replace(/:"\[/g,":[").replace(/\}\]"\}/g,"}]}");
             return jsonText;
         }
     },	//[json] end 
 	/**
	 **************************************************************************************
	 *********************************** json 대한 function 모음 ***************************
	 **************************************************************************************
	 */
     

     /**
 	  **************************************************************************************
	  *********************************** window 대한 function 모음 ***************************
	  **************************************************************************************
	  */
     window : {
         /**
          * 팝업창
          * @param {url} 주소
          * @param {formObj} 폼
          * @param {winname} 팝업창 이름
          * @param {w} 팝업창 넓이
          * @param {h} 팝업창 높이
          * @param {scroll} 표시 여부
          */
         openwin : function(url, formObj, winname, w, h, scroll) {
    	     window.open("", winname, "top=100, left=200, width="+w+", height="+h+",scrollbars="+scroll);
    		 formObj.action = url;  
    		 formObj.target = winname;  
    		 formObj.method = "post";
    		 formObj.submit();
    		 formObj.target = "_self";
    		 formObj.action = "";
         },
         /**
          * 모달팝업창
          * @param {url} 주소
          * @param {formObj} 폼
          * @param {winname} 팝업창 이름
          * @param {w} 팝업창 넓이
          * @param {h} 팝업창 높이
          * @param {scroll} 표시 여부
          */
         openmodal : function(url, w, h, scroll) {
         	 var agent = navigator.userAgent;
         	 var ww = 0;
         	 var wh = 0;

         	 if ((agent.indexOf('MSIE 6') > 0) && (agent.indexOf('MSIE 7') == -1) && (agent.indexOf('MSIE 8') == -1) ) {
         		 ww = w + 10;
         		 wh = h + 50;     		 
         	 }else{
 	    		 ww = w;
 	    		 wh = h;
         	 }
         	 var returnValue = showModalDialog(url, window, "dialogWidth:"+ww+"px;dialogHeight:"+wh+"px;scroll:"+scroll+";Status:Off;Help:No;Center:Yes");
         	 return returnValue;
         },           
         /**
          * 팝업창
          * @param {url} 주소
          * @param {formObj} 폼
          * @param {winname} 팝업창 이름
          * @param {w} 팝업창 넓이
          * @param {h} 팝업창 높이
          * @param {scroll} 표시 여부
          */
         postOpenWin : function(url, formObj, postNoParam1, postNoParam2, postAddrParam, winname, w, h, scroll) {
    	     window.open("", winname, "top=100, left=200, width="+w+", height="+h+",scrollbars="+scroll);
    	     
    	     url = url + "?postNoParam1="+postNoParam1+"&postNoParam2="+postNoParam2+"&postAddrParam="+postAddrParam;
    		 formObj.action = url;  
    		 formObj.target = winname;  
    		 formObj.method = "post";
    		 formObj.submit();
    		 formObj.target = "_self";
    		 formObj.action = "";
         }
     }	//[window] end 
 	/**
	 **************************************************************************************
	 *********************************** window 대한 function 모음 ***************************
	 **************************************************************************************
	 */
 
 	}		// [return] end
}();		// [jsl.Utils] end


var JslHtml = jsl.Utils.html; 
var JslInput = jsl.Utils.input;
var JslForm = jsl.Utils.form;
var JslCheckbox = jsl.Utils.checkbox;
var JslSelect = jsl.Utils.selectbox;
var JslRadio = jsl.Utils.radio;
var JslDate = jsl.Utils.date;
var JslUtil = jsl.Utils.util;
var JslFormat = jsl.Utils.format;
var JslJson = jsl.Utils.json;
var JslCalendar = jsl.Utils.calendarFunc;
var JslWindow = jsl.Utils.window;

