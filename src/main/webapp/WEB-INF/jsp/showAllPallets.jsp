<%--
  Created by IntelliJ IDEA.
  User: dana
  Date: 4/27/2017
  Time: 8:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Show all pallets</title>
    <!-- link rel="stylesheet" type="text/css" href="/css/styles.css"/> -->

    <link href="../resources/css/demo_page.css" rel="stylesheet" type="text/css"/>
    <link href="../resources/css/demo_table.css" rel="stylesheet" type="text/css"/>
    <link href="../resources/css/demo_table_jui.css" rel="stylesheet" type="text/css"/>
    <link href="../resources/css/dataTables.jqueryui.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="../resources/css/jquery-ui.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="../resources/css/jquery-ui-1.7.2.custom.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="../resources/css/styles.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="../resources/js/jquery-3.2.0.js"></script>
    <script type="text/javascript" src="../resources/js/jquery-ui.js"></script>
    <script type="text/javascript" src="../resources/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../resources/js/jquery.validate.js"></script>
    <script type="text/javascript" src="../resources/js/my-common-scripts.js"></script>
    <script type="text/javascript" src="../resources/js/my-pallets-scripts.js"></script>
</head> <div id="demo_jui">
<body id="dt_example">
<div id="container">

        <div id="userInfo" align="right"> </div>

        <form id="logoutForm" action="../logout" method="POST" align="right">
            <input type="hidden" name="action" value="logout"/>
            <a onclick="logout()">Logout</a>
        </form>

        <div class="pageTitle">Pallet list</div>

        <div id="messageAddSuccess" class="messageAddSuccess">Pallet successfully added to warehouse!</div>

        <div id="messageDeleteSuccess" class="messageDeleteSuccess">Pallet successfully deleted from warehouse!</div>

        <div class="addPallet">
            <a onclick="addPallet()">Add new pallet</a>
        </div>

        <table id="pallets_table" class="display">
            <thead>
            <tr>
                <th></th>
                <th>Id</th>
                <th>Description</th>
                <th></th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>

        <div id="pallet-dialog-form" title="Add new pallet" style="display: none">
            <p class="validateTips">All form fields are required.</p>

            <form id="form">
                <label for="description">Description</label>
                <input type="text" name="description" id="description" />
                <br/><br/>

                <table id="packageList">
                    <td>Package description</td>
                    <td>Package type</td>
                    </tr>
                </table>

                <input type="submit" tabindex="-1" style="position:absolute; top:-1000px" />
            </form>
        </div>

    </div>
</body>
</div>
</html>
<script>
    $(document).ready(function () {
        loadUserInfo();
    });
</script>
