$(document).ready(function() {

    $('#operatorSelect, #deviceSelect').select2({
        placeholder: "Select an option",
        theme:'classic',
        dropdownParent: $('#myModal')
    });
    $('#updateOperatorSelect, #updateDeviceSelect').select2({
        placeholder: "Select an option",
        theme:'classic',
        dropdownParent: $('#myUpdateModal')
    });

    var deviceTable=$('#deviceTable').DataTable({
        columns:getTableColumns()
    });

        $('#save').on('click', function(){
            var device = {
                "imei": $("#imei").val(),
                "simNumber": $("#simNumber").val(),
                "simOperator": $("#operatorSelect option:selected").text(),
                "deviceType": $("#deviceSelect option:selected").text()
            };
            ajaxPostCallApi('/gps/device/save', device).then(function (newDevice) {
                deviceTable.row.add(newDevice.data).draw();
            });
        });

    var deviceId;
    var index;
    $('#deviceTable').on('click','.idCell',function (){
        deviceId=deviceTable.cell(this).data();
        index=deviceTable.row(this).index();
        var rowsDevice=deviceTable.row($(this).parents('tr')).data();
        $("#updateIMEI").val(rowsDevice.imei);
        $("#updateSimNumber").val(rowsDevice.simNumber);
        $('#updateOperatorSelect').val(rowsDevice.simOperator);
        $('#updateDeviceSelect').val(rowsDevice.deviceType);
    });

    $('#update').on('click', function () {
        var device = {
            id:deviceId,
            imei: $("#updateIMEI").val(),
            simNumber: $("#updateSimNumber").val(),
            simOperator: $("#updateOperatorSelect option:selected").text(),
            deviceType: $("#updateDeviceSelect option:selected").text()
        };
        ajaxPutCallApi('/gps/device/update', device).then(function (updatedDevice) {
                deviceTable.row(index).data(updatedDevice.data).invalidate().draw();
        });
    });


    $("#deviceTable").on('click','.delete',function (){
        var deviceRow=$(this).parents('tr');
        var deviceId=deviceTable.row(deviceRow).data().id;
        var del=this;
        ajaxDeleteCallApi('/gps/device/delete/'+deviceId).then(function(){
            deviceTable.row(deviceRow).remove().draw();
        });
    });

    $('#deviceTable').on('click','.enable',function(){
        var deviceRow=$(this).parents('tr');
        var rowData=deviceTable.row(deviceRow).data();
        console.log(rowData);
        var deviceId=rowData.id;
        var enable=rowData.active;
        var active=!enable;
        ajaxEnableCallApi('/gps/device/'+active+'/'+deviceId).then(function(){
            rowData.active=active;
            deviceTable.row(deviceRow).data(rowData).draw();
        });
    });

    ajaxGetCallApi('/gps/device/list').then(function (devices) {
        deviceTable.rows.add(devices.data).draw();
    });
});

function getTableColumns(){
   return  [
        {
            title:"ID",
            data:"id",
            class:'idCell',
            "render": function(data, type, row, meta){
                return '<a href="#" data-toggle="modal" data-target="#myUpdateModal">' + data + '</a>';
            }},
        {
            title:"IMEI",
            data:"imei"
        },
        {
            title:"Sim Operator",
            data:"simOperator"
        },
        {
            title:"Device Type",
            data:"deviceType"
        },
        {
            title:"Phone Number",
            data:"simNumber"
        },
        {
            title:"Active", data:"active"
        },
        {
            title:"Assigned",
            data:"assigned"
        },
        {
            title:"User Id",
            data:"userId"
        },
        {
            title:"Delete",
            render:function(){
                return '<button class="delete">Delete</button>';
            }
        },
        {
            title:"Activate",
            "render":function(data,type,row,meta){
                if(row.active){
                    return '<button class="enable">Deactivate</button>';
                }
                return '<button class="enable">Activate</button>';
            }
        }
    ]
}


