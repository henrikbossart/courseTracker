<?php
  $servername = "138.197.33.171";
  $dbname     = "coursetracker";
  $username   = "coursetracker";
  $password   = "admin";

  $connection = mysqli_connect($servername, $username, $password, $dbname)
    or die("Error " . mysqli_error($connection));
