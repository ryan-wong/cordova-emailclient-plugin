package com.sixtooth.plugin;

/**
 * Created by sixtooth on 15-06-30.
 */
public class Email  {
    private boolean isRead;
    private boolean hasContainAttachment;
    private boolean replySign;
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String sendDate;
    private String messageId;
    private String title;
    private String content;

    public Email(){

    }

    /**
     *
     * @param isRead
     * @param hasContainAttachment
     * @param replySign
     * @param from
     * @param to
     * @param cc
     * @param bcc
     * @param sendDate
     * @param messageId
     * @param title
     * @param content
     */
    public Email(boolean isRead, boolean hasContainAttachment, boolean replySign, String from, String to, String cc, String bcc, String sendDate, String messageId, String title, String content) {
        this.isRead = isRead;
        this.hasContainAttachment = hasContainAttachment;
        this.replySign = replySign;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.sendDate = sendDate;
        this.messageId = messageId;
        this.title = title;
        this.content = content;
    }

    /**
     *
     * @return
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     *
     * @param isRead
     */
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    /**
     *
     * @return
     */
    public boolean isHasContainAttachment() {
        return hasContainAttachment;
    }

    /**
     *
     * @param hasContainAttachment
     */
    public void setHasContainAttachment(boolean hasContainAttachment) {
        this.hasContainAttachment = hasContainAttachment;
    }

    /**
     *
     * @return
     */
    public boolean isReplySign() {
        return replySign;
    }

    /**
     *
     * @param replySign
     */
    public void setReplySign(boolean replySign) {
        this.replySign = replySign;
    }

    /**
     *
     * @return
     */
    public String getFrom() {
        return from;
    }

    /**
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     *
     * @return
     */
    public String getTo() {
        return to;
    }

    /**
     *
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     *
     * @return
     */
    public String getCc() {
        return cc;
    }

    /**
     *
     * @param cc
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     *
     * @return
     */
    public String getBcc() {
        return bcc;
    }

    /**
     *
     * @param bcc
     */
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    /**
     *
     * @return
     */
    public String getSendDate() {
        return sendDate;
    }

    /**
     *
     * @param sendDate
     */
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    /**
     *
     * @return
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     *
     * @param messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}