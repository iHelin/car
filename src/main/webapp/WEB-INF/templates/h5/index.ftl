<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>首页</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="${request.contextPath}/plugins/mui/css/mui.min.css">
		<link rel="stylesheet" href="${request.contextPath}/plugins/mui/css/iconfont.css">
		<!--App自定义的css-->
		<link rel="stylesheet" type="text/css" href="${request.contextPath}/plugins/mui/css/app.css" />
		<style type="text/css">

		</style>
	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
			<div class="mui-input-row mui-search">
				<input type="search" class="mui-input-clear" placeholder="">
			</div>
		</header>
		<div id="slider" class="mui-slider">
			<div class="mui-slider-group mui-slider-loop">
				<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
				<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#">
						<img src="${request.contextPath}/plugins/mui/img/yuantiao.jpg">
						<p class="mui-slider-title">静静看这世界</p>
					</a>
				</div>
				<!-- 第一张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="${request.contextPath}/plugins/mui/img/shuijiao.jpg">
						<p class="mui-slider-title">幸福就是可以一起睡觉</p>
					</a>
				</div>
				<!-- 第二张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="${request.contextPath}/plugins/mui/img/muwu.jpg">
						<p class="mui-slider-title">想要一间这样的木屋，静静的喝咖啡</p>
					</a>
				</div>
				<!-- 第三张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="${request.contextPath}/plugins/mui/img/cbd.jpg">
						<p class="mui-slider-title">Color of SIP CBD</p>
					</a>
				</div>
				<!-- 第四张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="${request.contextPath}/plugins/mui/img/yuantiao.jpg">
						<p class="mui-slider-title">静静看这世界</p>
					</a>
				</div>
				<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
				<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#">
						<img src="${request.contextPath}/plugins/mui/img/shuijiao.jpg">
						<p class="mui-slider-title">幸福就是可以一起睡觉</p>
					</a>
				</div>
			</div>
			<div class="mui-slider-indicator mui-text-right">
				<div class="mui-indicator mui-active"></div>
				<div class="mui-indicator"></div>
				<div class="mui-indicator"></div>
				<div class="mui-indicator"></div>
			</div>
		</div>
		<div class="mui-content" style="padding-top: 0;">
			<ul class="mui-table-view mui-table-view-chevron" style="margin-top: 10px;">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="${request.contextPath}/h5/car_manager">
						<span class="mui-icon mui-icon-plus"></span>添加车辆
					</a>
				</li>
			</ul>
			<div class="icondiv" style="margin-top: 10px;">
				<ul class="mui-table-view mui-grid-view mui-grid-9" style="background-color: #fff;">
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="">
		                    <span class="mui-icon mui-icon-email"><span class="mui-badge">5</span></span>
		                    <div class="mui-media-body">汽车美容</div>
		            	</a>
		           	</li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="mui-icon mui-icon-chatbubble"></span>
		                    <div class="mui-media-body">保养</div>
		            	</a>
		            </li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="mui-icon mui-icon-location"></span>
		                    <div class="mui-media-body">轮胎</div>
		            	</a>
		          	</li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="mui-icon mui-icon-search"></span>
		                    <div class="mui-media-body">救援</div>
		            	</a>
		         	</li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="mui-icon mui-icon-phone"></span>
		                    <div class="mui-media-body">车务代办</div>
		            	</a>
		           	</li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="mui-icon mui-icon-gear"></span>
		                    <div class="mui-media-body">车友活动</div>
		             	</a>
		            </li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="mui-icon mui-icon-info"></span>
		                    <div class="mui-media-body">保险</div>
		             	</a>
		        	</li>
					<li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		         		<a href="#">
		                    <span class="mui-icon mui-icon-more"></span>
		                    <div class="mui-media-body">特惠商家</div>
		            	</a>
		        	</li>
		        	<li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="${request.contextPath}/h5/user_center">
		                    <span class="mui-icon mui-icon-home"></span>
		                    <div class="mui-media-body">个人中心</div>
		            	</a>
					</li>
		        </ul>
			</div>
		</div>
		<script src="${request.contextPath}/plugins/mui/js/mui.min.js"></script>
		<script>
			mui.init({
				swipeBack: true //启用右滑关闭功能
			});
			var slider = mui("#slider");
			slider.slider({
				interval: 3000
			});
		</script>
	</body>

</html>