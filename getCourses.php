<?php

require 'connect.php';

$coursecode = $_GET['courseID'];

$sql = "SELECT * FROM courses;"

$result = mysqli_query($connection, $sql)
  or die("Error in selecting " . mysqli_error($connection));

$emparray = array();
while ($row = mysqli_fetch_assoc($result)) {
  $emparray[] = $row;
}
echo json_encode($emparray);
mysqli_close($connection);
?>
