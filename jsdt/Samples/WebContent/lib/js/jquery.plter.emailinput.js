(function($){
	$.fn.mailinput=function(options){
		var mailArr=[ "163.com", 
		              "126.com", 
		              "qq.com",
		              "sina.com",
		              "vip.sina.com",
		              "hotmail.com",
		              "gmail.com",
		              "sina.cn",
		              "suho.com",
		              "yahoo.cn",
		              "139.com",
		              "wo.com.cn",
		              "189.cn"];

		if (options&&options.mails) {
			mailArr=options.mails;
		}

		$this = $(this);
		$this.autocomplete(mailArr);
		var sourceArr = [];
		var currentTxt="";
		var end=0;

		$this.bind("textchange",function(event){

			currentTxt = $this.attr("value");
			end=currentTxt.indexOf("@");
			if (end>-1) {
				currentTxt=currentTxt.substring(0, end);
			}

			sourceArr.length=0;

			for ( var i = 0; i < mailArr.length; i++) {
				sourceArr.push(currentTxt + "@" + mailArr[i]);
			}

			$this.autocomplete({
				source : sourceArr
			});
		});
	};
})(jQuery);