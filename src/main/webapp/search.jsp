<%@page import="service.HistoryPage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="service.SearchData"%>
<%@page import="dao.SqliteDb"%>
<%@page import="dto.PublicWifiInfo"%>
<%@page import="java.util.List"%>
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
</head>
<body>
	<%
		SqliteDb sqliteDb = new SqliteDb();
		SearchData sd = new SearchData();
		HistoryPage hp = new HistoryPage();
		List<PublicWifiInfo> pwiList = new ArrayList<>();
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lnt = Double.parseDouble(request.getParameter("lnt"));
		pwiList = sd.calDistance(lat, lnt);
		hp.saveUserHistory(lat, lnt);
	%>
	<h1>와이파이 정보 구하기</h1>
	
	<nav id="navbar">
		<ul class="navbar__menu">
	 		<li class="navbar__menu__item"><a href="index.jsp">홈</a></li>
	 		<li class="navbar__menu__item">| <a href="history.jsp">위치히스토리 목록</a></li>
	 		<li class="navbar__menu__item">| <a href="load-wifi.jsp">Open API 와이파이 정보가져오기</a></li>
		</ul>
	</nav>
	
	<section id="to-insert">
		<form action="/search.jsp" method="get">
			<span id="lat">
				LAT: <input type="text" name="lat" required/>
			</span>
			<span id="lnt">
				, LNT: <input type="text" name="lnt" required/>
			</span>
			<span>
				<input type="button"  value="내 위치 가져오기"/>
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
 			<%
			for (int i=0;i<20;i++){
			%>
					<tr>
						<td> <%=pwiList.get(i).getDistance()%> </td>
						<td> <%=pwiList.get(i).getMgr_no()%> </td>
						<td> <%=pwiList.get(i).getWrdofc()%> </td>
						<td> <%=pwiList.get(i).getMain_nm()%> </td>
						<td> <%=pwiList.get(i).getAdres1()%> </td>
						<td> <%=pwiList.get(i).getAdres2()%> </td>
						<td> <%=pwiList.get(i).getInstl_floor()%> </td>
						<td> <%=pwiList.get(i).getInstl_ty()%> </td>
						<td> <%=pwiList.get(i).getInstl_mby()%> </td>
						<td> <%=pwiList.get(i).getSvc_se()%> </td>
						<td> <%=pwiList.get(i).getCmcwr()%> </td>
						<td> <%=pwiList.get(i).getCnstc_year()%> </td>
						<td> <%=pwiList.get(i).getInout_door()%> </td>
						<td> <%=pwiList.get(i).getRemars3()%> </td>
						<td> <%=pwiList.get(i).getLat()%> </td>
						<td> <%=pwiList.get(i).getLnt()%> </td>
						<td> <%=pwiList.get(i).getWork_dttm()%> </td>
					</tr>		
			<%		
			}
			%>
    		</tbody>
    	</table>
	</section>
</body>
</html>