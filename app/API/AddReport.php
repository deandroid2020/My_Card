<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['Member_id']) && isset($_POST['Reason']) && isset($_POST['Post_id']))
{
 
    // receiving the post params
    $Post_id = $_POST['Post_id'];
    $Reason = $_POST['Reason'];
    $Member_id = $_POST['Member_id'];

 
    // get the user by email and password
    $result = $db->  addReport($Member_id , $Post_id  , $Reason );

    if ($result)
    {
        $response["error"] = FALSE;
        $response["msg"] = "This Post Has Been Reported To The Admin";
        header('Content-Type: application/json');
        echo json_encode($response);
    }
    else 
    {
        $response["error"] = TRUE;
        $response["error_msg"] = "There Is A Problem .Please try again!";
        header('Content-Type: application/json');
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters are missing!";
    header('Content-Type: application/json');
    echo json_encode($response);
}
?>


