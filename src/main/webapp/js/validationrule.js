/**
 * input 태그 안에있는 값들의 유효성 검사
 */
 
// 한글 부분 지우기
function deleteHangle(evt){
	const objTarget = evt.srcElement || evt.target;
	// Textbox 값
	const _value =  evt.srcElement.value;
	
	if(/[ㄱ-ㅎㅏ-ㅡ가-핳]/g.test(_value)){
		objTarget.value = null;	
	}
	
}

// 숫자를 제외한 값을 입력하지 못하게 함
function numberOfKey(evt){
	const CharContent = (evt.which) ? evt.which : evt.keyCode;
	// Textbox 값
	const _value =  evt.srcElement.value;
	
	// 숫자와 .만 입력 가능
	if(evt.keyCode < 48 || evt.keyCode > 57){
		if(evt.keyCode != 46) {
			return false;
		}
	}
	// 소수점(.)이 두번 이상 나오지 못하게
	const _pattern0 = /^\d*[.]\d*$/; // 현재 value 값에 소수점(.)이 있으면 .입력 불가
	
	if(_pattern0.test(_value)){
		if(CharContent == 46){
			return false;
		}
	}
}
