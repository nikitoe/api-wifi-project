/**
 * 나의 현재 위치 검색 기능
 */

// 나의 현재 위치 가져오기
function askForCoords(){
	navigator.geolocation.getCurrentPosition(onGeoOk,onGeoError);
}

// 가져온 현재 위치 값을(위도와 경도) text에 입력
function onGeoOk(position){
    const lat = position.coords.latitude;
    const lnt = position.coords.longitude;
    
    document.getElementById("input-lat").value = lat;
    document.getElementById("input-lnt").value = lnt;
}

// 에러 발생시 경고(위치 허용/거부 등)
function onGeoError(){
    alert("현재 위치를 찾을 수 없습니다.(허용 버튼을 눌러주세요.)");
}