<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
	var deleteUser = function(id){
		layer.confirm('确定要删除吗？', {
		  	btn: ['确定','取消'] //按钮
		}, function(){
			var index = layer.load(1, {
			  	shade: [0.1,'#000']
			});
			$.post('${request.contextPath}/admin/delete_user',{id:id},function(data){
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
<@main.page title="会员管理">
<div id="page-heading">
	<ol class="breadcrumb">
		<li><a href="${request.contextPath}/admin/index">首页</a></li>
		<li>会员管理</li>
	</ol>
	<h1>会员管理</h1>
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
				   				<div class="col-sm-2">
			                     	<input class="input-small form-control"  name="nickName" type="text" value="${nickName!}" placeholder="昵称"/>
			                	</div>
			   					<div class="col-md-1">
									<button type="submit" class="btn btn-md btn-primary"><i class="fa fa-search"></i> 查询</button>
								</div>
							</div>	  
						</form>
					 	<table class="table table-striped">
							<thead>
						    	<tr>
							    	<th class="text-center">头像</th>
							    	<th class="text-center">昵称</th>
							    	<th class="text-center">性别</th>
							    	<th class="text-center">关注时间</th>
							    	<th class="text-center">地区</th>
							    	<th class="text-center">手机</th>				    	
							    	<th class="text-center">操作</th>
							    </tr>
						 	</thead>
						 	<tbody>
								<#if users??>
									<#if users?size==0>
										<tr><td align="center" colspan="9">暂无任何用户</td><tr>
									<#else>
										<#list users as user>
											<#if user??>
												<tr>
													<td class="text-center" style="vertical-align: middle;"><img src="${user.headimgurl}" style="width:30px;height:30px"></td>
													<td class="text-center" style="vertical-align: middle;">
														${user.nickName!}
														<#if user.status==0>
															<span class="label label-primary">普通</span>
														<#elseif user.status==1>
															<span class="label label-success">会员</span>
														</#if>
													</td>
													<td class="text-center" style="vertical-align: middle;"><#if user.gender==1>男<#elseif user.gender==2>女</#if></td>
													<td class="text-center" style="vertical-align: middle;">${user.subscribeTime?string("yyyy-MM-dd")}</td>
													<td class="text-center" style="vertical-align: middle;">${user.province!}${user.city!}</td>
													<td class="text-center" style="vertical-align: middle;">${user.phone!"未设置"}</td>
													<td class="text-center" style="vertical-align: middle;">
														<button class="btn btn-sm btn-danger-alt tips" title="删除" onclick="deleteUser(#{user.id!});"><i class="fa fa-trash"></i></button>
													</td>
												</tr>
											</#if>
										</#list>
									</#if>
								</#if>
						 	</tbody>
						</table>
						<#import "pagination.ftl" as pager>
						<#assign currentUrl>user_admin?</#assign>
						<@pager.pageul pagination=pagination url="${currentUrl}" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</@main.page>