<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserHistory"%>
<%@page import="dao.SqliteDb"%>
<%@page import="service.HistoryPage"%>
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

#show-table{
	padding: 5px 0px;
}


td#initial-value{
	font-size : 13px;
	font-weight : bold;
}
.delete_button{
	text-align: center;
}
</style>

</head>
<body>
	<%
		
		HistoryPage hp = new HistoryPage();
		List<UserHistory> userHistoryList = new ArrayList<>();
		userHistoryList= hp.searchUserHistory();
	%>
	<h1>와이파이 정보 구하기</h1>
	
	<nav id="navbar">
		<ul class="navbar__menu">
	 		<li class="navbar__menu__item"><a href="index.jsp">홈</a></li>
	 		<li class="navbar__menu__item">| <a href="history.jsp">위치히스토리 목록</a></li>
	 		<li class="navbar__menu__item">| <a href="load-wifi.jsp">Open API 와이파이 정보가져오기</a></li>
		</ul>
	</nav>
	
	<section id="show-table">
		<table>
    		<thead>
    			<tr>
    				<th>ID</th>
    				<th>X좌표</th>
    				<th>Y좌표</th>
    				<th>조회일자</th>
    				<th>비고</th>
    			</tr>
    		</thead>
    		<tbody>
    			<%
    			if(!(hp.isSavedYn())) {
    			%>
    			
				<tr>
					<td id="initial-value" colspan='17'>위치 정보를 입력한 후에 조회해 주세요.</td>
				</tr>
				
				<%
				} else {
					for(int i=userHistoryList.size()-1 ; i >= 0 ; i--){
				%>
				
				<tr>
					<td><%=userHistoryList.get(i).getId()%></td>
					<td><%=userHistoryList.get(i).getLat()%></td>
					<td><%=userHistoryList.get(i).getLnt()%></td>
					<td><%=userHistoryList.get(i).getSearchDttm()%></td>
					<td><button onclick="hrefLink(<%=userHistoryList.get(i).getId()%>)">삭제</button></td>				
				</tr>
				
				<%
					} 
				}
				%>

    		</tbody>
    	</table>
	</section>
</body>
</html>