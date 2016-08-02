<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>个人中心</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${request.contextPath}/plugins/mui/css/mui.min.css">
		<link rel="stylesheet" href="${request.contextPath}/plugins/mui/css/iconfont.css">
	</head>

	<body>
		<div class="mui-content">
			<ul class="mui-table-view mui-table-view-chevron">
				<li class="mui-table-view-cell mui-media">
					<a class="mui-navigate-right" href="${request.contextPath}/h5/user_info">
						<img class="mui-media-object mui-pull-left head-img" id="head-img" src="${(wxUser.headimgurl)!}">
						<div class="mui-media-body">
							${(wxUser.nickName)!}
							<#if wxUser.status==0>
								<span class="mui-badge">普通用户</span>
							<#elseif wxUser.status==1>
								<span class="mui-badge mui-badge-danger">会员</span>
							</#if>
							<p class='mui-ellipsis'>账号:${(wxUser.name)!"未设置"}</p>
						</div>
					</a>
				</li>
			</ul>
			<ul class="mui-table-view mui-table-view-chevron" style="margin-top: 20px;">
				<li class="mui-table-view-cell">
					<a href="#notifications" class="mui-navigate-right">
						<i class="fa fa-file-text-o" style="color:#FF5053"></i> 我的订单
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a href="#privacy" class="mui-navigate-right">
						<i class="fa fa-cart-plus" style="color: #FF5053"></i> 我的购物车
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a href="#general" class="mui-navigate-right">
						<i class="fa fa-clipboard" style="color: #FF5053"></i> 我的维修保养记录
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a href="#general" class="mui-navigate-right">
						<i class="fa fa-money" style="color: #FF5053"></i> 我的积分
					</a>
				</li>
			</ul>
			<ul class="mui-table-view mui-table-view-chevron" style="margin-top: 20px;">
				<li class="mui-table-view-cell">
					<a href="${request.contextPath}/h5/become_user" class="mui-navigate-right">
						<i class="fa fa-user" style="color: #FF5053"></i> 成为会员
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a href="#privacy" class="mui-navigate-right">
						<i class="fa fa-file-o" style="color: #FF5053"></i> 会员章程与权益
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a href="#general" class="mui-navigate-right">
						<i class="fa fa-car" style="color: #FF5053"></i> 车辆管理
					</a>
				</li>
			</ul>
			<ul class="mui-table-view mui-table-view-chevron" style="margin-top: 20px;">
				<li class="mui-table-view-cell">
					<a href="#general" class="mui-navigate-right">
						<i class="fa fa-map-marker" style="color: #FF5053"></i> 常用收货地址
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a href="#general" class="mui-navigate-right">
						<i class="fa fa-gear" style="color: #FF5053"></i> 设置
					</a>
				</li>
			</ul>
		</div>
	</body>

</html>