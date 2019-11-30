package inter;

public interface IError {
    //register error
    int ERROR_REGISTER = 10000;
    int ERROR_REGISTER_ACCOUNT_ALREADY_USE = 10001;
    int ERROR_REGISTER_FAIL = 10002;
    int ERROR_REGISTER_VERIFICATION_CODE_IS_WRONG = 10003;
    int ERROR_REGISTER_EMAIL_WRONG = 10005;

    //login error
    int ERROR_LOGIN = 11000;
    int ERROR_LOGIN_ACCOUNT_NOT_FIND = 11001;
    int ERROR_LOGIN_MISMATCH = 11002;
    int ERROR_LOGIN_ACCOUNT_IS_BAN = 11003;
    int ERROR_LOGIN_ACCOUNT_IS_LOGIN = 11004;

    int ERROR_LOGIN_UID_IS_EXIST = 11005;
    int ERROR_LOGIN_UID_IS_WRONG = 11006;
    int ERROR_LOGIN_TOKEN_NOT_EXIST = 11007;
    int ERROR_LOGIN_TOKEN_IS_WRONG = 11008;
    int ERROR_LOGIN_VERIFICATION_CODE_IS_WRONG = 11009;
    int ERROR_LOGIN_ACCOUNT_NOT_EXIST = 11010;

    //relay error
    int ERROR_RELAY = 12000;
    int ERROR_RELAY_TOKEN_IS_EXPIRATION = 12001;

    //Server error
    int ERROR_SYSTEM = 80000;
    int ERROR_NULL_POINTER = 80001;
    int ERROR_MSG_CANT_ANALYZE = 80002;
    int ERROR_SMSClient_IS_CLOSE = 80003;

    int ERROR_MYSQL = 81000;

    //None error
    int ERROR_NONE = 99999;
}
