<?php
$username = "s2451244";
$password = "s2451244";
$database = "d2451244";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

// Check if the required parameters are set in the request
if (isset($_REQUEST['order_id']) && isset($_REQUEST['button_type'])) {
    // Get the order_id and button_type parameters from the request
    $order_id = mysqli_real_escape_string($link, $_REQUEST["order_id"]); // Escape special characters to prevent SQL injection
    $button_type = mysqli_real_escape_string($link, $_REQUEST["button_type"]); // Escape special characters to prevent SQL injection

    // Prepare a statement to update the ratings table based on the button_type
    if ($button_type == "thumbs_up") {
        $stmt = mysqli_prepare($link, "UPDATE ratings SET thumbs_up = thumbs_up + 1 WHERE order_id = ?");
    } elseif ($button_type == "thumbs_down") {
        $stmt = mysqli_prepare($link, "UPDATE ratings SET thumbs_down = thumbs_down + 1 WHERE order_id = ?");
    } else {
        // Invalid button_type provided
        die("Invalid button_type");
    }

    mysqli_stmt_bind_param($stmt, "i", $order_id);
    mysqli_stmt_execute($stmt);

    if (mysqli_stmt_affected_rows($stmt) > 0) {
        echo "Success";
    } else {
        echo "Failure";
    }

    // Close the statement
    mysqli_stmt_close($stmt);
    
    // Close the connection
    mysqli_close($link);
} else {
    die("No order_id or button_type specified");
}
?>
