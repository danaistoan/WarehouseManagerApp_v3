
function loadUserInfo(){
    var userInfo = $("#userInfo");
    $.get("userInfo", function(data, status){
        userInfo.html("Current user: <b>" + data.username + "</b>");

        if(data.userType == "A") {
            loadDataTablePallets();
        } else {
            loadDataTablePalletsReadOnly();
            $(".addPallet").hide();
        }
    });
}

function loadDataTablePallets(){
    table = $("#pallets_table").DataTable({
        "serverSide": true,
        "ajax": {url: 'getAllPallets', dataSrc: 'palletList'},
        //"ajaxDataProp": "",
        "processing": true,
        //"ordering": true,
        //"sPaginationType": "full_numbers",
        "bJQueryUI": true,
        "columns": [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            },
            {"data": "id"},
            {"data": "description"},
            {
                "className": 'delete-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            }
        ]
    });

    $('#pallets_table tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(formatPallet(row.data())).show();
            tr.addClass('shown');
        }
    });

    $('#pallets_table tbody').on('click', 'td.delete-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        $.post("deletePallet", { palletId: row.data().id}, function(){
            $("#messageDeleteSuccess").show();
            table.ajax.reload();
            $('#messageDeleteSuccess').delay(1000).hide(500);
        });
    });
}

function loadDataTablePalletsReadOnly(){
    table = $("#pallets_table").DataTable({
        "serverSide": true,
        "ajax": {url: 'getAllPallets', dataSrc: 'palletList'},
        //"ajaxDataProp": "",
        "processing": true,
        //"ordering": true,
        //"sPaginationType": "full_numbers",
        "bJQueryUI": true,
        "columns": [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            },
            {"data": "id"},
            {"data": "description"},
            {
                "data": null,
                "defaultContent": ''
            }
        ]
    });

    $('#pallets_table tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });
}

function addPallet(){
    var newPalletForm = $( "#pallet-dialog-form" ).dialog({
        autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        close: function (e) {
            $("#pallet-dialog-form form").trigger('reset');
            $(".packageItem").remove();
        },
        buttons: {
            "Save": savePallet,
            "Cancel": function() {
                newPalletForm.dialog( "close" );
                $("#pallet-dialog-form form").trigger('reset');
                $(".packageItem").remove();
            },
            "Add new package": addPackage
        }
    });

    // Initilize the validation
    $("#pallet-dialog-form form").validate({
        rules: {
            description: {
                required: true,
                minlength: 3
            },
            packDescription: {
                required: true,
                minlength: 3
            },
            packType: {
                required: true,
                minlength: 3
            }
        },
        messages: {
            description: "Please specify pallet description",
            packDescription: "Please specify package description",
            packType: "Please specify package type"
        }
    });

    addPackage();
    newPalletForm.dialog( "open" );
}

function savePallet(){
    var myForm = $("#pallet-dialog-form form");
    if (!myForm.valid())
        return;

    var pallet = {
        description: $('#description').val(),
        packages: []
    };
    $(".packageItem").each(function(index){

        var packageItem = {
            description: $(this).find('.packageDescription').val(),
            type: $(this).find('.packageType').val()
        };
        pallet.packages.push(packageItem);
    });

    $.post("addPallet",
        {
            palletJson: JSON.stringify(pallet)
        },
        function () {
            $("#pallet-dialog-form").dialog("close");
            $("#messageAddSuccess").show();
            table.ajax.reload();
            $('#messageAddSuccess').delay(1000).hide(500);
        }
    );
    $("#pallet-dialog-form form").trigger('reset');
    $(".packageItem").remove();
}

function addPackage(){
    $("#packageList").append('<tr class="packageItem">' +
                            '<td><input type="text" class="packageDescription" name="packDescription" /></td>' +
                            '<td><input type="text" class="packageType" name="packType" /></td></tr>');
}

function formatPallet(pallet) {
    return '<div class="childTableTitle">Packages</div>' +
        '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        '<tr>' +
        '<td>Id</td>' +
        '<td>Description</td>' +
        '<td>Type</td>' +
        '</tr>' +
        getPackageRows(pallet) +
        '</table>';
}

function getPackageRows(pallet) {
    var resultPackages = "";
    for(var i=0; i<pallet.packages.length; i++){
        resultPackages +=
            '<tr>' +
            '<td>' + pallet.packages[i].id + '</td>' +
            '<td>' + pallet.packages[i].description + '</td>' +
            '<td>' + pallet.packages[i].type + '</td>' +
            '</tr>'
    }
    return resultPackages;
}