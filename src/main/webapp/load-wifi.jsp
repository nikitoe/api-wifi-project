<%@ page import="dao.SqliteDb"%>
<%@ page import="dto.PublicWifiInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style type="text/css">
#get-information{
	text-align: center;
}
</style>
</head>
<body>
	<% 
		SqliteDb sqliteDb = new SqliteDb();
		sqliteDb.startWifiAPI();
		int totalCnt= sqliteDb.getData();
	%>
	<div id="get-information">
		<!-- openAPI에서 전체 데이터 개수 가져오기 -->
		<h1><%= totalCnt %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
			
		<a href="index.jsp">홈 으로 가기</a>
	</div>
</body>
</html>