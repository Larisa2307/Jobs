$(document).ready(function () {
    $('#dtBasicExample').DataTable();
    $('.dataTables_length').addClass('bs-select');
});

$('#addCategory').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');
})