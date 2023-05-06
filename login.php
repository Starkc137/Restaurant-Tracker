<?php
// connect to database
$username = "s2451244";
$password = "s2451244";
$database = "d2451244";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

if (isset($_REQUEST['email']) && isset($_REQUEST['password'])) {
    // get email and password parameters from the request
    $email = mysqli_real_escape_string($link, $_REQUEST["email"]); // escape special characters to prevent SQL injection
    $upassword = mysqli_real_escape_string($link, $_REQUEST["password"]); // escape special characters to prevent SQL injection

    // prepare a statement to select the user with the given email
    $stmt = mysqli_prepare($link, "SELECT * FROM users WHERE email = ?");
    mysqli_stmt_bind_param($stmt, "s", $email);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    // count the number of rows returned by the query
    $num = mysqli_num_rows($result);

    if ($num > 0) {
        // get the hashed password from the first row of the result set
        $row = mysqli_fetch_assoc($result);
        $dbpassword = $row['password'];

        // verify the password using the password_verify() function
        if (password_verify($upassword, $dbpassword)) {
            echo "User email exists and password matches";
        } else {
            // if password does not match, show error message
            echo "Password is incorrect";
        }
    } else {
        // if no user found, show error message
        echo "No user found";
    }

    // close connection
    mysqli_close($link);
} else {
    // if email and password parameters are not set, show error message
    die( "All fields are required");
}
?>
