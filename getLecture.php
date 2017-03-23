<?php

require 'connect.php';

$coursecode = $_GET['courseID'];
$date       = $_GET['date'];

$sql = "SELECT TIME_FORMAT(time, '%H:%i'), courses.courseID,
        courseName, location, date
        FROM courses
        INNER JOIN lecture
        WHERE courses.courseID = '$coursecode'
        AND courses.courseID = lecture.courseID
        AND lecture.date = '$date'";

$result = mysqli_query($connection, $sql)
  or die("Error in selecting " . mysqli_error($connection));

$emparray = array();
while ($row = mysqli_fetch_assoc($result)) {
  $emparray[] = $row;
}
echo json_encode($emparray);
mysqli_close($connection);
?>
