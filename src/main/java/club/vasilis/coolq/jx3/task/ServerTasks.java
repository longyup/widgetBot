package club.vasilis.coolq.jx3.task;

import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.message.MessageBuilder;
import club.vasilis.coolq.Main;
import club.vasilis.coolq.jx3.config.ServerList;
import javafx.util.Pair;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Vasilis
 * @date 2019/7/29 -0:19
 */

public class ServerTasks {

        private final static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

    public static void main(String[] args) {
        taskTimrFactory(null);

    }

    /**
     * 把map拆为pair
     *
     * @param pair
     */
    public static void taskTimrFactory(Pair<Long, String> pair) {

            taskTimr("10:30:00", 1, pair);
            taskTimr("10:30:00", 4, pair);

    }

    /**
     * 早上9点30执行
     * 大致是计算当前时间
     * 启动在定的时之前（√）
     * 启动在定的时之后（）直接到下周执行了
     */
    public static void taskTimr(String time, int day, Pair<Long, String> pair) {
        EchoServer runnable = new EchoServer();

        runnable.setPair(pair);
        long oneDay = 24 * 60 * 60 * 1000;
        long initDelay = getTimeMillis(time) - System.currentTimeMillis();


        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int periodDay = day + 8 - dayOfWeek;

        initDelay = initDelay > 0 ? initDelay : oneDay * periodDay + initDelay;
        scheduledExecutorService.scheduleAtFixedRate(
                runnable,
                initDelay,
                oneDay * 7,
                TimeUnit.MILLISECONDS);
    }

    /**
     * 获取指定时间对应的毫秒数
     *
     * @param time "HH:mm:ss"
     * @return
     */
    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    static class EchoServer implements Runnable {
        private Pair<Long, String> pair;


        @Override
        public void run() {

            Long groupid = pair.getKey();
            String host = ServerList.serverlist.get(pair.getValue());
            int port = 3724;

            while (true) {
                Socket s = new Socket();
                SocketAddress add = new InetSocketAddress(host, port);
                try {
                    s.connect(add, 3000);// 超时3秒
                    if (s.isConnected()) {
                       // System.out.println("开服");
                        IcqHttpApi icqHttpApi = Main.bot.getAccountManager().getNonAccountSpecifiedApi();
                        MessageBuilder builder = new MessageBuilder().add("区服")
                                .add(pair.getValue())
                                .add("已开服，大家可以上线玩耍了");
                        icqHttpApi.sendGroupMsg(groupid, builder.toString());
                        break;
                    }
                } catch (IOException e) {
                    //连接超时需要处理的业务逻辑
                    //System.out.println("未开服");
                } finally {
                    try {
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void setPair(Pair<Long, String> pair) {
            this.pair = pair;
        }
    }
}
