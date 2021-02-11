<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['User_ID']) && isset($_POST['Password']) ) {
 
    // receiving the post params
    $User_ID = $_POST['User_ID'];
    $Password = $_POST['Password'];
    
 
    // get the user by email and password
    $user = $db->getUserByEmailAndPassword($User_ID, $Password );
 
    if ($user != false) {
        // use is found
        $response["error"] = FALSE;
        $response["user"]= $user;
        header('Content-Type: application/json');
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "معلومات الدخول غير صحيحة الرجاء المحاولة مرة أخرى";
        header('Content-Type: application/json');
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters email or password is missing!";
    header('Content-Type: application/json');
    echo json_encode($response);
}
?>
