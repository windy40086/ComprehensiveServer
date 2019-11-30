package control.task;

import entity.Message;
import inter.IChannel;
import inter.IError;
import inter.ITask;
import inter.IType;
import service.TokenService;

public class TokenLoginTask implements ITask, IType, IError {

    @Override
    public boolean doTask(IChannel channel, Message message) {
        String token = message.getToken();
        String uid = message.getUid();
        Message result = new Message();
        result.setType(TYPE_LOGIN_TOKEN);

        //user的token正确
        if (TokenService.checkToken(uid, token)) {
            channel.getUser().setId(uid);

            result.setResult(RESULT_SUCCESS);
            result.setError(ERROR_NONE);

            TokenService.addToken(uid);
            result.setToken(TokenService.getToken(uid));
            sendMessage(channel, result.toString());
            return true;
        } else {
            result.setResult(RESULT_FAIL);
            result.setError(ERROR_LOGIN_TOKEN_IS_WRONG);
            sendMessage(channel, result.toString());
        }
        return false;
    }

    //发送登录信息
    private static boolean sendMessage(IChannel channel, String message) {
        return channel.send(message);
    }
}
