/*****************************************************************************
 * 파일명 : ta-validation.js
 * 작성일 : 2009. 02. 13
 * 작성자 : 한향덕
 * 설   명 : Form Data의 검증을 위한 스크립트 함수
 *****************************************************************************/
jsl = {}

jsl.JMessage = function (){ // 생성자 메소드
   /* Member field */
   this.init;
   this.messages;

}

jsl.JMessage.prototype = {
		/* Member method */
		get : function(id, args) {
		    if (!this.init) {
		    	this.messages = __VALIDATION_MESSAGE__;
	            this.init = true;
		    }
		    var message = this.messages[id];
		    if (!message) {
		        return id;
		    }
		    if (args) {
		        if (typeof args == "object" && args.length) {
		            for (var i = 0; i < args.length; i++) {
		                var pattern = new RegExp("\\{" + i + "\\}", "g");
		                message = message.replace(pattern, args[i]);
		            }
		        } else {
		            message = message.replace(/\{0\}/g, args);
		        }
		    }
		    return message;
		},

		/**
		 * 화면단에 메세지박스를 
		 * @param {Object} obj 메세지박스가 펼쳐질때 애니메이션이 시작할 오브젝트
		 * @param {Object} id 출력할 메세지패턴 아이디
		 * @param {Object} args 메세지패턴에 등록할 데이터
		 * @param {Object} isFocus 애니메이션이 시작할 오브젝트에 포커스선택 여부
		 */
		alert : function(obj, id, args, isFocus) {
			alert(this.get(id, args));
			if(id == "JSM-1017"){}
			else if (isFocus) {
				obj.focus();
			}
			return false;
	    },
		alertMessage : function(obj, msg, isFocus) {
			alert(msg);
			if (isFocus) {
				obj.focus();
			}
		}
	}

var JslMessages = new jsl.JMessage();





/** #. start JForm control **/
/**
 * Class 명 : JForm
 * 설명 : Form 관리
 */
jsl.JForm = function() { // 생성자 메소드
    /* Member field */
    this.children = new Array();
}

var JslJForm = jsl.JForm;

JslJForm.prototype = {
	/* Member method */
	add : function(child) {
	    this.children[this.children.length] = child;
	    return this;
	},
	validate : function() {
	    for (var i = 0; i < this.children.length; i++) {
	        if (!this.children[i].validate()) {
	            return false;
	        }
	    }
	    return true;
	}
}
/** #. end JForm control **/

/** #. start JText control TYPE에 따라 추가 정의한다. **/
/**
 * Class 명 : JText
 *
 * 설명 : text 타입 관리
 * validate method를 제외한 나머지는 모두 chained method 입니다.
 *
 * 사용 예 :
 * new jsl.JText('이름', formObj.name).nullable().range(4,10).isEnglish()
 * 위 소스는 name 필드에 빈값이 올 수 있으며 4,10자 사이의 영어만 입력 할 수 있도록
 * 설정한 내용입니다.
 *
 * filter method 를 통해 특수문자입력을 filtering 합니다.
 *
 * checkRegExp method 는 정규식을 통한 체크를 가능하게 해줍니다.
 * 하지만 사용을 위해선 메세지를 설정 해야 합니다.
 *
 * @param {String} name
 * @param {HTMLElement} object
 */
jsl.JText = function (name, object) {
    /* Member Field */
    this.name = name;
    this.object = object;

    this.min;
    this.max;

    this.nullCheck = true;
    this.rangeCheck = false;
    this.filterCheck = false;
    this.regExpCheck = false;

    this.pattern;
    this.messageCode = "JSM-1004";
    this.messageParam = [name];

    this.nullable = JslCMUtil.nullable;
    this.range = JslCMUtil.range;
}

var JslJText = jsl.JText;

