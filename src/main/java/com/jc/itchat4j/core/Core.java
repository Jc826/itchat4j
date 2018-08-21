package com.jc.itchat4j.core;

import com.jc.itchat4j.beans.BaseMsg;
import com.jc.itchat4j.utils.MyHttpClient;
import com.jc.itchat4j.utils.enums.parameters.BaseParaEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 核心存储类，全局只保存一份，单例模式
 *
 * @author https://github.com/yaphone
 * @version 1.0
 * @date 创建时间：2017年4月23日 下午2:33:56
 */
public class Core {


    public Core() {

    }


    boolean alive = false;
    protected int memberCount = 0;

    protected String indexUrl;
    protected String qr;

    protected String userName;
    protected String nickName;
    protected List<BaseMsg> msgList = new ArrayList<BaseMsg>();
    protected Map<String, Object> loginInfo = new HashMap<String, Object>();
    protected JSONObject userSelf; // 登陆账号自身信息
    protected List<JSONObject> memberList = new ArrayList<JSONObject>(); // 好友+群聊+公众号+特殊账号
    protected List<JSONObject> contactList = new ArrayList<JSONObject>();// 好友
    protected List<JSONObject> groupList = new ArrayList<JSONObject>();
    ; // 群
    protected Map<String, JSONArray> groupMemeberMap = new HashMap<String, JSONArray>(); // 群聊成员字典
    protected List<JSONObject> publicUsersList = new ArrayList<JSONObject>();
    ;// 公众号／服务号
    protected List<JSONObject> specialUsersList = new ArrayList<JSONObject>();
    ;// 特殊账号
    protected List<String> groupIdList = new ArrayList<String>(); // 群ID列表
    protected List<String> groupNickNameList = new ArrayList<String>(); // 群NickName列表
    protected List<Map<String, Object>> groupNickNameIdList = new ArrayList<>();


    protected Map<String, JSONObject> userInfoMap = new HashMap<String, JSONObject>();


    // CloseableHttpClient httpClient = HttpClients.createDefault();
    protected MyHttpClient myHttpClient = new MyHttpClient();
    protected String uuid = null;

    protected boolean useHotReload = false;
    protected String hotReloadDir = "itchat.pkl";
    protected int receivingRetryCount = 5;

    protected long lastNormalRetcodeTime; // 最后一次收到正常retcode的时间，秒为单位

    /**
     * 请求参数
     */
    public Map<String, Object> getParamMap() {
        return new HashMap<String, Object>(1) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                Map<String, String> map = new HashMap<String, String>();
                for (BaseParaEnum baseRequest : BaseParaEnum.values()) {
                    map.put(baseRequest.para(), getLoginInfo().get(baseRequest.value()).toString());
                }
                put("BaseRequest", map);
            }
        };
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public List<JSONObject> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<JSONObject> memberList) {
        this.memberList = memberList;
    }

    public Map<String, Object> getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(Map<String, Object> loginInfo) {
        this.loginInfo = loginInfo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public boolean isUseHotReload() {
        return useHotReload;
    }

    public void setUseHotReload(boolean useHotReload) {
        this.useHotReload = useHotReload;
    }

    public String getHotReloadDir() {
        return hotReloadDir;
    }

    public void setHotReloadDir(String hotReloadDir) {
        this.hotReloadDir = hotReloadDir;
    }

    public int getReceivingRetryCount() {
        return receivingRetryCount;
    }

    public void setReceivingRetryCount(int receivingRetryCount) {
        this.receivingRetryCount = receivingRetryCount;
    }

    public MyHttpClient getMyHttpClient() {
        return myHttpClient;
    }

    public List<BaseMsg> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<BaseMsg> msgList) {
        this.msgList = msgList;
    }

    public void setMyHttpClient(MyHttpClient myHttpClient) {
        this.myHttpClient = myHttpClient;
    }

    public List<String> getGroupIdList() {
        return groupIdList;
    }

    public void setGroupIdList(List<String> groupIdList) {
        this.groupIdList = groupIdList;
    }

    public List<JSONObject> getContactList() {
        return contactList;
    }

    public void setContactList(List<JSONObject> contactList) {
        this.contactList = contactList;
    }

    public List<JSONObject> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<JSONObject> groupList) {
        this.groupList = groupList;
    }

    public List<JSONObject> getPublicUsersList() {
        return publicUsersList;
    }

    public void setPublicUsersList(List<JSONObject> publicUsersList) {
        this.publicUsersList = publicUsersList;
    }

    public List<JSONObject> getSpecialUsersList() {
        return specialUsersList;
    }

    public void setSpecialUsersList(List<JSONObject> specialUsersList) {
        this.specialUsersList = specialUsersList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public JSONObject getUserSelf() {
        return userSelf;
    }

    public void setUserSelf(JSONObject userSelf) {
        this.userSelf = userSelf;
    }

    public Map<String, JSONObject> getUserInfoMap() {
        return userInfoMap;
    }

    public void setUserInfoMap(Map<String, JSONObject> userInfoMap) {
        this.userInfoMap = userInfoMap;
    }

    public synchronized long getLastNormalRetcodeTime() {
        return lastNormalRetcodeTime;
    }

    public synchronized void setLastNormalRetcodeTime(long lastNormalRetcodeTime) {
        this.lastNormalRetcodeTime = lastNormalRetcodeTime;
    }

    public List<String> getGroupNickNameList() {
        return groupNickNameList;
    }

    public void setGroupNickNameList(List<String> groupNickNameList) {
        this.groupNickNameList = groupNickNameList;
    }

    public Map<String, JSONArray> getGroupMemeberMap() {
        return groupMemeberMap;
    }

    public void setGroupMemeberMap(Map<String, JSONArray> groupMemeberMap) {
        this.groupMemeberMap = groupMemeberMap;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public List<Map<String, Object>> getGroupNickNameIdList() {
        return groupNickNameIdList;
    }

    public void setGroupNickNameIdList(List<Map<String, Object>> groupNickNameIdList) {
        this.groupNickNameIdList = groupNickNameIdList;
    }
    public List<JSONObject> getTimeMsgQueue(){
        return new ArrayList<>();
    }
    public void rmTimeMsgQueue(JSONObject data){
         getTimeMsgQueue().remove(data);
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }
}
