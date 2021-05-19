<!DOCTYPE html>
<html lang="en">
<head>
    <title>Vehicle</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/jquery.dataTables.min.css"></link>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"/>
</head>
<body>

<%-- ################################### ADD VEHICLE ######################################################## --%>
<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Add Vehicle</button>
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Create New Vehicle</h4>
            </div>

            <div class="modal-body">
                <form class="form-horizontal" id="submitVehicleForm">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="number">Vehicle Number:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="number"
                                   placeholder="Enter Vehicle Number">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="vehicleType">Vehicle Type:</label>
                        <div class="col-sm-10">
                            <select class="js-example-basic-single" style="width: 100%" id="vehicleType" name="Vehicle Type">
                                <option value="TRUCK">TRUCK</option>
                                <option value="BUS">BUS</option>
                                <option value="CAR">CAR</option>
                                <option value="BIKE">BIKE</option>
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="control-label col-sm-2" for="vehicleStatus">Vehicle Status:</label>
                        <div class="col-sm-10">
                            <select class="js-example-basic-single" style="width: 100%" id="vehicleStatus" name="Vehicle Status">
                                <option value="OVERSPEED">OVERSPEED</option>
                                <option value="RUNNING">RUNNING</option>
                                <option value="IDLE">IDLE</option>
                                <option value="STOPPED">STOPPED</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="latitude">Latitude:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="latitude" placeholder="Enter Latitude">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="longitude">Longitude:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="longitude" placeholder="Enter Longitude">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="deviceId">Device Id:</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" id="deviceId" placeholder="Enter Device Id">
                        </div>
                    </div>


                </form>
            </div>

            <div class="modal-footer">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" id="create" class="btn btn-default" data-dismiss="modal">Create</button>
                </div>
            </div>

        </div>
    </div>
</div>


<%-- ################################### UPDATE VEHICLE ######################################################## --%>
<div class="modal fade" id="myModal1" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Edit Record</h4>
            </div>

            <div class="modal-body">
                <form class="form-horizontal" id="updateVehicleForm">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="vehicleId">Vehicle Id:</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" id="vehicleId" placeholder="Enter Vehicle Id">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="number1">Vehicle Number:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="number1"
                                   placeholder="Enter Vehicle Number">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="control-label col-sm-2" for="vehicleType1">Vehicle Type:</label>
                        <div class="col-sm-10">
                            <select class="js-example-basic-single" style="width: 100%" id="vehicleType1" name="Vehicle Type">
                                <option value="TRUCK">TRUCK</option>
                                <option value="BUS">BUS</option>
                                <option value="CAR">CAR</option>
                                <option value="BIKE">BIKE</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="deviceId1">Device Id:</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" id="deviceId1" placeholder="Enter Device Id">
                        </div>
                    </div>

                </form>
            </div>

            <div class="modal-footer">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" id="update" class="btn btn-default" data-dismiss="modal">Update</button>
                </div>
            </div>

        </div>
    </div>
</div>
<div class="container">
    <table id="vehicle-table" class="display" width="100%">
    </table>
</div>

<script src="/gps/resources/js/jquery-3.6.0.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script src="/gps/resources/js/ajaxCallMethod.js"></script>
<script src="/gps/resources/js/vehicle-crud.js"></script>
</body>
</html>