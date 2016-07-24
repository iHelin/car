<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>车辆管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<link rel="stylesheet" href="${request.contextPath}/plugins/mui/css/mui.min.css">
		<link rel="stylesheet" href="${request.contextPath}/plugins/mui/css/iconfont.css">
	</head>

	<body>
		<div class="mui-content">
			<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media">
					<div class="mui-slider-handle">
						<img class="mui-media-object mui-pull-left" src="${request.contextPath}/plugins/mui/img/shuijiao.jpg">
						<div class="mui-media-body">
							奥迪 奥迪A4 <span class="mui-badge mui-badge-warning">默认</span>
							<p class='mui-ellipsis'>2004款 3.0L 娱乐型</p>
						</div>
					</div>
					<div class="mui-slider-right mui-disabled">
						<a class="mui-btn mui-btn-red">删除</a>
					</div>
				</li>
				<li class="mui-table-view-cell mui-media">
					<div class="mui-slider-handle">
						<img class="mui-media-object mui-pull-left" src="${request.contextPath}/plugins/mui/img/muwu.jpg">
						<div class="mui-media-body">
							宝马 宝马5系
							<p class='mui-ellipsis'>2015款 535Li 领先型</p>
						</div>
					</div>
					<div class="mui-slider-right mui-disabled">
						<a class="mui-btn mui-btn-blue">设为默认</a>
						<a class="mui-btn mui-btn-red">删除</a>
					</div>
				</li>
				<li class="mui-table-view-cell mui-media">
					<div class="mui-slider-handle">
						<img class="mui-media-object mui-pull-left" src="${request.contextPath}/plugins/mui/img/cbd.jpg">
						<div class="mui-media-body">
							比亚迪 比亚迪S3
							<p class='mui-ellipsis'>2015款 535Li 领先型</p>
						</div>
					</div>
					<div class="mui-slider-right mui-disabled">
						<a class="mui-btn mui-btn-blue">设为默认</a>
						<a class="mui-btn mui-btn-red">删除</a>
					</div>
				</li>

			</ul>
			<ul class="mui-table-view mui-table-view-chevron" style="margin-top: 10px;">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="${request.contextPath}/h5/car_list">
						<i class="fa fa-plus-circle"></i> 添加车辆
					</a>
				</li>
			</ul>
		</div>
		<script src="${request.contextPath}/plugins/mui/js/mui.min.js"></script>
	</body>

</html>