<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
	$(function(){
		
	});
	
	function addMenu(){
		if(15!=${menus?size}){
			var left = 15-${menus?size};
			layer.msg("您当前共创建了${menus?size}个菜单，还可以创建"+left+"个！");
			$("#menu_id_inp").val("");
			changeContentType(0);
			$('#add_menu').modal({
				keyboard: false
			});
		}
		else{
			layer.alert("您已创建了15个自定义菜单，不能再继续创建，请编辑或删除菜单！");
		}
	}
	
	function editMenu(id){
		$("#menu_id_inp").val(id);
		var index = layer.load(2, {
	    	shade: [0.3, '#000']
	    });
	    $.post("${request.contextPath}/admin/get_menu_by_id",{id,id},function(data){
	    	layer.close(index);
	    	if(data.status=="success"){
	    		$("input[name=menuName]").val(data.menu.name);
	    		$("input[name=sort]").val(data.menu.sort);
	    		$("#far_menu_id").val(data.menu.parentId);
	    		if(data.menu.contentType==0){
	    			$('#textType').attr("checked","true");
	    			$("#textarea_inp").val(data.menu.content);
	    		}else if(data.menu.contentType==1){
	    			$('#linkType').attr("checked","true");
	    			$("#url_inp").val(data.menu.content);
	    		}else if(data.menu.contentType==2){
	    			$('#picType').attr("checked","true");
	    		}
	    		changeContentType(data.menu.contentType);
	    		$('#add_menu').modal({
					keyboard: false
				});
	    	}
	    });
	}
	
	function deleteMenu(id){
		layer.confirm('确定要删除该菜单吗？', {
			btn: ['确定','取消'] //按钮
		}, function(){
			var index = layer.load(2, {
	            shade: [0.3, '#000']
	        });
			$.post("${request.contextPath}/admin/delete_menu",{id:id},function(data){
				if(data.status=="success"){
					window.location.reload();
				}
			});
		}, function(){
			layer.msg('您取消了删除！');
		});
	}
	
	function changeContentType(typeId) {
		if (typeId == 0) {
			$("#text_content").show();
			$("#url_content").hide();
			$("#pic_content").hide();
		} else if (typeId ==1) {
			$("#text_content").hide();
			$("#url_content").show();
			$("#pic_content").hide();
		}else if (typeId == 2) {
			$("#text_content").hide();
			$("#url_content").hide();
			$("#pic_content").show();
		}  
	}
	
</script>
</#assign>
<@main.page title="菜单管理">
<div id="page-heading">
	<ol class="breadcrumb">
		<li><a href="index">首页</a></li>
		<li>菜单管理</li>
	</ol>
	<h1>菜单管理</h1>
</div>
<div class="container">
	<div class="row">
	    <div class="col-md-12">
			<div class="panel panel-primary">
	    		<div class="panel-body">
					<div class="row">
						<div class="col-sm-12">
							<div class="pull-right">
				               	<a role="button" class="btn btn-success" href="javascript:;" onclick="addMenu();">
				               		<i class="fa fa-plus"></i> 添加自定义菜单
				               	</a>
					  			<a role="button" class="btn btn-primary" href="${request.contextPath}/admin/service_menu_sync">
					  				<i class="fa fa-retweet"></i> 同步菜单
					  			</a>
				            </div>
						</div>
					</div>
					<p></p>
					<!--
						<div class="alert alert-dismissable alert-danger">
		    				哈哈
							<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
						</div>
					-->
					<div class="alert alert-info">
						<p>1.自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。</p>
						<p>2.若设有二级菜单，则该二级菜单的父菜单即一级菜单的功能将只是用来展开/折叠二级菜单。</p>
					</div>
					<div>
						<table class="table table-striped">
							<thead>
						    	<tr>
							    	<th class="text-center">菜单名称</th>
							      	<th class="text-center">菜单类型</th>
							      	<th class="text-center">菜单内容</th>
							      	<th class="text-center">排序值</th>
							      	<th class="text-center">操作</th>
							   	</tr>
						  	</thead>
						  	<tbody>
						  		<#list menus as menu>
						  			<#if menu.parentId==0>
										<tr class="text-center">
											<td><#if menu.parentId??>-----</#if>${menu.name!}</td>
											<td><#if menu.contentType==0>文本<#elseif menu.contentType==1>链接<#elseif menu.contentType==2>图文</#if></td>
											<td>${menu.content!}</td>
											<td>${menu.sort!}</td>
											<td>
												<a href="javascript:;" onclick="editMenu(#{menu.id!});" class="btn btn-primary-alt btn-sm tips" title="编辑"><i class="fa fa-pencil-square-o"></i></a>
												<a href="javascript:;" onclick="deleteMenu(#{menu.id!});" class="btn btn-danger-alt btn-sm tips" title="删除"><i class="fa fa-trash"></i></a>
											</td>
										</tr>
										<#list menus as subMenu>
											<#if subMenu.parentId?? && subMenu.parentId == menu.id>
												<tr class="text-center">
													<td class="text-right"><i class="fa fa-chevron-right"></i><i class="fa fa-chevron-right"></i><i class="fa fa-chevron-right"></i> ${subMenu.name!}</td>
													<td><#if subMenu.contentType==0>文本<#elseif subMenu.contentType==1>链接<#elseif subMenu.contentType==2>图文</#if></td>
													<td>${subMenu.content!}</td>
													<td>${subMenu.sort!}</td>
													<td>
														<a href="javascript:;" onclick="editMenu(#{subMenu.id!});" class="btn btn-primary-alt btn-sm tips" title="编辑"><i class="fa fa-pencil-square-o"></i></a>
														<a href="javascript:;" onclick="deleteMenu(#{subMenu.id!});" class="btn btn-danger-alt btn-sm tips" title="删除"><i class="fa fa-trash"></i></a>
													</td>
												</tr>
											</#if>
										</#list>
									</#if>
								</#list>
						  	</tbody>
						</table>
					</div>
				</div>
			</div>
	    </div>
	</div>
