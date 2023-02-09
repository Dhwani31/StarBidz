package com.example.starbidz.Connection;

public class ServerConfig
{
    public static String BASE_URL = "http://invisionsoftwaresolution.in/Student/Starbidz/"; //Admin
    //public static String BASE_URL = "http://192.168.0.107/bidz_api/";

    //SELLER
    public static String S_CHECK_USER = BASE_URL + "s_check_user.php";
    public static String S_REGISTER = BASE_URL + "s_register.php";
    public static String S_LOGIN = BASE_URL + "s_login.php";
    public static String S_FORGOT_PASSWORD = BASE_URL + "s_forgot_password.php";

    public static String S_PROFILE = BASE_URL + "s_profile.php";
    public static String S_CHANGE_PASSWORD = BASE_URL + "s_change_password.php";
    public static String S_CHANGE_PROFILE = BASE_URL + "s_change_profile.php";

    public static String FETCH_AREA = BASE_URL + "area.php";
    public static String FETCH_CATEGORY = BASE_URL + "category.php";
    public static String S_FEEDBACK = BASE_URL + "s_feedback.php";

    public static String S_ADD_PROPERTY = BASE_URL + "s_add_property.php";
    public static String S_MY_PROPERTY = BASE_URL + "s_my_property.php";
    public static String S_DELETE_PROPERTY = BASE_URL + "s_delete_property.php";
    public static String S_AUCTION = BASE_URL + "s_auction.php";
    public static String S_RESULT = BASE_URL + "s_result.php";


    public static String CHECK_TODAY = BASE_URL + "check_product.php";



    //BUYER
    public static String B_LOGIN = BASE_URL + "b_login.php";
    public static String B_CHECK_USER = BASE_URL + "b_check_user.php";
    public static String B_REGISTER = BASE_URL + "b_register.php";
    public static String B_FORGOT_PASSWORD = BASE_URL + "b_forgot_password.php";

    public static String B_PROFILE = BASE_URL + "b_profile.php";
    public static String B_CHANGE_PASSWORD = BASE_URL + "b_change_password.php";
    public static String B_CHANGE_PROFILE = BASE_URL + "b_change_profile.php";

    public static String B_ALL_PROPERTY = BASE_URL + "b_all_property.php";
    public static String B_AUCTION_LIST = BASE_URL + "b_auction_list.php";
    public static String B_AUCTION_ADD = BASE_URL + "b_auction_add.php";
    public static String B_MY_AUCTION = BASE_URL + "b_my_auction.php";
    public static String B_RESULT = BASE_URL + "b_result.php";


}
