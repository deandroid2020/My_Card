<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['Post_id'])) 
{
 
    // receiving the post params
    $Post_id= $_POST['Post_id'];
 
    // get shops by type
    $comment = $db->getComment($Post_id);
 
    if ($comment)
     {
        // use is found
        $response["error"] = FALSE;
        $response["Comment"]= $comment;
        

        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = " Wrong No Comment Yet!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters Post Id is missing!";
    echo json_encode($response);
}

?>