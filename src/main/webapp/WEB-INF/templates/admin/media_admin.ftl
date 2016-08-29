<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
	var lookMedia = function(){
		$('#media_modal').modal({
			keyboard: false
		});
	}
</script>
</#assign>
<@main.page title="素材管理">
<div id="page-heading">
	<ol class="breadcrumb">
		<li><a href="${request.contextPath}/admin/index">首页</a></li>
		<li>素材管理</li>
	</ol>
	<h1>素材管理</h1>
</div>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-body">
					<div class="row">
					 	<table class="table table-striped">
							<thead>
						    	<tr>
							    	<th class="text-center" style="width:20px;">ID</th>
							    	<th class="text-center">标题</th>
							    	<th class="text-center">操作</th>
							    </tr>
						 	</thead>
						 	<tbody>
								<#if items??>
									<#if items?size==0>
										<tr><td align="center" colspan="9">暂无任何商品</td><tr>
									<#else>
										<#list items as item>
											<#if item??>
												<tr>
													<td class="text-center">${item.media_id!}</td>
													<td class="text-center">${item.content.news_item[0].title!}</td>
													<td class="text-center">
														<button class="btn btn-sm btn-primary-alt tips" title="查看" onclick="lookMedia();" type="button"><i class="fa fa-search"></i></button>
													</td>
												</tr>
											</#if>
										</#list>
									</#if>
								</#if>
						 	</tbody>
						</table>
						<#import "pagination.ftl" as pager>
						<#assign currentUrl>media_admin?</#assign>
						<@pager.pageul pagination=pagination url="${currentUrl}" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade form-group" id="media_modal" tabindex="-1" role="dialog" aria-labelledby="modalTitle">
	<div class="modal-dialog" role="document" style="max-width:700px;width:auto;">
		<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times"></i></span></button>
        		<h4 class="modal-title" id="modalTitle">素材预览</h4>
      		</div>
      		<div class="modal-body">
      			<div style="width:400px;margin: 0 auto;border:solid 1px">
      				
      			</div>
			</div>
    	</div>
  	</div>
</div>
</@main.page>