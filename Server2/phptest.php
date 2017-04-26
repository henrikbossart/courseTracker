<?php
$servername = "138.197.33.171";
$dbname     = "coursetracker";
$username   = "coursetracker";
$password   = "admin";

$coursecode = $_GET['courseID'];

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connection_error);
}

$sql = "SELECT * FROM coursetracker.courses WHERE courseID='$coursecode'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  while ($row = $result->fetch_assoc()) {
    echo "courseID: " . $row["courseID"] . ", coursename: " . $row["coursename"] . "<br>";
  }
} else {
  echo "0 results";
}
$conn->close();

 ?>
