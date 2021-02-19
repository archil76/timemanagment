<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Настройки</title>
</head>
<body>
	<h3>Настройки</h3>
	<form method="post" action="editsettings">
		<label>Путь к базе (с параметрами): </label>
		<input name="url"  value="${url}"/> <br><br>
		<label>Пользователь: </label>
		<input name="username" value="${username}"/><br><br>
		<label>Пароль: </label>
		<input name="password" value="${password}"/><br><br>
		<br>
		<p>${error}</p>
		<input type="submit" value="Сохранить" /><br><br>
		
		
	</form>
</body>
</html>