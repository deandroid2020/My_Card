<?php

require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['Member_id'])) 
{
    // receiving the post params
    $Member_id = $_POST['Member_id'];
        
        $result = $db-> getFavorite($Member_id);
        if ($result)
        {
           // use is found
           $response["error"] = FALSE;
           $response["result"]= $result;
           header('Content-Type: application/json');
           echo json_encode($response);
       } 
       else
        {
           // user is not found with the credentials
           $response["error"] = TRUE;
           $response["error_msg"] = " No Transactions Is  Found!";
           header('Content-Type: application/json');
           echo json_encode($response);
       }
    }
    else
{
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name) is missing!";
    header('Content-Type: application/json');
    echo json_encode($response);
}
?>