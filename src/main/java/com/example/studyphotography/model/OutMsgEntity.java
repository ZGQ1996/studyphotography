package com.example.studyphotography.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 9:00 2019/3/27
 * @ Description：回复消息
 * @ Modified By：
 * @Version: $
 */

@Data
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutMsgEntity {
    // 发送方的账号
    protected String FromUserName;
    // 接收方的账号(OpenID)
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
     * news 图文消息
     */
    protected String MsgType;
    // 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
    @XmlElementWrapper(name="Image")
    private String[] MediaId ;
    // 文本内容
    private String Content;


    //图文消息个数；当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
    private Integer ArticleCount;

    //图文消息
    @XmlElementWrapper(name="Articles")
    private ArticleItem[] item;
}
