package cn.zhouyafeng.itchat4j.demo.demo1;

import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.beans.RecommendInfo;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.thread.CoreHolder;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * 简单示例程序，收到文本信息自动回复原信息，收到图片、语音、小视频后根据路径自动保存
 *
 * @author https://github.com/yaphone
 * @version 1.0
 * @date 创建时间：2017年4月25日 上午12:18:09
 */
public class SimpleDemo implements IMsgHandlerFace {
    Logger LOG = Logger.getLogger(SimpleDemo.class);
    private Core core ;
    private WechatTools wechatTools ;
    private MessageTools messageTools ;
    private DownloadTools downloadTools ;
    private String rootPath;

    public SimpleDemo(String rootPath,Core core) {
        this.rootPath = rootPath;
        this.core = core;
        this.wechatTools = new WechatTools(core);
        this.messageTools = new MessageTools(core, wechatTools);
        this.downloadTools = new DownloadTools(core);
    }

    @Override
    public String textMsgHandle(BaseMsg msg) {
        // String docFilePath = "D:/itchat4j/pic/1.jpg"; // 这里是需要发送的文件的路径
        if (!msg.isGroupMsg()) { // 群消息不处理
            // String userId = msg.getString("FromUserName");
            // MessageTools.sendFileMsgByUserId(userId, docFilePath); // 发送文件
            // MessageTools.sendPicMsgByUserId(userId, docFilePath);

            String text = msg.getText(); // 发送文本消息，也可调用MessageTools.sendFileMsgByUserId(userId,text);
            LOG.info(text);
            if (text.equals("111")) {
                wechatTools.logout();
            }
            if (text.equals("222")) {
                wechatTools.remarkNameByNickName("yaphone", "Hello");
            }
            if (text.equals("333")) { // 测试群列表
//				System.out.print(WechatTools.getGroupNickNameList());
//				System.out.print(WechatTools.getGroupIdList());
                System.out.print(wechatTools.getGroupList());
                System.out.print(wechatTools.getGroupNickNameIdList());
//				System.out.print(Core.getInstance().getGroupMemeberMap());
            }
            return text;
        }
        return null;
    }

    @Override
    public String picMsgHandle(BaseMsg msg) {
        downloadTools.getDownloadFn(msg, MsgTypeEnum.PIC.getType(), rootPath); // 保存图片的路径
        return "图片保存成功";
    }

    @Override
    public String voiceMsgHandle(BaseMsg msg) {

        downloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), rootPath);
        return "声音保存成功";
    }

    @Override
    public String viedoMsgHandle(BaseMsg msg) {
        downloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), rootPath);
        return "视频保存成功";
    }

    @Override
    public String nameCardMsgHandle(BaseMsg msg) {
        return "收到名片消息";
    }

    @Override
    public void sysMsgHandle(BaseMsg msg) { // 收到系统消息
        String text = msg.getContent();
        LOG.info(text);
    }

    @Override
    public String verifyAddFriendMsgHandle(BaseMsg msg) {
        messageTools.addFriend(msg, true); // 同意好友请求，false为不接受好友请求
        RecommendInfo recommendInfo = msg.getRecommendInfo();
        String nickName = recommendInfo.getNickName();
        String province = recommendInfo.getProvince();
        String city = recommendInfo.getCity();
        String text = "你好，来自" + province + city + "的" + nickName + "， 欢迎添加我为好友！";
        return text;
    }

    @Override
    public String mediaMsgHandle(BaseMsg msg) {
        String fileName = msg.getFileName();
        String filePath = rootPath + "/file" + File.separator + fileName; // 这里是需要保存收到的文件路径，文件可以是任何格式如PDF，WORD，EXCEL等。
        downloadTools.getDownloadFn(msg, MsgTypeEnum.MEDIA.getType(), filePath);
        return "文件" + fileName + "保存成功";
    }

}
