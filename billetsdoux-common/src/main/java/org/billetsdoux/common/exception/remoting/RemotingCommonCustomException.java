package org.billetsdoux.common.exception.remoting;
/***
* @Description:
* @Param:
* @return:
* @Author: billets-doux
* @Date: 11:57 下午 2020/4/2
*/
public class RemotingCommonCustomException extends RemotingException {
    private static final long serialVersionUID = 1546308581637799641L;

    public RemotingCommonCustomException(String message) {
        super(message);
    }

    public RemotingCommonCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
