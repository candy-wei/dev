package com.ningyuan.route.dto;

public class ShopInviterDto {
    private Long id;
    private String inviterNickname;
    private String inviterMobile;
    private String inviteeNickname;
    private String inviteTime;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getInviterNickname() { return inviterNickname; }

    public void setInviterNickname(String inviterNickname) { this.inviterNickname = inviterNickname; }

    public String getInviterMobile() { return inviterMobile; }

    public void setInviterMobile(String inviterMobile) { this.inviterMobile = inviterMobile; }

    public String getInviteeNickname() { return inviteeNickname; }

    public void setInviteeNickname(String inviteeNickname) { this.inviteeNickname = inviteeNickname; }

    public String getInviteTime() { return inviteTime; }

    public void setInviteTime(String inviteTime) { this.inviteTime = inviteTime; }
}
