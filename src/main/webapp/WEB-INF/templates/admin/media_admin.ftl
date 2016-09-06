<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script src="${request.contextPath}/js/masonry.pkgd.min.js"></script>
<script>
	$(function(){
		var $container = $('.items');
			$container.masonry({
				columnWidth: '.item',
				itemSelector: '.item'
			});
	});
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
					<div class="row items">
						<#if items??>
							<#list items as item>
								<#if item??>
									<div class="col-sm-4 item">
										<div class="panel panel-success">
											<div class="panel-heading">
												${(item.update_time*1000)?number_to_date?string("yyyy/MM/dd HH:mm")}
											</div>
											<div class="panel-body">
												<#if (item.content.news_item)??>
													<#list item.content.news_item as news>
														<#if news??>
															<div>
																<a href="${news.url!}">
																	<h5>${news.title!}</h5>
																</a>
															</div>
														</#if>
													</#list>
												</#if>
											</div>
										</div>
									</div>
								</#if>
							</#list>
						</#if>
					</div>
					<#import "pagination.ftl" as pager>
					<#assign currentUrl>media_admin?</#assign>
					<@pager.pageul pagination=pagination url="${currentUrl}" />
				</div>
			</div>
		</div>
	</div>
</div>
</@main.page>