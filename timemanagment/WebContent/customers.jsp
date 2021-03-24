<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Контрагенты</title>
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
	<h2>Контрагенты</h2>
	
	<table>
		<tr>
			<td><a href='<c:url value="/createcustomer" />'>Создать</a></td>
			<td></td>
			<td><a href='<c:url value="/indexj" />'>Задачи</a></td>
		</tr>
		<tr>
			<td><br></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<th>Контрагент</th>
			<td></td>
			<td></td>
		</tr>
		<c:forEach var="customer" items="${customersLists}">
				<tr>
					<td>${customer.name}</td>
					<td></td>
					<td>
											
						<a href='<c:url value="/editcustomer?id=${customer.id}"/>'>Edit</a>
						
						<form method="post" action='<c:url value="/deletecustomer" />'
							style="display: inline;">
							<input type="hidden" name="id" value="${customer.id}">
							<input type="submit" value="x">
						</form>
					</td>
				</tr>
			</c:forEach>
		
	</table>

</body>
</html>