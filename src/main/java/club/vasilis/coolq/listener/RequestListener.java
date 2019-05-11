package club.vasilis.coolq.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.request.EventGroupAddRequest;
import cc.moecraft.icq.event.events.request.EventGroupInviteRequest;

/**
 * @author Vasilis
 * @date 2019/5/10 -19:21
 */

public class RequestListener extends IcqListener {

    @EventHandler
    public void onGroupInvite(EventGroupInviteRequest event)
    {
        // 接受所有群邀请
        event.accept();
    }
    @EventHandler
    public void onGroupAdd(EventGroupAddRequest event)
    {
        event.accept(); // 接受所有

    }
}
