package com.ningyuan.route.dto;

public class ShopInviterDto {
    private Long id;
    private String inviterNickname;
    private String inviterMobile;
    private String inviterName;
    private String inviteeName;
    private String inviteeMobile;
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

    public String getInviterName() { return inviterName; }

    public void setInviterName(String inviterName) { this.inviterName = inviterName; }

    public String getInviteeName() { return inviteeName; }

    public void setInviteeName(String inviteeName) { this.inviteeName = inviteeName; }

    public String getInviteeMobile() { return inviteeMobile; }

    public void setInviteeMobile(String inviteeMobile) { this.inviteeMobile = inviteeMobile; }
}
