<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
    $Report = $db->getPosts(); 
 
    if ($Report)
     {
        
        $response["error"] = FALSE;
        $response["Report"]= $Report;
        header('Content-Type: application/json');
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = " No Lesson Found!";
        header('Content-Type: application/json');
        echo json_encode($response);
    }

?>