JslJText.prototype =  {
    /* Member Method */
	/**
	 * 문자열 타입 유효성 검사
	 */
    validate : function(){
	    var value = this.object.val();
	    
        if (this.nullCheck && JslCMUtil.isNull(value)) {
	        return JslMessages.alert(this.object, "JSM-1004", JslCMUtil.ul(this.name), true);
	    }

        if (this.regExpCheck && !JslCMUtil.isNull(value) && !this.pattern.test(value) ){
            return JslMessages.alert(this.object, this.messageCode, this.messageParam, true);
        }

        if (this.rangeCheck && !JslCMUtil.isNull(value) && !JslCMUtil.checkCharacterSize(value, this.min, this.max)) {
	        if (this.min == this.max) {
	            return JslMessages.alert(this.object, "JSM-1005", [this.name, this.min], true);
	        } else {
	            return JslMessages.alert(this.object, "JSM-1006", [this.name, this.min, this.max], true);
	        }
	    }

        if (this.filterCheck && JslCMUtil.isExistSpacialChar(value) ){
            return JslMessages.alert(this.object, "JSM-1007", [this.name], true);
        }

        return true;
	},

    /**
	 * 전화번호 형식 체크함.
	 * ex) 02-523-5353, 023.3552.2353, 02 202 4024, 0324236450
	 * 주의: 구분자가 없을 경우 정확한 전화번호를 체크할 수 없음
	 */
    isTelnum : function() {
		// 예전 소스
        //this.checkRegExp( new RegExp("^0[1-9]{1,2}[-. ]?[0-9]{3,4}[-. ]?[0-9]{4}$","g"), "JSM-1030", JslCMUtil.un([this.name]));
        this.checkRegExp( new RegExp("^(02|031|032|033|041|042|043|051|052|053|054|055|061|062|063|064|010|011|015|016|017|018|019)[-. ]?[0-9]{3,4}[-. ]?[0-9]{4}$","g"), "JSM-1030", JslCMUtil.un([this.name]));
        return this;
	},

    /**
	 * 이메일 형식 체크함.
	 */
    isEmail : function() {
	    this.checkRegExp( new RegExp("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$","ig"), "JSM-1017", JslCMUtil.un([this.name]));
        return this;
	},

    /**
	 * 한글 형식체크함. 한글만 존재 해야함. (공백 가능)
	 */
    isKorean : function() {
	    this.checkRegExp( new RegExp("^[가-힝\\s]+$","g"), "JSM-1031", JslCMUtil.un([this.name]));
        return this;
	},

    /**
	 * 영어 형식체크함. 영어만 존재 해야함. (공백, ',', '.', '_' 가능)
	 */
    isEnglish : function() {
	    this.checkRegExp( new RegExp("^[A-Z0-9.,_\\s]+$","ig"), "JSM-1032", JslCMUtil.un([this.name]));
        return this;
	},


    /**
     * 정규식을 인자로 받아서 체크한다.
     * 메세지 정의 필요합니다.
     *
     * @param reg 정규식
     * @param msgCd 메세지 코드
     * @param msgParam 메세지 param
     */
    checkRegExp : function(reg, msgCd, msgParam) {
	    this.regExpCheck = true;
        this.pattern = reg;
        this.messageCode = msgCd;
        this.messageParam = msgParam;
        return this;
	},

    /**
     * 특수문자 허용안함
     * `~!@#$%^&*'\"
     */
    filter : function (){
        this.filterCheck = true;
        return this;
    }
}
/** #. end JText**/

/** #. start JNumber control TYPE에 따라 추가 정의한다. **/
/**
 * Class 명 : JNumber
 *
 * 설명 : number 타입 관리
 * validate method를 제외한 나머지는 모두 chained method 입니다.
 *
 * 사용 예 :
 * new jsl.JNumber('나이', formObj.age).nullable().range(1,999)
 * 위 소스는 age 필드에 숫자만 입력 가능하고 빈값이 올 수 있으며 1~999 까지의 숫자만 입력
 * 할 수 있도록 설정한 내용입니다.
 *
 * @param {String} name
 * @param {HTMLElement} object
 * @param {String} 생성될 hidden 필드명
 */
jsl.JNumber = function(name, object, targetElId){
    /* Member Field */
    this.name = name;
    this.object = object;
    this.targetElId = targetElId;

    this.nullCheck = true;
    this.rangeCheck = false;
    this.lengthCheck = false;

    this.min;
    this.max;

    this.minlength;
    this.maxlength;

    this.nullable = JslCMUtil.nullable;
    this.range = JslCMUtil.range;

}

var JslJNumber = jsl.JNumber;

jsl.JNumber.prototype = {
	/* Member Method */
	/**
	 * Number타입 유효성 검사
	 */
	validate : function(){
	    var value = JslInput.toNumber(this.object.val());
	    
        //nullcheck
        if (this.nullCheck && JslCMUtil.isNull(value) ) {
	        return JslMessages.alert(this.object, "JSM-1004", JslCMUtil.ul(this.name), true);
	    }

        // null 이 아닌 경우 숫자인지 체크
	    if (isNaN(value)) {
	        return JslMessages.alert(this.object, "JSM-1008", this.name, true);
	    }

        //범위 체크 ex) 1~999
        if (this.rangeCheck && !JslCMUtil.isNull(value) && !JslCMUtil.checkNumberSize(value, this.min, this.max)) {
	        if (this.min == this.max) {
	            return JslMessages.alert(this.object, "JSM-1009", [this.name, this.min], true);
	        } else {
	            return JslMessages.alert(this.object, "JSM-1010", [this.name, JslCMUtil.wa(this.min), this.max], true);
	        }
	    }

        // 자리수 체크
        if (this.lengthCheck && !JslCMUtil.isNull(value) && !JslCMUtil.checkNumberLength(value, this.minlength, this.maxlength)) {
	        if (this.minlength == this.maxlength) {
	            return JslMessages.alert(this.object, "JSM-1033", [this.name, this.minlength], true);
	        } else {
	            return JslMessages.alert(this.object, "JSM-1034", [this.name, this.minlength, this.maxlength], true);
	        }
	    }

        return true;
	},

    limitLength : function(min, max){
        this.minlength = min;
        this.maxlength = max;
        this.lengthCheck = true;
        return this;
    }
}
/** #. end JNumber control **/

