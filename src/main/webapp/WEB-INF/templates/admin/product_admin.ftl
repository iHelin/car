<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
	//删除商品
	var deleteProduct = function(id){
		layer.confirm('确定要删除该商品吗？', {
		  	btn: ['确定','取消'] //按钮
		}, function(){
			var index = layer.load(1, {
			  	shade: [0.1,'#000']
			});
			$.post('${request.contextPath}/admin/delete_product',{id:id},function(data){
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
	
	//上架or下架
	var upDownProduct = function(id){
		var index = layer.load(1, {
			shade: [0.1,'#000']
		});
		$.post('${request.contextPath}/admin/up_down_product',{id:id},function(data){
			layer.close(index);
			if(data.status=='success'){
				window.location.reload();
			}else if(data.error=="item_is_null"){
				layer.msg("获取商品信息异常！");
			}else{
				layer.msg("出错了");
			}
		});
	}

</script>
</#assign>
<@main.page title="商品管理">
<div id="page-heading">
	<ol class="breadcrumb">
		<li><a href="${request.contextPath}/admin/index">首页</a></li>
		<li>商品管理</li>
	</ol>
	<h1>商品管理</h1>
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
			                     	<input class="input-small form-control"  name="productName" type="text" value="${productName!}" placeholder="商品名称"/>
			                	</div>
								<label class="col-sm-1 control-label"><strong>状态：</strong></label>
					 			<div class="col-sm-2">
									<select name="status" class="form-control">
										<option value="">所有</option>
										<option value="1" <#if status?? && status==1> selected</#if>>上架</option>
										<option value="2" <#if status?? && status==2> selected</#if>>下架</option>
			   						</select>
			   					</div>
			   					<div class="col-md-1">
									<button type="submit" class="btn btn-md btn-primary"><i class="fa fa-search"></i> 查询</button>
								</div>
							</div>	  
						</form>
					 	<div class="pull-right">
							<a href="${request.contextPath}/admin/product_admin_edit" class="btn btn-md btn-primary btn-sm"><i class="fa fa-plus"></i> 新增</a>
						</div>
					 	<table class="table table-striped">
							<thead>
						    	<tr>
							    	<th class="text-center" style="width:20px;">ID</th>
							    	<th class="text-center">名称</th>
							    	<th class="text-center">促销价</th>
							    	<th class="text-center">添加时间</th>
							    	<th class="text-center">销量</th>
							    	<th class="text-center">库存</th>					    							    	
							    	<th class="text-center">状态</th>						    	
							    	<th class="text-center">操作</th>
							    </tr>
						 	</thead>
						 	<tbody>
								<#if products??>
									<#if products?size==0>
										<tr><td align="center" colspan="9">暂无任何商品</td><tr>
									<#else>
										<#list products as product>
											<#if product??>
												<tr>
													<td class="text-center">${product.id!}</td>
													<td class="text-center">${product.name!}<#if product.isFreePostage>&nbsp;<small><span class="label label-warning">包邮</span></small></#if></td>
													<td class="text-center">
														<span class="link_tip" data-toggle="tooltip" title="原价：${product.price!?string.currency}" >${product.bargin!?string.currency}</span>
													</td>
													<td class="text-center">
														<#if product.createTime??>
															<#if .now?string("yyyy")==product.createTime?string("yyyy")>
																${product.createTime?string("MM-dd")}
															<#else>
																${product.createTime?string("yyyy-MM-dd")}
															</#if>
														</#if>
													</td>
													<td class="text-center">${product.sellCount!}</td>
													<td class="text-center">${product.stock!}</td>
													<td class="text-center">
														<#if product.status??>
															<#if product.status==1>
																<span class="badge badge-success">上架中</span>
															<#elseif product.status==2>
																<span class="badge badge-danger">已下架</span>
															</#if>
														</#if>
													</td>
													<td class="text-center">
														<button class="btn btn-sm btn-orange-alt" onclick="upDownProduct(${product.id!});" type="button">
															<#if product.status?? && product.status==1>
																<i class="fa fa-arrow-down"></i> 下架
															<#elseif product.status?? && product.status==2>
																<i class="fa fa-arrow-up"></i> 上架
															</#if>
														</button>
														<a class="btn btn-sm btn-primary-alt" href="${request.contextPath}/admin/product_admin_edit?productId=${product.id!}" type="button"><i class="fa fa-edit"></i> 编辑</a>
														<button class="btn btn-sm btn-danger-alt" onclick="deleteProduct(${product.id!});" type="button"><i class="fa fa-trash"></i> 删除</button>
													</td>
												</tr>
											</#if>
										</#list>
									</#if>
								</#if>
						 	</tbody>
						</table>
						<#import "pagination.ftl" as pager>
						<#assign currentUrl>product_admin?</#assign>
						<@pager.pageul pagination=pagination url="${currentUrl}" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</@main.page>