package com.example.l8411.wut2eat29.Model;

public class Invitation {
    private String send_uid;
    private String recv_uid;
    /**
     * status = 0 : pending
     * status = 1 : accept
     * status = 2 : decline
     */
    private int status;

    public Invitation() {
    }

    public Invitation(String send_uid, String recv_uid) {
        this.send_uid = send_uid;
        this.recv_uid = recv_uid;
        this.status = 0;
    }

    public String getSend_uid() {

        return send_uid;
    }

    public void setSend_uid(String send_uid) {
        this.send_uid = send_uid;
    }

    public String getRecv_uid() {
        return recv_uid;
    }

    public void setRecv_uid(String recv_uid) {
        this.recv_uid = recv_uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
