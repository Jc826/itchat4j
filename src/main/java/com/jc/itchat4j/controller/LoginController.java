package com.jc.itchat4j.controller;

import com.jc.itchat4j.api.WechatTools;
import com.jc.itchat4j.core.Core;
import com.jc.itchat4j.core.CoreRedisStorage;
import com.jc.itchat4j.core.MsgCenter;
import com.jc.itchat4j.service.ILoginService;
import com.jc.itchat4j.service.impl.LoginServiceImpl;
import com.jc.itchat4j.thread.CheckLoginStatusThread;
import com.jc.itchat4j.utils.SleepUtils;
import com.jc.itchat4j.utils.tools.CommonTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

/**
 * 登陆控制器
 *
 * @author https://github.com/yaphone
 * @version 1.0
 * @date 创建时间：2017年5月13日 下午12:56:07
 */
public class LoginController {
    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);
    private Core core ;
    private WechatTools wechatTools;

    private ILoginService loginService ;

    public LoginController(JedisCluster jedisCluster,String appkey) {
        core = new CoreRedisStorage(jedisCluster,appkey);
        wechatTools = new WechatTools(core);
        loginService = new LoginServiceImpl(core);

    }
    public LoginController() {
        core = new Core();
        wechatTools = new WechatTools(core);
        loginService = new LoginServiceImpl(core);

    }

    public MsgCenter getMsgCenter() {
        return loginService.getMsgCenter();
    }

    public void login(String qrPath) {
        if (core.isAlive()) { // 已登陆
            LOG.info("itchat4j已登陆");
            return;
        }

        while (true) {
            for (int count = 0; count < 10; count++) {
                LOG.info("获取UUID");
                while (loginService.getUuid() == null) {
                    LOG.info("1. 获取微信UUID");
                    while (loginService.getUuid() == null) {
                        LOG.warn("1.1. 获取微信UUID失败，两秒后重新获取");
                        SleepUtils.sleep(2000);
                    }
                }
                LOG.info("2. 获取登陆二维码图片");
                if (loginService.getQR(qrPath)) {
                    break;
                } else if (count == 10) {
                    LOG.error("2.2. 获取登陆二维码图片失败，系统退出");
                    System.exit(0);
                }
            }
            LOG.info("3. 请扫描二维码图片，并在手机上确认");
            if (!core.isAlive()) {
                loginService.login();
                core.setAlive(true);
                LOG.info(("登陆成功"));
                break;
            }
            LOG.info("4. 登陆超时，请重新扫描二维码图片");
        }

        LOG.info("5. 登陆成功，微信初始化");
        if (!loginService.webWxInit()) {
            LOG.info("6. 微信初始化异常");
            System.exit(0);
        }

        LOG.info("6. 开启微信状态通知");
        loginService.wxStatusNotify();

        LOG.info("7. 清除。。。。");
        CommonTools.clearScreen();
        LOG.info(String.format("欢迎回来， %s", core.getNickName()));

        LOG.info("8. 开始接收消息");
        loginService.startReceiving();

        LOG.info("9. 获取联系人信息");
        loginService.webWxGetContact();

        LOG.info("10. 获取群好友及群好友列表");
        loginService.WebWxBatchGetContact();

        LOG.info("11. 缓存本次登陆好友相关消息");
        wechatTools = new WechatTools(core);
        wechatTools.setUserInfo(); // 登陆成功后缓存本次登陆好友相关消息（NickName, UserName）

        LOG.info("12.开启微信状态检测线程");
        new Thread(new CheckLoginStatusThread(core)).start();
        LOG.info("13.开启微信定时发送消息接口");
        loginService.sendTimingMsg();
    }

    public Core getCore() {
        return this.core;
    }
}