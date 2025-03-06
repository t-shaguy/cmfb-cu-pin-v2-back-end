/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.vanso.dto;

/**
 *
 * @author root
 */
public class SMSObject {
    
    private long tid;
    private String server;
    private int port;
    private String username;
    private String password;
    private String message;
    private String dlr;
    private String type;
    private String destination;
    private String source;
    private String sendReply;
    private String smsServiceUrl;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDlr() {
        return dlr;
    }

    public void setDlr(String dlr) {
        this.dlr = dlr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getSendReply() {
        return sendReply;
    }

    public void setSendReply(String sendReply) {
        this.sendReply = sendReply;
    }

    public String getSmsServiceUrl() {
        return smsServiceUrl;
    }

    public void setSmsServiceUrl(String smsServiceUrl) {
        this.smsServiceUrl = smsServiceUrl;
    }

    @Override
    public String toString() {
        return "SMSObject{" + "tid=" + tid + ", server=" + server + ", port=" + port + ", username=" + username + ", password=" + password + ", message=" + message + ", dlr=" + dlr + ", type=" + type + ", destination=" + destination + ", source=" + source + ", sendReply=" + sendReply + ", smsServiceUrl=" + smsServiceUrl + '}';
    }
 
}
