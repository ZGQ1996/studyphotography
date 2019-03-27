package com.example.studyphotography.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 9:00 2019/3/27
 * @ Description：发送消息
 * @ Modified By：
 * @Version: $
 */

@Data
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class InMsgEntity {

    // 开发者微信号
    protected String FromUserName;
    // 发送方帐号（一个OpenID）
    protected String ToUserName;
    // 消息创建时间
    protected Long CreateTime;
    /**
     * 消息类型
     * text 文本消息
     * image 图片消息
     * voice 语音消息
     * video 视频消息
     * music 音乐消息
     */
    protected String MsgType;
    // 消息id
    protected Long MsgId;
    // 文本内容
    private String Content;
    // 图片链接（由系统生成）
    private String PicUrl;
    // 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
    private String MediaId;

    /**
     * 事件类型
     * subscribe(订阅)
     * unsubscribe(取消订阅)
     * LOCATION(上报地理位置)
     * CLICK(点击普通的菜单)
     * VIEW(点击跳转链接的菜单)
     */
    private String Event;
    private String EventKey;//菜单的key值

}
