<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>个人资料</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${request.contextPath}/plugins/mui/css/mui.min.css">
		<link rel="stylesheet" href="${request.contextPath}/plugins/mui/css/iconfont.css">
		<style>
		</style>
	</head>

	<body>
		<div class="mui-content">
			<ul class="mui-table-view mui-table-view-chevron">
				<li class="mui-table-view-cell mui-media">
					<span class="" style="line-height: 40px;">
						头像
						<span class="mui-media-body">
							<img class="mui-media-object mui-pull-right head-img" style="float:right;right:20px;position:absolute" src="${(wxUser.headimgurl)!}">
						</span>
					</span>
				</li>
				<li class="mui-table-view-cell">
					<a style="padding-right:15px;">
						昵称
						<span class="mui-pull-right" style="color: #999;">${(wxUser.nickName)!}</span>
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a style="padding-right:15px;">
						账号
						<span class="mui-pull-right" style="color: #999;">${(wxUser.name)!"未设置"}</span>
					</a>
				</li>
			</ul>
			<ul class="mui-table-view mui-table-view-chevron" style="margin-top: 20px;">
				<li class="mui-table-view-cell">
					<a style="padding-right:15px;">
						性别
						<span class="mui-pull-right" style="color: #999;">
							<#if (wxUser.gender)??>
								<#if wxUser.gender==1>
									男
								<#elseif wxUser.gender==2>
									女
								</#if>
							</#if>
						</span>
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a style="padding-right:15px;">
						地区
						<span class="mui-pull-right" style="color: #999;">${(wxUser.country)!}${(wxUser.province)!}${(wxUser.city)!}</span>
					</a>
				</li>
			</ul>
			<ul class="mui-table-view mui-table-view-chevron" style="margin-top: 20px;">
				<li class="mui-table-view-cell">
					<a style="padding-right:15px;">
						手机号
						<span class="mui-pull-right" style="color: #999;">${(wxUser.phone)!"未设置"}</span>
					</a>
				</li>
			</ul>
		</div>
	</body>

</html>