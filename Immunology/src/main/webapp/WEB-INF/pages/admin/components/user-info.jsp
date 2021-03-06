<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.sub-panel .col-sm-7{
padding-left: 4px;
}
.DTTT{
display: none;
}

</style>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#" onclick="doAjaxGet('/users');">Головна</a></li>
			<li><a href="#" onclick="doAjaxGet('/users');">Всі користувачі</a></li>
			<li> ${user.lastName} ${user.firstName}</li>
		</ol>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="box">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-user"></i> <span>${user.lastName} 
						${user.firstName}
						</span>
				</div>
				<div class="no-move"></div>
			</div>
			<div class="box-content">
			<fieldset>
				
				<div class="col-xs-6">
				<label class="control-label">Синдроми пов'язані із користувачем:</label>
					<select id="user-syndroms" multiple="" class="form-control">
						<c:forEach items="${syndromes}" var="syndrom">
							<option>${syndrom}</option>
						</c:forEach>
					</select>
				
				<label class="control-label">Додати новий синдром:</label>
				
				<select id="not-used-syndroms" class="form-control" id="not-subscribed-syndroms">
					<c:forEach items="${allSyndroms}" var="syndrom">
						<option>${syndrom}</option>
					</c:forEach>
				</select>
				<button id="add-syndrom-button" class="btn btn-primary" style="margin-top: 10px; width: 30%;">Додати</button>
				<button id="remove-syndrom-button" class="btn btn-danger" style="margin-top: 10px; width: 30%;">Видалити</button>
				</div>
				
			</fieldset>
			</div>
		</div>
	</div>

	<input type="hidden" id="user_id" value="${user.id}"/>




<script type="text/javascript">
$("#add-syndrom-button").click(function(){
	var syndromeName = $("#not-used-syndroms").val();
	var userId = $("#user_id").val();
	console.log("You choose :" + syndromeName);
	
	$.ajax({
		type : "post",
		url :  "syndromes/template/{name}/user/{id}".replace("{id}", userId).replace("{name}", syndromeName),
		async:   false,
		success : function(response) {
				console.log("successful ajax request" + response);
				reloadPageUSingUrl('users/'+userId);
		},
		error: function (request, status, error) {
			alert(error);
	    }
	});
	
	
});

$("#remove-syndrom-button").click(function(){
	var syndromeName = $("#user-syndroms option:selected").val();
	var userId = $("#user_id").val();
	console.log("You choose to remove:" + syndromeName);
	
 	if(syndromeName!= undefined) {
 		$.ajax({
			type : "delete",
			url :  "syndromes/template/{name}/user/{id}".replace("{id}", userId).replace("{name}", syndromeName),
			success : function(response) {
				console.log("successful ajax request" + response);
				reloadPageUSingUrl('users/' + userId);
			}
		}); 
 	}
	
});


</script>