const locations = [
    {lat: 27.898193, lng: 78.087989},
    {lat: 28.544951, lng: 77.381315},
    {lat: 28.667164, lng: 77.465372},
    {lat: 28.456759, lng: 77.027681},
    {lat: 28.681622, lng: 77.200326},
    {lat: 28.414887, lng: 77.320842},
    {lat: 28.476608, lng: 77.502450}
];

var positions=[];
var poly;
var markers=[];
function initMap(){

    const map=new google.maps.Map(document.getElementById("map"),{
        center:{lat: 28.582247, lng:  77.185448},
        zoom:2
    });


    const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    markers=locations.map((location,i)=>{
        return new google.maps.Marker({
            map:map,
            draggable:true,
            position:location,
            label: labels[i % labels.length]
        });
    });

    poly = new google.maps.Polyline({
        path:locations,
        strokeColor: "#FF0000",
        strokeOpacity: 1.0,
        strokeWeight: 3,
        map: map,
    });

    markers.forEach((marker)=>{
        positions = marker.getPosition();
        const bounds = new google.maps.LatLngBounds(positions);
        map.fitBounds(bounds);
        google.maps.event.addListener(markers,"position_changed",update);
    });

    update();
}

function update() {
    const path = positions;
    poly.setPath(path);
}