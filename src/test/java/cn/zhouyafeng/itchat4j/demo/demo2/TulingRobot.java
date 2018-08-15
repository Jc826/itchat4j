package cn.zhouyafeng.itchat4j.demo.demo2;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.thread.CoreHolder;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;

/**
 * 图灵机器人示例
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月24日 上午12:13:26
 * @version 1.0
 *
 */
public class TulingRobot implements IMsgHandlerFace {
	Logger logger = Logger.getLogger("TulingRobot");
	private   Core core = CoreHolder.getCore();

	MyHttpClient myHttpClient = CoreHolder.getCore().getMyHttpClient();
	private WechatTools wechatTools=new WechatTools(core);
	private MessageTools messageTools=new MessageTools(core,wechatTools);
	private  DownloadTools downloadTools=new DownloadTools(core);

	String url = "http://openapi.tuling123.com/openapi/api/v2";
	String apiKey = "b7e983edaf034481b02289c4b585c6a5"; // 这里是我申请的图灵机器人API接口，每天只能5000次调用，建议自己去申请一个，免费的:)

	@Override
	public String textMsgHandle(BaseMsg msg) {
		String result = "";
		String text = msg.getText();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> perception = new HashMap<String, Object>();
        perception.put("inputText",text);
		Map<String, Object> userInfo = new HashMap<String, Object>();

        userInfo.put("apiKey",apiKey);
        userInfo.put("userId","12345");


		paramMap.put("userInfo", userInfo);
		paramMap.put("perception", perception);
		String paramStr = JSON.toJSONString(paramMap);
		try {
			HttpEntity entity = myHttpClient.doPost(url, paramStr);
			result = EntityUtils.toString(entity, "UTF-8");
			JSONObject obj = JSON.parseObject(result);
			if (obj.getString("code").equals("100000")) {
				result = obj.getString("text");
			} else {
				result = "处理有误";
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return result;
	}

	@Override
	public String picMsgHandle(BaseMsg msg) {
		return "收到图片";
	}

	@Override
	public String voiceMsgHandle(BaseMsg msg) {
		String fileName = String.valueOf(new Date().getTime());
		String voicePath = "D://itchat4j/voice" + File.separator + fileName + ".mp3";
		downloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath);
		return "收到语音";
	}

	@Override
	public String viedoMsgHandle(BaseMsg msg) {
		String fileName = String.valueOf(new Date().getTime());
		String viedoPath = "D://itchat4j/viedo" + File.separator + fileName + ".mp4";
		downloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), viedoPath);
		return "收到视频";
	}

	public static void main(String[] args) {

		Wechat wechat = new Wechat( "D://itchat4j/login");
		IMsgHandlerFace msgHandler = new TulingRobot();
		wechat.setMsgHandler(msgHandler);
		wechat.start();
	}

	@Override
	public String nameCardMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sysMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
	}

	@Override
	public String verifyAddFriendMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mediaMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
