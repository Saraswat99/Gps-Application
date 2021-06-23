<!DOCTYPE html>
<html lang="en">

<head>
    <title>Device</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.0/js/dataTables.buttons.min.js"></script>
    <script src="/gps/resources/js/DeviceCrud.js"></script>
    <script type="text/javascript" src="/gps/resources/js/methods.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
</head>

<body>

    <div class="container">
        <h1 style="font-weight: bold; font-style: italic; color: #333333">Device</h1><br><br>
        <button type="submit" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal" id="createbtn">Create</button>
        <br><br>
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title" id="modaltitle">Create Device</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group" id="imeidiv">
                                <label for="imei">IMEI:</label>
                                <input class="form-control" id="imei" placeholder="Enter IMEI" name="imei">
                            </div>
                            <div class="form-group" id="simnumdiv">
                                <label for="simNumber">Sim Number:</label>
                                <input class="form-control" id="simNumber" placeholder="Enter Phone Number">
                            </div>
                            <div class="form-group" id="operatordiv">
                                <label>Sim Operator:</label>
                                <select id="operatorSelect" class="form-control" style="width:100%">
                                    <option></option>
                                    <option value="AIRTEL">AIRTEL</option>
                                    <option value="VODAFONE">VODAFONE</option>
                                    <option value="JIO">JIO</option>
                                </select>
                            </div>
                            <div class="form-group" id="typediv">
                                <label>Device Type</label>
                                <select id="deviceSelect" class="form-control" style="width:100%">
                                    <option></option>
                                    <option value="CONCOX">CONCOX</option>
                                    <option value="COBAN">COBAN</option>
                                    <option value="GT_06">GT_06</option>
                                    </select>
                            </div>
                            <button type="button" class="btn btn-default" data-dismiss="modal" id="save">Save</button>
                        </form>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
<%----------------------------------------------------------------------------------%>
    <div class="modal fade" id="myUpdateModal" role="dialog">
        <div class="modal-dialog">

            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Update Device</h4>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="imei">IMEI:</label>
                            <input class="form-control" id="updateIMEI" value="">
                        </div>
                        <div class="form-group">
                            <label for="simNumber">Sim Number:</label>
                            <input class="form-control" id="updateSimNumber" value="">
                        </div>
                        <div class="form-group">
                            <label>Sim Operator:</label>
                            <select id="updateOperatorSelect" class="form-control" style="width:100%">
                                <option></option>
                                <option value="AIRTEL">AIRTEL</option>
                                <option value="VODAFONE">VODAFONE</option>
                                <option value="JIO">JIO</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Device Type</label>
                            <select id="updateDeviceSelect" class="form-control" style="width:100%">
                                <option></option>
                                <option value="CONCOX">CONCOX</option>
                                <option value="COBAN">COBAN</option>
                                <option value="GT_06">GT_06</option>
                            </select>
                        </div>
                        <button type="button" class="btn btn-default" data-dismiss="modal" id="update">Update</button>
                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    </div>

    <br><br>
    <div class="container">
        <table id="deviceTable" class="display" width="100%">
        </table>
    </div>
</body>
</html>