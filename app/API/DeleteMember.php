<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['Member_id']))
{
 
    // receiving the post params
    $Member_id = $_POST['Member_id'];

 
    // get the user by email and password
    $result = $db-> DeleteMember($Member_id);

    if ($result)
    {
        $response["error"] = FALSE;
        $response["msg"] = "Your Member Has Been Updated";
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