/** #. start JCurrency control TYPE에 따라 추가 정의한다. **/
/**
 * Class 명 : JCurrency
 *
 * 설명 : number 타입 관리
 * validate method를 제외한 나머지는 모두 chained method 입니다.
 *
 * 사용 예 :
 * new jsl.JCurrency('나이', formObj.age).nullable().range(1,999)
 * 위 소스는 age 필드에 숫자만 입력 가능하고 빈값이 올 수 있으며 1~999 까지의 숫자만 입력
 * 할 수 있도록 설정한 내용입니다.
 *
 * @param {String} name
 * @param {HTMLElement} object
 * @param {String} 생성될 hidden 필드명
 */
jsl.JCurrency = function(name, object, targetElId){
    /* Member Field */
    this.name = name;
    this.object = object;
    this.targetElId = targetElId;

    this.nullCheck = true;
    this.rangeCheck = false;
    this.rangeMoneyCheck = false;
    this.lengthCheck = false;

    this.min;
    this.max;
    this.type;

    this.minlength;
    this.maxlength;

    this.nullable = JslCMUtil.nullable;
    this.range = JslCMUtil.range;
    this.rangeMoney = JslCMUtil.rangeMoney;

}
var JslJCurrency = jsl.JCurrency;

jsl.JCurrency.prototype = {
    /**
	 * Currency타입 유효성 검사
	 */
	validate : function(){
	
	    var value = this.object.val();

        //nullcheck
        if (this.nullCheck && JslCMUtil.isNull(value) ) {
	        return JslMessages.alert(this.object, "JSM-1004", JslCMUtil.ul(this.name), true);
	    }

        // null 이 아닌 경우 숫자인지 체크
        if (!JslCMUtil.isNull(value) && JslCMUtil.isNaN(value)) {
	        return JslMessages.alert(this.object, "JSM-1008", this.name, true);
	    }

        //범위 체크 ex) 1~999
        if (this.rangeCheck && !JslCMUtil.isNull(value) && !JslCMUtil.checkNumberSize(value, this.min, this.max)) {
	        if (this.min == this.max) {
	            return JslMessages.alert(this.object, "JSM-1009", [this.name, this.min], true);
	        } else {
	            return JslMessages.alert(this.object, "JSM-1010", [this.name, JslCMUtil.wa(this.min), this.max], true);
	        }
	    }
	    
	    //금액체크 
	    if(this.rangeMoneyCheck && !JslCMUtil.isNull(value) && "" != JslCMUtil.checkMoney(value,this.min, this.max, this.type)) {
		    //var str_min = JslCMUtil.getKoreaMoney(this.min);   일만
		    //var str_max = JslCMUtil.getKoreaMoney(this.max);	오만 이런식으로 한글로 뿌려줌
    	var str_min = JslInput.toMoney(this.min);		// 10,000   이런식으로 금액 형식으로 뿌려줌			
		    var str_max = JslInput.toMoney(this.max);
	    	if(this.type == "1") {
	    		return JslMessages.alert(this.object, "JSM-1036", [this.name, str_min, str_max], true);
	    	} else if(this.type == "2") {
	    		return JslMessages.alert(this.object, "JSM-1037", [this.name, str_min, str_max], true);
	    	} else if(this.type == "3") {
	    		return JslMessages.alert(this.object, "JSM-1038", [this.name,str_min,str_max], true);
	    	}
	    }

        // 자리수 체크
        if (this.lengthCheck && !JslCMUtil.isNull(value) && !JslCMUtil.checkNumberLength(value, this.minlength, this.maxlength)) {
	        if (this.minlength == this.maxlength) {
	            return JslMessages.alert(this.object, "JSM-1033", [this.name, this.minlength], true);
	        } else {
	            return JslMessages.alert(this.object, "JSM-1034", [this.name, this.minlength, this.maxlength], true);
	        }
	    }

        JslCMUtil.makeHiddenInput(this, value, /[,]/g);
        return true;
	},
    limitLength : function(min, max){
        this.minlength = min;
        this.maxlength = max;
        this.lengthCheck = true;
        return this;
    }
}
/** #. end JCurrency control **/

/** #. start Date control **/
/**
 * Class 명 : JDate
 *
 * 설명 : Date 타입 관리
 * nullable 과 range method만이 chained method 입니다.
 *
 * toString 은 입력된 날짜를 this.format = "YYYY년MM월DD일(DAY)" 에 정의된
 * 형식으로 반환해 주는 method입니다.( 메세지 출력시 내부적 호출 )
 * toDate => Date 클래스로 반환.
 *
 * 사용 예 :
 * new jsl.JDate('날짜', formObj.date).nullable().range('20050101','20070101')
 * 위 예제는 date 필드에 빈값이 올수 있으며 20050101 부터 20070101 까지 날짜 입력에
 * 제한을 둔 설정입니다.
 *
 * new jsl.JDate('날짜', formObj.date, /^\d{4}[년]?\d{2}[월]?\d{2}[일]?$/)
 * date 필드에 입력되는 날짜 형식이 2007년02월20일 과 같은 형식일 때 날짜 입력
 * 여부를 체크하도록 설정한 내용입니다.
 *
 * 기존 설정은 구분자가 없거나 '-','.','/'를 구분자로 사용하는 날짜 형식이며
 * 이외의 날짜 입력시 잘못된 날짜로 간주 합니다.
 *
 * 반드시 년도 4자리, 월 2자리, 일 2자리로 구성되는 형식이여야 하며
 * 특별한 구분자를 사용할 경우 해당하는 구분자 형식의 정규식을 넘겨주어야 합니다.
 *
 * 추가 작업 : targetElId 인자를 받아 validation 성공 시 targetElId 로 hidden 필드를 만듭니다.
 *
 * @param {String} name
 * @param {HTMLElement} object
 * @param {String} 생성될 hidden 필드명
 * @param {Object} regExp 정규식 객체
 *
 */
