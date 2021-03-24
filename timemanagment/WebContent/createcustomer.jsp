<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Создание контрагента</title>
</head>
<body>
	<h3>Новый контрагента</h3>
	
	<form method="post" action='<c:url value="/createcustomer" />'>
		<label>Наименование</label><br> <input name="name" /><br>
		<br> <input type="submit" value="Сохранить" />
	</form>
</body>
</html>