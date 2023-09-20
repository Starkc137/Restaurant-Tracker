<?php
$username = "s2451244";
$password = "s2451244";
$database = "d2451244";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

// Check if the required parameters are set in the request
if (isset($_REQUEST['email']) && isset($_REQUEST['password'])) {
    // Get the email and password parameters from the request
    $email = mysqli_real_escape_string($link, $_REQUEST["email"]); // Escape special characters to prevent SQL injection
    $password = mysqli_real_escape_string($link, $_REQUEST["password"]); // Escape special characters to prevent SQL injection

    // Hash the password
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

    // Prepare a statement to update the password for the user with the given email
    $stmt = mysqli_prepare($link, "UPDATE users SET password = ? WHERE email = ?");
    mysqli_stmt_bind_param($stmt, "ss", $hashedPassword, $email);
    mysqli_stmt_execute($stmt);

    if (mysqli_stmt_affected_rows($stmt) > 0) {
        echo "Success";
    } else {
        echo "Failure";
    }

    // Close connection
    mysqli_close($link);
} else {
    die("No email or password specified");
}
?>
