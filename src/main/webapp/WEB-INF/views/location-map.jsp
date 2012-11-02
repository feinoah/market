<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=true&language=zh_CN"></script>
<script type="text/javascript">
var map;
var poly;
var latLngs=[];
var index=0;
var maxLen;
var timmer;
var delay=3000;

function initialize(){
	var center = new google.maps.LatLng(22.543675, 114.057741);
	var myOptions = {
		zoom : 15,
		center : center,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("map"),
			myOptions);
	var polyOptions = {
          strokeColor: '#F96D6D',
          strokeOpacity: 1.0,
          strokeWeight: 3
    };
	poly=new google.maps.Polyline(polyOptions);
    poly.setMap(map);
    
    $.getJSON('/query/locations/${param.deviceId}/${portalUser.id}?type=${param.type}&startTime=${param.startTime}&endTime=${param.endTime}', function(data) {
		var json=$.parseJSON(data);
		latLngs=json.lists;
		timmer=window.setTimeout(function(){  
	        refreshMap(map);
	    }, delay);
	});
	
}

google.maps.event.addDomListener(window, 'load', initialize);

//Refresh map function  
function refreshMap(map){
	if(index>=latLngs.length){
		clearTimeout(timmer);
	} else {
		var coordinate=new google.maps.LatLng(latLngs[index].lat, latLngs[index].lng);
		poly.getPath().push(coordinate);
		map.setCenter(coordinate);
		new google.maps.Marker({
			position: coordinate, 
			//icon:'',
			map: map
		});
		index++;
		timmer=window.setTimeout(function(){  
	        refreshMap(map);
	    }, delay);
	}
}
</script>
<style type="text/css">
body{margin:0 auto;height:450px;}
#map{width:100%;height:100%}
</style>
</head>
<body>
	<div id="map">
	</div>
</body>
</html>