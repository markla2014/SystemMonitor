
<#macro columns col cou>
<#list col as c>
<#if cou==0>
 <td width="285"  bgcolor="#6fb3e0"  style="color:#FFF;">${c}</td>
<#else>
<td height="40">${c}</td>
</#if>		
</#list>
</#macro>