<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

 
if (isset($_POST['Member_id']) && isset($_POST['Post_id']) && isset($_POST['Note']))
{
 
    // receiving the post params
    $Member_id= $_POST['Member_id'];
    $Post_id = $_POST['Post_id'] ;
    $Note=$_POST['Note'];


        $user = $db->addComment($Member_id , $Post_id , $Note);
        if ($user)
         {
            // user stored successfully
            $response["error"] = FALSE;
            $response["error_msga"] = "Your Comment Has Been Added";
            echo json_encode($response);
        } 
        else {
            // user failed to update
            $response["error"] = TRUE;
            $response["error_msg"] = "There is Error With inserting Your Data !";
            echo json_encode($response);
        }
    }
    else 
    {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters is missing!";
    echo json_encode($response);
}
?>