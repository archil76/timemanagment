<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Сообщение</title>
</head>
<body>
	<h2>${message}</h2>
	<a href='<c:url value="/${backpage}" />'>Назад</a>
	
	<c:forEach var="paramval" items="${param}">
		<h2>${paramval}</h2>	
	</c:forEach>
</body>
</html>