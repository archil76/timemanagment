<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Создание задачи</title>
</head>
<body>
	<h3>Новая задача</h3>
	<form method="post">
		<label>Наименование</label><br> <input name="name" /><br>
		Заказчик: <select name="customer">
    		<c:forEach var="item" items="${customersList}">
      		  <option value="${item.id}" ${item.id == developerTask.customer.id ? 'selected="selected"' : ''}>${item.name}</option>
    		</c:forEach>
		</select>
		<br>
		<br> <input type="submit" value="Сохранить" />
	</form>
</body>
</html>