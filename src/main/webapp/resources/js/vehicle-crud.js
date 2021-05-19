$(document).ready(function () {
    $('.js-example-basic-single').select2();
    console.log($);
    var vehicleTable = $('#vehicle-table').DataTable({
        columns: createTable()
    });

    function createTable() {
        var tableArray = [
            {
                title: "Id", "data": "id", "class": "edit-vehicle-data",
                'render': function (id) {
                    return '<a data-toggle="modal" data-target="#myModal1" href=' + id + '>' + id + '</a>';
                }
            },
            {title: "Number", "data": "number"},
            {title: "Vehicle Type", "data": "vehicleType"},
            {title: "Vehicle Status", "data": "vehicleStatus"},
            {title: "Latitude", "data": "lat"},
            {title: "Longitude", "data": "lng"},
            {title: "Active", "data": "active"},
            {title: "Device Id", "data": "deviceDTO.id"},
            {
                title: "Delete",
                "class": "delete-vehicle",
                'render': function () {
                    return '<a href="#"><img  src="/gps/resources/img/delete.png"></a>';
                }
            }, {
                title: "Activate",
                'render': function (data, type, row, meta) {
                    if (row.active) {
                        return '<button type="button" id="disable" class="activate-vehicle" >Disable</button>';
                    } else {
                        return '<button type="button" id="enable" class="activate-vehicle" >Enable</button>';
                    }
                }
            }
        ]
        return tableArray;
    }

    $('#vehicle-table').on('click', ".edit-vehicle-data a", function () {
        var vehicle = vehicleTable.row($(this).parents('tr')).data();
        $("#vehicleId").val(vehicle.id);
        $("#number1").val(vehicle.number);
        $("#vehicleType1").val(vehicle.vehicleType);
        $("#deviceId1").val(vehicle.deviceDTO.id);
    })

    //########################################  GET API ###########################################
    ajaxGetCallApi('/gps/vehicle/list').then(function (vehicles) {
        vehicleTable.rows.add(vehicles.data).draw();
    })

    //########################################  POST API ###########################################
    $('#create').on('click', function () {
        var vehicle = {
            "number": $("#number").val(),
            "vehicleType": $("#vehicleType").val(),
            "vehicleStatus": $("#vehicleStatus").val(),
            "lat": $("#latitude").val(),
            "lng": $("#longitude").val(),
            "deviceId": $("#deviceId").val()
        }
        ajaxPostCallApi('/gps/vehicle/create', vehicle).then(function (vehicles) {
            vehicleTable.row.add(vehicles.data).draw();
        });
    })

    //#######################################  PUT API ###########################################
    $('#update').on('click', function () {
        var index = vehicleTable.row(this).index();
        var vehicle = {
            "id": $("#vehicleId").val(),
            "number": $("#number1").val(),
            "vehicleType": $("#vehicleType1").val(),
            "deviceId": $("#deviceId1").val()
        }
        ajaxPutCallApi('/gps/vehicle/update', vehicle).then(function (vehicles) {
            vehicleTable.row(index).data(vehicles.data).draw();
        });
    })

    //########################################  DELETE API ###########################################
    $('#vehicle-table').on('click', '.delete-vehicle', function () {
        var remove = confirm("Are you sure you wish to remove this Device?");
        if(remove){
            var row = $(this).parents('tr');
            var rowData = vehicleTable.row(row).data();
            var vehicleId = rowData.id;
            ajaxDeleteCallApi('/gps/vehicle/delete/' + vehicleId).then(function () {
                vehicleTable.row(row).remove().draw();
            });
        }
    })

    //########################################  ENABLE API ###########################################
    $('#vehicle-table').on('click', '.activate-vehicle', function () {
        var row = $(this).parents('tr');
        var rowData = vehicleTable.row(row).data();
        var vehicleId = rowData.id;
        var active = rowData.active;
        if (active)
            active = false;
        else
            active = true;
        ajaxActivateCallApi('/gps/vehicle/' + active + '/' + vehicleId).then(function (vehicles) {
            rowData.active = active;
            vehicleTable.row(row).data(rowData).draw();
        });
    })
});