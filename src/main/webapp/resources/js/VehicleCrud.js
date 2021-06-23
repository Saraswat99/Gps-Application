$(document).ready(function(){

    $('#vehicleSelect, #vehicleStatus, #updateVehicleSelect').select2({
        placeholder: "Select an option",
        theme:'classic',
        dropdownParent: $('#myModal')
    });

    $('#updateVehicleSelect').select2({
        placeholder: "Select an option",
        theme:'classic',
        dropdownParent: $('#myUpdateModal')
    });

    var vehicleTable=$('#vehicleTable').DataTable({

        //columns property creates the columns with given titles of column head, data property and more required property in an arrray
        columns:getTableColumns()

    });

    $('#save').on('click',function (){
        var vehicle={
            "number": $("#number").val(),
            "lat": $("#lat").val(),
            "lng": $("#lng").val(),
            "vehicleType":  $("#vehicleSelect option:selected").text(),
            "vehicleStatus": $("#vehicleStatus option:selected").text(),
            "deviceId": $("#deviceId").val()
        };

        ajaxPostCallApi('/gps/vehicle/create',vehicle).then(function (newVehicle){
            vehicleTable.row.add(newVehicle).draw();
        });
    });

    var vehicleId;
    var index;
    $('#vehicleTable').on('click','.idCell',function(){
        //on clicking any cell inside Id column we need to get the id of this field, DataTable's cell().data() returns the data of clicked cell
        vehicleId=vehicleTable.cell(this).data();
        index=vehicleTable.row(this).index();
        var rowsVehicle=vehicleTable.row($(this).parent('tr')).data();
        $("#updateNumber").val(rowsVehicle.number);
        $("#updateDeviceId").val(rowsVehicle.deviceDTO.id);
        $("#updateVehicleSelect").val(rowsVehicle.vehicleType);
    });

    $('#update').on('click',function (){
       var vehicle={
            "id":vehicleId,
            "number": $("#updateNumber").val(),
            "vehicleType":  $("#updateVehicleSelect option:selected").text(),
            "deviceId": $("#updateDeviceId").val()
        };
        ajaxPutCallApi('/gps/vehicle/update',vehicle).then(function (newVehicle){
            vehicleTable.row(index).data(newVehicle.data).draw();
        });
    });

    $("#vehicleTable").on('click','.delete',function (){
        //On pressing the delete button we need the vehicleId to pass it in a url, row($(this).parents('tr')) returns the data of the
        // row that has an event of button click.
        var remove=confirm("Are you sure you want to delete");
        if(remove){
            var vehicleId=vehicleTable.row($(this).parents('tr')).data().id;
            var del=this;
            ajaxDeleteCallApi('/gps/vehicle/delete/'+vehicleId).then(function (){
                vehicleTable.row($(del).parents('tr')).remove().draw();
            });
        }
    });


    $('#vehicleTable').on('click','.enable',function(){
        var currentRow=$(this).parents('tr');
        var rowData=vehicleTable.row(currentRow).data();
        var vehicleId=rowData.id;
        var enable=rowData.active;
        var active=!enable;
        let url='/gps/vehicle/'+active+'/'+vehicleId;
        ajaxEnableCallApi(url).then(function(){
            rowData.active=active;
            vehicleTable.row(currentRow).data(rowData).draw();
        });
    });

    ajaxGetCallApi('/gps/vehicle/list').then(function (vehicles){
        //rows.add() adds the row in data table and the data that is passed inside the argument kept in corresponding column, that we have defined above.
        vehicleTable.rows.add(vehicles.data).draw();
    });
});

function getTableColumns(){
   return [
        //columns.render property used to read data from datasource and return value is what will be used for the data requested.
        //Here we use it for create anchor tag for only the id column so that we can popup the modal to update vehicle.
        {
            title:"ID",
            data:'id',
            class:'idCell',
            "render": function(data, type, row, meta){
                return  '<a href="#" data-toggle="modal" data-target="#myUpdateModal">' + data + '</a>';
            }
        },
        {
            title:"Vehicle Number",
            data:"number"},
        {
            title:"Latitude",
            data:"lat"
        },
        {
            title:"Longitude",
            data:"lng"
        },
        {
            title:"Active",
            data:"active"
        },
        {
            title:"Vehicle Type",
            data:"vehicleType"
        },
        {
            title:"Vehicle Status",
            data:"vehicleStatus"
        },
        {
            title:"Device Id",
            data:"deviceDTO.id"
        },
        //below to objects are defined for delete and activate button, we don't need data for this so we assigned null
        {
            title:"Delete",
            data:null,
            render:function(data,type,row,meta){
                data='<button class="delete">Delete</button>'
                return data;
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