const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
var markers = [];
var marker2;
let poly;
var location2 = [];
const locations = [
 //   [{lat: 27.898193, lng: 78.087989}, "Aligarh"],
    [{lat: 28.544951, lng: 77.381315}, "Noida"],
    [{lat: 28.476669, lng: 77.503669}, "Greater Noida"],
    //[{lat: 28.456759, lng: 77.027681}, "Gurugram"],
    //[{lat: 28.667164, lng: 77.465372}, "Ghaziabad"],

      [{lat: 28.681622, lng: 77.200326}, "Delhi"],
];

var position = [28.544951, 77.381315];

function initMap() {

    var latlng = new google.maps.LatLng(position[0], position[1]);
    const map = new google.maps.Map(document.getElementById("map"), {
        center: latlng,
        zoom: 8,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
    });
    const image = "/gps/resources/images/img/sport-car.svg";

    markers = locations.map((location, labelIndex) => {
        return new google.maps.Marker({
            position: location[0],
            label: labels[labelIndex % labels.length],
            title: location[1],
        });
    });

    new MarkerClusterer(map, markers, {imagePath: "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m",});
    marker2 = new google.maps.Marker({
        position: latlng,
        icon: {url: image, scaledSize: new google.maps.Size(60, 60)},
        map: map,

    })

    const bounds = new google.maps.LatLngBounds();
    markers.forEach(addPosition);

    function addPosition(marker) {
        bounds.extend(marker.getPosition())
    }

    map.fitBounds(bounds);

    const infoWindow = new google.maps.InfoWindow();
    markers.forEach(myListener);

    function myListener(marker) {
        marker.addListener("click", () => {
            infoWindow.close();
            infoWindow.setContent(marker.getTitle());
            infoWindow.open(marker.getMap(), marker);
        });
    }

    locations.forEach((location) => {
        location2.push(location[0]);
    });


    poly = new google.maps.Polyline({
        path: location2,
        strokeColor: "#292",
        strokeOpacity: 1.0,
        strokeWeight: 3,
        map: map,
    });

    // ################################################ DRAWING #######################################################
    const drawingManager = new google.maps.drawing.DrawingManager({
        drawingControl: true,
        drawingControlOptions: {
            position: google.maps.ControlPosition.BOTTOM_CENTER,
            drawingModes: [
                google.maps.drawing.OverlayType.MARKER,
                google.maps.drawing.OverlayType.CIRCLE,
                google.maps.drawing.OverlayType.POLYGON,
                google.maps.drawing.OverlayType.POLYLINE,
                google.maps.drawing.OverlayType.RECTANGLE,
            ],
        },
        markerOptions: {
            icon: "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png",
        },
        circleOptions: {
            fillColor: "#ffff00",
            fillOpacity: 1,
            strokeWeight: 5,
            clickable: false,
            editable: true,
            zIndex: 1,
        },
    });
    drawingManager.setMap(map);
    // ###############################################################################################################
}

$("#start").click(function () {
    console.log("start");
    setLatLng();
});

var index = 0;

function setLatLng() {
    console.log("call");
    ++index;
    //id = setInterval(transition,5000,location2[i]);
    transition(location2[index]);
}

var numDeltas = 1000;
var i = 0;
var deltaLat;
var deltaLng;

function transition(target) {
    //console.log(target);
    i = 0;
    deltaLat = parseFloat(((target.lat - position[0]) / numDeltas).toFixed(6));
    deltaLng = parseFloat(((target.lng - position[1]) / numDeltas).toFixed(6));
    //console.log(deltaLat + "deltaLat");
    //console.log(deltaLng + "deltaLng");
    moveMarker();
}


var id;

function moveMarker() {
    console.log("move");
   // console.log("position[0]" + position[0]);
    //console.log("position[1]" + position[1]);
    position[0] = parseFloat((position[0]+deltaLat).toFixed(6));
    position[1] = parseFloat((position[1]+deltaLng).toFixed(6));
    console.log(position[0],location2[index].lat,position[0] != location2[index].lat)
    var latlng = new google.maps.LatLng(position[0], position[1]);
    marker2.setPosition(latlng);


    //console.log(position[1] ,location2[index].lng,position[1] != location2[index].lng)
    // if (i != numDeltas) {
    if (position[0] != location2[index].lat && position[1] != location2[index].lng) {
       // console.log("target.lat" + location2[index].lat);
       // console.log("target.lng" + location2[index].lng);
        i++;
       // console.log(i + "  i");
        id = setInterval(moveMarker, 200);
    } else {
        console.log("madan###########################################################");
        //clearInterval(id);
        //setLatLng();
    }
    console.log("end");
}



//var timer = null, count = 0;
$("#stop").click(function () {
    clearInterval(id);
     //timer = null
});
