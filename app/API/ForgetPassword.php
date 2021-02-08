<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['E_mail']) && isset($_POST['Password'])) 
{
 
    // receiving the post params
    $E_mail = $_POST['E_mail'];
    $Password = $_POST['Password'];
     
    // check if The Email is already existe
    if ( ! $db->isUserExisted($E_mail)) 
    {
        // user already existed
        $response["error"] = TRUE;
        $response["email_error_msg"] = "غير مسجل  في النظام ". $E_mail." البريد الإلكتروني";
        echo json_encode($response);
    } 
    else 
    {
        // create a new user
        $user = $db-> Forgetpassword($E_mail , $Password) ;
        if ($user)
         {
            // user stored successfully
            $response["error"] = FALSE;
            $response["error_msg"] = "تم ارسال كلمة المرور الجديدة للايميل ";
            echo json_encode($response);
        } 
        else 
        {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "يوجد مشكلة في استعادة كلمة المرور حاول لاحقا";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}
?>