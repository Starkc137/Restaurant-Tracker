<?php
$username = "s2451244";
$password = "s2451244";
$database = "d2451244";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

// Check if the required parameters are set in the request
if (isset($_REQUEST['username']) && isset($_REQUEST['email']) && isset($_REQUEST['password']) && isset($_REQUEST['user_type']) && isset($_REQUEST['restaurant_name'])) {
    // Get the username, email, password, and restaurant_name parameters from the request
    $name = mysqli_real_escape_string($link, $_REQUEST["username"]); // Escape special characters to prevent SQL injection
    $email = mysqli_real_escape_string($link, $_REQUEST["email"]); // Escape special characters to prevent SQL injection
    $upassword = mysqli_real_escape_string($link, $_REQUEST["password"]);
    $restaurant_name = mysqli_real_escape_string($link, $_REQUEST["restaurant_name"]); // Escape special characters to prevent SQL injection
    $user_type = mysqli_real_escape_string($link, $_REQUEST["user_type"]); // escape special characters to prevent SQL injection

    // Prepare a statement to select the restaurant ID with the given restaurant name
    $stmt = mysqli_prepare($link, "SELECT restaurant_id FROM restaurants WHERE restaurant_name = ?");
    mysqli_stmt_bind_param($stmt, "s", $restaurant_name);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    // Check if the restaurant exists
    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        $restaurant_id = $row['restaurant_id'];

        // Prepare a statement to select the user with the given email
        $stmt = mysqli_prepare($link, "SELECT * FROM users WHERE email = ?");
        mysqli_stmt_bind_param($stmt, "s", $email);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);

        // Count the number of rows returned by the query
        $num = mysqli_num_rows($result);

        if ($num == 0) {
            // Hash the password using the password_hash() function
            $hash = password_hash($upassword, PASSWORD_DEFAULT);

            // Start a transaction
            mysqli_begin_transaction($link);

            try {
                // Insert the new user into the users table
                $stmt = mysqli_prepare($link, "INSERT INTO users (username, email, password, user_type) VALUES (?, ?, ?, ?)");
                mysqli_stmt_bind_param($stmt, "ssss", $name, $email, $hash, $user_type);
                mysqli_stmt_execute($stmt);
                $user_id = mysqli_insert_id($link);

                // Insert the staff information into the staff table
                $stmt2 = mysqli_prepare($link, "INSERT INTO staff (staff_name, email, restaurant_id) VALUES (?, ?, ?)");
                mysqli_stmt_bind_param($stmt2, "ssi", $name, $email, $restaurant_id);
                mysqli_stmt_execute($stmt2);

                // Commit the transaction
                mysqli_commit($link);

                echo "Success";
            } catch (Exception $e) {
                // Rollback the transaction in case of any error
                mysqli_rollback($link);

                echo "Failure";
            }
        } else {
            echo "User with this email already exists";
        }
    } else {
        echo "Restaurant not found";
    }

    // Close the connection
    mysqli_close($link);
} else {
    die("No username, password, or email specified");
}
?>
