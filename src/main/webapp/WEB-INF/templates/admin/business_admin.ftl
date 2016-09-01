<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<link href="${request.contextPath}/plugins/city/city.css" rel="stylesheet">
<script src="${request.contextPath}/plugins/city/city-picker.data.js"></script>
<script src="${request.contextPath}/plugins/city/city-picker.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=B1025c9e66c30ccd43df5e69d2f5dbc7"></script>
<script>
	$(function () {
        $('#city_picker').citypicker();
    });
    
	var addBusiness = function(){
		$('#edit_business_modal_form')[0].reset();
		$('#city_picker').citypicker('reset');
		$('#business_id_inp').val("");
		$('#modalTitle').text('新增商家');
		$('#add_business_modal').modal({
			keyboard: false
		});
	}
	
	var submitForm = function(){
		if($('#edit_business_modal_form').parsley("validate")){
			var index = layer.load(1, {
				shade: [0.1,'#000']
			});
			var zone = $('#city_picker').val().replace(/\//g,"");
			var address = zone+$('#shop_address_inp').val();
			var map = new BMap.Map("allmap");
			var myGeo = new BMap.Geocoder();
			myGeo.getPoint(address, function(point){
				if (point) {
					$('#longitude_inp').val(point.lng);
					$('#latitude_inp').val(point.lat);
					$.post("${request.contextPath}/admin/add_business.do",$("#edit_business_modal_form").serialize(),function(data){
						layer.close(index);
						if(data.status=="success"){
							window.location.reload();
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
	
	//删除商家
	var deleteBusiness = function(id){
		layer.confirm('确定要删除该商家吗？', {
		  	btn: ['确定','取消'] //按钮
		}, function(){
			var index = layer.load(1, {
			  	shade: [0.1,'#000']
			});
			$.post('${request.contextPath}/admin/delete_business',{id:id},function(data){
				layer.close(index);
				if(data.status=='success'){
					layer.msg("删除成功！");
					setTimeout(function(){
						window.location.reload();
					},500);
				}else{
					layer.msg("删除异常！");
				}
			});
		}, function(){
		  	
		});
	}
	
	function edit(id){
		var index = layer.load(1, {
			shade: [0.1,'#000']
		});
		$.post('${request.contextPath}/admin/get_business',{id:id},function(data){
			layer.close(index);
			if(data.status=='success'){
				var business = data.business;
				$('#edit_business_modal_form')[0].reset();
				$('#business_id_inp').val(id);
				$('#business_name_inp').val(business.name);
				$('#modalTitle').text('编辑商家');
				$('#city_picker').citypicker('destroy');
				$('#city_picker').val(business.zone);
				$('#city_picker').citypicker();
				$('#address_inp').val(business.address);
				$('#contact_name_inp').val(business.contactName);
				$('#contact_inp').val(business.phone);
				$('#add_business_modal').modal({
					keyboard: false
				});
			}else if(data.error=="item_is_null"){
				layer.msg("获取商家信息异常！");
			}
		});
	}
</script>
</#assign>
<@main.page title="商家管理">
<div id="page-heading">
	<ol class="breadcrumb">
		<li><a href="index">首页</a></li>
		<li>商家管理</li>
	</ol>
	<h1>商家管理</h1>
</div>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-body">
					<div class="row">
						<form class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label"><strong>关键词:</strong></label>
				   				<div class="col-sm-3">
			                     	<input class="input-small form-control"  name="keyword" type="text" value="${keyword!}" placeholder="商家名称"/>
			                	</div>
			   					<div class="col-md-1">
									<button type="submit" class="btn btn-md btn-primary"><i class="fa fa-search"></i> 查询</button>
								</div>
							</div>
						</form>
					 	<div class="pull-right">
							<a href="${request.contextPath}/admin/business_edit" class="btn btn-md btn-primary btn-sm"><i class="fa fa-plus"></i> 新增</a>
						</div>
					 	<table class="table table-striped">
							<thead>
						    	<tr>
							    	<th class="text-center">名称</th>
							    	<th class="text-center">地址</th>
							    	<th class="text-center">联系人</th>
							    	<th class="text-center">联系方式</th>
							    	<th class="text-center">操作</th>
							    </tr>
						 	</thead>
						 	<tbody>
								<#if businesses??>
									<#list businesses as business>
										<#if business??>
											<tr>
												<td class="text-center" style="vertical-align:middle;">${business.name!?html}</td>
												<td class="text-center" style="vertical-align:middle;"><small>${business.zone?replace("/","")}<br>${business.address!}</small></td>
												<td class="text-center" style="vertical-align:middle;">${business.contactName!}</td>
												<td class="text-center" style="vertical-align:middle;">${business.phone!}</td>
												<td class="text-center" style="vertical-align:middle;">
													<a href="${request.contextPath}/admin/business_edit?businessId=#{business.id!}" class="btn btn-sm btn-primary-alt tips" title="编辑"><i class="fa fa-edit"></i></a>
													<button class="btn btn-sm btn-danger-alt tips" title="删除" onclick="deleteBusiness(#{business.id!});" type="button"><i class="fa fa-trash"></i></button>
												</td>
											</tr>
										</#if>
									</#list>
								</#if>
						 	</tbody>
						</table>
						<#import "pagination.ftl" as pager>
						<#assign currentUrl>business_admin?</#assign>
						<@pager.pageul pagination=pagination url="${currentUrl}" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade form-group" id="add_business_modal" tabindex="-1" role="dialog" aria-labelledby="modalTitle">
	<div id="" class="modal-dialog" role="document" style="max-width:700px;width:auto;">
		<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times"></i></span></button>
        		<h4 class="modal-title" id="modalTitle">新增商家</h4>
      		</div>
      		<div class="modal-body">
      			<form class="form-horizontal myform" method="post" id="edit_business_modal_form" data-validate="parsley">
	            	<input type="hidden" name="businessId" id="business_id_inp">
	            	<input type="hidden" name="longitude" id="longitude_inp">
	            	<input type="hidden" name="latitude" id="latitude_inp">
	            	<div class="form-group">
	                	<label for="business_name_inp" class="col-sm-3 control-label">商家名称</label>
	                	<div class="col-sm-6">
	                		<input type="text" name="businessName" id="business_name_inp" placeholder="必填" class="form-control" data-maxlength="20" data-required="true">
	                	</div>
	           		</div>
	           		<div class="form-group">
	                	<label for="city_picker" class="col-sm-3 control-label">所在地区</label>
	                	<div class="col-sm-6">
	                		<input type="text" readonly id="city_picker" name="zone" class="form-control"  value="" data-required="true" data-toggle="city-picker">
	                	</div>
	           		</div>
	           		<div class="form-group">
	                	<label for="address_inp" class="col-sm-3 control-label">详细地址</label>
	                	<div class="col-sm-6">
				      		<textarea name="address" id="address_inp" rows="3" class="form-control" placeholder="建议您如实填写详细地址，以便会员通过地址搜索到您的位置" data-required="true"></textarea>
						</div>
	           		</div>
	           		<div class="form-group">
	                	<label for="contact_name_inp" class="col-sm-3 control-label">联系人</label>
	                	<div class="col-sm-6">
	                		<input type="text" name="contactName" id="contact_name_inp" placeholder="必填" class="form-control" data-required="true">
	                	</div>
	           		</div>
	           		<div class="form-group">
	           			<label for="contact_inp" class="col-sm-3 control-label">联系方式</label>
	           			<div class="col-sm-6">
	                		<input type="text" name="phone" id="contact_inp" placeholder="必填" class="form-control" data-type="phone" data-required="true">
	                	</div>
					</div>
	       		</form>
			</div>
      		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        		<button type="button" class="btn btn-primary" onclick="submitForm();">保存</button>
      		</div>
    	</div>
  	</div>
</div>
</@main.page>