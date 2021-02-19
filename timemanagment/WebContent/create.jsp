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
		<br> <input type="submit" value="Сохранить" />
	</form>
</body>
</html>