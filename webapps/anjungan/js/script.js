$('table').dataTable();
viewData();
$('#update').prop("disabled",true);

function viewData(){
    $.get('includes/crud-users.php', function(data){
        $('tbody').html(data)
    })
}

function saveData(){
    var username = $('#username').val()
    var role = $('#role').val()
    var cap = $('#cap').val()
    $.post('includes/crud-users.php?p=add', {username:username, role:role, cap:cap}, function(data){
        viewData()
        $('#username').val(' ')
        $('#role').val(' ')
        $('#cap').val(' ')
    })
}

function editData(username, role, cap) {
    $('#username').val(username)
    $('#role').val(role)
    $('#cap').val(cap)
    $('#username').prop("readonly",true);
    $('#save').prop("disabled",true);
    $('#update').prop("disabled",false);
}

function updateData(){
    var username = $('#username').val()
    var role = $('#role').val()
    var cap = $('#cap').val()
    $.post('includes/crud-users.php?p=update', {username:username, role:role, cap:cap}, function(data){
        viewData()
        $('#username').val(' ')
        $('#role').val(' ')
        $('#cap').val(' ')
        $('#username').prop("readonly",false);
        $('#save').prop("disabled",false);
        $('#update').prop("disabled",true);
    })
}

function deleteData(username){
    $.post('includes/crud-users.php?p=delete', {username:username}, function(data){
        viewData()
    })
}

function removeConfirm(username){
    var con = confirm('Are you sure, want to delete this data!');
    if(con=='1'){
        deleteData(username);
    }
}
