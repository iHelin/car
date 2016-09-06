<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<link href="${request.contextPath}/plugins/city/city.css" rel="stylesheet">
<script src="${request.contextPath}/plugins/city/city-picker.data.js"></script>
<script src="${request.contextPath}/plugins/city/city-picker.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=B1025c9e66c30ccd43df5e69d2f5dbc7"></script>
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
	
	$("#business_name_inp").change(function(){
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
	
		initImgSlt("thumbnailVal","${(business.img)!}", false);
		if("undefined" != typeof imagesjson && undefined != imagesjson){
			initImgSlt("imagesVal",imagesjson, true);
		}else{
			initImgSlt("imagesVal","",true);
		}
		$('#city_picker').citypicker();
	});
	
	function submitForm(){
		if($('#edit_business_modal_form').parsley("validate")){
			var index = layer.load(1, {
				shade: [0.1,'#000']
			});
			var zone = $('#city_picker').val().replace(/\//g,"");
			var address = zone+$('#address_inp').val();
			var map = new BMap.Map("allmap");
			var myGeo = new BMap.Geocoder();
			myGeo.getPoint(address, function(point){
				if (point) {
					$('#longitude_inp').val(point.lng);
					$('#latitude_inp').val(point.lat);
					$.post("${request.contextPath}/admin/add_business.do",$("#edit_business_modal_form").serialize(),function(data){
						layer.close(index);
						if(data.status=="success"){
							blnCheckUnload = false;
							window.location.href='${request.contextPath}/admin/business_admin';
						}else{
							layer.msg('糟糕，出错了');
						}
					});
				}else{
					layer.msg("您选择的地址没有解析到结果!");
				}
			}, $('#city_picker').val().split(',')[1]);
		}
	}
	
</script>
</#assign>
<@main.page title="商家管理">
<div id="page-heading">
	<ol class="breadcrumb">
		<li><a href="index">首页</a></li>
		<li>商家管理</li>
		<li><#if business??>编辑商家<#else>新增商家</#if></li>
	</ol>
	<h1><#if business??>编辑商家<#else>新增商家</#if></h1>
</div>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form class="form-horizontal myform" id="edit_business_modal_form" method="post" data-validate="parsley">
						<#if business?? && business.id??>
				  			<input type="hidden" value="#{business.id}" id="businessId" name="businessId"/>
				  		</#if>
				  		<input type="hidden" name="longitude" id="longitude_inp">
	            		<input type="hidden" name="latitude" id="latitude_inp">
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
				                	<label for="business_name_inp" class="col-sm-4 control-label">商家名称</label>
				                	<div class="col-sm-8">
				                		<input type="text" name="businessName" value="${(business.name)!}" id="business_name_inp" placeholder="必填" class="form-control" data-maxlength="20" data-required="true">
				                	</div>
				           		</div>
						  	</div>
						  	<div class="col-sm-6">
							  	<div class="form-group">
				                	<label for="contact_name_inp" class="col-sm-4 control-label">联系人</label>
				                	<div class="col-sm-8">
				                		<input type="text" name="contactName" value="${(business.contactName)!}" id="contact_name_inp" placeholder="必填" class="form-control" data-required="true">
				                	</div>
				           		</div>
			           		</div>
							<div class="col-sm-6">
							  	<div class="form-group">
				           			<label for="contact_inp" class="col-sm-4 control-label">联系方式</label>
				           			<div class="col-sm-8">
				                		<input type="text" name="phone" value="${(business.phone)!}" id="contact_inp" placeholder="必填" class="form-control" data-type="phone" data-required="true">
				                	</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
				                	<label for="city_picker" class="col-sm-4 control-label">所在地区</label>
				                	<div class="col-sm-8">
				                		<input type="text" id="city_picker" name="zone" value="${(business.zone)!}" class="form-control" data-required="true" readonly>
				                	</div>
				           		</div>
			           		</div>
							<div class="col-sm-12">
								<div class="form-group">
									<label for="address_inp" class="col-sm-2 control-label">详细地址</label>
									<div class="col-sm-10">
										<textarea class="form-control" id="address_inp" name="address" placeholder="建议您如实填写详细地址，以便会员通过地址搜索到您的位置" data-required="true">${(business.address)!}</textarea>
									</div>
						   		</div>
						   	</div>
						   	<div class="col-sm-12">
								<div class="form-group">
									<label class="col-sm-2 control-label">商家图片</label>
									<div class="col-sm-10">
										<input type="hidden" id="thumbnailVal" name="img" category="business" sizeHint="" value="" />
									</div>
								</div>
							</div>
						   	<div class="col-sm-12">
						   		<div class="form-group">
									<label class="col-sm-2 control-label" for="editor_id">商家详情</label>
									<div class="col-sm-10">
							   			<textarea class="form-control" id="editor_id" name="detail" placeholder="商家详情" autofocus>${(business.detail)!}</textarea>
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