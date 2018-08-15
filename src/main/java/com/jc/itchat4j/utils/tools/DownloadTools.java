package com.jc.itchat4j.utils.tools;

import com.jc.itchat4j.beans.BaseMsg;
import com.jc.itchat4j.core.Core;
import com.jc.itchat4j.utils.MyHttpClient;
import com.jc.itchat4j.utils.enums.MsgTypeEnum;
import com.jc.itchat4j.utils.enums.URLEnum;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * 下载工具类
 *
 * @author https://github.com/yaphone
 * @version 1.0
 * @date 创建时间：2017年4月21日 下午11:18:46
 */
public class DownloadTools {
    private static Logger logger = Logger.getLogger("DownloadTools");
    private Core core;
    private MyHttpClient myHttpClient;

    public DownloadTools(Core core) {
        this.core = core;
        this.myHttpClient = core.getMyHttpClient();
    }

    /**
     * 处理下载任务
     *
     * @param msg
     * @param type
     * @param path
     * @return
     * @author https://github.com/yaphone
     * @date 2017年4月21日 下午11:00:25
     */
    public Object getDownloadFn(BaseMsg msg, String type, String path) {
        Map<String, String> headerMap = new HashMap<String, String>();
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        String url = "";
        String filePath = path;
        String fileItem = "";
        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());// 这里使用收到图片的时间作为文件名
        if (type.equals(MsgTypeEnum.PIC.getType())) {
            filePath = path + "/pic";

            fileItem = filePath + File.separator + fileName + ".jpg"; // 调用此方法来保存图片
            url = String.format(URLEnum.WEB_WX_GET_MSG_IMG.getUrl(), (String) core.getLoginInfo().get("url"));
        } else if (type.equals(MsgTypeEnum.VOICE.getType())) {
            filePath = path + "/voice";
            fileItem = filePath + File.separator + fileName + ".mp3";
            url = String.format(URLEnum.WEB_WX_GET_VOICE.getUrl(), (String) core.getLoginInfo().get("url"));
        } else if (type.equals(MsgTypeEnum.VIEDO.getType())) {
            filePath = path + "/viedo";
            fileItem = filePath + File.separator + fileName + ".mp4";
            headerMap.put("Range", "bytes=0-");
            url = String.format(URLEnum.WEB_WX_GET_VIEDO.getUrl(), (String) core.getLoginInfo().get("url"));
        } else if (type.equals(MsgTypeEnum.MEDIA.getType())) {
            headerMap.put("Range", "bytes=0-");
            url = String.format(URLEnum.WEB_WX_GET_MEDIA.getUrl(), (String) core.getLoginInfo().get("fileUrl"));
            params.add(new BasicNameValuePair("sender", msg.getFromUserName()));
            params.add(new BasicNameValuePair("mediaid", msg.getMediaId()));
            params.add(new BasicNameValuePair("filename", msg.getFileName()));
        }
        params.add(new BasicNameValuePair("msgid", msg.getNewMsgId()));
        params.add(new BasicNameValuePair("skey", (String) core.getLoginInfo().get("skey")));
        HttpEntity entity = myHttpClient.doGet(url, params, true, headerMap);
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            OutputStream out = new FileOutputStream(fileItem);
            byte[] bytes = EntityUtils.toByteArray(entity);
            out.write(bytes);
            out.flush();
            out.close();
//			 Tools.printQr(path);

        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
        return null;
    }

    ;

}
