/*******************************************************************************
 * **** 警告提示框插件*****<br>
 * 原js来自ZDocker Sunny(tufeiping@gmail.com)修改原有（ZDocker）版本<br>
 * 将确认后动作的地址加入函数处理方式，并增加了事件支持 如果使用cb回调事件，最好不要设置url参数<br>
 ******************************************************************************/

(function($) {
	$.fn.balert = function(options) {
		var defaults = {
			url : null,
			title : '',
			close : false,
			id : 0,
			div : '.table',
			msgid : 'balertid-you-can-replace-it',
			cb : null,
			type : 'warning' // [warning, alert]
		};
		var opts = $.extend(defaults, options);
		$(this)
				.click(
						function() {
							var warning = opts.type == 'warning';
							var msghtml = '<div id="' + opts.msgid;
							if (warning)
								msghtml += '" class="alert alert-warning alert-dismissable">';
							else
								msghtml += '" class="alert alert-success alert-dismissable">';
							msghtml += '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">关闭</button>';
							if (warning)
								msghtml += '<strong>警告!</strong> ' + opts.title;
							else
								msghtml += '<strong>提示!</strong> ' + opts.title;
							if (warning) {
								if (opts.cb != null) {
									msghtml += '<a id="'
											+ opts.msgid
											+ '_ok" class="btn btn_xs btn_primary">确定</a>';
								} else {
									if (opts.url != null) {
										if (opts.url instanceof Function) {
											var target = opts.url($(this));
											msghtml += '<a href="'
													+ target
													+ '" class="btn btn_xs btn_primary">确定</a>';
										} else
											msghtml += '<a href="'
													+ opts.url
													+ '" class="btn btn_xs btn_primary">确定</a>';
									} else
										msghtml += '<a href="#" class="btn btn_xs btn_primary">确定</a>';
								}
							}
							msghtml += '</div>';
							var msgidobj = $('#' + opts.msgid);
							if (msgidobj)
								msgidobj.remove();
							var msg = $(msghtml).hide();
							$(opts.div).before(msg);
							var self = $(this);
							if (opts.cb != null) {
								$('#' + opts.msgid + '_ok').click(function() {
									opts.cb(self);
								});
							}
							msg.show("slow");
						});
	};
})(jQuery);

jQuery.balert = function(opts) {
	var tmpDiv = $("<div>&nbsp;</div>");
	tmpDiv.balert(opts);
	tmpDiv.click();
	tmpDiv.remove();
};