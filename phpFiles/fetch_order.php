<?php
$username = "s2451244";
$password = "s2451244";
$dbname = "d2451244";

// Create connection
$conn = new mysqli("127.0.0.1", $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Fetch orders with staff name and restaurant name
$query = "SELECT o.order_id, o.customer_name, o.staff_id, o.order_time, o.status, s.staff_name, r.restaurant_name 
          FROM orders o
          LEFT JOIN staff s ON o.staff_id = s.staff_id
          LEFT JOIN restaurants r ON o.restaurant_id = r.restaurant_id";
$result = $conn->query($query);

$orders = array();
while ($row = $result->fetch_assoc()) {
    $order = array(
        'order_id' => $row['order_id'],
        'customer_name' => $row['customer_name'],
        'staff_id' => $row['staff_id'],
        'order_time' => $row['order_time'],
        'status' => $row['status'],
        'staff_name' => $row['staff_name'],
        'restaurant_name' => $row['restaurant_name']
        // Add other fields as needed
    );
    $orders[] = $order;
}

// Return JSON response
header('Content-Type: application/json');
echo json_encode($orders);

$conn->close();
?>
