<#--
	主导航栏
-->
<#macro navigation>
	<#-- 获得请求URI中的模块名，结果变量 rm -->
	<#assign r = request.getRequestURI() />      
	<#assign r = r?replace(base,"") />
	<#assign ri = r?index_of("/",2)-1 />
	<#if ri gt 1>
		<#assign rm = r[1..ri] />
	<#else>
		<#assign rm = "effect" />
	</#if>
	<#-- 设置导航列表 -->
	<#if Session?exists && Session.user?exists && Session.user.role==1>
		<#assign navs={
			"特效首页":"/effect/index.jspx",
			"系统设置":"/setting/user/index.jspx",
			"个人中心":"/center/changeInfo.jspx"
			} />
	<#else>
		<#assign navs={
			"特效首页":"/effect/index.jspx",
			"个人中心":"/center/changeInfo.jspx"
			} />
	</#if>
	<#-- 显示导航列表 -->
	<#assign ks=navs?keys>
	<#list ks as k>
		<#assign v = navs[k] />
		<#assign i = v?index_of("/",2)-1 />
		<#if i gt 1>
			<#assign m = v[1..i] />
		</#if>
		<li <#if m?exists && rm?exists && m==rm>class="on"</#if>><a href="<#if v[0..6] == 'http://'>${v}<#else>${base+v}</#if>" <#if v[0..6] == 'http://'>target="_blank"</#if>><span>${k}</span></a></li>
	</#list>
</#macro>

<#--
	权限验证
<#macro auth uri >
	<@s.action id="a" name="auth" namespace="/" executeResult="false" uri=uri/>
	<#nested a>
</#macro>
-->