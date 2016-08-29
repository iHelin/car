<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
	var save = function(){
		if($('#config_form').parsley("validate")){
			var postage = $('#postage_id').val();
			$.post("${request.contextPath}/admin/set_config",$('#config_form').serialize(),function(data){
				if(data.status=="success"){
					layer.msg("保存成功！");
					window.location.reload();
				}else{
					layer.msg("保存失败！");
				}
			});
		}
	}
</script>
</#assign>
<@main.page title="商城管理">
<div id="page-heading">
	<ol class="breadcrumb">
		<li><a href="index">首页</a></li>
		<li>商城管理</li>
	</ol>
	<h1>商城管理</h1>
</div>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-12">
							<div class="" style="width:60%;margin:0 auto;" >
								<form class="form-horizontal myform" data-validate="parsley" method="post" id="config_form">
									<div class="form-group">
										<label for="access_token" class="col-sm-4 control-label"><strong>Access Token</strong></label>
								    	<div class="col-sm-8">
								      		<input type="text" class="form-control" name="accessToken" data-required="true" value="${accessToken.token?substring(0,6)}******" id="access_token" readonly>
								    	</div>
								 	</div>
								 	<div class="form-group">
										<label for="postage" class="col-sm-4 control-label"><strong>商品邮费</strong></label>
								    	<div class="col-sm-8">
								      		<input type="text" class="form-control" name="postage" data-type="number" data-required="true" value="${postage!0}" id="postage" placeholder="商品邮费">
								    	</div>
								 	</div>
									<div class="form-group">
										<label for="" class="col-sm-4 control-label"><strong>最低包邮费</strong></label>
										<div class="col-sm-8">
											<input type="text" class="form-control" name="unionPostage" data-type="number" data-required="true" value="${unionPostage!0}" id="" placeholder="最低包邮费">
										</div>
								  	</div>
								  	<div class="form-group">
								    	<div class="col-sm-offset-1 col-sm-10 text-center">
								      		<button type="button" onclick="save();" class="btn btn-primary" >保存</button>
								    	</div>
								  	</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</@main.page>