jsl.JDate = function(name, object, targetElId, re) { // 생성자 함수
	this.DAY_OF_WEEK = new Array("일","월","화","수","목","금","토");
    /* Member Field */
    this.name = name;
    this.object = object;

    this.nullCheck = true;
    this.rangeCheck = false;

    this.date = null;

	this.targetElId = targetElId;

    if (re)  {
    	this.re = re;
    } else {
		this.re = /^\d{4}[-.\/]?\d{2}[-.\/]?\d{2}$/;
    }

    this.min = null; /* YYYYMMDD Format */
    this.max = null; /* YYYYMMDD Format */

    this.format = "YYYY년MM월DD일(DAY)";

    this.parse();

    this.nullable = JslCMUtil.nullable;
    this.range = JslCMUtil.range;
}
var JslJDate = jsl.JDate;
jsl.JDate.prototype = {
	/* Member Method */
	/**
	 * Date타입 유효성 검사
	 */
    validate : function(){
	
	    var value = JslUtil.stripPoint(this.object.val());

        // null check
        if (this.nullCheck && JslCMUtil.isNull(value)) {
	        return JslMessages.alert(this.object, "JSM-1004", JslCMUtil.ul(this.name), true);
	    }

        // nullable 인 경우 null 이면 여기서 return true 한다.
        if (!this.nullCheck && JslCMUtil.isNull(value)) {
	        JslCMUtil.makeHiddenInput(this, value, /[^\d]/g);
            return true;
	    }

        // 범위 체크
        if (this.rangeCheck) {
	        var minDate = JslUtil.stripPoint(this.min);
	        var maxDate = JslUtil.stripPoint(this.max);
	        if(  minDate > maxDate) {
        		return JslMessages.alert(this.object, "JSM-1019", [this.name, this.max], true);
	        }
	    }

        return true;
	},

    /**
	 * Date 반환
	 */
	toDate :  function() {
	    return this.date;
	},

	/**
	 * 문자열을 Date 클래스로 parsing 합니다.
	 */
	parse : function() {
	    var value = this.object;
	    if (this.parse.arguments.length > 0) {
	        value = this.parse.arguments[0];
	    } else if (this.object && typeof this.object=="object") {
	        value = this.object.val();
	    } else {
	    	this.date = new Date();
	        return this;
	    }
	    this.date = null;
	    //if (value.search(this.re) >= 0) {
	    //    value = value.replace(/[^\d]/g,"");
	        var aDate = new Date(value.substring(0,4),value.substring(4,6)-1,value.substring(6,8));
	        if (   aDate.getFullYear()  == Math.abs(value.substring(0,4))
	            && aDate.getMonth() == Math.abs(value.substring(4,6))-1
	            && aDate.getDate()  == Math.abs(value.substring(6,8)) ) {
	            this.date = aDate;
	        }
	    //}
	    return this;
	},

	/**
	 * string 타입으로 반환
	 */
	toString : function() {
	    var formatString = this.format;
	    if (toString.arguments != undefined && toString.arguments.length > 0) {
	        formatString = toString.arguments[0];
	    }
	    var str = formatString.replace(/YYYY/g , this.getYear());
	    str = str.replace(/MM/g , this.getMonth());
	    str = str.replace(/DD/g , this.getDate());
	    str = str.replace(/DAY/g , this.getDay());
	    str = str.replace(/yy/g , new String(this.getYear()).substring(2,4));
	    return str;
	},
	getYear : function() {
	    return this.date == null ? 1000 : this.date.getFullYear();
	},
	getMonth : function () {
	    var num = (this.date == null ? 0 : this.date.getMonth()+1);
	    return (num < 10 ? '0' + new String(num) : num);
	},
	getDate : function() {
	    var num = (this.date == null ? 0 : this.date.getDate());
	    return (num < 10 ? '0' + new String(num) : num);
	},
	getDay : function() {
	    return (this.date == null ? this.DAY_OF_WEEK[0] : this.DAY_OF_WEEK[this.date.getDay()]);
	}
}
/** #. end Date control **/

/** #. start Check/Radio control **/
/**
 * Class 명 : JCheck
 *
 * 설명 : JCheck 타입 관리
 * nullable 과 range method만이 chained method 입니다.
 *
 * 사용 예 :
 * new jsl.JCheck('체크', formObj.chk).nullable().range(2,3)
 * 위 예제는 chk 선택이 선택 되지 않아도 되며 선택 된다면 2개에서 3개 사이가 선택 되도록
 * 제한을 둔 설정입니다.
 *
 * @param {String} name
 * @param {HTMLElement} object
 */
