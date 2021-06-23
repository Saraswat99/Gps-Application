<!DOCTYPE html>
<html lang="en">
<head>
    <title>Vehicle</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.0/js/dataTables.buttons.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script type="text/javascript" src="/gps/resources/js/VehicleCrud.js"></script>
    <script type="text/javascript" src="/gps/resources/js/methods.js"></script>
</head>
<body>
<div class="container">
    <h1><b><i>Vehicle</i></b></h1><br><br>
    <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal" id="createbtn">Create</button>
    <br><br>
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Create Vehicle</h4>
                </div>
                <div class="modal-body">
                    <form class="form">
                        <div class="form-group">
                            <label for="number">Vehicle Number:</label>
                            <input class="form-control" id="number" placeholder="Enter Vehicle Number">
                        </div>
                        <div class="form-group">
                            <label for="lat">Lat:</label>
                            <input class="form-control" id="lat" placeholder="Enter Latitude">
                        </div>
                        <div class="form-group">
                            <label for="lng">Lng:</label>
                            <input class="form-control" id="lng" placeholder="Enter Longitude">
                        </div>
                        <div class="form-group">
                            <label>Vehicle Type: </label>
                            <select id="vehicleSelect" style="width:100%">
                                <option></option>
                                <option value="TRUCK">TRUCK</option>
                                <option value="BUS">BUS</option>
                                <option value="CAR">CAR</option>
                                <option value="BIKE">BIKE</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Vehicle Status</label>
                            <select id="vehicleStatus" style="width:100%">
                                <option></option>
                                <option value="OVERSPEED">OVERSPEED</option>
                                <option value="RUNNING">RUNNING</option>
                                <option value="IDLE">IDLE</option>
                                <option value="STOPPED">STOPPED</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Device Id:</label>
                            <input class="form-control" id="deviceId" placeholder="Enter Device Id">
                        </div>
                        <button type="button" id="save" class="btn btn-default" data-dismiss="modal">Save</button>
                    </form>

                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>

</div>

<%------------------------------------------------------------------------------------------%>

<div class="modal fade" id="myUpdateModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Update Vehicle</h4>
            </div>
            <div class="modal-body">
                <form class="form">
                    <div class="form-group"  >
                        <label for="number">Vehicle Number:</label>
                        <input class="form-control" id="updateNumber" value="">
                    </div>
                    <div class="form-group">
                        <label>Vehicle Type: </label>
                        <select id="updateVehicleSelect" style="width:100%">
                            <option></option>
                            <option value="TRUCK">TRUCK</option>
                            <option value="BUS">BUS</option>
                            <option value="CAR">CAR</option>
                            <option value="BIKE">BIKE</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Device Id:</label>
                        <input class="form-control" id="updateDeviceId" value="">
                    </div>
                    <button type="button" id="update" class="btn btn-default" data-dismiss="modal">Update</button>
                </form>

            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>
</div>
<br><br>
<div class="container">
    <table id="vehicleTable" class="display" width="100%">
    </table>
</div>
</body>
</html>
