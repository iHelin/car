<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>管理后台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="iHelin">
    <link rel="stylesheet" href="${request.contextPath}/css/styles.min.css">
</head>
<body class="focusedform">
<div class="verticalcenter">
	<div class="panel panel-primary">
		<form action="${request.contextPath}/admin/login.do" method="post" class="form-horizontal" style="margin-bottom: 0px !important;">
			<div class="panel-body">
				<h4 class="text-center" style="margin-bottom: 25px;">管理后台</h4>
				<div class="form-group">
					<div class="col-sm-12">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user"></i></span>
							<input type="text" class="form-control" name="username" placeholder="用户名">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-12">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							<input type="password" class="form-control" name="password" placeholder="密码">
						</div>
					</div>
				</div>
			<#if error??><span class="text-danger">${error!}</span></#if>
			</div>
			<div class="panel-footer">
				<div class="pull-right">
					<button type="reset" class="btn btn-default">重置</button>
					<button type="submit" class="btn btn-primary">登录</button>
				</div>
			</div>
		</form>
	</div>
</div>
      
</body>
</html>