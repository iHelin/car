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
						<img src="${carousels[carousels?size-1].thumbnail!}">
						<p class="mui-slider-title"></p>
					</a>
				</div>
				<!-- 第一张 -->
				<#if carousels??>
		    		<#list carousels as carousel>
		    			<#if carousel??>
							<div class="mui-slider-item">
								<a href="javascript:;">
									<img src="${carousel.thumbnail!}">
									<p class="mui-slider-title">${carousel_index+1}</p>
								</a>
							</div>
						</#if>
					</#list>
				</#if>
				<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
				<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#">
						<img src="${carousels[0].thumbnail!}">
						<p class="mui-slider-title"></p>
					</a>
				</div>
			</div>
			<div class="mui-slider-indicator mui-text-right">
				<#if carousels?? && carousels?size gt 0>
    				<#list 1..carousels?size as x>
						<div class="mui-indicator mui-active"></div>
					</#list>
				</#if>
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
		                    <span class="fa fa-car"></span>
		                    <div class="mui-media-body">洗车美容</div>
		            	</a>
		           	</li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="fa fa-history"></span>
		                    <div class="mui-media-body">保养</div>
		            	</a>
		            </li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="fa fa-cog"></span>
		                    <div class="mui-media-body">轮胎</div>
		            	</a>
		          	</li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="fa fa-ambulance"></span>
		                    <div class="mui-media-body">救援</div>
		            	</a>
		         	</li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="fa fa-cab"></span>
		                    <div class="mui-media-body">车务代办</div>
		            	</a>
		           	</li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="fa fa-users"></span>
		                    <div class="mui-media-body">车友活动</div>
		             	</a>
		            </li>
		            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="#">
		                    <span class="fa fa-shield"></span>
		                    <div class="mui-media-body">保险</div>
		             	</a>
		        	</li>
					<li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		         		<a href="#">
		                    <span class="fa fa-cny"></span>
		                    <div class="mui-media-body">特惠商家</div>
		            	</a>
		        	</li>
		        	<li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
		            	<a href="${request.contextPath}/h5/user/user_center">
		                    <span class="fa fa-user"></span>
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