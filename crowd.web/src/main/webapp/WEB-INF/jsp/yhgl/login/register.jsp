<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<%@ include file="/jsp/top2.jsp"%>
<%@ include file="/jsp/param.jsp"%>
<link rel='stylesheet'
	href='<%=request.getContextPath()%>/statics/css/advert.css'>
<link rel='stylesheet'
	href='<%=request.getContextPath()%>/statics/css/yhgl/login.css'>
<link rel='stylesheet'
	href='<%=request.getContextPath()%>/statics/css/advert.css'>
<style>
.met-member {
	background-color:;
}

.login_index {
	background-color:;
}
.kbtn_green{
 background: #1abd9b none repeat scroll 0 0;
 color: #fff;
}
.kbtn_green:hover {
    background: #1dd2ad none repeat scroll 0 0;
    color: #fff;
}
</style>
<body>
	<div class="container met-head">

	<div class="row">
		<div class="col-xs-6 col-sm-6 logo">
			<ul class="list-none">
				<li><a href="<%=request.getContextPath()%>/index.jsp"
					class="met-logo"><img
						src="<%=request.getContextPath()%>/statics/images/header/logo.png" /></a></li>

				<li>用户注册</li>

			</ul>
		</div>

		<div class="col-xs-6 col-sm-6 user-info">
			<ol class="breadcrumb pull-right">

				<li style="float: right"><a
					href="<%=request.getContextPath()%>/index.jsp" title="返回首页">返回首页</a></li>
			</ol>
		</div>
	</div>

</div>

	<div class="register_index met-member">
		<div class="container">
			<form id="demandSideDeveloper" class="form-register met-form" method="post" action="" style="background:#efefef">
			<div class="form-group">
					<h3 style="text-align: center">请选择身份</h3>
				</div>
					<div class="xzbox">
						<div>
							<span id="demand" class="radio-select"></span><span
								class="spanvertical">需求方</span>
						</div>
						<p>
							我有项目，我想作为需求方在平台上<b>发布需求</b>
						</p>

					</div>
					<div class="xzbox">
						<div>
							<span id="developer" class="radio-moren"></span><span
								class="spanvertical">开发者</span>
						</div>
						<p>
							我是工程师，我想作为开发者在平台上<b>接包挣钱</b>
						</p>
					</div>
					<button class="btn btn-lg btn-primary btn-block" type="button"
					id="btnNextStep" style="width:30%;margin-left:25%;">下一步</button>
			</form>
			
			
			<form id="personalSideEnterprise" class="form-register met-form" method="post" action="" style="background:#efefef;display:none;">
			<div class="form-group">
					<h3 style="text-align: center">请选择用户类型</h3>
				</div>
					<div class="xzbox">
						<div>
							<span id="personal" class="radio-select"></span><span
								class="spanvertical">个人</span>
						</div>
				     	<p>我想作为个人用户在平台上<b>接包挣钱,发布需求</b></p>

					</div>
					<div class="xzbox">
						<div>
							<span id="enterprise" class="radio-moren"></span><span
								class="spanvertical">企业</span>
						</div>
						<p>我想入驻成为企业服务商,要在平台上<b>接包挣钱,发布需求</b></p>
					
					</div>
					<button class="btn btn-lg btn-primary btn-block" type="button"
					id="btnBackStep" style="width:30%;margin-left:25%;">上一步</button>
					<button class="btn btn-lg  btn-block kbtn_green" type="button"
					id="registerNow" style="width:40%;margin-left:20%;margin-top: 10%;">立即注册</button>
			</form>
			
			<div style="clear: 0"></div>
		</div>
	</div>
	<footer class="container met-footer">
		<script type="text/javascript">
			var page_type = "register";
		</script>
		<%@ include file="/jsp/bottom.jsp"%>
		<%@ include file="/jsp/footer/footer_bq.jsp"%>

	</footer>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">注册协议</h4>
				</div>
				<div class="modal-body">
					<div class="register-content">
						<p>您在申请注册流程中点击同意前，应当认真阅读以下协议，充分理解协议中相关条款内容，其中包括：</p>
						<p>
							<span class="underline">1、与您约定免除或限制责任的条款；</span>
						</p>
						<p>
							<span class="underline">2、与您约定法律适用和管辖的条款；</span>
						</p>
						<p>
							<span class="underline">3、其他以粗体下划线标识的重要条款。</span>
						</p>
						<p>如您对协议有任何疑问，可向平台客服咨询。</p>
						<p>
							<span class="b">【特别提示】</span>当您按照注册页面提示填写信息、阅读并同意协议且完成全部注册程序后，及表示您已充分阅读、理解并接受协议的全部内容。如您因平台服务与解放号发生争议的，适用《智慧校园云工场平台服务协议》处理。如您在使用平台服务过程中与其他用户发生争议的，依您与其他用户达成的协议处理。
						</p>
						<p>
							<span class="underline">阅读协议的过程中，如果您不同意相关协议或其中任何条款约定，您应立即停止注册。</span>
						</p>
						<div class="spacer8"></div>
						<div class="spacer8"></div>
						<p>
							<a href="<%=request.getContextPath()%>/xy/index"
								class="green font14 b" target="_blank">《智慧校园云工场平台服务协议》</a>
						</p>
					</div>
				</div>
				<div class="text-xs-center" style="margin-bottom: 10px;">
					<button type="button" id="btnAgree" class="btn btn-primary">同意协议</button>
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
	</div>
	<%@ include file="/jsp/bottom.jsp"%>
</body>
</html>