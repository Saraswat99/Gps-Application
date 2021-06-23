const locations = [
    {lat: 28.544951, lng: 77.381315},
    {lat: 28.667164, lng: 77.465372},
    {lat: 28.456759, lng: 77.027681},
    {lat: 28.681622, lng: 77.200326},
    {lat: 28.414887, lng: 77.320842},
    {lat: 28.476608, lng: 77.502450}
];

const contentString = ["google", "map", "api", "infowindow"];
var positions = [];
var latLngArray=[];
function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: {lat: 20.5937, lng: 78.9629},
        zoom: 3,
        mapTypeId: 'roadmap'
    });

    const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /*
        const arrowSymbol = {
            path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
            scale: 5,
            strokeColor: "#393",
        };*/

    const poly = new google.maps.Polyline({
        path: locations,
        strokeColor: "blue",
        strokeOpacity: 1.0,
        strokeWeight: 3,
        map: map
        // icons: [
        //     {
        //         icon: arrowSymbol,
        //         offset: "100%",
        //     },
        // ],
    });

    var m = 0, lat, lng, marker1;
    marker1 = new google.maps.Marker({
        position: {lat: 28.498168447722932, lng: 77.39891552942096},
        icon: {
            url: "/gps/resources/css/image/sport-car.svg",
            scaledSize: new google.maps.Size(75, 75),
        },
        draggable: false,
        map: map
    });

    var resumeLat, resumeLng;
    $("#stop").on('click', function () {
        console.log(moveId);
        clearInterval(moveId);
    });

    $("#move").on('click', function () {
        if(m==latLngArray.length){
            m = 0;
            latLngArray = [];
            window.clearInterval(moveId);
        }
        carMarker();
    });

    var id
    function carMarker() {
        if (m != locations.length - 1) {
            lat = locations[m + 1].lat;
            lng = locations[m + 1].lng;
            latLngArray.push({lat: locations[m].lat, lng: locations[m].lng})
            var result = [lat, lng];
            moveCar(result);
            // console.log(position.length+"-------------------------------")
            m++
        }
    }

    var i = 0, delta = 100, arrayLng, arrayLat, latDiff, lngDiff;
    var moveId, posLat, posLng;
    var markerPosition;

    function moveCar(result) {
        i = 0;
        arrayLat = latLngArray[m].lat;
        arrayLng = latLngArray[m].lng;
        // console.log("arrayLat-: "+arrayLat+", arrayLng-: "+arrayLng);
        latDiff = (result[0] - arrayLat) / delta;
        lngDiff = (result[1] - arrayLng) / delta;
        // console.log("latDiff-: "+latDiff+", lngDiff-: "+lngDiff);
        console.log("moveCar call");
        moveId = window.setInterval(function () {
            if (i != delta) {
                i++;
                arrayLat += latDiff;
                arrayLng += lngDiff;
                var loc1 = new google.maps.LatLng(arrayLat, arrayLng);
                marker1.setPosition(loc1);
                markerPosition = marker1.getPosition();
                posLat = markerPosition.lat().toFixed(6);
                posLng = markerPosition.lng().toFixed(6);
                // console.log("lat-: " + posLat + ", lng-: " + posLng);
                console.log("m-: "+m);
                if (posLat == locations[m].lat && posLng == locations[m].lng) {
                    console.log("m-: "+m+", location"+locations[m].lat+", "+locations[m].lng);
                    carMarker();
                }
            }
        }, 40);
    }

    var overlay = new google.maps.OverlayView()
    overlay.draw = function () {
        this.getPanes().markerLayer.id = 'markerLayer'
    }
    overlay.setMap(map)
    function changeMarker(degree) {
        var deg = degree;
        document.querySelector('#markerLayer img').style.transform = 'rotate(' + deg + 'deg)'
        console.log('changed')
        google.maps.event.clearListeners(map, 'idle');
    }
    // google.maps.event.addDomListener(window, 'load', initMap)

    var markers = locations.map((location, i) => {
        return new google.maps.Marker({
            position: location,
            draggable: true,
            label: labels[i % labels.length],
            title: contentString[i]
        });
    });
    markers.push(marker1);
    new MarkerClusterer(map, markers, {
        imagePath:
            "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m",
    });

    markers.map((marker, i) => {
        lat = marker.getPosition().lat();
        lng = marker.getPosition().lng();
        const infowindow = new google.maps.InfoWindow({
            content: "lat-: " + lat + ", " + "lng-: " + lng
        });
        marker.addListener("rightclick", () => {
            infowindow.open(map, marker);
        });
    });

    markers.forEach((marker) => {
        positions.push(marker.getPosition());
        google.maps.event.addListener(marker, "drag", (event) => {
            const position = event.latLng;
            const path = poly.getPath();
            path.push(position);
        });
    });

    const drawingManager = new google.maps.drawing.DrawingManager({
        drawingControl: true,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER,
            drawingModes: [
                google.maps.drawing.OverlayType.MARKER,
                google.maps.drawing.OverlayType.CIRCLE,
                google.maps.drawing.OverlayType.POLYGON,
                google.maps.drawing.OverlayType.POLYLINE,
                google.maps.drawing.OverlayType.RECTANGLE
            ]
        },
        markerOptions: {
            icon: "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png"
        },
        circleOptions: {
            fillColor: "#ffff00",
            fillOpacity: 1,
            strokeWeight: 5,
            clickable: false,
            editable: true,
            zIndex: 1
        }
    });
    drawingManager.setMap(map);
}


function animateCar(line) {
    let count = 0;
    return window.setInterval(() => {
        count = (count + 1) % 1000;
        const icons = line.get("icons");
        icons[0].offset = count / 10 + "%";
        line.set("icons", icons);
    }, 15);
}