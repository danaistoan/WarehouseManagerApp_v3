<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: dana
  Date: 5/2/2017
  Time: 11:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Show shipments</title>
    <!-- link rel="stylesheet" type="text/css" href="/css/styles.css"/> -->

    <link href="resources/css/demo_page.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/demo_table.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/demo_table_jui.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/dataTables.jqueryui.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="resources/css/jquery-ui.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="resources/css/jquery-ui-1.7.2.custom.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="resources/css/styles.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="resources/js/jquery-3.2.0.js"></script>
    <script type="text/javascript" src="resources/js/jquery-ui.js"></script>
    <script type="text/javascript" src="resources/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="resources/js/jquery.validate.js"></script>
    <script type="text/javascript" src="resources/js/my-common-scripts.js"></script>
    <script type="text/javascript" src="resources/js/my-shipments-scripts.js"></script>
</head>
<body id="dt_example">
<div id="container">
    <div id="demo_jui">
        <div id="userInfo" align="right"> </div>

        <form id="logoutForm" action="logout" method="POST" align="right">
            <input type="hidden" name="action" value="logout"/>
            <a onclick="logout()">Logout</a>
        </form>

        <div class="pageTitle">Shipment list</div>

        <div id="messageAddSuccess" class="messageAddSuccess">Shipment successfully added to warehouse!</div>
        <div id="messageUpdateSuccess" class="messageUpdateSuccess">Shipment successfully updated!</div>
        <div id="messageDeleteSuccess" class="messageDeleteSuccess">Shipment successfully deleted from warehouse!</div>

        <div class="addShipment">
            <a onclick="addShipment()">Add new shipment</a>
        </div>

        <table id="shipments_table" class="display">
            <thead>
            <tr>
                <th></th>
                <th>Id</th>
                <th>Planned shipment</th>
                <th>Status</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>

        <div id="shipment-dialog-form" title="Add new shipment" style="display: none">

            <form id="form">
                <input type="hidden" name="id" id="id" />
                <br />
                <label for="plannedShipment">Planned shipment</label>
                <select name="plannedShipment" id="plannedShipment">
                    <option value=""></option>
                </select>
                <br /><br />
                <label for="pallet">Pallet</label>
                <select name="pallet" id="pallet">
                    <option value=""></option>
                </select>
                <br /><br />
                <input type="button" name="btnAddPallet" id="btnAddPallet" value="Add pallet" />
                <br />
                <input type="submit" tabindex="-1" style="position:absolute; top:-1000px" />
            </form>
        </div>

    </div>
</div>
</body>
</html>
<script>
    $(document).ready(function () {
        loadUserInfo();
    });
</script>
