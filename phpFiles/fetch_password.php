<?php
$username = "s2451244";
$password = "s2451244";
$dbname = "d2451244";

// Create connection
$conn = new mysqli("127.0.0.1", $username, $password, $dbname);

// Fetch username based on email
if (isset($_REQUEST['email'])) {
    $email = mysqli_real_escape_string($conn, $_REQUEST['email']); // Escape special characters to prevent SQL injection

    // Fetch the username associated with the email
    $query = "SELECT password FROM users WHERE email = '$email'";
    $result = $conn->query($query);
    $row = $result->fetch_assoc();
    $password = $row['password'];

    // Return the password as a response
    echo $password;

    $conn->close();
} else {
    die("No email specified");
}
?>
