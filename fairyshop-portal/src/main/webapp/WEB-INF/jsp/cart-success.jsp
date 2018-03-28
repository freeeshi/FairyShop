<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购物车添加成功</title>
<link rel="stylesheet" type="text/css" href="/css/taotao.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="/css/pshow.css" media="all" />

</head>
<body>

	<!-- header start -->
	<jsp:include page="commons/header.jsp" />
	<!-- header end -->

	<h1>此商品已经成功添加到购物车！</h1>
	<a href="/cart/cart.html">查看购物车</a>
	<a href="javascript:history.back(-1)">继续购物</a>


	<!-- footer start -->
	<jsp:include page="commons/footer.jsp" />
	<!-- footer end -->

</body>
</html>