jsl.JCheck = function (name, object) {

    /* Member Field */
    this.name = name;
    this.object = object;

    this.min;
    this.max;

    this.nullCheck = true;
    this.rangeCheck;

    this.nullable = JslCMUtil.nullable;
    this.range = JslCMUtil.range;
}

var JslJCheck = jsl.JCheck;

jsl.JCheck.prototype = {
	validate : function() {
		//var value = this.object;
		// checkbox/radio에서 체크된 개수
	    var number = JslCMUtil.isCheckedCnt(this.object);

		if (this.nullCheck && number == 0) {
			return JslMessages.alert(this.object, "JSM-1011", JslCMUtil.ul(this.name), false);
		}

		if (this.rangeCheck && number != 0 && (number < this.min || number > this.max)) {
		    if (this.min == this.max) {
				return JslMessages.alert(this.object, "JSM-1012", [this.name, this.min], false);
		    } else {
				return JslMessages.alert(this.object, "JSM-1013", [this.name, JslCMUtil.wa(this.min), this.max], false);
		    }
		}
	    return true;
	}
}
/** #. end Check/Radio control **/


/** #. start Select control **/
/**
 * Class 명 : JSelect
 *
 * 설명 : JSelect 타입 관리
 * nullable 과 range method만이 chained method 입니다.
 *
 * 사용 예 :
 * new jsl.JSelect('선택', formObj.select).nullable().range(1,2)
 * 위 예제는 select 선택이 선택 되지 않아도 되며 선택 된다면 1개에서 2개 사이가 선택 되도록
 * 제한을 둔 설정입니다.
 *
 * @param {String} name
 * @param {HTMLElement} object
 */
jsl.JSelect = function (name, object) {

    /* Member Field */
    this.name = name;
    this.object = object;

    this.min;
    this.max;

    this.nullCheck = true;
    this.rangeCheck;

    this.range = JslCMUtil.range;
    this.nullable = JslCMUtil.nullable;
}

var JslJSelect = jsl.JSelect;

jsl.JSelect.prototype = {
    validate : function () {
        var value = this.object.val();

        if (this.nullCheck && JslCMUtil.isNull(value)) {
            return JslMessages.alert(this.object, "JSM-1011", JslCMUtil.ul(this.name), true);
        }

        var number = JslCMUtil.isSelectedCnt(this.object);

        if (this.rangeCheck && number != 0 && (number < this.min || number > this.max)) {
		    if (this.min == this.max) {
				return JslMessages.alert(this.object, "JSM-1012", [this.name, this.min], false);
		    } else {
				return JslMessages.alert(this.object, "JSM-1013", [this.name, JslCMUtil.wa(this.min), this.max], false);
		    }
		}
        return true;
    }
}
/** #. end Select control **/

/** #. start JFile control **/
/**
 * Class 명 : JFile
 *
 * 설명 : JFile 타입 관리
 * nullable, arrowFileExt, denyFileExt 이 chained method 입니다.
 *
 * 사용 예 :
 * new jsl.JFile('파일', formObj.file).nullable().denyFileExt(['zip','exe'])
 * 위 예제는 file 에 파일을 첨부 하지 않아도 되며 첨부 시 확장자가 'zip','exe' 인 형식은
 * 제한을 둔 설정입니다.
 *
 * @param {String} name
 * @param {HTMLElement} object
 */
jsl.JFile = function (name, object) {
    /* Member Field */
    this.name = name;
    this.object = object;

    this.nullCheck = true;
    this.allowedExtension;
    this.disallowedExtension;
    
    this.nullable = JslCMUtil.nullable;
}

var JslJFile = jsl.JFile;

jsl.JFile.prototype = {

    validate : function () {
        var value = this.object.val();

        /*IE 버그로 인한 입력값 정확성 체크 */
        var re = /^[a-z]:\\(.){0,300}$/i;

        if (value != "" && !value.match(re)) {
            return JslMessages.alert(this.object, "JSM-1022", true);
        }

        if (this.nullCheck && JslCMUtil.isNull(value)) {
            return JslMessages.alert(this.object, "JSM-1023", [this.name], false);
        }

        if (this.allowedExtension) {
            var passed = true;
            var extension = JslCMUtil.getFileExt(value);
            if (extension) {
                for(var j = 0; j < this.allowedExtension.length ; j++) {
                    if (this.allowedExtension[j].toLowerCase() == extension) {
                        break;
                    }
                    if (j == this.allowedExtension.length - 1) {
                        passed = false;
                    }
                }
            }
            if (!passed) {
                return JslMessages.alert(this.object, "JSM-1024", [this.name, this.allowedExtension.join(", ")], false);
            }
        }

        if (this.disallowedExtension) {
            var passedFlag = true;
            var extensionVal = JslCMUtil.getFileExt(value);
            if (extensionVal) {
                for(var k = 0; k < this.disallowedExtension.length ; k++) {
                    if (this.disallowedExtension[k].toLowerCase() == extensionVal) {
                    	passedFlag = false;
                        break;
                    }
                    if (k == this.disallowedExtension.length - 1) {
                    	passedFlag = true;
                    }
                }
            }
            if (!passedFlag) {
            	this.object.val("");
                return JslMessages.alert(this.object, "JSM-1025", [this.name, this.disallowedExtension.join(", ")], false);
            }
        }
        
        return true;
    },

    /**
     * 허용 확장명 설정
     * @param array 확장자 array
     */
    allowFileExt : function (array) {
        this.allowedExtension = array;
        return this;
    },

    /**
     * 불가 확장명 설정
     * @param array 확장자 array
     */
    denyFileExt : function (array) {
        this.disallowedExtension = array;
        return this;
    }
}
/** #. end JFile control **/

