debugger;
// var system = {};
// var p = navigator.platform;
// var u = navigator.userAgent;
// system.win = p.indexOf("Win") == 0;
// system.mac = p.indexOf("Mac") == 0;
// system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
// if (system.win || system.mac || system.xll) {//如果是PC转
//     if (u.indexOf('Windows Phone') > -1) {  //win手机端
//
//     }
//     else {
//     	location.replace(base.prefix+"extfunc/404.html");
//     }
// }
if(!$.cookie("openid")){
	document.write("<script src='js/getUserInfo.js'><\/script>");
}
else{
	getOpenidOk();
}
