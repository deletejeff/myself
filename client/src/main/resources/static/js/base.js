debugger;
var base = {};
base.appid = "wx16a904e7f1a7910b";
base.context = "/myself";
base.prefix = "/myself/";
base.fillTemplate = function(tmpl,obj){
	var html = tmpl;
	for(var key in obj){
		var regexp = eval("/\{" + key + "\}/ig");			
		html = html.replace(regexp,obj[key]);
	}
	return html;
};
function GetQueryString(name) {
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	   var r = window.location.search.substr(1).match(reg);
	   if (r!=null) 
		   return (r[2]); 
	   return null;
}