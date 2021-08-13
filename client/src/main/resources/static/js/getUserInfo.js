if(!$.cookie("openid")){  
	var fromurl=location.href;
	var code = GetQueryString("code");
	if(code==null){
		var url='https://open.weixin.qq.com/connect/oauth2/authorize?appid='+base.appid+'&redirect_uri='+encodeURIComponent(fromurl)+'&response_type=code&scope=snsapi_base&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect';
	    window.location.href=url;
	}
	else{
		document.write("<script src='../js/getOpenid.js'><\/script>");
	}
}
