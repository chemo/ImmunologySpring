<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="/cabinet">Головна</a></li>
			<li>Профіль</li>
		</ol>
	</div>
</div>

<div class="row" id="validation">
	<div class="col-xs-12 col-sm-6">
	<div class="box">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-user-md"></i> <span>Ваш профіль</span>
				</div>
				<div class="box-icons">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a> <a class="expand-link"> <i class="fa fa-expand"></i>
					</a> <a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
				<div class="no-move"></div>
			</div>
			<div class="box-content">
				<form id="defaultForm" method="POST" action="/cabinet/profile/edit"
					class="form-horizontal">
					<fieldset>
						<legend></legend>
						<div class="form-group">
							<label class="col-sm-3 control-label">Прізвище</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="lastName"
									id="lastName" value="${user.lastName}" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Ім'я</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="firstName"
									value="${user.firstName}" id="firstName" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">По-батькові</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="middleName"
									id="middleName" value="${user.middleName}" />
							</div>
						</div>
					</fieldset>
				<fieldset>
						<legend></legend>

						<div class="form-group">
							<label class="col-sm-3 control-label">Логін</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="login" id="login"
									value="${user.login}" disabled="disabled"/>
							</div>
						</div>
					</fieldset>
					<div class="form-group">
						<div class="col-sm-11 col-sm-offset-3">
							<button type="submit" class="btn btn-primary">Редагувати</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6">
		<div class="box">
			<div class="box-header">
				<div class="box-name">
					<i class="fa  fa-unlock-alt"></i> <span>Змінити пароль</span>
				</div>
				<div class="box-icons">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a> <a class="expand-link"> <i class="fa fa-expand"></i>
					</a> <a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
				<div class="no-move"></div>
			</div>
			<div class="box-content">
				<div id="defaultForm-password"
					 class="form-horizontal">
					<fieldset>
						<legend></legend>

						<div class="form-group">
							<label class="col-sm-3 control-label">Поточний пароль</label>
							<div class="col-sm-5">
								<input type="password" name="oldPassword" class="form-control" name="oldPassword"
									id="oldPassword" value="" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label">Новий пароль</label>
							<div class="col-sm-5">
								<input type="password" name="password" class="form-control" id="password"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Повторіть пароль</label>
							<div class="col-sm-5">
								<input type="password" name="confirmPassword" class="form-control" id="confirmPassword"/>
							</div>
						</div>
					</fieldset>
					<div class="form-group">
						<div class="col-sm-11 col-sm-offset-3">
							<button class="btn btn-primary" onClick="changePassword();">Зберегти</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$('.form-control').tooltip();
		BootstrapValidatorScript(PatientValidator);
		BootstrapValidatorScript(PasswordValidator);
		//WinMove();
	});	
	function changePassword(){
		var oldPassword = $("#oldPassword").val();
		var password = $("#password").val();
		$.ajax({
			type : "post",
			url : "/cabinet/change/password",
			data: {
				'oldPassword': oldPassword,
				'password': password
			},
			success : function(response) {
				if(response.toString() == 'true') {
					alert('Пароль змінено успішно');
					location.href = "/cabinet";
				} else {
					alert('Невірний поточний пароль');
				}
		
			},
			error: function (request, status, error) {
				alert(error);
		    }

		});
	}
</script>
