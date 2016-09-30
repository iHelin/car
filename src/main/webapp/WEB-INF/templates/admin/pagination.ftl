<#macro pageul pagination url extra...>
	<#if extra?size gt 0>
		<#if extra?keys?seq_contains("noCount")>
			<#assign noCount=extra['noCount'] />
		<#else>
			<#assign noCount="0" />
		</#if>
	<#else>
		<#assign noCount="0" />
	</#if>
	<#if noCount=="0">
	<div class="row">
		<div class="col-xs-5" style="margin-top:25px">共#{pagination.totalCount}条，#{pagination.totalPageNum}页</div>
	</#if>
		<#if pagination.totalPageNum gt 0>
		<div class="col-xs-7">
			<ul class="pagination pull-right">
			<#if pagination.currentPage &gt; 1>
			  <li><a href="${url}pageNum=1">&laquo;</a></li>
			<#else>
				<li class="disabled"><a href="javascript:;">&laquo;</a></li>
			</#if>
			<#if pagination.startPage &gt; 1>
			  <li class="disabled"><span>...</span></li>
			</#if>
			<#list pagination.startPage..pagination.endPage as page>
			  <li<#if page=pagination.currentPage> class="active"</#if>>
			  	<a href="${url}pageNum=#{page}">#{page}</a>
			  </li>
			</#list>
			<#if pagination.endPage &lt; pagination.totalPageNum>
			  <li class="disabled"><span>...</span></li>
			</#if>
			<#if pagination.currentPage &lt; pagination.totalPageNum>
			  <li><a href="${url}pageNum=#{pagination.totalPageNum}">&raquo;</a></li>
			<#else>
				<li class="disabled"><a href="javascript:;">&raquo;</a></li>
			</#if>
			</ul>
		</div>
		</#if>
	<#if noCount=="0">
	</div>
	</#if>
</#macro>