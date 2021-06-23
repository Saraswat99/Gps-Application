<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Map</title>
    <link rel="stylesheet" type="text/css" href="/gps/resources/css/style.css" />
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script src="/gps/resources/js/map.js"></script>
</head>
<body>
<h1>Google Map</h1>
<button type="button" id="move">Move</button>
<button type="button" id="stop">Stop</button>
<div id="map"></div>
<script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB41DRUbKWJHPxaFjMAwdrzWzbVKartNGg&libraries=geometry,places,drawing&callback=initMap&v=weekly"
        async
></script>
</body>
</html>
