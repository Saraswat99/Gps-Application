$(document).ready(function () {
    $('.js-example-basic-single').select2();
    var deviceTable = $('#device-table').DataTable({
        columns: createTable()
    });

    function createTable() {
        var tableArray = [
            {
                title: "Id", "data": "id",
                "class": "edit-device-data",
                'render': function (id) {
                    return '<a data-toggle="modal" data-target="#updateModal" href=' + id + '>' + id + '</a>';
                }
            },
            {title: "IMEI", "data": "imei"},
            {title: "Device Type", "data": "deviceType"},
            {title: "Sim Operator", "data": "simOperator"},
            {title: "Sim Number", "data": "simNumber"},
            {title: "Active", "data": "active"},
            {title: "Assigned", "data": "assigned"},
            {title: "User Id", "data": "userId"},
            {
                title: "Delete",
                "class": "delete-device",
                'render': function () {
                    return '<a href="#"><img src="/gps/resources/img/delete.png"></a>';
                }
            },
            {
                title: "Activate",
                'render': function (data, type, row, meta) {
                    if (row.active) {
                        return '<button type="button" id="disable" class="activate-device" >Disable</button>';
                    } else {
                        return '<button type="button" id="enable" class="activate-device" >Enable</button>';
                    }
                }
            }
        ];
        return tableArray;
    }


    $('#device-table').on('click', ".edit-device-data a", function () {
        var device = deviceTable.row($(this).parents('tr')).data();
        $("#deviceId").val(device.id);
        $("#uimei").val(device.imei);
        $("#usimOperator").val(device.simOperator);
        $("#udeviceType").val(device.deviceType);
        $("#usimNumber").val(device.simNumber);

    })

    //########################################  GET API ###########################################
    ajaxGetCallApi('/gps/device/list').then(function (devices) {
        deviceTable.rows.add(devices.data).draw();
    })

    //########################################  POST API ###########################################
    $('#create').on('click', function () {
        var device = {
            "imei": $("#imei").val(),
            "simOperator": $("#simOperator").val(),
            "deviceType": $("#deviceType").val(),
            "simNumber": $("#simNumber").val(),
        }
        ajaxPostCallApi('/gps/device/save', device).then(function (devices) {
            deviceTable.row.add(devices.data).draw();
        });
    })

    //#######################################  PUT API ###########################################
    $('#update').on('click', function () {
        var index = deviceTable.row(this).index();
        var device = {
            "id": $("#deviceId").val(),
            "imei": $("#uimei").val(),
            "simOperator": $("#usimOperator").val(),
            "deviceType": $("#udeviceType").val(),
            "simNumber": $("#usimNumber").val()
        }
        ajaxPutCallApi('/gps/device/update', device).then(function (devices) {
            deviceTable.row(index).data(devices.data).draw();
        });
    })

    //########################################  DELETE API ###########################################
    $('#device-table').on('click', '.delete-device', function () {
        var remove = confirm("Are you sure you wish to remove this Device?");
        if (remove == true) {
            var row = $(this).parents('tr');
            var rowData = deviceTable.row(row).data();
            var deviceId = rowData.id;
            ajaxDeleteCallApi('/gps/device/delete/' + deviceId).then(function () {
                deviceTable.row(row).remove().draw();
            });
        }
    })

    //########################################  ENABLE API ###########################################
    $('#device-table').on('click', '.activate-device', function () {
        var row = $(this).parents('tr');
        var rowData = deviceTable.row(row).data();
        console.log(rowData);
        var deviceId = rowData.id;
        var active = rowData.active;
        if (active)
            active = false;
        else
            active = true;
        ajaxActivateCallApi('/gps/device/' + active + '/' + deviceId).then(function (devices) {
            rowData.active = active;
            deviceTable.row(row).data(rowData).draw();
        });
    })
});