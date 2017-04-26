<?php

require 'connect.php';

$coursecode = $_GET['courseID'];

$sql = "SELECT courses.courseID, courseName, TIME_FORMAT(time, '%H:%i') AS 'time',
        room, lecture.date, curriculum
        FROM courses
        INNER JOIN lecture
        WHERE courses.courseID = '$coursecode'
        AND courses.courseID = lecture.courseID
        AND lecture.date = CURDATE()
	ORDER BY time";

$result = mysqli_query($connection, $sql)
  or die("Error in selecting " . mysqli_error($connection));

$emparray = array();
while ($row = mysqli_fetch_assoc($result)) {
  $emparray[] = $row;
}
echo json_encode($emparray);
mysqli_close($connection);
?>



