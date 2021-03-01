<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Изменение</title>
</head>
<body>
	<h3>Изменение задачи</h3>
	<form method="post">
		
		<input type="hidden" value="${developerTask.id}" name="id" /> <label>Наименование</label><br>
		<input name="name" value="${developerTask.name}" /><br> <br>
		Заказчик: <select name="customer">
    		<c:forEach var="item" items="${customersList}">
      		  <option value="${item}" ${item.id == developerTask.customer.id ? 'selected="selected"' : ''}>${item.name}</option>
    		</c:forEach>
		</select>
		<a href='<c:url value="/createcustomer" />'>Создать</a>
		Статус: <select name="state">
    		<c:forEach var="item" items="${taskStateList}">
      		  <option value="${item}" ${item.id == developerTask.state.id ? 'selected="selected"' : ''}>${item.description}</option>
    		</c:forEach>
		</select>
		<br><br> 
		<input type="submit" value="Сохранить" /><br><br>
	</form>
</body>
</html>