$.fx.speeds._default = 1000;
/**
 * OBD 数值参考范围 
 * flag 是否有参考值
 */
var obdDataReference=[
      {'name':'发动机负荷计算值', 'flag':true,'min':15,'max':100}
	, {'name':'发动机冷却液温度', 'flag':false}
	, {'name':'发动机转速', 'flag':true,'min':600,'max':7000}
	, {'name':'里程', 'flag':true,'min':0,'max':999999}
	, {'name':'车速', 'flag':true,'min':0,'max':250}
	, {'name':'进气温度', 'flag':true,'min':-30,'max':50}
	, {'name':'空气流量', 'flag':true,'min':3,'max':80}
	, {'name':'节气门绝对值', 'flag':true,'min':3,'max':100}
	, {'name':'车辆电瓶电压', 'flag':true,'min':11.5,'max':14.5}
	, {'name':'环境温度', 'flag':true,'min':-40,'max':60}
	, {'name':'长期燃油修正', 'flag':true,'min':-8,'max':8}
	, {'name':'气缸1点火提前角', 'flag':true,'min':3,'max':25}
	, {'name':'进气歧管绝对压力', 'flag':true,'min':25,'max':100}
	, {'name':'OBD类型', 'flag':false}
	, {'name':'瞬时油耗(L/H)', 'flag':false}
	, {'name':'瞬时油耗(L/KM)', 'flag':false}
	, {'name':'当前里程', 'flag':false}
	, {'name':'保养里程', 'flag':false}
	, {'name':'保养天数', 'flag':false}
];

/**
 * 时间对象的格式化
 */
Date.prototype.format = function(format) {
	/*
	 * format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};