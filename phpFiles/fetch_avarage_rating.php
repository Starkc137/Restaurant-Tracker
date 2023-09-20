<?php
$username = "s2451244";
$password = "s2451244";
$dbname = "d2451244";

// Create connection
$conn = new mysqli("127.0.0.1", $username, $password, $dbname);

// Fetch average ratings for staff members based on email
if (isset($_REQUEST['email'])) {
    $email = mysqli_real_escape_string($conn, $_REQUEST['email']); // Escape special characters to prevent SQL injection

    // Fetch the staff ID associated with the email
    $query = "SELECT staff_id FROM staff WHERE email = '$email'";
    $result = $conn->query($query);
    $row = $result->fetch_assoc();
    $staffId = $row['staff_id'];

    // Fetch the average ratings for the staff member
    $query = "SELECT ((r.thumbs_up/(r.thumbs_up + r.thumbs_down))*5) AS average_ratings
              FROM orders o
              JOIN ratings r ON o.order_id = r.order_id
              WHERE o.staff_id = $staffId";
    $result = $conn->query($query);
    $row = $result->fetch_assoc();
    $averageRatings = $row['average_ratings'];

    // Convert the average ratings to a string
    $averageRatingsString = number_format($averageRatings, 1);


    // Return the average ratings as a response
    echo $averageRatingsString,"/5";

    $conn->close();
} else {
    die("No email specified");
}
?>
