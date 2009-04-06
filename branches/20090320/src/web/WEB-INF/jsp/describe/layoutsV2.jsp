<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Layouts</title>
			<link rel="stylesheet" type="text/css" media="all" href="./css/describe.css" />

<style>
.found {
	background-color: #0F0;
}
</style>
</head>

<body>

<h1>Layouts: "<s:property value="describeContext.target" />"</h1>


<s:if test="summary != null">

<p><a href="<s:url action="select" />">Select New Object</a> |  

<a href="<s:url action="describe"><s:param name="target" value="target" /></s:url>">Switch to Describe</a> </p>

<table>
	<tr>
		<th>Label</th>
		<th>API Name</th>
		<th>Not Found</th>
		<s:iterator value="summary.headers">
			<th colspan="3"><s:property /></th>
		</s:iterator>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<s:iterator value="summary.headers">
			<td>Editable?</td>
			<td>Required?</td>
			<td>Values</td>
		</s:iterator>
	</tr>
						
	<s:iterator value="summary.rows">
		<tr>
			<td><s:property value="field.label" /></td>
			<td><s:property value="field.name" /></td>
			<td ><s:if test="missing">Missing</s:if>&nbsp;</td>
			<s:iterator value="layouts">
				<s:if test="present">
					<td class="found"><s:property value="editable" /> </td>
					<td class="found"><s:property value="required" /></td>
					<td class="found">
						<s:if test="values != null">
							<s:iterator value="values" status="rowstatus">
								<s:property /><s:if test="!#rowstatus.last"><br/></s:if>
							</s:iterator>
						</s:if>
					</td>
				</s:if>
				<s:else>
					<td colspan="3">&nbsp;</td>
				</s:else>
			</s:iterator>			
				
		</tr>
	</s:iterator>
</table>

</s:if>

</body>
</html>