/** #. start JEtc message **/
/**
 * Class 명 : JEtc
 *
 * 설명 : JEtc 타입 관리
 * contains, equals 이 chained method 입니다.
 *
 * 사용 예 :
 * new jsl.JEtc('파일', formObj.input).contains('xx').equals('xxx')
 * 위 예제는 file 에 파일을 첨부 하지 않아도 되며 첨부 시 확장자가 'zip','exe' 인 형식은
 * 제한을 둔 설정입니다.
 *
 * @param {String} msg
 * @param {HTMLElement} object
 */
jsl.JEtc = function (msg, object) {
    /* Member Field */
    this.msg = msg;
    this.object = object;
    this.contains;
    this.equals;
}

var JslJEtc = jsl.JEtc;

jsl.JEtc.prototype = {

    validate : function () {
        var value = this.object.val();

        if (this.contains) {
        	if (value.indexOf(this.contains) > 0)
                return JslMessages.alertMessage(this.object, [this.msg], false);
        }
        if (this.equals) {
        	if (value == this.equals)
                return JslMessages.alertMessage(this.object, [this.msg], false);
        }
        
        return true;
    },

    /**
     * 문자 포함 여부 설정
     * @param str 포함문자
     */
    contains : function (str) {
        this.contains = str;
        return this;
    },
    /**
     * 문자 동일 여부 설정
     * @param str 포함문자
     */
    equals : function (str) {
    	this.equals = str;
    	return this;
    }
}
/** #. end JEtc control **/

