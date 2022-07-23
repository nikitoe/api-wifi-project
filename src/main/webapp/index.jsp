<%@page import="service.IndexPage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/getcurrentlocation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validationrule.js"></script>
<script>
function checkWifiApi(){
	
	<%@page import="service.IndexPage"%>
	<% 
	IndexPage indexPage = new IndexPage();
	int result = indexPage.checkApiWifi();
	%>	
	const lat  = document.getElementById("input-lat").value;
	const lnt = document.getElementById("input-lnt").value;
	
	const result = "<%=result%>";
	
	if(result == 0){
		alert("Open API 와이파이 정보를 찾을 수 없습니다.");
		return false;
	}
	
	if(!lat || !lnt){
		document.getElementById("input-lat").value = '0.0';
		document.getElementById("input-lnt").value = '0.0';
		alert("위도와 경도를 입력해주세요.");
		return false;
	}
	
	location.href = "search.jsp?lat="+ lat + "&lnt=" + lnt;
}
</script>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<nav id="navbar">
		<ul class="navbar__menu">
	 		<li class="navbar__menu__item"><a href="index.jsp">홈</a></li>
	 		<li class="navbar__menu__item">| <a href="history.jsp?id=-1">위치 히스토리 목록</a></li>
	 		<li class="navbar__menu__item">| <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a></li>
		</ul>
	</nav>
	
	<section id="to-insert">
			<span id="lat">
				LAT: <input id="input-lat" type="text" name="lat" value="0.0" onkeypress="return numberOfKey(event)" onkeyup="return deleteHangle(event)" required/>
			</span>
			<span id="lnt">
				, LNT: <input id="input-lnt" type="text" name="lnt" value="0.0" onkeypress="return numberOfKey(event)" onkeyup="return deleteHangle(event)" required/>
			</span>
			<span>
				<input type="button"  onClick="askForCoords()" value="내 위치 가져오기"/>
			</span>
			<span>
				<input type="button" onclick="checkWifiApi()" value="근처 WIPI 정보 보기"/>
			</span>
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