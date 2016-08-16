<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
	$(function() {
		initImgSlt("carousel_inp","${(carousel.thumbnail)!}", false);
		if("undefined" != typeof imagesjson && undefined != imagesjson){
			initImgSlt("imagesVal",imagesjson, true);
		}else{
			initImgSlt("imagesVal","",true);
		}
	});
	
	var addCarousel = function(){
		$('#edit_carousel_modal_form')[0].reset();
		$('#carousel_id_inp').val("");
		$('#modalTitle').text('新增轮播图');
		$('#add_carousel_modal').modal({
			keyboard: false
		});
	}
	
	var submitCarousel = function(){
		if($('#edit_carousel_modal_form').parsley("validate")){
			var index = layer.load(1, {
			  	shade: [0.1,'#000']
			});
			$.post("${request.contextPath}/admin/add_carousel.do",$("#edit_carousel_modal_form").serialize(),function(data){
				layer.close(index);
				if(data.status=="success"){
					window.location.reload();
				}else{
					layer.msg('糟糕，出错了');
				}
			});
		}
	}
	
	var imgDetail = function(src){
		layer.open({
		  type: 1,
		  title:'图片预览',
		  shadeClose:true,
		  content: '<img src='+src+' style="max-width:600px;max-height:600px;" />'
		});
	}
	
	var editCarousel = function(id){
		var index = layer.load(1, {
			shade: [0.1,'#000']
		});
		$.post('${request.contextPath}/admin/get_carousel',{id:id},function(data){
			layer.close(index);
			if(data.status=='success'){
				var carousel = data.carousel;
				$('#edit_carousel_modal_form')[0].reset();
				$('#carousel_id_inp').val(id);
				$('#sort_inp').val(carousel.sort);
				$("#img_val_div div div").remove();
				initImgSlt("carousel_inp",carousel.thumbnail, false);
				$('#modalTitle').text('编辑轮播图');
				$('#add_carousel_modal').modal({
					keyboard: false
				});
			}else if(data.error=="item_is_null"){
				layer.msg("获取轮播图信息异常！");
			}
		});
	}
	
	var deleteCarousel = function(id){
		layer.confirm('确定要删除该轮播图吗？', {
		  	btn: ['确定','取消'] //按钮
		}, function(){
			var index = layer.load(1, {
			  	shade: [0.1,'#000']
			});
			$.post('${request.contextPath}/admin/delete_carousel',{id:id},function(data){
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
</script>
</#assign>
<@main.page title="轮播图管理">
<div id="page-heading">
	<ol class="breadcrumb">
		<li><a href="index">首页</a></li>
		<li>轮播图管理</li>
	</ol>
	<h1>轮播图管理</h1>
</div>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-body">
					<div class="row">
						<div class="pull-right">
							<a href="javascript:;" class="btn btn-md btn-primary btn-sm" onclick="addCarousel();"><i class="fa fa-plus"></i> 新增</a>
						</div>
					 	<table class="table table-striped">
							<thead>
						    	<tr>
							    	<th class="text-center">编号</th>
							    	<th class="text-center">缩略图</th>
							    	<th class="text-center">排序值</th>
							    	<th class="text-center">创建时间</th>
							    	<th class="text-center">操作</th>
							    </tr>
						 	</thead>
						 	<tbody>
								<#if carousels??>
									<#list carousels as carousel>
										<#if carousel??>
											<tr>
												<td class="text-center" style="vertical-align: middle;">${carousel.id!}</td>
												<td class="text-center" style="vertical-align: middle;">
													<a href="javascript:void(0);" onclick="imgDetail('${carousel.thumbnail!}');">
														<img src="${carousel.thumbnail!}" style="width:35px;height:35px;">
													</a>
												</td>
												<td class="text-center" style="vertical-align: middle;">${carousel.sort!}</td>
												<td class="text-center" style="vertical-align: middle;">${carousel.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
												<td class="text-center" style="vertical-align: middle;">
													<button class="btn btn-sm btn-primary-alt" onclick="editCarousel(${carousel.id!});"><i class="fa fa-edit"></i> 编辑</button>
													<button class="btn btn-sm btn-danger-alt" onclick="deleteCarousel(${carousel.id!});"><i class="fa fa-times"></i> 删除</button>
												</td>
											</tr>
										</#if>
									</#list>
								</#if>
						 	</tbody>
						</table>
						<#import "pagination.ftl" as pager>
						<#assign currentUrl>carousel_admin?</#assign>
						<@pager.pageul pagination=pagination url="${currentUrl}" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade form-group" id="add_carousel_modal" tabindex="-1" role="dialog" aria-labelledby="modalTitle">
	<div id="" class="modal-dialog" role="document" style="max-width:700px;width:auto;">
		<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times"></i></span></button>
        		<h4 class="modal-title" id="modalTitle">新增轮播图</h4>
      		</div>
      		<div class="modal-body">
      			<form class="form-horizontal myform" method="post" id="edit_carousel_modal_form" data-validate="parsley">
	            	<input type="hidden" name="carouselId" id="carousel_id_inp">
	            	<div class="form-group" id="img_val_div">
	                	<label for="task_project_id_inp" class="col-sm-3 control-label">图片</label>
	                	<div class="col-sm-6">
	                		<input type="hidden" id="carousel_inp" name="thumbnail" category="carousel" sizeHint="" data-required="true">
	                	</div>
	           		</div>
	           		<div class="form-group">
	                	<label class="col-sm-3 control-label">排序</label>
	                	<div class="col-sm-6">
	                		<input type="text" name="sort" id="sort_inp" class="form-control" data-required="true" data-trigger="keyup" data-type="number">
	                	</div>
	           		</div>
	       		</form>
			</div>
      		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        		<button type="button" class="btn btn-primary" onclick="submitCarousel();">保存</button>
      		</div>
    	</div>
  	</div>
</div>
<#include "image_upload.ftl">
</@main.page>