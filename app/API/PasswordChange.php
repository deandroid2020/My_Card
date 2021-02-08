<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['E_mail']) && isset($_POST['CPassword']) && isset($_POST['Password'])) {
 
    // receiving the post params
    $E_mail = $_POST['E_mail'];
    $CPassword = $_POST['CPassword'];
    $Password = $_POST['Password'];
 
    // get the user by email and password
    $user = $db->CheckPassword($E_mail, $CPassword) ;
 
    if ($user != false) 
    {
        // use is found
        $response["error"] = FALSE;
        $result = $db->UpdatePassword($E_mail , $Password);
        if ($result == TRUE)
        {
        $response["msg"] = "Your Password Has Been Updated";
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

    }
     else
      {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Email Or Current Password is incorrect . Please try again!";
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