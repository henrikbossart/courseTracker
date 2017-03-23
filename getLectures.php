<?php

require 'connect.php';

$coursecode = $_GET['courseID'];

$sql = "SELECT courses.courseID, courseName, time, date, room FROM `lecture`
        INNER JOIN courses
        WHERE lecture.courseID = courses.courseID";

$result = mysqli_query($connection, $sql)
  or die("Error in selecting " . mysqli_error($connection));

$emparray = array();
while ($row = mysqli_fetch_assoc($result)) {
  $emparray[] = $row;
}
echo json_encode($emparray);
mysqli_close($connection);
?>
