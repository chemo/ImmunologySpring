<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.sub-panel .col-sm-7{
	padding-left: 4px;
}
</style>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="#" onclick="doAjaxGet('/users');">Головна</a></li>
			<li>Медична картка</li>
		</ol>
	</div>
</div>

<div class="row">
	<div class="col-xs-3">
		<div class="box">
			<div class="box-header">
				<div class="box-name">
					<span>Інструменти</span>
				</div>
				<div class="no-move"></div>
			</div>
			<div class="box-content" style="height: 300px;">
				
				<ul class="nav nav-pills nav-stacked">
				  <li class="active">
				    <a href="#" data-toggle="modal" data-target="#create-panel">
				      Створити панель
				    </a>
				  </li>
				  <li class="active">
				    <a href="#" data-toggle="modal" data-target="#create-sub-panel">
				      Створити саб-панель
				    </a>
				  </li>
				  <li class="active">
				    <a href="#" data-toggle="modal" data-target="#create-dropdown">
				      Створити випадаючий список
				    </a>
				  </li>
				   <li class="active">
				    <a href="#" data-toggle="modal" data-target="#create-textbox">
				      Створити текстове поле
				    </a>
				  </li>
				  <li class="active">
				    <a href="#" data-toggle="modal" style="background-color: #cc181e" data-target="#remove-element">
				     	Видалити елемент
				    </a>
				  </li>
				</ul>
				<div class="col-sm-5" style="margin-top: 5px;">
					<button type="button" name="save-button"  class="btn btn-default btn-lg">Зберегти</button>
				</div>

			</div>
		</div>
	</div>
	

	<div class="col-xs-9">
		<div class="box">
			<div class="box-header">
				<div class="box-name">
					<span>Вигляд медичної картки</span>
				</div>
				<div class="no-move"></div>
			</div>
			<div class="box-content">
			<div id="container"></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	init();
	initEvents();
	initCoefficientEvents();
});
</script>
	