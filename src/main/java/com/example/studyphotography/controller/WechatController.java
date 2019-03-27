package com.example.studyphotography.controller;

import com.example.studyphotography.model.ArticleItem;
import com.example.studyphotography.model.InMsgEntity;
import com.example.studyphotography.model.OutMsgEntity;
import com.example.studyphotography.util.SHA1;
import com.example.studyphotography.util.WeChatUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 17:35 2019/3/26
 * @ Description：
 * @ Modified By：
 * @Version: $
 */

@RestController
public class WechatController {


    /**
     * 微信URL接入校验
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping(value = "/weixin",method = RequestMethod.GET)
    public String check(String signature,String timestamp,String nonce, String echostr){

        //1）将token、timestamp、nonce三个参数进行字典序排序
        String[] arr= {WeChatUtil.token,timestamp,nonce};
        Arrays.sort(arr);
        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder sb=new StringBuilder();
        for (String temp:arr) {
            sb.append(temp);
        }
       String mysignature=  SHA1.encode(sb.toString());
        // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信

        if(mysignature.equals(signature)){
            //原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
            System.out.printf("接入成功");
            return echostr;
        }
        System.out.printf("接入失败");
        return null;
    }



    /**
     * 微信消息处理
     * 返回json格式，手机公众号总显示“改公众号提供的服务出现故障，请稍后再试”，在RequestMapping 加了produces= {MediaType.TEXT_XML_VALUE} 类型才可以
     */
    @RequestMapping(value = "/weixin", method = RequestMethod.POST,produces =  {MediaType.TEXT_XML_VALUE} )
    public Object handleMessage(@RequestBody InMsgEntity msg) {
        //创建消息响应对象
        OutMsgEntity out = new OutMsgEntity();
        //把原来的发送方设置为接收方
        out.setToUserName(msg.getFromUserName());
        //把原来的接收方设置为发送方
        out.setFromUserName(msg.getToUserName());
        //获取接收的消息类型
        String msgType = msg.getMsgType();
        //设置消息的响应类型
        out.setMsgType(msgType);
        //设置消息创建时间
        out.setCreateTime(System.currentTimeMillis());
        //公众号回复的内容
        String outContent = null;
        //根据类型设置不同的消息数据
        if ("text".equals(msgType)) {
            //用户发送的内容
            String inContent = msg.getContent();
            //关键字判断
            if (inContent.contains("儿子")) {
                outContent = "我的儿子叫阳仔";
            } else if (inContent.contains("爸爸")) {
                outContent = "阳仔的爸爸是我";
            } else {
                //用户发什么就回复什么
                outContent = inContent;
            }
            out.setContent(outContent);
        } else if ("image".equals(msgType)) {
            out.setMediaId(new String[]{msg.getMediaId()});
        } else if ("event".equals(msgType)) {
            //判断关注事件
            if ("subscribe".equals(msg.getEvent())) {
                //回复文本消息
               /* out.setContent("欢迎关注![愉快]");
                //设置消息的响应类型
                out.setMsgType("text");*/

                //回复图文消息
                out.setMsgType("news");
                //图文个数
                out.setArticleCount(1);
                //明细
                ArticleItem item = new ArticleItem();
                item.setTitle("标题");
                item.setPicUrl("https://www.jianshu.com/u/ba8c3ff5a775");
                item.setDescription("描述");
                item.setUrl("www.baidu.com");
                out.setItem(new ArticleItem[]{item});
            }else if ("CLICK".equals(msg.getEvent())) {
                //获取菜单的key值
                String key = msg.getEventKey();
                if ("classinfo".equals(key)) {
                    outContent = "上海Java基础班第05期于2018/05/10开班\n" +
                            "广州Java基础班第24期于2018/04/02开班";
                } else if ("address".equals(key)) {
                    outContent = "北京校区：北京昌平区沙河镇万家灯火装饰城2楼8077号\n" +
                            "广州校区：广州市天河区棠下涌东路大地工业区D栋六楼\n" +
                            "上海校区：上海市青浦区华新镇华隆路1777号E通世界商务园华新园A座4楼402";
                }
                //设置消息的响应类型
                out.setMsgType("text");
                out.setContent(outContent);
            }
        }
        return out;
    }


}
