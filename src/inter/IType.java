package inter;

public interface IType {

    //系统消息 系统->用户
    //send: requestType=0&account=1&msg=xxx
    String TYPE_SYSTEM = "0";

    //登录 用户->系统
    //send: requestType=1&result=true/false&error=xxx
    //receive: requestType=1&account=username&password=pass
    String TYPE_LOGIN = "1";
    String TYPE_LOGIN_VC = "11";
    String TYPE_LOGIN_TOKEN = "12";

    //注册 用户->系统
    //send: requestType=2&result=true/false&error=xxx
    //receive: requestType=2&account=username&password=pass
    String TYPE_REGISTER = "2";

    //转发 用户->用户/用户群
    //根据转发目标判断是否需要群发
    //send: requestType=3&account=username&receiver=username/群ID&msg=xxx
    //receive: requestType=3&account=username&receiver=username/群ID&msg=xxx
    String TYPE_RELAY = "3";

    //Error 信息 系统 -> 用户
    //send: requestType=4&error=xxx
    String TYPE_ERROR = "4";

    //history 历史记录
    String TYPE_HISTORT = "5";

    //消息对应的解析词
    //消息类型
    String STRING_TYPE = "request_type";

    //密码
    String STRING_PASSWORD = "password";

    //uid
    String STRING_UID = "uid";

    //用户ID
    String STRING_ACCOUNT = "account";

    //接受用户
    String STRING_RECEIVER = "receiver";

    //消息主体
    String STRING_MSG = "message";

    //错误
    String STRING_ERR = "error";

    //验证码类型
    String STRING_VC = "verification_code";

    //返回类型
    String STRING_RESULT = "result";

    //消息标志
    String STRING_CURSOR = "cursor";

    //hash code
    String STRING_TOKEN = "token";
    /*
    例：
    public String toLogin(){
        return TYPE+"="+type+"&"+MSG+"="+message ;
    }
     */

    //注册登录返回result
    String RESULT_SUCCESS = "1";
    String RESULT_FAIL = "0";


    ///////////////////////////////////////
    //注册登录用
    int PHONE = 0;
    int EMAIL = 1;
}
