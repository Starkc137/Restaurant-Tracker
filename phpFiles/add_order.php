<?php
$username = "s2451244";
$password = "s2451244";
$database = "d2451244";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

// Check if the required parameters are set in the request
if (isset($_REQUEST['email']) && isset($_REQUEST['restaurant_name']) && isset($_REQUEST['staff_name'])) {
    // Get the username, email, password, and restaurant_name parameters from the request
    $email = mysqli_real_escape_string($link, $_REQUEST["email"]); // Escape special characters to prevent SQL injection
    $restaurant_name = mysqli_real_escape_string($link, $_REQUEST["restaurant_name"]); // Escape special characters to prevent SQL injection
    $staff_name = mysqli_real_escape_string($link, $_REQUEST["staff_name"]); // Escape special characters to prevent SQL injection

    $stmt = mysqli_prepare($link, "SELECT restaurant_id FROM restaurants WHERE restaurant_name = ?");
    mysqli_stmt_bind_param($stmt, "s", $restaurant_name);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    // Check if the restaurant exists
    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        $restaurant_id = $row['restaurant_id'];

        // Prepare a statement to select the staff with the given staff_name
        $stmt = mysqli_prepare($link, "SELECT staff_id FROM staff WHERE staff_name = ?");
        mysqli_stmt_bind_param($stmt, "s", $staff_name);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);

        if (mysqli_num_rows($result) > 0) {
            $row = mysqli_fetch_assoc($result);
            $staff_id = $row['staff_id'];

            // Prepare a statement to select the user with the given email
            $stmt = mysqli_prepare($link, "SELECT username FROM users WHERE email = ?");
            mysqli_stmt_bind_param($stmt, "s", $email);
            mysqli_stmt_execute($stmt);
            $result = mysqli_stmt_get_result($stmt);

            if (mysqli_num_rows($result) > 0) {
                $row = mysqli_fetch_assoc($result);
                $customer_name = $row['username'];

                // Prepare a statement to insert the new order into the orders table
                $stmt = mysqli_prepare($link, "INSERT INTO orders (customer_name, staff_id, restaurant_id) VALUES (?, ?, ?)");
                mysqli_stmt_bind_param($stmt, "sii", $customer_name, $staff_id, $restaurant_id);
                mysqli_stmt_execute($stmt);

                if (mysqli_stmt_affected_rows($stmt) > 0) {
                    // Get the inserted order_id
                    $order_id = mysqli_insert_id($link);

                    // Prepare a statement to insert the new rating into the ratings table
                    $stmt = mysqli_prepare($link, "INSERT INTO ratings (order_id, thumbs_up, thumbs_down) VALUES (?, 0, 0)");
                    mysqli_stmt_bind_param($stmt, "i", $order_id);
                    mysqli_stmt_execute($stmt);

                    if (mysqli_stmt_affected_rows($stmt) > 0) {
                        echo "Success";
                    } else {
                        echo "Failure";
                    }
                } else {
                    echo "Failure";
                }
            }
        }
    }

    // Close connection
    mysqli_close($link);
} else {
    die("No username, password, email, or type specified");
}
?>
