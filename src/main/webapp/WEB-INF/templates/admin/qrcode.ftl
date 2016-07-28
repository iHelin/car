<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>二维码生成</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   	<link href="${request.contextPath}/css/styles.min.css" rel="stylesheet" type='text/css' media="all" />
   	<script type='text/javascript' src='${request.contextPath}/js/jquery-2.2.1.min.js'></script>
   	<script src="${request.contextPath}/plugins/layer/layer.js"></script>
</head>
<body class="">
<script>
	var generate = function(){
		var content = $("#content_inp").val();
		if(content==""){
			layer.alert("请输入内容！");
			return false;
		}
		$.post('${request.contextPath}/admin/generate_img',{content:content},function(data){
			if(data.status=='success'){
				layer.msg("成功");
				$('#img_id').attr("src",data.url);
			}else{
				layer.msg("生成失败");
			}
		});
		
	}
</script>
	<div align="center">
		<span></span>
		<h1>二维码生成</h1>
	</div>
	<div class="container">
		<div class="row">
		    <div class="col-md-12">
				<div class="panel panel-primary">
		    		<div class="panel-body">
						<div class="row">
							<div class="col-sm-6">
								<div class="panel panel-gray">
									<div class="panel-body">
										<form class="form-horizontal myform" id="product_form" method="post" data-validate="parsley">
											<div class="form-group">
												<label class="col-sm-2 control-label">内容</label>
										  		<div class="col-sm-10">
													<input class="form-control" type="text" name="content" id="content_inp" >
												</div>
											</div>
										</form>
										<div align="center">
											<button class="btn btn-primary" onclick="generate()">生成</button>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="panel panel-orange">
									<div class="panel-body">
										<div style="min-width:300px;min-height:300px;margin: auto;" align="center">
											<img id="img_id" style="vertical-align: middle;" alt="二维码生成区">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
		    </div>
		</div>
		<div align="center">powered by <a href="http://weibo.com/378920717" target="_blank">iHelin</a></div>
	</div>
	
	<script type='text/javascript' src='${request.contextPath}/js/bootstrap.min.js'></script> 
	<script type='text/javascript' src='${request.contextPath}/js/enquire.js'></script> 
	<script type='text/javascript' src='${request.contextPath}/js/jquery.cookie.js'></script> 
	<script type='text/javascript' src='${request.contextPath}/js/jquery.touchSwipe.min.js'></script> 
	<script type='text/javascript' src='${request.contextPath}/js/jquery.nicescroll.min.js'></script>
	<script type='text/javascript' src='${request.contextPath}/js/application.js'></script>
	<script type='text/javascript' src='${request.contextPath}/plugins/form-parsley/parsley.min.js'></script> 
	<script type='text/javascript' src='${request.contextPath}/js/formvalidation.js'></script>
</body>
</html>