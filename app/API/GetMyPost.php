<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
// json response array
$response = array("error" => FALSE);
if (isset($_POST['Member_id']))
{

    // receiving the post params
    $Member_id= $_POST['Member_id'];  
    $Report = $db->getMyPost($Member_id); 
    if ($Report)
     {
        // use is found
        $response["error"] = FALSE;
        $response["Report"]= $Report;
        header('Content-Type: application/json');
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = " No Post Is Found!";
        header('Content-Type: application/json');
        echo json_encode($response);
    
    }
}
else 
{
$response["error"] = TRUE;
$response["error_msg"] = "Required parameters is missing!";
header('Content-Type: application/json');
echo json_encode($response);
}
?>