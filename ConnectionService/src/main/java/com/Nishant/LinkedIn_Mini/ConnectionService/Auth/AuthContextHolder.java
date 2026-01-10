package com.Nishant.LinkedIn_Mini.ConnectionService.Auth;

public class AuthContextHolder {
    //create a threadLocal that will hold the CurrentUserId , globally , so we can access this in post service
    //anywhere  , we dont need to pass it everywhere inside the code
    private static final ThreadLocal<Long>  currentUserId = new ThreadLocal<>(); //this will hold the current user id

    //getter
    public static Long getCurrentUserId()
    {
        return currentUserId.get();
    }

    //setter , this is not public so only class within this Auth package will be able to set the userId , so this is package private
    static void setCurrentUserId(Long userId)
   {
       currentUserId.set(userId);
   }

   static void clear()
   {
       currentUserId.remove(); // it will remove the user id , again this is static not public static
   }

}
