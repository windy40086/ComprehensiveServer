package inter;

public interface IError {
    //register error
    int ERROR_REGISTER = 10000;
    int ERROR_REGISTER_ACCOUNT_ALREADY_USE = 10001;

    //login error
    int ERROR_LOGIN = 11000;
    int ERROR_LOGIN_ACCOUNT_NOT_FIND = 11001;
    int ERROR_LOGIN_MISMATCH = 11002;
    int ERROR_LOGIN_ACCOUNT_IS_BAN = 11003;

    //Msg error

    int ERROR_MSG_NULLPORINT = 80001;

    //None error
    int ERROR_NONE = 99999;
}
