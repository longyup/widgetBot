package club.vasilis.coolq.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.local.EventLocalException;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;

/**
 * @author Vasilis
 * @date 2019/5/10 -19:25
 */

public class ExceptionListener extends IcqListener
{
    @EventHandler
    public void onException(EventLocalException event)
    {
        if (event.getParentEvent() instanceof EventMessage)
        {
            ((EventMessage) event.getParentEvent()).respond(new MessageBuilder()
                    .add("指令执行失败: ").add(event.getException()).newLine()
                    .add("已记录报错, 不过也可以重试一下看看w")
                    .toString());
            System.err.println("消息事件异常: " + event.getParentEvent().toString());
            event.getException().printStackTrace();
        }
    }
}
