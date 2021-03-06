<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/plugins/simditor/simditor.css" />
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/module.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/hotkeys.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/uploader.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/simditor.js"></script>
<style>
	img {
		width: auto;
		height:auto;
	    max-height: 100%;
	    max-width: 100%;
	}
</style>
<script>
	var simditor;
	var blnCheckUnload = false;
	window.onbeforeunload=function (event){
		var tips = "本页面要求您确认您要离开 - 您输入的数据可能不会被保存？";
		if (blnCheckUnload) {
			return tips;
		}
	};
	
	$("#product_name_inp").change(function(){
		blnCheckUnload = true;//离开提示失效
	})
	
	$(function() {
		//编辑器初始化
	    simditor = new Simditor({
	        textarea: $('#editor_id'),
	        toolbar:['title','bold','fontScale','italic','link','underline','color','blockquote','image','code','indent','outdent'],
	        toolbarFloat: false,
	        upload: {
	            url: '${request.contextPath}/imgupload.do',
	            params: null,
	            fileKey: 'fileDataFileName',
	            connectionCount: 3,
	            leaveConfirm: '文件正在上传，确定要离开吗？'
	        },
	        defaultImage: '${request.contextPath}/img/simditor-default.png',
	        pasteImage: true,
	        imageButton: ['upload']
	    });
	
		initImgSlt("thumbnailVal","${(product.img)!}", false);
		if("undefined" != typeof imagesjson && undefined != imagesjson){
			initImgSlt("imagesVal",imagesjson, true);
		}else{
			initImgSlt("imagesVal","",true);
		}
	});
	
	function submitForm(){
		if($('#product_form').parsley("validate")){
			blnCheckUnload = false;
			var index = layer.load(1, {
				shade: [0.1,'#000']
			});
			$.post("${request.contextPath}/admin/product_admin_edit.do",$("#product_form").serialize(),function(data){
				layer.close(index);
				if(data.status=='success'){
					window.location.href='${request.contextPath}/admin/product_admin';
				}else{
					
				}
			});
		}
	}
	
</script>
</#assign>
<@main.page title="商品管理">
<div id="page-heading">
	<ol class="breadcrumb">
		<li><a href="index">首页</a></li>
		<li>商品管理</li>
		<li><#if product??>编辑商品<#else>新增商品</#if></li>
	</ol>
	<h1><#if product??>编辑商品<#else>新增商品</#if></h1>
</div>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form class="form-horizontal myform" id="product_form" method="post" data-validate="parsley">
						<#if product?? && product.id??>
				  			<input type="hidden" value="#{product.id}" id="productId" name="id"/>
				  		</#if>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<label class="col-sm-4 control-label" for="product_name_inp">名称</label>
							  		<div class="col-sm-8">
							 			<input type="text" name="name" id="product_name_inp" placeholder="名称" class="form-control" <#if product?? && product.name??>value="${product.name?html}"</#if> data-required="true" data-rangelength="[1,20]" />
							  		</div>
							  	</div>
						  	</div>
						  	<div class="col-sm-6">
								<div class="form-group">
									<label class="col-sm-4 control-label" for="stock_input">库存</label>
							  		<div class="col-sm-8">
							 			<input type="text" id="stock_input" name="stock" placeholder="库存" <#if product?? && product.stock??>value="${product.stock?html}"</#if> class="form-control" data-required="true" data-trigger="keyup" data-type="number" />
							  		</div>
								</div>
							</div>
							<div class="col-sm-6">
							  	<div class="form-group">
							  		<label class="col-sm-4 control-label" for="price_input">原价</label>
							  		<div class="col-sm-8">
							 			<input type="text" name="price" id="price_input" placeholder="原价" class="form-control" <#if product?? && product.price??>value="${product.price?html}"</#if> data-required="true" data-trigger="keyup" data-type="number" />
							  		</div>
								</div>
							</div>
							<div class="col-sm-6">
							  	<div class="form-group">
							  		<label class="col-sm-4 control-label" for="bargin_input">促销价</label>
							  		<div class="col-sm-8">
							 			<input type="text" name="bargin" id="bargin_input" placeholder="促销价" class="form-control" <#if product?? && product.bargin??>value="${product.bargin?html}"</#if> data-required="true" data-trigger="keyup" data-type="number" />
							  		</div>
								</div>
							</div>
						  	<div class="col-sm-6">
								<div class="form-group">
									<label class="col-sm-4 control-label" for="catalogId">分类</label>
									<div class="col-sm-8">
										<select name="catalogId" id="catalogId" class="form-control" data-required="true">
											<option value="1" <#if product?? && product.catalogId?? && product.catalogId==1>selected</#if>>商城产品</option>
											<option value="2" <#if product?? && product.catalogId?? && product.catalogId==2>selected</#if>>保养产品</option>
											<option value="3" <#if product?? && product.catalogId?? && product.catalogId==3>selected</#if>>组合产品</option>
										</select>
									</div>
								</div>
							</div>
						  	<div class="col-sm-6">
							  	<div class="form-group">
									<label class="col-sm-4 control-label" for="status">状态</label>
									<div class="col-sm-8">
										<select name="status" id="status" class="form-control" data-required="true">
											<option value="1" <#if product?? && product.status?? && product.status==1>selected</#if>>上架</option>
											<option value="2" <#if product?? && product.status?? && product.status==2>selected</#if>>下架</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label class="col-sm-4 control-label">商品图片</label>
									<div class="col-sm-8">
										<input type="hidden" id="thumbnailVal" name="img" category="product" sizeHint="" value=""/>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
							  	<div class="form-group">
							  		<label class="col-sm-4 control-label" for="isFreePostage">是否包邮</label>
							  		<div class="col-sm-8">
							  			<label class="checkbox-inline">
					                		<input type="checkbox" id="isFreePostage" name="isFreePostage" value="1" <#if product?? && product.isFreePostage?? && product.isFreePostage==true>checked="checked"</#if> />包邮
					               		</label>
							  		</div>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="promo">促销信息</label>
									<div class="col-sm-10">
										<textarea class="form-control" id="promo" name="promo" placeholder="促销信息">${(product.promo)!}</textarea>
									</div>
						   		</div>
						   	</div>
						   	<div class="col-sm-12">
						   		<div class="form-group">
									<label class="col-sm-2 control-label" for="editor_id">商品详情</label>
									<div class="col-sm-10">
							   			<textarea class="form-control" id="editor_id" name="detail" placeholder="商品详情" autofocus>${(product.detail)!}</textarea>
							   		</div>
						   		</div>
					   		</div>
					   	</div>
					</form>
				</div>
				<div class="panel-footer" >
					<div class="row">
						<div class="col-sm-5 col-sm-offset-5">
							<div class="btn-toolbar">
								<button class="btn-primary btn" type="button" onclick="submitForm()">保存</button>
					 			<button class="btn-primary btn" type="button" onclick="history.back();">取消</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<#include "image_upload.ftl">
</@main.page>