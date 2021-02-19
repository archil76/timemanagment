<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Мои задачи</title>
<style>
.colortext {
	background-color: #ffff00;
}

.colortr {
	background-color: #e0ffff;
}
</style>
</head> 
<body>
	<h2>Мои задачи</h2>
	<%-- 	<% System.out.println( "debug= " + pageContext.findAttribute("developerTasksLists") ); %> --%>
	<table>
		<tr>
			<td><a href='<c:url value="/create" />'>Создать</a></td>
			<td></td>
			<td><a href='<c:url value="/editsettings" />'>Настройки</a></td>
		</tr>
		<c:forEach var="developerTasksList" items="${developerTasksLists}">

			<tr class="colortr">
				<th><br></th>
				<th></th>
				<th></th>
			</tr>
			<tr>
				<th><h3>${developerTasksList.state.description}</h3></th>
				<th></th>
				<th></th>
			</tr>

			<c:forEach var="developerTask" items="${developerTasksList.list}">
				<tr>
					<th>Задача / Время</th>
					<th></th>
					<th></th>
				</tr>
				<tr>
					<td>${developerTask.name}(${developerTask.durationString})-
						${developerTask.state.description}</td>
					<td></td>
					<td>
						<form method="post" action='<c:url value="/startstop" />'
							style="display: inline;">
							<input type="hidden" name="id" value="${developerTask.id}">
							<input type="hidden" name="isActual"
								value="${developerTask.isActual}"> <input type="submit"
								value=${developerTask.isActual ? '"Stop"' : '"Start"'}
								class=${developerTask.isActual ? '"colortext"' : ''}>
						</form> <a href='<c:url value="/edit?id=${developerTask.id}"/>'>Edit</a>
						<form method="post" action='<c:url value="/delete" />'
							style="display: inline;">
							<input type="hidden" name="id" value="${developerTask.id}">
							<input type="submit" value="x">
						</form>
					</td>
					<c:forEach var="timeInterval"
						items="${developerTask.timeIntervals}">
						<tr>
							<td>${timeInterval}</td>
							<td></td>
							<td></td>
						</tr>
					</c:forEach>

				</tr>
			</c:forEach>
		</c:forEach>
	</table>

</body>
</html>