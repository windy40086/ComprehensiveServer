package inter;

public interface IError {
    //register error
    int ERROR_REGISTER = 10000;
    int ERROR_REGISTER_ACCOUNT_ALREADY_USE = 10001;
    int ERROR_REGISTER_FAIL = 10002;
    int ERROR_REGISTER_VERIFICATION_CODE_IS_WRONG = 10003;
    int ERROR_REGISTER_SMSClient_IS_CLOSE = 10004;
    int ERROR_REGISTER_EMAIL_WRONG = 10005;

    //login error
    int ERROR_LOGIN = 11000;
    int ERROR_LOGIN_ACCOUNT_NOT_FIND = 11001;
    int ERROR_LOGIN_MISMATCH = 11002;
    int ERROR_LOGIN_ACCOUNT_IS_BAN = 11003;
    int ERROR_LOGIN_ACCOUNT_IS_LOGIN = 11004;

    //relay error
    int ERROR_RELAY = 12000;

    //Server error
    int ERROR_SYSTEM = 80000;
    int ERROR_NULL_POINTER = 80001;
    int ERROR_MSG_CANT_ANALYZE = 80002;

    int ERROR_MYSQL = 81000;

    //None error
    int ERROR_NONE = 99999;
}
