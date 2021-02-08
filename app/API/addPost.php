<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
// json response array
$response = array("error" => FALSE);
// // $_POST = json_decode(file_get_contents('php://input'), true);

// if ( isset($_POST['Title']))
// {
//     $response["error"] = TRUE;
//     $response["error_msg"] = "                 Title is here           ";
//     echo json_encode($response);

// }
// else 
// {
//     $response["error"] = TRUE;
//     $response["error_msg"] = $_POST[''];
//     echo json_encode($response);
// }


if ( isset($_POST['Title']) && isset($_POST['Member_id']) && isset($_POST['Description'])
 && isset($_POST['Category_id']) && isset($_POST['Price'])  && isset($_POST['lat']) 
 && isset($_POST['Longit']) && isset($_POST['City']) && isset($_POST['Pic_link']) && isset($_POST['Place_Name']) && isset($_POST['End_date']))
{
    // receiving the post params
    $Title = $_POST['Title'];
    $Member_id = $_POST['Member_id'] ;
    $Description=$_POST['Description'];
    $Category_id=$_POST['Category_id'];
    $Price=$_POST['Price'];
    $lat=$_POST['lat'];
    $Longit=$_POST['Longit'];
    $City=$_POST['City'];
    $Pic_link=$_POST['Pic_link'];
    $Place_Name=$_POST['Place_Name'];
    $End_date=$_POST['End_date'];

    $user = $db->addPost($Title , $Member_id, $Description, $Category_id  , $Price , $lat ,$Longit , $City , $Pic_link , $Place_Name , $End_date );
        if ($user)
         {
            // user stored successfully
            $response["error"] = FALSE;
            $response["user"]["Post_id"] = $user["Post_id"];
            echo json_encode($response);
        } 
        else {
            // user failed to update
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in insertation!";
            echo json_encode($response);
        }
    }
    else 
    {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (ID ) is missing!";
    echo json_encode($response);
}
?>