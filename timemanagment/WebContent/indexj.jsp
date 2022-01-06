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

.tabs {
	width: 100%;
	padding: 0px;
	margin: 0 auto;
}

.tabs>input {
	display: none;
}

.tabs>div {
	display: none;
	padding: 12px;
	border: 1px solid #C0C0C0;
	background: #FFFFFF;
}

.tabs>label {
	display: inline-block;
	padding: 7px;
	margin: 0 -5px -1px 0;
	text-align: center;
	color: #666666;
	border: 1px solid #C0C0C0;
	background: #E0E0E0;
	cursor: pointer;
}

.tabs>input:checked+label {
	color: #000000;
	border: 1px solid #C0C0C0;
	border-bottom: 1px solid #FFFFFF;
	background: #FFFFFF;
}

/*Связь закладок и содержимого*/
<c:forEach items="${developerTasksListsLists}" var="developerTasksLists" varStatus="status">
         <c:out value="#tab_${developerTasksLists.customer.id}:checked ~ #txt_${developerTasksLists.customer.id}"/>
         <c:if test="${!status.last}">,</c:if>
</c:forEach>
{display: block;}

</style>
</head>
<body>
	<h2>Мои задачи</h2>
	<table>
		<tr>
			<td><a href='<c:url value="/create" />'>Создать</a></td>
			<td><a href='<c:url value="/customers" />'>Контрагенты</a></td>
			<td><a href='<c:url value="/editsettings" />'>Настройки</a></td>
		</tr>
		<tr class="colortr">
			<th><br></th>
			<th></th>
			<th></th>
		</tr>
	</table>

	<div class="tabs">
		<c:set var="ischecked" value="checked" />
		
		<c:forEach var="developerTasksLists"
			items="${developerTasksListsLists}">
			
			<c:set var="tabid" value="tab_${developerTasksLists.customer.id}" />
			
			<c:set var="ischecked" value="${currentTab_id==null ? ischecked : (currentTab_id eq tabid ? 'checked' : '')}" />

			
			<input type="radio" name="inset" value=""
				id="tab_${developerTasksLists.customer.id}" ${ischecked}>
			<label for="tab_${developerTasksLists.customer.id}">${developerTasksLists.customer.name}</label>
			<c:set var="ischecked"></c:set>

		</c:forEach>

		<c:forEach var="developerTasksLists"
			items="${developerTasksListsLists}">
			<div id="txt_${developerTasksLists.customer.id}">
				
				<table>
					<c:forEach var="developerTasksList"
						items="${developerTasksLists.list}">
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
								<td>${developerTask.customer.name}:
									${developerTask.name}(${developerTask.durationString})-
									${developerTask.state.description}</td>
								<td></td>
								<td>
									<form method="post" action='<c:url value="/startstop" />'
										style="display: inline;">
										<input type="hidden" name="id" value="${developerTask.id}">
										<input type="hidden" name="currentTab_id" value="tab_${developerTasksLists.customer.id}">
										<input type="hidden" name="isActual"
											value="${developerTask.isActual}"> <input
											type="submit"
											value=${developerTask.isActual ? '"Stop"' : '"Start"'}
											class=${developerTask.isActual ? '"colortext"' : ''}>
									</form> <a href='<c:url value="/edit?id=${developerTask.id}&currentTab_id=tab_${developerTasksLists.customer.id}"/>'>Edit</a>

									<form method="post" action='<c:url value="/delete" />'
										style="display: inline;">
										<input type="hidden" name="id" value="${developerTask.id}">
										<input type="hidden" name="currentTab_id" value="tab_${developerTasksLists.customer.id}">
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
			</div>
		</c:forEach>

	</div>
</body>
</html>