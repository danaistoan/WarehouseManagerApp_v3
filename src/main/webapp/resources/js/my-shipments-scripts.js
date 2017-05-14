
var allPlannedShipments;
var allPalletList;
var palletIdList = [];

function loadUserInfo(){
    var userInfo = $("#userInfo");
    $.get("../userInfo", function(data, status){
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

    $('#shipments_table tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(formatShipment(row.data())).show();
            tr.addClass('shown');
        }
    });

    $('#shipments_table tbody').on('click', 'td.edit-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        var id = row.data().id;
        //var plannedShipment = row.data().plannedShipment;
        //var status = row.data().status;
        //updateShipment(id, plannedShipment, status);
        var currentShipment = row.data();

        updateShipment(currentShipment);
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
    // Create the dialog instance
    var newShipmentForm = $("#shipment-dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        close: function (e) {
            $("#shipment-dialog-form form").trigger('reset');
            $("#pallet-container").html("");
        },
        buttons: {
            "Save": saveShipment,
            "Cancel": function () {
                newShipmentForm.dialog("close");
                $("#shipment-dialog-form form").trigger('reset');
                $("#pallet-container").html("");
            },
            "Add new pallet": function () {
                var plShipmentId = $("#plannedShipment").val();
                var currentPlannedShipment = getCurrentPlShipment(plShipmentId);
                addPallet(currentPlannedShipment);
            }
        }
    });

    var plannedShipmentCombo = $("#plannedShipment");
    plannedShipmentCombo.html("");

    // Load data for combo: planned shipments
    $.get("../PlannedShipments/getPlannedShipments", function(data, status){
        allPlannedShipments = data.plannedShipmentList;

        $.each(allPlannedShipments, function(key, value) {
            plannedShipmentCombo.append("<option value='" + value.id + "'>" + value.id + " - " + value.customerName + " (" + value.quantity + ")" + "</option>");
        });
    });

    // Load data for combo: pallets
    $.get("../Pallets/getPallets", function(data, status){
        allPalletList = data.palletList;
    });

    //
    plannedShipmentCombo.change(function() {
        $("#pallet-container").html("");
    });

    // Open the dialog
    newShipmentForm.dialog('option', 'title', 'Add new shipment');
    newShipmentForm.dialog("open");
}

