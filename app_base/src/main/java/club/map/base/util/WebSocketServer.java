package club.map.base.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2019-01-15
 * Time: 16:54
 * Description:
 */
@ServerEndpoint("/websocket/{user}")
//@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {

    protected final Log log = LogFactory.getLog(getClass());

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全，用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<Integer, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //用户id
    private Integer id = null;

    /**
     * 对应用户
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "id") Integer id, Session session) {
        this.session = session;
        this.id = id;//接收到发送消息的人员编号
        webSocketSet.put(id, this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("用户" + id + "加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        //可以自己约定字符串内容，比如 内容|0 表示信息群发，内容|X 表示信息发给id为X的用户
        String sendMessage = message.split("[|]")[0];
        Integer sendUserId = Integer.valueOf(message.split("[|]")[1]);
        try {
            if (sendUserId.equals("0")) {
                sendtoAll(sendMessage);
            } else {
                sendtoUser(sendMessage, sendUserId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
     *
     * @param message
     * @param sendUserId
     * @throws IOException
     */
    public void sendtoUser(String message, Integer sendUserId) throws IOException {
        if (webSocketSet.get(sendUserId) != null) {
            if (!id.equals(sendUserId)) {
                webSocketSet.get(sendUserId).sendMessage("用户" + id + "发来消息：" + " <br/> " + message);
            } else {
                webSocketSet.get(sendUserId).sendMessage(message);
            }
        } else {
            //如果用户不在线则返回不在线信息给自己
            sendtoUser("当前用户不在线", id);
        }
    }

    /**
     * 发送信息给所有人
     *
     * @param message
     * @throws IOException
     */
    public void sendtoAll(String message) throws IOException {
        for (Integer key : webSocketSet.keySet()) {
            try {
                webSocketSet.get(key).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


//    /**
//     * 群发自定义消息
//     * */
//    public static void sendInfo(String message) throws IOException {
//        log.info(message);
//        for (WebSocketServer item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                continue;
//            }
//        }
//    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
