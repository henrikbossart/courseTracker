<?php

require 'connect.php';

$coursecode = $_GET['courseID'];

$sql = "select courses.courseID, courseName, location, examDate
        from courses
        inner join lecture
        WHERE courses.courseID = '$coursecode'
        AND courses.courseID = lecture.courseID";

$result = mysqli_query($connection, $sql)
  or die("Error in selecting " . mysqli_error($connection));

$emparray = array();
while ($row = mysqli_fetch_assoc($result)) {
  $emparray[] = $row;
}
echo json_encode($emparray);
mysqli_close($connection);
?>
