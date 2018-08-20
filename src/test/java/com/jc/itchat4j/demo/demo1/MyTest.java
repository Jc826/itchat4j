package com.jc.itchat4j.demo.demo1;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jc.itchat4j.Wechat;
import com.jc.itchat4j.face.IMsgHandlerFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月28日 上午12:44:10
 * @version 1.0
 *
 */
public class MyTest {
	public static void main(String[] args) {
//		String qrPath = "/mnt/weixin/login"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
//		String rootPath ="/mnt/weixin";
//		Wechat wechat = new Wechat( qrPath); // 【注入】
//		IMsgHandlerFace msgHandler = new SimpleDemo(rootPath,wechat.getCore()); // 实现IMsgHandlerFace接口的类
//		wechat.setMsgHandler(msgHandler);
//		wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
		List<JSONObject> memberList=new ArrayList<>();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("name","jc");
		jsonObject.put("sex","boy");
		memberList.add(jsonObject);
		Map ss=new HashMap();
		ss.put("cc","sss");
		System.out.println(new JSONObject(ss));
		System.out.println(memberList.toString());
		System.out.println(JSONArray.parseArray(memberList.toString(),JSONObject.class));

	}
}
