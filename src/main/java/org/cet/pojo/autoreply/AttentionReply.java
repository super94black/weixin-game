package org.cet.pojo.autoreply;

/**
 * @Author zhang
 * @Date 2017/8/25 19:28
 * @Content
 */
public class AttentionReply{
    private int is_add_friend_reply_open;
    private int is_autoreply_open;
    private BaseAutoReply add_friend_autoreply_info;

    public int getIs_add_friend_reply_open() {
        return is_add_friend_reply_open;
    }

    public void setIs_add_friend_reply_open(int is_add_friend_reply_open) {
        this.is_add_friend_reply_open = is_add_friend_reply_open;
    }

    public int getIs_autoreply_open() {
        return is_autoreply_open;
    }

    public void setIs_autoreply_open(int is_autoreply_open) {
        this.is_autoreply_open = is_autoreply_open;
    }

    public BaseAutoReply getAdd_friend_autoreply_info() {
        return add_friend_autoreply_info;
    }

    public void setAdd_friend_autoreply_info(BaseAutoReply add_friend_autoreply_info) {
        this.add_friend_autoreply_info = add_friend_autoreply_info;
    }
}