jsl.validation = {

    /**
     * 입력된 문자열 중 특정 문자를 지우고 이를 targetElId 의 input hidden 필드로 넣어주는 함수
     * 이때 생성된 input hidden 태그는 Object 의 앞에 들어간다. (parentNode.insertBefore)
     *
     * @param obj {Object} validation 객체에 넘어온 Object
     * @param value {String} value
     * @param regExp {Object} replace 시 수행할 정규식
     */
    makeHiddenInput : function(obj, value, regExp){
        var replaceVal = value.replace(regExp,"");

        if (obj.targetElId && document.getElementById(obj.targetElId)) {
			// 숫자를 제외한 문자들은 전부 뺀다. replace
			document.getElementById(obj.targetElId).value = replaceVal;
		} else if( obj.targetElId ) {
			var cEl = document.createElement("INPUT");
			cEl.type = "hidden";
			cEl.name = obj.targetElId;
			cEl.id = obj.targetElId;
			cEl.value = replaceVal;
			obj.object.parentNode.insertBefore(cEl, obj.object);
		} else{
            obj.object.value = replaceVal;
        }
    },


    /**
     * 공통함수입니다.
     *
	 * Null 체크 비활성화
	 */
    nullable : function() {
	   this.nullCheck = false;
	   return this;
	},

	/**
	 * 공통함수입니다.
	 *
	 * 유효성 검사 범위 설정
	 * @param {Object} min 최소값
	 * @param {Object} max 최대값
	 */
	range : function(min, max) {
	    this.rangeCheck = true;
	    this.min = min;
	    this.max = max;
	    return this;
	},
	
	/**공통함수입니다.
	 * 금액 범위 체크함.
	 * 금액이 min 와 max 사이가 아닐경우 안내메시지
	 * type : ('1':일반) ('2':만원단위로 입력체크 해야할경우) 
	 * ('3':min값만 있을경우 ) rangeMoney(3000000,999999999999,'3'); => "삼백만원 이상 가능합니다"
	 */
	rangeMoney : function(min, max, type) {
		this.rangeMoneyCheck = true;
	    this.min = min;
	    this.max = max;
	    this.type = type;
	    return this;
	},
    
    /**
	 * 금액 범위 체크함.
	 * 금액이 min 와 max 사이가 아닐경우 안내메시지
	 * type : ('1':일반) ('2':만원단위로 입력체크 해야할경우) 
	 * ('3':min값만 있을경우 ) rangeMoney(this,3000000,999999999999,'3'); => "삼백만원 이상 가능합니다"
	 */
    checkMoney : function(value,min, max, type) {
	    if (!this.nullCheck || !JslCMUtil.isNull(value) ) {
		    var flag = JslCMUtil.checkNumberSize(value,min,max);
		    if(flag && "1" == type) {	    			                
	    		type = "";
	    	} else if("2" == type) {
	    		var money = Number(value.replace(/[,]/g,"").replace(/[.][0-9]+$/g,""));
	    		if(flag && money%10000 == 0) {
	    			type = "";
	    		}
	    	} else if("3" == type && flag) {
	    		type = "";
	    	}
	        return type;
        }
	},
	

    /* 내부함수 (한글 종성체크) */
    isJongsong : function (wd) {

        var INDETERMINATE = 0;
        var NOJONGSONG = 1;
        var JONGSONG = 2;

        var word = new String(wd);                    /* 숫자가 들어오는 등에 대비해 무조건 문자열로 바꿈 */
        var numStr1 = "013678lmnLMN";                 /* '조' 전까지는 0이 받침이 있는걸로 나옴 --; */
        var numStr2 = "2459aefhijkoqrsuvwxyzAEFHIJKOQRSUVWXYZ";
        /* bdgpt들은 읽기에 따라 받침이 있기도 하고 없기도 한다고 판단. */
        /* 대문자는 단독으로 읽을 때를 감안하면 받침 있다고 확정되는 것이 더 적음. */

        if (word == null || word.length < 1) {
            return INDETERMINATE;
        }

        var lastChar = word.charAt(word.length - 1);
        var lastCharCode = word.charCodeAt(word.length - 1);

        if (numStr1.indexOf(lastChar) > -1) {
            return JONGSONG;
        }else if (numStr2.indexOf(lastChar) > -1) {
            return NOJONGSONG;
        }

        if (lastCharCode<0xac00 || lastCharCode>0xda0c) {
            return INDETERMINATE;
        }
        else{
            var lastjongseong = (lastCharCode - 0xAC00) % (21*28) % 28  ;

            if (lastjongseong == 0){
                return NOJONGSONG;
            }else{
                return JONGSONG;
            }
        }
    },

    /* 내부함수 (을/를) */
    ul : function (s) {
//        var __JOSA_UL__ = new Array("(을)를", "를", "을");
        return s + __JOSA_UL__[JslCMUtil.isJongsong(s)];
    },

    /* 내부함수 (이/가) */
    ka : function (s){
//        var __JOSA_KA__ = new Array("(이)가", "가", "이");
        return s + __JOSA_KA__[JslCMUtil.isJongsong(s)];
    },

    /* 내부함수 (은/는) */
    un : function (s){
//       var __JOSA_UN__ = new Array("(은)는", "는", "은");
        return s + __JOSA_UN__[JslCMUtil.isJongsong(s)];
    },

    /* 내부함수 (와/과) */
    wa : function (s){
//        var __JOSA_WA__ = new Array("(와)과", "와", "과");
        return s + __JOSA_WA__[JslCMUtil.isJongsong(s)];
    },

    /**
     * Text 사이즈를 check
     * @param {String} data
     * @param {int} min
     * @param {int} max
     */
    checkCharacterSize : function (data, min, max) {
        var total = 0;

        for (var i = 0; i < data.length; i++) {
            var a = data.charAt(i);
            /* 한글인 경우 길이가 6 이다. */
            if (escape(a).length >= 6) {
                total = total + 2;
            } else {
                total = total + 1;
            }
        }
        return total >= min && total <= max;
    },

    /**
     * Number의 크기 체크
     * data에 들어온 ',' 문자와 소수점을 제외하고 min, max를 비교한다.
     *
     * @param {number} data
     * @param {int} min
     * @param {int} max
     */
    checkNumberSize : function(data, min, max){
    	var parseData = data.replace(/[,]/g,"").replace(/[.][0-9]+$/g,"");
        if(max <= 0) {
            return parseData >= min;
        } else {
            return parseData >= min && parseData <= max;
        }
    },

    /**
     * Number의 길이 체크
     * data에 들어온 ',' 문자와 소수점을 제외하고 min, max를 비교한다.
     *
     * @param {number} data
     * @param {int} min
     * @param {int} max
     */
    checkNumberLength : function(data, min, max){
    	var parseData = data.replace(/[,]/g,"").replace(/[.][0-9]+$/g,"");
        var total = parseData.length;
        return total >= min && total <= max;
    },

    /**
     * 공백문자를 제외한 글자가 있는지 체크
     * @param val {String} check 할 문자열
     */
    isNull : function(val){
        return !new RegExp("[^\\s]+","g").test(val);
    },


    /**
     * 빈 문자열인 경우 false 임.
     * 앞에 +- 를 제외하고 세자리 마다찍히는 ','를 제외하고 소수점 2자리 까지만 숫자로 체크
     * @param val {String} check 할 문자열
     */
    isNaN : function(val){
        return !new RegExp("^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?$","g").test(val);
    },

    /**
     * 특수 문자가 포함되어있는지 체크
     * @param val {String} check 할 문자열
     */
    isExistSpacialChar : function(val){
        return new RegExp("[`~!@#$%^&*'\"]","g").test(val);
    },

    /**
     * checkbox/radio list 에서 check 된 개수를 return한다.
     * @param list checkbox/radio list
     */
    isCheckedCnt : function(list) {
        if (list == null) return 0;
        var result = 0;

        /* list array의 데이터가 1인 경우 */
        if (list.checked) {
            return 1;
        }
        for (var i = 0; i < list.length; i++) {
            if (list[i].checked) {
                result++;
            }
        }
        return result;
    },

    /**
     * selectbox 에서 선택된 item 의 개수를 return 한다.
     * @param item selecbox
     */
    isSelectedCnt : function (item) {
        if (item == null) return 0;
        var result = 0;

        for (var i = 0; i < item.length; i++) {
            if (item[i].selected) {
                result++;
            }
        }
        return result;
    },

    /**
     * 파일의 확장자 가져옴
     * @param val {String} 파일경로
     */
    getFileExt : function (value) {
        var ext;
        var extension = value;
        var index = extension.lastIndexOf(".");

        if (index != -1){
            ext = extension.substring(index + 1).toLowerCase();
        }

        return ext;
	},
	
	/*
	* 입력된 숫자를 문자 금액으로 변환
	* @param val {money}
	*/
	getKoreaMoney : function (money) {
		var arrayNum=new Array("","일","이","삼","사","오","육","칠","팔","구");
		var arrayUnit=new Array("","십","백","천","만 ","십만 ","백만 ","천만 ",		
	    		                "억 ","십억 ","백억 ","천억 ","조 ","십조 ","백조");
		var arrayStr= new Array();
		var numStr = String(money);
		    numStr = JslInput.toNumber(numStr);
		var len = numStr.length;
		
		var isValid = true;
		var hanStr = "";
		if (isValid) {
			for (i = 0; i < len; i++) {
				arrayStr[i] = numStr.substr(i, 1)
			}
			code = len;
			for (i = 0; i < len; i++) {
				code--;
				tmpUnit = "";
				if (arrayNum[arrayStr[i]] != "") {
					tmpUnit = arrayUnit[code];
					if (code > 4) {
						if ((Math.floor(code / 4) == Math.floor((code - 1) / 4) &&
						arrayNum[arrayStr[i + 1]] != "") ||
						(Math.floor(code / 4) == Math.floor((code - 2) / 4) &&
						arrayNum[arrayStr[i + 2]] != "")) {
							tmpUnit = arrayUnit[code].substr(0, 1);
						}
					}
				}
				hanStr += arrayNum[arrayStr[i]] + tmpUnit;
			}
		}			    		                
    	return hanStr;
	},
	
	/**
     * 년월일 에 대한 데이터 검증 추가 해야 함.(정확한 검증은 되지 않음.)
     * 
     * 주민번호 체크 
     * @param numString
     * @return true, false 규칙이 맞는지 틀린지 반환
     * 
     */
    juminCheck : function( juminNumber ){
       var return_check = false;
       var str = juminNumber;
       if(!str)return false;
       num = 0;
       num7 = 0;
       num13 = 0;
       totalnum = 0;
       chknum = 0;
       num7 = parseInt(str.substring(6,7),10) ;
       num  = parseInt(str.substring(0,1),10)   * 2 +
       		  parseInt(str.substring(1,2),10)   * 3 +
       		  parseInt(str.substring(2,3),10)   * 4 +
       		  parseInt(str.substring(3,4),10)   * 5 +
       		  parseInt(str.substring(4,5),10)   * 6 +
       		  parseInt(str.substring(5,6),10)   * 7 +
       		  parseInt(str.substring(6,7),10)   * 8 +
       		  parseInt(str.substring(7,8),10)   * 9 +
       		  parseInt(str.substring(8,9),10)   * 2 +
       		  parseInt(str.substring(9,10),10)  * 3 +
       		  parseInt(str.substring(10,11),10) * 4 +
       		  parseInt(str.substring(11,12),10) * 5;
       num13 = parseInt(str.substring(12,13),10);
       totalnum = num % 11;
       chknum   = 11 - totalnum;

       if(chknum >= 10 ) chknum = chknum - 10;
       
       if((num13 == chknum) && ( num7 == 1 || num7 == 2 ||  num7 == 3 ||  num7 == 4 ||  num7 == 5 ||  num7 == 6))	{
          return_check = true;
       } else {
          if (num7 == 5 || num7 == 6){
             return_check =  true;
          }else{
        	 return_check =  false;
          }
       }
       return return_check;
     },

    /**
     * TODO 정확한 데이터 검증이 되지 않는다. 
     * 사업자 번호 체크
     * @param vencod
     */
    isBinNo : function(vencod){
    	 
       if(!vencod)return false;
       var sum = 0;
       var getlist =new Array(10);
       var chkvalue =new Array("1","3","7","1","3","7","1","3","5");
       for(var i=0; i<10; i++) {
          getlist[i] = vencod.substring(i, i+1); 
       }
       for(var k=0; i<9; k++) {
          sum += getlist[k]*chkvalue[k]; 
       }
       sum = sum + parseInt((getlist[8]*5)/10,10);
       sidliy = sum % 10;
       sidchk = 0;

       if(sidliy != 0) {
          sidchk = 10 - sidliy; 
       } else {
       	  sidchk = 0; 
       }

       if(sidchk != getlist[9]) { 
          return false; 
       }
       return true;
    }
}

var JslCMUtil = jsl.validation;
