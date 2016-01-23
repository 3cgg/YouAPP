<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img
	src="${pageContext.request.contextPath}/images/glyphicons_001_leaf.png"
	class="user-image" alt="User Image" /> <span class="hidden-xs">YouAPP</span>
</a>

<ul class="dropdown-menu">
	<!-- User image -->
	<li class="user-header"><img
		src="${pageContext.request.contextPath}/images/glyphicons_001_leaf.png"
		class="img-circle" alt="User Image" />
		<p>
			YouAPP <small>designed by zhongjin</small>
		</p></li>
	<!-- Menu Body -->
	<li class="user-body">
		<div class="col-xs-4 text-center">
			<a href="#">Followers</a>
		</div>
		<div class="col-xs-4 text-center">
			<a href="#">Sales</a>
		</div>
		<div class="col-xs-4 text-center">
			<a href="#">Friends</a>
		</div>
	</li>
	<!-- Menu Footer-->
	<li class="user-footer">
		<div class="pull-left">
			<a href="javascript:void(0)" class="btn btn-default btn-flat"
			onclick='GET("/login.loginaction/profile")'
			>Profile</a>
		</div>
		<div class="pull-right">
			<a href="javascript:void(0)" class="btn btn-default btn-flat"
			onclick='GET("/login.loginaction/loginout")'
			>Sign out</a>
		</div>
	</li>
</ul>