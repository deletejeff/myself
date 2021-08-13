function getUserInfo(){
	$.ajax({
		url : base.context + "/user/getOpenid",
		type : 'POST',
		data : JSON.stringify(
				{
					"code":code
				}
		),
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
				location.replace(base.prefix+"about/getopenid_error.html");
			}
		}
		,error : function(xhr,status,error){
			location.replace(base.prefix+"about/getopenid_error.html");
		}
		,complete:function(xhr){
			//$.hideLoading();
		}
	});
}
getUserInfo();
