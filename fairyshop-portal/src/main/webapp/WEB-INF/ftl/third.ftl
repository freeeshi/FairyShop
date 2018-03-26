<html>
<head>
	<title>${title!"DefaultTitle"}</title>
</head>
<body>
	<p>网页描述：${desc!"是一个测试"}</p>

	<lable>second的内容：</lable><br>
	<#include "second.ftl"><br><br>

	<table border="1">
		<tr>
			<td>下标</td>
			<td>学号</td>
			<td>姓名</td>
			<td>住址</td>
		</tr>
		<#list stus as s>
			<#if s_index % 3 == 0>
			<tr style="color:blue">
			<#elseif s_index % 3 == 1>
			<tr style="color:yellow">
			<#elseif s_index % 3 == 2>
			<tr style="color:red">
			</#if>
				<td>${s_index}</td>
				<td>${s.id}</td>
				<td>${s.name}</td>
				<td>${s.addr}</td>
			</tr>
		</#list>
	</table><br>
	
	<lable>日期格式</lable>
	<#if currDate??>
		<lable>data:</lable>${currDate?date}<br>
		<lable>time:</lable>${currDate?time}<br>
		<lable>datatime:</lable>${currDate?datetime}<br>
		<lable>自定义:</lable>${currDate?string("yyyy年MM月dd日 HH:mm:ss")}<br>
	<#else>
		<lable>data为空</lable>
	</#if>
	
</body>


</html>