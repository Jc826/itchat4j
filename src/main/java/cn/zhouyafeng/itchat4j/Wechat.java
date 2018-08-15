package cn.zhouyafeng.itchat4j;

import cn.zhouyafeng.itchat4j.controller.LoginController;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.core.MsgCenter;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wechat {
    private static final Logger LOG = LoggerFactory.getLogger(Wechat.class);
    private IMsgHandlerFace msgHandler;
    private MsgCenter msgCenter;

    private Core core;

    public IMsgHandlerFace getMsgHandler() {
        return msgHandler;
    }

    public void setMsgHandler(IMsgHandlerFace msgHandler) {
        this.msgHandler = msgHandler;
    }

    public Wechat(String qrPath) {
        System.setProperty("jsse.enableSNIExtension", "false"); // 防止SSL错误


        // 登陆
        LoginController login = new LoginController();
        msgCenter = login.getMsgCenter();
        login.login(qrPath);
        setCore(login.getCore());
    }

    public Core getCore() {
        return core;
    }

    private void setCore(Core core) {
        this.core = core;
    }

    public void start() {
        LOG.info("+++++++++++++++++++开始消息处理+++++++++++++++++++++");
        new Thread(new Runnable() {
            @Override
            public void run() {

                msgCenter.handleMsg(msgHandler);
            }
        }).start();
    }

}
