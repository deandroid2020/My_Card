<?php
 
class DB_Functions {

    private $conn;
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }

    // destructor
    function __destruct() {
         
    }


      // ------------------------------------------------------ {Post Functions}-----------------------------------------------------------------------//
    public function addPost($Title , $Member_id, $Description, $Category_id  , $Price , $lat ,$Longit , $City , $Pic_link  , $Place_Name , $End_date)
    {
    
$stmt = $this->conn->prepare("INSERT INTO Post VALUES(null , ? , ?, ? , ? ,? , ?  , ? , ? , ? , ? , 'Active' , ? )");
$stmt->bind_param("sssssssssss", $Title , $Member_id , $Description ,$Category_id , $Price  , $lat , $Longit ,
 $City , $Pic_link , $Place_Name , $End_date) ; // <--- 10 Data type  
        $result = $stmt->execute();
        $stmt->close();

        if ($result)
        {
            $stmt=$this->conn->prepare("SELECT Post_id From Post WHERE Title = ?");
            $stmt->bind_param("s", $Title);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        }
        else 
        {
        return false;
        }
    }

    public function getPosts()
{

    $stmt = $this->conn->prepare("select * ,(select if(sum(Rate.Value)<>'NULL',sum(Rate.Value),0) from Rate WHERE Rate.Post_id = Post.Post_id) AS Rate , 
    (select if(COUNT(Reports.Post_id)<>'NULL',COUNT(Reports.Post_id),0) from Reports WHERE Reports.Post_id = Post.Post_id) AS Report
    from Post WHERE Post.Post_Status = 'Active' and  ( Post.End_date >= CURDATE() or 
    Post.End_date ='0000-00-00' )and 
    Member_id= (SELECT Members.Member_id from Members where Members.Status = 'Active' and Post.Member_id = Members.Member_id) ORDER BY Report DESC ");

    if ($stmt->execute())
     {
        $user= array();
        $result=$stmt->get_result();
        while($row = $result->fetch_assoc()) {
            $user[] = $row;
        }
        $stmt->close();

        return $user;
}
}

public function getPostsByMemberId($Member_id)
{

    $stmt = $this->conn->prepare("select * ,(select if(sum(Rate.Value)<>'NULL',sum(Rate.Value),0) from Rate WHERE Rate.Post_id = Post.Post_id) AS Rate , 
    (select if ((Rate.Value) <>'NULL' , (Rate.Value) ,0 ) from Rate Where Rate.Member_id = ? and Rate.Post_id = Post.Post_id ) As RateValue , 
    (select if(COUNT(Reports.Post_id)<>'NULL',COUNT(Reports.Post_id),0) from Reports WHERE Reports.Post_id = Post.Post_id) AS Report
    from Post WHERE Post.Post_Status = 'Active' and  ( Post.End_date >= CURDATE() or 
    Post.End_date ='0000-00-00' )and Member_id=? and  Member_id= (SELECT Members.Member_id 
    from Members where Members.Status = 'Active' and Post.Member_id = Members.Member_id) ORDER BY Report DESC");
    
     $stmt->bind_param ( 'ss' , $Member_id , $Member_id);
    if ($stmt->execute())
     {
        $user= array();
        $result=$stmt->get_result();
        while($row = $result->fetch_assoc()) {
            $user[] = $row;
        }
        $stmt->close();

        return $user;
}
}


public function getPostsByPostId($Member_id , $Post_id)
{

    $stmt = $this->conn->prepare("select * ,(select if(sum(Rate.Value)<>'NULL',sum(Rate.Value),0) from Rate WHERE Rate.Post_id =? ) AS Rate , 
    (select if ((Rate.Value) <>'NULL' , (Rate.Value) ,0 ) from Rate Where Rate.Member_id = ? and Rate.Post_id = ? ) As RateValue  
    from Post WHERE  Post.Post_id = ?");
     $stmt->bind_param ( 'ssss' , $Post_id , $Member_id ,$Post_id,$Post_id);
    if ($stmt->execute())
     {
        $user= array();
        $result=$stmt->get_result();
        while($row = $result->fetch_assoc()) {
            $user[] = $row;
        }
        $stmt->close();

        return $user;
}
}







public function getBestPosts()
{
    $stmt = $this->conn->prepare("select * ,(select if(sum(Rate.Value)<>'NULL',sum(Rate.Value),0) from Rate WHERE Rate.Post_id = Post.Post_id) AS Rate , 
    (select if(COUNT(Reports.Post_id)<>'NULL',COUNT(Reports.Post_id),0) from Reports WHERE Reports.Post_id = Post.Post_id) AS Report
     from Post WHERE Post.Post_Status = 'Active' and  ( Post.End_date >= CURDATE() or Post.End_date ='0000-00-00'  )and 
    Member_id= (SELECT Members.Member_id from Members where Members.Status = 'Active' and Post.Member_id = Members.Member_id)
    ORDER BY Rate DESC
    LIMIT 10");

    if ($stmt->execute())
     {
        $user= array();
        $result=$stmt->get_result();
        while($row = $result->fetch_assoc()) {
            $user[] = $row;
        }
        $stmt->close();

        return $user;
}
}

public function getMyPost($Member_id)
{
    $stmt = $this->conn->prepare("select * ,(select if(sum(Rate.Value)<>'NULL',sum(Rate.Value),0) from Rate WHERE Rate.Post_id = Post.Post_id) AS Rate , 
    (select if(COUNT(Reports.Post_id)<>'NULL',COUNT(Reports.Post_id),0) from Reports WHERE Reports.Post_id = Post.Post_id) AS Report
    from Post WHERE Post.Member_id = ? and Post.Post_Status = 'Active'  ORDER BY Report DESC");
    
     $stmt->bind_param ( 's' , $Member_id);
    if ($stmt->execute())
     {
        $user= array();
        $result=$stmt->get_result();
        while($row = $result->fetch_assoc()) {
            $user[] = $row;
        }
        $stmt->close();

        return $user;
}
}


public function DeletePost($Post_id )
{
    $stmt = $this->conn->prepare("UPDATE Post SET Post_Status='Not'  WHERE Post_id=?");
    $stmt->bind_param( 's',$Post_id);  
    $result = $stmt->execute();
    $stmt->close();
 
        if ($result)
        {
            return true;
        }
        else 
        {
        return false;
        }

    }





    
    
      // ------------------------------------------------------ {Comment Functions}-----------------------------------------------------------------------//
    public function addComment($Member_id , $Post_id , $Note)
    {

        $stmt = $this->conn->prepare("INSERT INTO Comment VALUES(null , ?,?,? , 'Active')");
        $stmt->bind_param("sss", $Member_id , $Post_id , $Note ) ;  
        $result = $stmt->execute();
        $stmt->close();

        if ($result)
        {
            return true;
        }
        else 
        {
        return false;
        }

    }

    public function getComment($Post_id)
{

    $stmt = $this->conn->prepare("SELECT Members.Name , Comment.Note , Comment.Comment_id from Comment, Members 
    where Comment.Post_id =? and Comment.Status = 'Active' and Members.Member_id = Comment.Member_id ");
    
    $stmt->bind_param("s", $Post_id);
    if ($stmt->execute())
     {
        $user= array();
        $result=$stmt->get_result();
        while($row = $result->fetch_assoc()) {
            $user[] = $row;
        }
        $stmt->close();

        return $user;
}
}


public function DeleteComment($Comment_id)
{
    $stmt = $this->conn->prepare("UPDATE Comment set Status='Not' WHERE Comment_id=? ");
    $stmt->bind_param( 's', $Comment_id);  
    $result = $stmt->execute();
    $stmt->close();
 
        if ($result)
        {
            return true;
        }
        else 
        {
        return false;
        }

    }





    // ------------------------------------------------------ {Favorite Functions}-----------------------------------------------------------------------//
            public function addFavorite($Member_id , $Post_id)
            {
        
                $stmt = $this->conn->prepare("INSERT INTO Favorite VALUES(?,?, 'Active')");
                $stmt->bind_param("ss", $Member_id , $Post_id) ;
                $result = $stmt->execute();
                $stmt->close();
        
                if ($result)
                {
                    return true;
                }
                else 
                {
                    $stmt = $this->conn->prepare("UPDATE  Favorite Set Status='Active' Where   Member_id=? and Post_id=?");
                    $stmt->bind_param("ss", $Member_id , $Post_id) ;
                    $update = $stmt->execute();
                    $stmt->close();

                    if ($update)
                    {
                        return true;
                    }
                    else 
                    {
                        return false;
                    }
                }
        
            }


            public function DeleteFavorite($Member_id , $Post_id )
            {
                $stmt = $this->conn->prepare("UPDATE Favorite set Status='Not' WHERE Member_id=? and Post_id=?");
                $stmt->bind_param( 'ss', $Member_id , $Post_id);  
                $result = $stmt->execute();
                $stmt->close();
             
                    if ($result)
                    {
                        return true;
                    }
                    else 
                    {
                    return false;
                    }
            
                }

 


    // ------------------------------------------------------ {Member Functions}-----------------------------------------------------------------------//





        /**
     * Check user is existed or not
     */
    public function isUserExisted($E_mail) {
        $stmt = $this->conn->prepare("SELECT E_mail from Members WHERE E_mail = ?");
        $stmt->bind_param("s", $E_mail);
        $stmt->execute();
        $stmt->store_result();
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }


    public function getMemberById($Member_id) {
 
        $stmt = $this->conn->prepare("SELECT * FROM Members WHERE Member_id=?");
    
        $stmt->bind_param("i", $Member_id);
    
        if ($stmt->execute())
         {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
    }
    }




    



public function Forgetpassword($E_mail , $Password)
{

    $stmt = $this->conn->prepare("UPDATE Members SET Password=?  WHERE E_mail=?");
    $stmt->bind_param( 'ss' , $Password ,$E_mail );  
    $result = $stmt->execute();
    $stmt->close();

    // check for successful updated
    if ($result) 
    {
        return true;
    }
     else
    {
        return false;
    }
}
     


    
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($UserName, $Password ) {
 
        $stmt = $this->conn->prepare("SELECT * FROM USER WHERE User_ID = ?");
 
        $stmt->bind_param("s", $User_ID);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            // check for password equality
            if ($Password == $user['Password'])
             {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }


    public function CheckPassword($E_mail, $CPassword) {
 
        $stmt = $this->conn->prepare("SELECT * FROM Members WHERE E_mail= ?");
 
        $stmt->bind_param("s", $E_mail);
 
        if ($stmt->execute()) 
        {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            // check for password equality
            if ($CPassword == $user['Password'])
             {
                // user authentication details are correct
                return True;
            }
        } else {
            return FALSE;
        }
    }


 
}
 
?>