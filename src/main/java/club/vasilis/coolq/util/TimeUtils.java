package club.vasilis.coolq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Vasilis
 * @date 2019/6/11 -23:35
 */

public class TimeUtils {
    public static String getDayAndWeek(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd，EEEE");
        return format.format(date);
    }

    public static void main(String[] args) {
        String dayAndWeek = TimeUtils.getDayAndWeek();
        System.out.println(dayAndWeek);
    }
}
