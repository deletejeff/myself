debugger;
function getUserInfo(){
	$.ajax({
		url: base.context + "/user/getUserInfo?code=" + code,
		type : 'POST',
		beforeSend : function(xhr) {
			//$.showLoading();
		},
		async : true,
		cache : false,
		success : function(data) {
			if(data.retcode == 0){
				$.cookie("openid", data.data.user.openId , { path: '/' });
				$.cookie("nickname", data.data.user.nickname , { path: '/' });
				$.cookie("image_url", data.data.user.headImgUrl , { path: '/' });
				getOpenidOk();
			} else {
				// location.replace(base.prefix+"about/getopenid_error.html");
			}
		}
		,error : function(xhr,status,error){
			// location.replace(base.prefix+"about/getopenid_error.html");
		}
		,complete:function(xhr){
			//$.hideLoading();
		}
	});
}
getUserInfo();
