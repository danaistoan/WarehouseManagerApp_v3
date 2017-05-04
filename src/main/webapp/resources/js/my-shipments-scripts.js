
var allPalletList;

function loadUserInfo(){
    var userInfo = $("#userInfo");
    $.get("userInfo", function(data, status){
        userInfo.html("Current user: <b>" + data.username + "</b>");

        loadDataTableShipments();

        if(data.userType != "A") {
            $(".addShipment").hide();
        }
    });
}

function loadDataTableShipments(){
    table = $("#shipments_table").DataTable({
        "serverSide": true,
        "ajax": {url: 'getAllShipments', dataSrc: 'shipmentList'},
        "processing": true,
        "bJQueryUI": true,
        "columns": [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            },
            {"data": "id"},
            {"render": function ( data, type, row ) {
                return row.plannedShipment.id + ' - ' + row.plannedShipment.customerName + ' (qty: ' + row.plannedShipment.quantity + ')';
            }},
            {"render": function ( data, type, row ) {
                return row.completed ? 'Complete' : 'Incomplete';
            }},
            {
                "className": 'edit-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            },
            {
                "className": 'delete-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            }
        ]
    });

    $('#shipments_table tbody').on('click', 'td.edit-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        var id = row.data().id;
        var plannedShipment = row.data().plannedShipment;
        var stats = row.data().status;
        updateShipment(id, plannedShipment, status);
    });

    $('#shipments_table tbody').on('click', 'td.delete-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        $.post("deleteShipment", {shipmentId: row.data().id}, function(){
            $("#messageDeleteSuccess").show();
            table.ajax.reload();
            $('#messageDeleteSuccess').delay(1000).hide(500);
        });
    });
}

function addShipment() {
    var newShipmentForm = $("#shipment-dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        close: function (e) {
            $("#shipment-dialog-form form").trigger('reset');
        },
        buttons: {
            "Save": saveShipment,
            "Cancel": function () {
                newShipmentForm.dialog("close");
                $("#shipment-dialog-form form").trigger('reset');
            },
        }
    });

    // Load data for combo
    $.get("getPlannedShipments", function(data, status){

        $.each(data.plannedShipmentList, function(key, value) {
            $("#plannedShipment").append("<option value='" + value.id + "'>" + value.id + " - " + value.customerName + "</option>");
        });

    });

    $.get("getPallets", function(data, status){
        allPalletList = data.palletList;

        $.each(data.palletList, function(key, value) {
            $("#pallet").append("<option value='" + value.id + "'>" + value.id + " - " + value.description + "</option>");
        });

    });

    // Initilize the validation
    // $("#shipment-dialog-form form").validate({
    //     rules: {
    //         plannedShipment: {
    //             required: true,
    //             minlength: 3
    //         },
    //         pallet: {
    //             required: true,
    //             minlength: 3
    //         }
    //     },
    //     messages: {
    //         plannedShipment: "Please select a planned shipment",
    //         pallet: "Please select a pallet"
    //     }
    // });
    // Display the popup
    newShipmentForm.dialog("open");
}

function saveShipment() {

    var myForm = $("#shipment-dialog-form form");
    if (!myForm.valid())
        return;

    var shipment = {
        plannedShipment: $('#plannedShipment').val(),
        pallet: $('#pallet').val()
    };

    $.post("addShipment",
        {
            shipmentJson: JSON.stringify(shipment)
        },
        function () {
            $("#shipment-dialog-form").dialog("close");
            $("#messageAddSuccess").show();
            table.ajax.reload();
            $('#messageAddSuccess').delay(1000).hide(500);
        }
    );
    $("#shipment-dialog-form form").trigger('reset');
}

//TO DO - Function for displaying Shipment details
function formatShipment(shipment) {
    return '<div class="childTablePlShipmentTitle">Pallets</div>' +
        '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        '<tr>' +
        '<td>Id</td>' +
        '<td>Description</td>' +
        '</tr>' +
        getPalletRows(shipment) +
        '</table>';
}

//TO DO - for displaying Shipment details - pallets have to be bounded to shipment
function getPalletRows(shipment) {
    var resultPallets = "";
    for(var i=0; i<shipment.pallets.length; i++){
        resultPallets +=
            '<tr>' +
            '<td>' + shipment.pallets[i].id + '</td>' +
            '<td>' + shipment.pallets[i].description + '</td>' +
            '</tr>'
    }
    return resultPallets;
}