package com.example.studyphotography.util;

import com.alibaba.fastjson.JSONObject;


/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 23:21 2019/3/26
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
public class WeChatUtil {
    public static final String token="studyphotography";

    public static final String APPID="wxa8f6425c1b1b30ff";

    public static final String AppSecret="e80ca90d27ea48b34195e8acc83a2208";

    //创建菜单接口
    public static final String CREATEMENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    //删除菜单接口
    public static final String DELETEMENU_URL= "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    //获取accesstoken接口
    public static final String GET_ACCESStOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=AppSecret";

    //发送模板消息的接口
    public static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //调用各个接口的凭证
    public static  String accessToken;

    //凭证的失效时间
    public static long expiresTime;
    /**
     * 获取全局凭据
     * @return
     */
    public static String getAccessToken(){
        //当access_token为空或者失效才会重新获取
        if(accessToken==null || System.currentTimeMillis()>expiresTime) {
            String result = HttpUtil.getHttp(GET_ACCESStOKEN_URL.replace("APPID", APPID).replace("AppSecret", AppSecret));
            JSONObject json = JSONObject.parseObject(result);
            //凭据
            accessToken = json.getString("access_token");
            //有效期
            Long expires_in = json.getLong("expires_in");

            //设置凭据的失效时间=当前时间+有效期
            expiresTime = System.currentTimeMillis() + (expires_in - 60 * 1000);
        }
        System.out.println(accessToken);
        return  accessToken;
    }
    /**
     * 创建菜单
     */
    public static void createMenu(String menuJson){
    String result= HttpUtil.sendPost(CREATEMENU_URL.replace("ACCESS_TOKEN",getAccessToken()),menuJson);
        System.out.println(result);
    }


    /**
     * 删除菜单
     */
    public static void deleteMenu(){
        String result= HttpUtil.getHttp(DELETEMENU_URL.replace("ACCESS_TOKEN",getAccessToken()));
        System.out.println(result);
    }

    /**
     * 发送模板
     *
     */
    public static void sendTemplate(String menuJson){
        String result = HttpUtil.sendPost(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN", getAccessToken()),menuJson);
        System.out.println(result);
    }

    public static void main(String[] args) {
        //deleteMenu();
       /* createMenu("{\n" +
                "     \"button\":[\n" +
                "     {    \n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"开班信息\",\n" +
                "          \"key\":\"classinfo\"\n" +
                "      },\n" +
                "     {    \n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"校区地址\",\n" +
                "          \"key\":\"address\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"学科介绍\",\n" +
                "           \"sub_button\":[\n" +
                "           {    \n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"Java课程\",\n" +
                "               \"url\":\"http://www.wolfcode.cn/zt/java/index.html\"\n" +
                "            },\n" +
                "           {    \n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"Python课程\",\n" +
                "               \"url\":\"http://www.wolfcode.cn/zt/python/index.html\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }");*/

        sendTemplate(" {\n" +
                "           \"touser\":\"oDn8j1eIzss2-i5PEv71Og43QVRo\",\n" +
                "           \"template_id\":\"3a0tgOBvwP-xpSpNbGUsdbwCQR64UNg5sUT0gwHLdns\",\n" +
                "           \"url\":\"http://ep9h85.natappfree.cc/twfc/weixin_front/teacher/index.jsp\",  \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功啦啦啦啦啦啦！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"second\":{\n" +
                "                       \"value\":\"巧克力\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"fourth\": {\n" +
                "                       \"value\":\"39.8元\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"third\": {\n" +
                "                       \"value\":\"2014年9月22日\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }");


    }
}
