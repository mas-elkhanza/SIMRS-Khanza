<?php
require_once('../../conf/conf.php');
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($_POST['base64Pdf'])) {
        // Get the base64 PDF data from the POST request
        $base64Pdf = $_POST['base64Pdf'];
        $nomorsepsimpan = $_POST['nomorsep'];

        // Decode the base64 PDF data
        $pdfData = base64_decode($base64Pdf);
        // echo $base64Pdf;
        // Store the PDF data in the database (you need to set up your database connection here)
        // HapusAll("inacbg_data_printinvoice");
        Hapus2("inacbg_data_printinvoice", "no_sep='" . $nomorsepsimpan . "'");
        InsertData2("inacbg_data_printinvoice", "'".$nomorsepsimpan."','" . $base64Pdf. "'");

        // Respond to the AJAX request
        // echo 'PDF saved to database.';
    } else {
        header('HTTP/1.1 400 Bad Request');
        echo 'Missing base64Pdf parameter.';
    }
} else {
    header('HTTP/1.1 405 Method Not Allowed');
    echo 'Method not allowed.';
}
