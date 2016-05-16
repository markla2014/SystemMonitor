
<#macro columns col cou>
<#list col as c>
<#if cou==0>
 <th height="40">${c}</th>
<#else>
<td height="40">${c}</td>
</#if>		
</#list>
</#macro>