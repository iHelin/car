<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
	$(function(){
		
	});
</script>
</#assign>
<@main.page title="首页">
<div id="page-heading">
	<ol class="breadcrumb">
		<li><a href="index">首页</a></li>
	</ol>
	<h1>首页</h1>
</div>
<div class="container">
	<div class="row">
		<div class="col-md-4 col-xs-12 col-sm-6">
	        <a class="info-tiles tiles-green" href="product_admin">
	            <div class="tiles-heading">商品管理</div>
	            <div class="tiles-body-alt">
	                <i class="fa fa-gift"></i>
	                <div class="text-center"><#if productCount??>${productCount?c}<#else>0</#if></div>
	            </div>
	            <div class="tiles-footer"></div>
	        </a>
	    </div>
	    <div class="col-md-4 col-xs-12 col-sm-6">
	        <a class="info-tiles tiles-midnightblue" href="order_admin">
	            <div class="tiles-heading">订单管理</div>
	            <div class="tiles-body-alt">
	                <i class="fa fa-globe"></i>
	                <div class="text-center"><#if orderCount??>${orderCount?c}<#else>0</#if></div>
	            </div>
	            <div class="tiles-footer"></div>
	        </a>
	    </div>
	    <div class="col-md-4 col-xs-12 col-sm-6">
	        <a class="info-tiles tiles-indigo" href="user_admin">
	            <div class="tiles-heading">会员管理</div>
	            <div class="tiles-body-alt">
	               <i class="fa fa-user"></i>
	                <div class="text-center"><#if userCount??>${userCount?c}<#else>0</#if></div>
	            </div>
	            <div class="tiles-footer"></div>
	        </a>
	    </div>
	</div>
</div>
</@main.page>