<?php
$username = "s2451244";
$password = "s2451244";
$database = "d2451244";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

// Check if the required parameters are set in the request
if (isset($_REQUEST['email']) && isset($_REQUEST['security_question']) && isset($_REQUEST['security_answer'])) {
    // Get the username, email, password, and restaurant_name parameters from the request
    $email = mysqli_real_escape_string($link, $_REQUEST["email"]); // Escape special characters to prevent SQL injection
    $security_question = mysqli_real_escape_string($link, $_REQUEST["security_question"]);
    $security_answer = mysqli_real_escape_string($link, $_REQUEST["security_answer"]); // Escape special characters to prevent SQL injection

    // Insert the new user into the users table
    $stmt = mysqli_prepare($link, "UPDATE users SET security_question = ?, security_answer = ? WHERE email = ?");
    mysqli_stmt_bind_param($stmt, "sss", $security_question, $security_answer, $email);
    mysqli_stmt_execute($stmt);


    // check if the query was successful
    if (mysqli_stmt_affected_rows($stmt) > 0) {
        echo "Success";
    } else {
        echo "Failure";
    }

    // Close the connection
    mysqli_close($link);
} else {
    die("No email, security_question, or security_answer specified");
}
?>
