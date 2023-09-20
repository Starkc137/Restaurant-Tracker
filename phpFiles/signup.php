<?php
$username = "s2451244";
$password = "s2451244";
$database = "d2451244";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

// check if the required parameters are set in the request
if(isset($_REQUEST['username']) && isset($_REQUEST['email']) && isset($_REQUEST['password'])&& isset($_REQUEST['user_type'])) {
    // get the username, email, and password parameters from the request
    $name = mysqli_real_escape_string($link, $_REQUEST["username"]); // escape special characters to prevent SQL injection
    $email = mysqli_real_escape_string($link, $_REQUEST["email"]); // escape special characters to prevent SQL injection
    $upassword = mysqli_real_escape_string($link, $_REQUEST["password"]);
    $user_type = mysqli_real_escape_string($link, $_REQUEST["user_type"]); // escape special characters to prevent SQL injection

    // prepare a statement to select the user with the given email
    $stmt = mysqli_prepare($link, "SELECT * FROM users WHERE email = ?");
    mysqli_stmt_bind_param($stmt, "s", $email);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    // count the number of rows returned by the query
    $num = mysqli_num_rows($result);

    if ($num == 0) {
        // hash the password using the password_hash() function
        $hash = password_hash($upassword, PASSWORD_DEFAULT);

        // prepare a statement to insert the new user into the database
        $stmt = mysqli_prepare($link, "INSERT INTO users (username, email, password ,user_type) VALUES (?, ?, ?,?)");
        mysqli_stmt_bind_param($stmt, "ssss", $name, $email, $hash, $user_type);
        mysqli_stmt_execute($stmt);

        // check if the query was successful
        if (mysqli_stmt_affected_rows($stmt) > 0) {
            echo "Success";
        } else {
            echo "Failure";
        }
    } else {
        echo "User with this email already exists";
    }

    // close connection
    mysqli_close($link);
} else {
    die("No username, password, email or type specified");
}

