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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/history.css"></link>
<script>
function hrefLink(id){
	const link = 'history.jsp?id='+id;
	
	location.href = link;
}
</script>
</head>
<body>
	<%
		HistoryPage hp = new HistoryPage();
		List<UserHistory> userHistoryList = new ArrayList<>();
		int id = Integer.parseInt(request.getParameter("id"));
		if(id != -1){
			hp.deleteUserHistory(id);
		}
		userHistoryList= hp.searchUserHistory();
	%>
	<h1>와이파이 정보 구하기</h1>
	
	<nav id="navbar">
		<ul class="navbar__menu">
	 		<li class="navbar__menu__item"><a href="index.jsp">홈</a></li>
	 		<li class="navbar__menu__item">| <a href="history.jsp?id=-1">위치 히스토리 목록</a></li>
	 		<li class="navbar__menu__item">| <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a></li>
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
    		<tbody id="tbody-history-style">
    			<%
    			if(!(hp.isSavedYn())||userHistoryList.size()==0) {
    			%>
    			
				<tr>
					<td id="initial-value" colspan='17'>위치 정보를 입력한 후에 조회해 주세요.</td>
				</tr>
				
				<%
				} else {
					for(int i=userHistoryList.size()-1 ; i >= 0 ; i--){
				%>
				
				<tr>
					<td class="td-history-style"><%=userHistoryList.get(i).getId()%></td>
					<td class="td-history-style"><%=userHistoryList.get(i).getLat()%></td>
					<td class="td-history-style"><%=userHistoryList.get(i).getLnt()%></td>
					<td class="td-history-style"><%=userHistoryList.get(i).getSearchDttm()%></td>
					<td><button onclick="hrefLink(<%= userHistoryList.get(i).getId()%>)">삭제</button></td>				
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