
function loadUserInfo(){
    var userInfo = $("#userInfo");
    $.get("../userInfo", function(data, status){
        userInfo.html("Current user: <b>" + data.username + "</b>");

        loadDataTablePlShipments();

        if(data.userType != "A") {
            $(".addPlannedShipment").hide();
        }
    });
}

function loadDataTablePlShipments(){
    table = $("#planned_shipments_table").DataTable({
        "serverSide": true,
        "ajax": {url: 'getAllPlannedShipments', dataSrc: 'plannedShipmentList'},
        //"ajaxDataProp": "",
        "processing": true,
        //"ordering": true,
        //"sPaginationType": "full_numbers",
        "bJQueryUI": true,
        "columns": [
            {"data": "id"},
            {"data": "customerName"},
            {"data": "quantity"},
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

    $('#planned_shipments_table tbody').on('click', 'td.edit-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        var currentPlannedShipment = row.data();

        updatePlannedShipment(currentPlannedShipment);
    });

    $('#planned_shipments_table tbody').on('click', 'td.delete-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        $.post("deletePlannedShipment", {plShipmentId: row.data().id}, function(){
            $("#messageDeleteSuccess").show();
            table.ajax.reload();
            $('#messageDeleteSuccess').delay(1000).hide(500);
        });
    });
}

function addPlannedShipment() {
    var newPlShipmentForm = $("#pl-shipment-dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        close: function (e) {
            $("#pl-shipment-dialog-form form").trigger('reset');
        },
        buttons: {
            "Save": savePlannedShipment,
            "Cancel": function () {
                newPlShipmentForm.dialog("close");
                $("#pl-shipment-dialog-form form").trigger('reset');
            },
        }
    });

    // Initilize the validation
    $("#pl-shipment-dialog-form form").validate({
        rules: {
            customerName: {
                required: true,
                minlength: 3
            },
            numberOfPallets: {
                required: true,
                minlength: 1
            }
        },
        messages: {
            customerName: "Please specify customer name",
            numberOfPallets: "Please specify the number of pallets"
        }
    });
    // Display the popup
    newPlShipmentForm.dialog('option', 'title', 'Add new planned shipment');
    newPlShipmentForm.dialog("open");
}

function savePlannedShipment(){
    var myForm = $("#pl-shipment-dialog-form form");
    if (!myForm.valid())
        return;

    var shipment = {
        customerName: $('#customerName').val(),
        quantity: $('#numberOfPallets').val()
    };

    $.post("addPlannedShipment",
        {
            plannedShipmentJson: JSON.stringify(shipment)
        },
        function () {
            $("#pl-shipment-dialog-form").dialog("close");
            $("#messageAddSuccess").show();
            table.ajax.reload();
            $('#messageAddSuccess').delay(1000).hide(500);
        }
    );
    $("#pl-shipment-dialog-form form").trigger('reset');
}

function updatePlannedShipment(plannedShipment) {

    var updatePlShipmentForm = $("#pl-shipment-dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        close: function (e) {
            $("#pl-shipment-dialog-form form").trigger('reset');
        },
        buttons: {
            "Save": saveUpdatedPlannedShipment,
            "Cancel": function () {
                updatePlShipmentForm.dialog("close");
                $("#pl-shipment-dialog-form form").trigger('reset');
            },
        }
    });

    // Initilize the validation
    $("#pl-shipment-dialog-form form").validate({
        rules: {
            customerName: {
                required: true,
                minlength: 3
            },
            numberOfPallets: {
                required: true,
                minlength: 1
            }
        },
        messages: {
            customerName: "Please specify customer name",
            numberOfPallets: "Please specify the number of pallets"
        }
    });

    //Initialize the fields with current Shipment details
    $("#id").val(plannedShipment.id);
    $("#customerName").val(plannedShipment.customerName);
    $("#numberOfPallets").val(plannedShipment.quantity);

    // Display the popup
    updatePlShipmentForm.dialog('option', 'title', 'Update planned shipment');
    updatePlShipmentForm.dialog("open");
}

function saveUpdatedPlannedShipment(){
    var myForm = $("#pl-shipment-dialog-form form");
    if (!myForm.valid())
        return;

    var shipment = {
        id: $('#id').val(),
        customerName: $('#customerName').val(),
        quantity: $('#numberOfPallets').val()
    };

    $.post("updatePlannedShipment",
        {
            plannedShipmentJson: JSON.stringify(shipment)
        },
        function () {
            $("#pl-shipment-dialog-form").dialog("close");
            $("#messageUpdateSuccess").show();
            table.ajax.reload();
            $('#messageUpdateSuccess').delay(1000).hide(500);
        }
    );
    $("#pl-shipment-dialog-form form").trigger('reset');
}