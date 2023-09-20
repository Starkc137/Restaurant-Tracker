<?php
// Database configuration
$username = "s2451244";
$password = "s2451244";
$dbname = "d2451244";

// Get order ID and new status from the request
if (isset($_GET['order_id']) && isset($_GET['status'])) {
    $orderId = $_GET['order_id'];
    $newStatus = $_GET['status'];

    // Create a new database connection
    $conn = new mysqli("127.0.0.1", $username, $password, $dbname);

    // Check the connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    // Prepare and execute the SQL update statement
    $stmt = $conn->prepare("UPDATE orders SET status = ? WHERE order_id = ?");
    $stmt->bind_param("si", $newStatus, $orderId);
    $stmt->execute();

    // Check if the update was successful
    if ($stmt->affected_rows > 0) {
        echo "Order status updated successfully";
    } else {
        echo "Failed to update order status";
    }

    // Close the statement and connection
    $stmt->close();
    $conn->close();
} else {
    echo "Invalid request";
}
?>
