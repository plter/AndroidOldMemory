(function($){
	$.loadScripts=function(options) {
		var index = 0;

		if (options && options.scripts) {
			loadScript();
		}

		function loadScript() {
			$.getScript(options.scripts[index]).done(
					function(script, textStatus) {

						index++;
						if (index < options.scripts.length) {
							if (options.progress) {
								options.progress(index/options.scripts.length);
							}
							loadScript();
						} else {
							if (options.progress) {
								options.progress(index/options.scripts.length);
							}
							if (options.done) {
								options.done();
							}
						}
					}).fail(function(jqxhr, settings, exception) {
						if (options.fail) {
							options.fail(options.scripts[index]);
						}
					});
		}
	};
})(jQuery);

