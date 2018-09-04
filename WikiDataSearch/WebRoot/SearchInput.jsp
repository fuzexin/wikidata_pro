<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Search Page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
	<form style="height: 75px; width: 973px; position: relative"
		method="post" action="SearchContent.jsp">
		<img src="img/wikilogo.bmp" alt="logo"
			style="top: 18px; position: relative;
		 width: 328px; height: 51px; left: 10px; right: 10px">
		<input type="text" name="searchText"
			style="height: 42px; width: 500px; 
		position: relative; left: 10px"><input
			type="submit" value="Search"
			style="height: 41px; 
		width: 112px;background-color: AliceBlue">
	</form>
</body>
</html>
