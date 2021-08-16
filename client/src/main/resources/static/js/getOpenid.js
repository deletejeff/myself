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
				$.cookie("openid", data.openid , { path: '/' });
				$.cookie("nickname", data.user.nickname , { path: '/' });
				$.cookie("image_url", data.user.imageUrl , { path: '/' });
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