</div>
<div class="modal fade form-group" id="add_menu" tabindex="-1" role="dialog" aria-labelledby="menuModalTitle">
	<div id="demandModalmain" class="modal-dialog" role="document" style="max-width:60%;width:auto;">
		<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times"></i></span></button>
        		<h4 class="modal-title" id="menuModalTitle">添加自定义菜单</h4>
      		</div>
      		<div class="modal-body">
      			<form class="form-horizontal myform" method="post" id="edit_menu_modal_form" data-validate="parsley" action="${request.contextPath}/admin/service_menu_update" onsubmit="return $('#edit_menu_modal_form').parsley( 'validate' );">
	            	<input type="hidden" name="menuId" id="menu_id_inp">
	            	<div class="form-group">
	                	<label for="task_project_id_inp" class="col-sm-3 control-label">菜单名称</label>
	                	<div class="col-sm-6">
	                		<input type="text" name="menuName" class="form-control" data-maxlength="16" required="required">
	                	</div>
	                	<div class="col-md-9 col-md-offset-3">
				        	<p class="help-block"><i class="fa fa-exclamation-triangle"></i>一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。</p>
				        </div>
	           		</div>
	           		<div class="form-group">
	                	<label for="task_project_id_inp" class="col-sm-3 control-label">排序</label>
	                	<div class="col-sm-6">
	                		<input type="text" name="sort" class="form-control" data-type="digits">
	                	</div>
	           		</div>
	           		<div class="form-group">
	                	<label class="col-sm-3 control-label">上级菜单</label>
	                	<div class="col-sm-6">
	                		<select id="far_menu_id" name="parentId" class="form-control">
								<option value="0">--根菜单--</option>
								<#list menus as menu>
									<#if !(menu.parentId)?? || (menu.parentId)==0>
					  					<option value="#{menu.id!}">${menu.name!}</option>
					  				</#if>
								</#list>
							</select>
	                	</div>
	           		</div>
	           		<div class="form-group">
	           			<label class="col-sm-3 control-label">菜单类型</label>
	           			<div class="text-center">
		           			<div class="col-sm-2 form-group">
		           				<label for="textType" class="control-label">文本</label>
		           				<input type="radio" class="icheck" id="textType" name="menuType" value="0" onclick="changeContentType(this.value)" checked>
							</div>
							<div class="col-sm-2 form-group">
								<label for="linkType" class="control-label">链接</label>
								<input type="radio" class="icheck" id="linkType" name="menuType" onclick="changeContentType(this.value)" value="1">
							</div>
							<div class="col-sm-2 form-group">
								<label for="picType" class="control-label">图文</label>
								<input type="radio" class="icheck" id="picType" name="menuType" onclick="changeContentType(this.value)" value="2">
							</div>
						</div>
					</div>
					<div class="form-group" id="text_content">
				    	<label class="col-sm-3 control-label">文本内容:</label>
				       	<div class="col-sm-6">
				      		<textarea name="content" id="textarea_inp" rows="3" class="form-control"></textarea>
						</div>
				    </div>
					<div class="form-group" id="url_content">
				    	<label class="col-sm-3 control-label">链接地址:</label>
				       	<div class="col-sm-6">
				       		<input name="url" id="url_inp" class="form-control" value="">
				      	</div>
				        <div class="col-md-9 col-md-offset-3">
				        	<p class="help-block"><i class="fa fa-info-circle"></i>URL必须以http://或https://开头</p>
				        </div>
					</div>
					<div class="form-group" id="pic_content">
				    	<label class="col-sm-3 control-label">图文选择:</label>
				        <div class="col-sm-6">
				        	<a href="javascript:;" class="btn btn-success acticle_select" data-insert='articleContainer' data-name='articleIdInput' data-aid="" role="button">
				        		<i class="fa fa-picture-o"></i> 选择
				        	</a>
							<input type="hidden" id="articleIdInput" name="articleId" value="">
							<p></p>
							<div class="row" id="articleContainer"></div>
				    	</div>
					</div>
	       		</form>
			</div>
      		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        		<button type="button" class="btn btn-primary" id="submit_menu_form_button" onclick="$('#edit_menu_modal_form').submit();">保存</button>
      		</div>
    	</div>
  	</div>
</div>
</@main.page>