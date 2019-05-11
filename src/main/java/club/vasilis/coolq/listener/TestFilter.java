package club.vasilis.coolq.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.local.EventLocalSendMessage;

/**
 * @author Vasilis
 * @date 2019/5/11 -13:19
 */

public class TestFilter extends IcqListener {
    @EventHandler
    public void onAllLocalMessageEvent(EventLocalSendMessage event) // 监听所有发送消息的事件
    {
        // 获取消息
        String message = event.getMessage();

        // 这里可以做任何处理
        // 我把所有"%prefix%"变量替换成了"!"
        message = message.replace("%prefix%", "!");

        // 设置消息
        event.setMessage(message);
    }
}
