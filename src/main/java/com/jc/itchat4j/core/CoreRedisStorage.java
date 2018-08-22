package com.jc.itchat4j.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoreRedisStorage extends Core {
    private JedisCluster jedisCluster;
    private String kyeName = "weixinAccountCash";
    /**
     * 平台的唯一标识
     */
    private String appkey;

    public CoreRedisStorage(JedisCluster jedisCluster, String appkey) {
        this.jedisCluster = jedisCluster;
        kyeName = kyeName + appkey;
        this.appkey = appkey;
        jedisCluster.hset(kyeName, "appkey", appkey);
    }

    @Override
    public JSONObject getUserSelf() {
        if (jedisCluster.hget(kyeName, "userSelf") != null) {

            userSelf = JSON.parseObject(jedisCluster.hget(kyeName, "userSelf"));
        } else {
            userSelf = super.getUserSelf();
        }
        return userSelf;
    }

    @Override
    public void setUserSelf(JSONObject userSelf) {
        jedisCluster.hset(kyeName, "userSelf", userSelf.toString());
        this.userSelf = userSelf;
    }


    @Override
    public boolean isAlive() {
        if (jedisCluster.hget(kyeName, "alive") != null) {
            alive = Boolean.parseBoolean(jedisCluster.hget(kyeName, "alive"));
        } else {
            alive = super.isAlive();
        }

        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        jedisCluster.hset(kyeName, "alive", Boolean.toString(alive));

    }



    @Override
    public void setMemberList(List<JSONObject> memberList) {
        jedisCluster.hset(kyeName, "memberList",JSONArray.toJSONString(memberList) );
    }



    @Override
    public void setLoginInfo(Map<String, Object> loginInfo) {
        jedisCluster.hset(kyeName, "loginInfo", new JSONObject(loginInfo).toString());

    }

    @Override
    public String getUuid() {
        if (jedisCluster.hget(kyeName, "uuid") != null) {

            uuid = jedisCluster.hget(kyeName, "uuid");
        } else {
            uuid = super.getUuid();
        }
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        jedisCluster.hset(kyeName, "uuid", uuid);

    }

    @Override
    public int getMemberCount() {
        if (jedisCluster.hget(kyeName, "memberCount") != null) {
            memberCount = Integer.parseInt(jedisCluster.hget(kyeName, "memberCount"));
        } else {
            memberCount = super.getMemberCount();
        }

        return memberCount;
    }

    @Override
    public void setMemberCount(int memberCount) {
        jedisCluster.hset(kyeName, "memberCount", Integer.toString(memberCount));
    }

    @Override
    public boolean isUseHotReload() {
        if (jedisCluster.hget(kyeName, "useHotReload") != null) {
            useHotReload = Boolean.parseBoolean(jedisCluster.hget(kyeName, "useHotReload"));
        } else {
            useHotReload = super.isUseHotReload();
        }

        return useHotReload;
    }

    @Override
    public void setUseHotReload(boolean useHotReload) {

        jedisCluster.hset(kyeName, "useHotReload", Boolean.toString(useHotReload));

    }

    @Override
    public String getHotReloadDir() {
        if (jedisCluster.hget(kyeName, "hotReloadDir") != null) {
            hotReloadDir = jedisCluster.hget(kyeName, "hotReloadDir");
        } else {
            hotReloadDir = super.getHotReloadDir();
        }
        return hotReloadDir;
    }

    @Override
    public void setHotReloadDir(String hotReloadDir) {
        jedisCluster.hset(kyeName, "hotReloadDir", hotReloadDir);
    }



    @Override
    public void setReceivingRetryCount(int receivingRetryCount) {
        jedisCluster.hset(kyeName, "receivingRetryCount", Integer.toString(receivingRetryCount));
    }



    @Override
    public void setGroupIdList(List<String> groupIdList) {
        jedisCluster.hset(kyeName, "groupIdList", JSONArray.toJSONString(groupIdList));

    }


    @Override
    public void setContactList(List<JSONObject> contactList) {
        jedisCluster.hset(kyeName, "contactList",JSONArray.toJSONString(contactList) );

    }



    @Override
    public void setGroupList(List<JSONObject> groupList) {
        jedisCluster.hset(kyeName, "groupList",JSONArray.toJSONString(groupList) );
    }


    @Override
    public void setPublicUsersList(List<JSONObject> publicUsersList) {

        jedisCluster.hset(kyeName, "publicUsersList", JSONArray.toJSONString(publicUsersList));
    }


    @Override
    public void setSpecialUsersList(List<JSONObject> specialUsersList) {
        jedisCluster.hset(kyeName, "specialUsersList",JSONArray.toJSONString(specialUsersList));
    }

    @Override
    public String getUserName() {
        if (jedisCluster.hget(kyeName, "userName") != null) {

            userName = jedisCluster.hget(kyeName, "userName");
        } else {
            userName = super.getUserName();
        }
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        jedisCluster.hset(kyeName, "userName", userName);
    }

    @Override
    public String getNickName() {
        if (jedisCluster.hget(kyeName, "nickName") != null) {
            nickName = jedisCluster.hget(kyeName, "nickName");
        } else {
            nickName = super.getNickName();
        }

        return nickName;
    }

    @Override
    public void setNickName(String nickName) {
        jedisCluster.hset(kyeName, "nickName", nickName);
    }



    @Override
    public void setUserInfoMap(Map<String, JSONObject> userInfoMap) {
        Map item = new HashMap();
        item.putAll(userInfoMap);
        jedisCluster.hset(kyeName, "userInfoMap", new JSONObject(item).toString());

    }



    @Override
    public void setGroupNickNameList(List<String> groupNickNameList) {
        jedisCluster.hset(kyeName, "groupNickNameList",JSONArray.toJSONString(groupNickNameList));

    }



    @Override
    public void setGroupMemeberMap(Map<String, JSONArray> groupMemeberMap) {
        jedisCluster.hset(kyeName, "groupMemeberMap", JSONObject.toJSONString(groupMemeberMap));
    }

    @Override
    public String getIndexUrl() {
        if (jedisCluster.hget(kyeName, "indexUrl") != null) {
            indexUrl = jedisCluster.hget(kyeName, "indexUrl");
        } else {
            indexUrl = super.getIndexUrl();
        }

        return indexUrl;
    }

    @Override
    public void setIndexUrl(String indexUrl) {
        jedisCluster.hset(kyeName, "indexUrl", indexUrl);
    }



    @Override
    public void setGroupNickNameIdList(List<Map<String, Object>> groupNickNameIdList) {
        jedisCluster.hset(kyeName, "groupNickNameIdList", JSONArray.toJSONString(groupNickNameIdList));
    }

    @Override
    public List<JSONObject> getTimeMsgQueue() {
        if(jedisCluster.get("weixinAccountMsgQueue"+appkey)!=null){
            List<JSONObject> msgQue=JSONArray.parseArray( jedisCluster.get("weixinAccountMsgQueue"+appkey),JSONObject.class) ;
            return msgQue;
        }

        return super.getTimeMsgQueue();
    }
    @Override
    public void rmTimeMsgQueue(JSONObject data){
        List<JSONObject> old=new ArrayList<>(getTimeMsgQueue());
        old.remove(data);

        jedisCluster.set("weixinAccountMsgQueue"+appkey,old.toString());

    }

    @Override
    public String getQr() {
        if (jedisCluster.hget(kyeName, "QR") != null) {
            qr = jedisCluster.hget(kyeName, "QR");
        } else {
            qr = super.getQr();
        }

        return qr;
    }

    @Override
    public void setQr(String qr) {
        jedisCluster.hset(kyeName, "QR", qr);

    }
}
