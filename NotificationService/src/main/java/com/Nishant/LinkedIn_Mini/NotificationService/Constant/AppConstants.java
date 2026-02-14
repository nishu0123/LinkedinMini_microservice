package com.Nishant.LinkedIn_Mini.NotificationService.Constant;

public final class AppConstants {
    public static final Long SUPER_USER_FOLLOWER_MIN_LIMIT = 1000L; //make it public and static so that it can be accessed from anywhere


//private constructor to insure that this class should not be initialized
    private AppConstants() {
        throw new UnsupportedOperationException("This is a constant class and cannot be instantiated");
    }
}
