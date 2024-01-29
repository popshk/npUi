<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

    <title>Orders Table</title>
</head>
<body class="p-3 mb-2 bg-danger-subtle text-emphasis-danger">
<h1>Orders Table</h1>
<table class="table table-striped">
    <thead>
    <tr>
        <th class="col-1">Barcode</th>
        <th class="col-1">Scheduled Delivery Date</th>
        <th class="col-1">Tracking Status Name</th>
        <th class="col-3">Comment</th>
    </tr>
    </thead>
    <tbody>
    <#list orders as order>
        <tr>
            <td>${order.barcode()}</td>
            <td>${order.scheduledDeliveryDate()}</td>
            <td>${order.trackingStatusName()}</td>
            <td>
                <form method="post" class="d-grid gap-2" action="/saveComment">
                    <div class="form-floating">
                        <textarea name="comment" class="form-control" style="height:100px">${order.comment()}</textarea>
                        <input type="hidden" name="barcode" value="${order.barcode()}">
                        <input type="hidden" name="scheduledDeliveryDate" value="${order.scheduledDeliveryDate()}">
                        <input type="hidden" name="trackingStatusName" value="${order.trackingStatusName()}">
                        <input type="submit" class="btn btn-danger btn-sm w-100" value="Edit">
                    </div>
                </form>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<form method="post" class="d-grid gap-2" action="/cleanDB">
    <div class="form-floating">
        <input type="submit" class="btn btn-danger btn-sm w-100" value="RemoveOldOrders">
    </div>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
</body>
</html>