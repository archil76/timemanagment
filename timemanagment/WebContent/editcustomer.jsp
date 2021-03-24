<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Изменение</title>
</head>
<body>
	<h3>Изменение контрагента</h3>
	<form method="post" action='<c:url value="/editcustomer" />'>
		<input type="hidden" value="${customer.id}" name="id" /> <label>Наименование</label><br>
		<input name="name" value="${customer.name}" />
		<br> <input type="submit" value="Сохранить" />
	</form>
</body>
</html>