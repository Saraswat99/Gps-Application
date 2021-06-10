<!DOCTYPE html>
<html lang="en">
<head>
    <head>
        <title>Map</title>
        <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    </head>
<body>
<h1>Google Map</h1>
<input id="stop" type="button" value="stop"/>
<input id="start" type="button" value="start"/>
<div id="map" style="width:100%;height:400px;"></div>


<script src="/gps/resources/js/jquery-3.6.0.js"></script>
<script src="https://unpkg.com/@googlemaps/markerclustererplus/dist/index.min.js"></script>
<script src="/gps/resources/js/google-map.js"></script>
<script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB41DRUbKWJHPxaFjMAwdrzWzbVKartNGg&callback=initMap&libraries=drawing&geometry&v=weekly"
        async
></script>

</body>
</html>