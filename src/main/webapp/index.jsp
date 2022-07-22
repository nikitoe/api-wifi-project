<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style type="text/css">
table {
    width: 100%;
    border-top: 1px solid #444444;
    border-collapse: collapse;
}
th, td {
    border: 1px solid #b4b4b4;
    padding: 10px;
    text-align: center;
}
thead tr {
    background-color: #00A972;
    color: #ffffff;
}
tbody tr:nth-child(2n) {
    background-color: #f2f2f2;
}
tbody tr:nth-child(2n+1) {
    background-color: #ffffff;
}

li {
	/* 목록 스타일을 제거 */
	list-style: none;
}

.navbar__menu {
	display: flex;
	padding: 0px 0px;
 	margin: 0;
}
.navbar__menu__item{
	padding-right: 5px;
}
#navbar {
	padding: 10px 0px;
}
#to-insert{
	padding: 10px 0px;
}

#show-table{
	padding: 5px 0px;
}


td#initial-value{
	font-size : 13px;
	font-weight : bold;
}
</style>
<script>
function askForCoords(){
	navigator.geolocation.getCurrentPosition(onGeoOk,onGeoError);
}

function onGeoOk(position){
    const lat = position.coords.latitude;
    const lnt = position.coords.longitude;
    console.log(lat, lnt)
    
    document.getElementById("input-lat").value = lat;
    document.getElementById("input-lnt").value = lnt;
}
function onGeoError(){
    alert("현재 위치를 찾을 수 없습니다.(허용 버튼을 눌러주세요.)");
}
</script>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<nav id="navbar">
		<ul class="navbar__menu">
	 		<li class="navbar__menu__item"><a href="index.jsp">홈</a></li>
	 		<li class="navbar__menu__item">| <a href="history.jsp?id=-1">위치 히스토리 목록</a></li>
	 		<li class="navbar__menu__item">| <a href="load-wifi.jsp">Open API 와이파이 정보가져오기</a></li>
		</ul>
	</nav>
	
	<section id="to-insert">
		<form action="search.jsp" method="get">
			<span id="lat">
				LAT: <input id="input-lat" type="text" name="lat" required/>
			</span>
			<span id="lnt">
				, LNT: <input id="input-lnt" type="text" name="lnt" required/>
			</span>
			<span>
				<input type="button"  onClick="askForCoords()" value="내 위치 가져오기"/>
			</span>
			<span>
				<input type="submit" value="근처 WIPI 정보 보기"/>
			</span>
		</form>
	</section>
	
	<section id="show-table">
		<table>
    		<thead>
    			<tr>
    				<th>거리(Km)</th>
    				<th>관리번호</th>
    				<th>자치구</th>
    				<th>와이파이명</th>
    				<th>도로명주소</th>
    				<th>상세주소</th>
    				<th>설치위치(층)</th>
    				<th>설치유형</th>
    				<th>설치기관</th>
    				<th>서비스구분</th>
    				<th>망종류</th>
    				<th>설치년도</th>
    				<th>실내외구분</th> 
    				<th>WIFI접속환경</th>
    				<th>X좌표</th>
    				<th>Y좌표</th>
    				<th>작업일자</th>
    			</tr>
    		</thead>
    		<tbody>
				<tr>
					<td id="initial-value" colspan='17'>위치 정보를 입력한 후에 조회해 주세요.</td>
				</tr>
    		</tbody>
    	</table>
	</section>
</body>
</html>