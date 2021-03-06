<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>管理员列表</title>
<!-- web路径问题 
	不以/开头的相对路径，找资源以当前资源路径为基准，容易出问题
	以/开头的相对路径，找资源以服务器路径为基准（http：//localshost：8080），需要加上项目名
 -->
<%
	/* 获取服务器根目录，以/开头不以/结尾 */
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!-- 引入jQuery -->
<script type="text/javascript"
	src="${APP_PATH}/static/js/jquery-3.3.1.min.js"></script>
</head>

<!-- 引入Bootstrap -->
<link
	href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<!-- 引入自定义样式 -->
<link href="${APP_PATH}/static/css/common.css" rel="stylesheet">
<body>
	<!-- 用于显示通知 -->
	<div class="alert"></div>
	<!-- updateModal -->
	<div class="modal fade" id="updateManModal" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="updateModalLabel">更新管理员</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="updateManForm">
						<div class="form-group">
							<label for="updateName" class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<input type="name" class="form-control" id="updateName"
									name="manName" autofocus="autofocus" placeholder="管理员名称" />
							</div>
						</div>
						<div class="form-group">
							<label for="updatePassword1" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="updatePassword1"
									name="manPassword" placeholder="密码" />
							</div>
						</div>
						<div class="form-group">
							<label for="updatePassword2" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="updatePassword2"
									name="confirm_password" placeholder="确认密码" />
							</div>
						</div>
						<div class="form-group">
							<label for="manOptions" class="col-sm-2 control-label">ManLevel</label>
							<div class="col-sm-2">
								<select class="form-control" id="manOptions" name="manLevel">
									<option value="0">0</option>
									<option value="1">1</option>
								</select>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary" id="updateMan">更新</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- addModal -->
	<div class="modal fade" id="addManModal" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="addModalLabel">新增管理员</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="addManForm">
						<div class="form-group">
							<label for="updateName" class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<input type="name" class="form-control" id="addName"
									name="manName" autofocus="autofocus" placeholder="管理员名称" />
							</div>
						</div>
						<div class="form-group">
							<label for="updatePassword1" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="addPassword1"
									name="manPassword" placeholder="密码" />
							</div>
						</div>
						<div class="form-group">
							<label for="updatePassword2" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="addPassword2"
									name="confirm_password" placeholder="确认密码" />
							</div>
						</div>
						<div class="form-group">
							<label for="manOptions" class="col-sm-2 control-label">ManLevel</label>
							<div class="col-sm-4">
								<select class="form-control" id="manOptions" name="manLevel">
									<option value="0" selected="selected">初级权限</option>
									<option value="1">高级权限</option>
								</select>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary" id="saveMan">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<h1>SSM-CRUD</h1>
			</div>
			<div class="col-md-4 text-center">
				<h1>管理员列表</h1>
			</div>
			<div class="col-md-4 text-right">
				<ul class="list-inline" style="margin-top:15px;">
  					<li><h4><b>${sessionScope.man.manName}</b></h4></li>
  					<li><a href="${APP_PATH }/Manager/loginOut">退出</a></li>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<ul class="list-inline" style="margin-top:15px;">
  					<li><a href="${APP_PATH }/employee.jsp"><button type="button" class="btn text-primary">员工管理</button></a></li>
  					<li><a href="${APP_PATH }/department.jsp"><button type="button" class="btn text-primary">部门管理</button></a></li>
				</ul>	
			</div>
			<div class="col-md-8 text-right">
				<form class="form-inline">				
					<div class="input-group">
						<input type="text" class="form-control" id="search_val" placeholder="可根据管理员名查询">
						<div class="input-group-addon" id="search"><i class="glyphicon glyphicon-search"></i></div>
					</div>					
					<button type="button" class="btn btn-primary" id="addMan">新增</button>
					<button type="button" class="btn btn-danger" id="deleMan">删除</button>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-striped table-hover" id="man_table">
					<thead>
						<tr>
							<th class="text-center"><input type="checkbox"
								id="check_all" /></th>
							<th class="text-center">#</th>
							<th class="text-center">manName</th>
							<th class="text-center">manLevel</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody id="man_tbody">

					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6" id="page_msg"></div>
			<div class="col-md-6 text-right" id="page_nav"></div>
		</div>
	</div>

	<script type="text/javascript"
		src="${APP_PATH }/static/js/jquery.validate.min.js"></script>
	<!-- 导入自定义的js文件 -->
	<script type="text/javascript"
		src="${APP_PATH }/static/js/manager/manager.js"></script>
	<script type="text/javascript" src="${APP_PATH }/static/js/unit.js"></script>
</body>
</html>