function saveShipment() {

    var myForm = $("#shipment-dialog-form form");
    if (!myForm.valid())
        return;

    palletIdList = getPalletIds();

    // Check for palletId duplicates
    if(findDuplicatePalletId(palletIdList)){
        $("#messageDuplicatePallet").show();
        $('#messageDuplicatePallet').delay(5000).hide(1000);
        return;
    }

    // Save the shipment
    $.post("addShipment",
        {
            plannedShipmentId: $('#plannedShipment').val(),
            palletIdListJson: JSON.stringify(palletIdList)
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

function addPallet(currentPlannedShipment){

    var plShipmentQty = currentPlannedShipment.quantity;
    var noOfAddedPallets = getNoOfPallets();

    if (noOfAddedPallets == plShipmentQty){
        // Display message
        $("#messageAddLimit").show();
        $('#messageAddLimit').delay(2000).hide(1000);

        // Disable button
        $("#shipment-dialog-form").parent().find("button").each(function() {
            if( $(this).text() == 'Add new pallet' ) {
                $(this).attr('disabled', true);
            }
        });

        return;
    }

    var label = $('<label>Pallet</label>');
    var select = $('<select name="pallet" class="palletItem"><option value=""></option></select>');
    var palletContainer = $("#pallet-container");

    palletContainer.append(label);
    palletContainer.append(select);
    palletContainer.append('<br /><br />');

    // Populate combo
    $.each(allPalletList, function(key, value) {
        select.append("<option value='" + value.id + "'>" + value.id + " - " + value.description + "</option>");
    });
}

// Function for displaying Shipment details
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

// Retrieve pallets from shipment & render them in the datatable as shipments' child rows
function getPalletRows(shipment) {
    var resultPallets = "";
    for(var i=0; i<shipment.productPalletList.length; i++){
        resultPallets +=
            '<tr>' +
            '<td>' + shipment.productPalletList[i].id + '</td>' +
            '<td>' + shipment.productPalletList[i].description + '</td>' +
            '</tr>'
    }
    return resultPallets;
}

function getCurrentPlShipment(plShipmentId){
    var currentPlShipment ="";
    for(var i=0; i<allPlannedShipments.length; i++){
        if(allPlannedShipments[i].id == plShipmentId) {
            currentPlShipment = allPlannedShipments[i];
        }
    }
    return currentPlShipment;
}

function getPalletIds(){

    var palletIdList = [];
    $(".palletItem").each(function() {
        palletIdList.push($(this).val());
    });
    return palletIdList;
}

function getNoOfPallets() {
    var noOfPallets = 0;
    $(".palletItem").each(function() {
        noOfPallets++;
    });
    return noOfPallets;
}

function findDuplicatePalletId(palletIdList){
    var currentId;
    for(var i=0; i<palletIdList.length; i++){
        for(var j=i+1; j<palletIdList.length; j++){
            if(palletIdList[i] == palletIdList[j]){
                return true;
            }
        }
    }
    return false;
}

function updateShipment(shipment) {

    var updateShipmentForm = $("#shipment-dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        close: function (e) {
            $("#shipment-dialog-form form").trigger('reset');
            $("#pallet-container").html("");
        },
        buttons: {
            "Save": function () {
                saveUpdatedShipment(shipment);
            },
            "Cancel": function () {
                updateShipmentForm.dialog("close");
                $("#shipment-dialog-form form").trigger('reset');
                $("#pallet-container").html("");
            },
            "Add new pallet": function () {
                var plShipmentId = $("#plannedShipment").val();
                var currentPlShipment = shipment.plannedShipment;
                addPallet(currentPlShipment);
            }
        }
    });

    //Initialize the fields with current Shipment details - planned shipment
    var plannedShipmentCombo = $("#plannedShipment");
    plannedShipmentCombo.html("");
    plannedShipmentCombo.append("<option value='" + shipment.plannedShipment.id + "'>" + shipment.plannedShipment.id + " - " + shipment.plannedShipment.customerName + " (" + shipment.plannedShipment.quantity + ")" + "</option>");

    var shipmentPalletList = [];
    shipmentPalletList = shipment.productPalletList;

    //Initialize the fields with current Shipment details - shipment's pallets
    $.each(shipmentPalletList, function(key, value) {
        var label = $('<label>Pallet</label>');
        var select = $('<select name="pallet" class="palletItem"><option value=""></option></select>');
        var palletContainer = $("#pallet-container");
        palletContainer.append(label);
        palletContainer.append(select);
        palletContainer.append('<br /><br />');
        select.html("");
        select.append("<option value='" + value.id + "'>" + value.id + " - " + value.description + "</option>");
    });

    // TODO: Add new pallets until shipment's quantity is reached

    // Load data for combo: pallets
    $.get("../Pallets/getPallets", function(data, status){
        allPalletList = data.palletList;
    });

    // Open the dialog
    updateShipmentForm.dialog('option', 'title', 'Update shipment');
    updateShipmentForm.dialog("open");
}

function saveUpdatedShipment(shipment) {

    var myForm = $("#shipment-dialog-form form");
    if (!myForm.valid())
        return;

    palletIdList = getPalletIds();

    // Check for palletId duplicates
    if(findDuplicatePalletId(palletIdList)){
        $("#messageDuplicatePallet").show();
        $('#messageDuplicatePallet').delay(5000).hide(1000);
        return;
    }

    // Update the shipment
    $.post("updateShipment",
        {
            shipmentId: shipment.id,
            plannedShipmentId: $('#plannedShipment').val(),
            palletIdListJson: JSON.stringify(palletIdList)
        },
        function () {
            $("#shipment-dialog-form").dialog("close");
            $("#messageUpdateSuccess").show();
            table.ajax.reload();
            $('#messageUpdateSuccess').delay(1000).hide(500);
        }
    );
    $("#shipment-dialog-form form").trigger('reset');
}