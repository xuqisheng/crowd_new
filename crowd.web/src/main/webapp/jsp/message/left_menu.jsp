<%@page import="com.wisedu.crowd.common.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String menuFlag = request.getParameter("flag");
%>
<%!public String setActive(String _menuFlag, String flag) {

		if (flag.equals(_menuFlag)) {
			return " active ";
		}
		if (StringUtil.isEmpty(_menuFlag) && "demand".equals(flag)) {
			return " active ";
		}
		return "";
	}%>
<div class="dropdown m-b-15 hidden-lg-up shop-user-nav">
		<button type="button" class="btn btn-primary btn-block dropdown-toggle" data-toggle="dropdown">开发者中心</button>
		<div class="dropdown-menu animate w-full" role="menu">
			<a class="dropdown-item " href="https://show.metinfo.cn/muban/M1156014/381/shop/profile.php?lang=cn">个人中心</a>
			<a class="dropdown-item active" href="https://show.metinfo.cn/muban/M1156014/381/shop/order.php?lang=cn">我的订单</a>
			<a class="dropdown-item " href="https://show.metinfo.cn/muban/M1156014/381/shop/favorite.php?lang=cn">我的收藏</a>
			<a class="dropdown-item " href="https://show.metinfo.cn/muban/M1156014/381/shop/discount.php?lang=cn">我的优惠券</a>
			<a class="dropdown-item " href="https://show.metinfo.cn/muban/M1156014/381/shop/address.php?lang=cn">收货地址</a>
			<a class="dropdown-item " href="https://show.metinfo.cn/muban/M1156014/381/shop/finance.php?lang=cn">消费明细</a>
			<a class="dropdown-item" href="https://show.metinfo.cn/muban/M1156014/381/member/basic.php?lang=cn&amp;nojump=1" target="_blank">账户设置</a>
		</div>
	</div>
<div class="panel row m-b-0 m-r-15 hidden-md-down" boxmh-h>
	<div class="panel-body">
		<h2 class="m-l-30 font-size-18 font-weight-unset">消息中心</h2>
		<ul class="list-group m-l-15 met-sidebar-nav">
			<li class="list-group-item <%=setActive(menuFlag, "demand")%>"><a
				href="<%=request.getContextPath()%>/demandMessage/index?flag=demand">需求消息</a></li>
<%-- 			<li class="list-group-item <%=setActive(menuFlag, "system")%>"><a
				href="<%=request.getContextPath() %>/systemMessage/index?flag=system">系统信息</a></li> --%>

		</ul>
	</div>
</div>