<?php
$servername = "138.197.33.171";
$dbname     = "coursetracker";
$username   = "coursetracker";
$password   = "admin";

$coursecode = $_GET['courseID'];

// Create connection
$connection = mysqli_connect($servername, $username, $password, $dbname)
  or die("Error " . mysqli_error($connection));

$sql = "select TIME_FORMAT(time, '%H:%i'), courses.courseID, courseName, location
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
