<!DOCTYPE html>
<html lang="en">
<head>
    <title>Device</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/jquery.dataTables.min.css"></link>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"/>
</head>
<body>

<%-- ################################### ADD DEVICE ######################################################## --%>
<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#create-modal">Add Device</button>
<div class="modal fade" id="create-modal" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Create New Device</h4>
            </div>

            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="create-imei">Device IMEI:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="create-imei"
                                   placeholder=" Device IMEI">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="create-simOperator">Sim Operator:</label>
                        <div class="col-sm-10">
                            <select class="js-example-basic-single" style="width: 100%" id="create-simOperator"
                                    name="Sim Operator">
                                <option value="IDEA">IDEA</option>
                                <option value="JIO">JIO</option>
                                <option value="AIRTEL">AIRTEL</option>
                                <option value="VODAFONE">VODAFONE</option>
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="control-label col-sm-2" for="create-deviceType">Device Type:</label>
                        <div class="col-sm-10">
                            <select class="js-example-basic-single" style="width: 100%" id="create-deviceType"
                                    name="Device Type">
                                <option value="CONCOX">CONCOX</option>
                                <option value="COBAN">COBAN</option>
                                <option value="GT_06">GT_06</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="create-simNumber">Sim Number:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="create-simNumber" placeholder="Sim Number">
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


<%-- ################################### UPDATE DEVICE ######################################################## --%>
<div class="modal fade" id="update-modal" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Edit Device</h4>
            </div>

            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="update-imei">IMEI:</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" id="update-imei" placeholder="Device IMEI">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="update-deviceType">Device Type:</label>
                        <div class="col-sm-10">
                            <select class="js-example-basic-single" style="width: 100%" id="update-deviceType"
                                    name="Device Type">
                                <option value="CONCOX">CONCOX</option>
                                <option value="COBAN">COBAN</option>
                                <option value="GT_06">GT_06</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="update-simOperator">Sim Operator:</label>
                        <div class="col-sm-10">
                            <select class="js-example-basic-single" style="width: 100%" id="update-simOperator"
                                    name="Sim Operator">
                                <option value="IDEA">IDEA</option>
                                <option value="JIO">JIO</option>
                                <option value="AIRTEL">AIRTEL</option>
                                <option value="VODAFONE">VODAFONE</option>
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="control-label col-sm-2" for="update-simNumber">Sim Number:</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" id="update-simNumber" placeholder="Sim Number">
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
    <table id="device-table" class="display" width="100%">
    </table>
</div>

<script src="/gps/resources/js/jquery-3.6.0.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script src="/gps/resources/js/ajaxCallMethod.js"></script>
<script src="/gps/resources/js/device-curd.js"></script>
</body>
</html>