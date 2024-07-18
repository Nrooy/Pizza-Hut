package PizzaHut_be.model.enums;

public enum StatusCodeEnum {
    //LOGIN SUCCESS
    LOGIN1201("LOGIN1201"), // Login Google Sso successfully with create new user
    LOGIN1206("LOGIN1206"), // Login Google successfully with user exist
    //LOGIN FAIL
    LOGIN0201("LOGIN0201"), // Login Google Sso failed by verify Google authorization code
    LOGIN0213("LOGIN0213"), // Login Google Sso failed when create new user by Google user
    LOGIN0228("LOGIN0228"); // Login Google Sso failed by update avatar user failed
    public final String value;

    StatusCodeEnum(String i) {
        value = i;
    }
}
