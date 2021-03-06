package club.vasilis.coolq.jx3.dao;

import club.vasilis.coolq.util.DsUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author Vasilis
 * @date 2019/6/12 -9:49
 */

public class WeekCoolDao {
    /**
     * 查询下一次应该的冷却时间
     * @param groupId
     * @return
     * @throws SQLException
     */
    public Map<String, Object> getNextEvent(long groupId) throws SQLException {
        QueryRunner runner = new QueryRunner(DsUtils.getDataSource());
        String sql = "select nextEvent from weekcool where groupId = ?";
        Map<String, Object> query = runner.query(sql, new MapHandler(), groupId);
        return query;
    }

    /**
     * 如果没有就创建
     *
     * @param groupId   群号
     * @param nextEvent 下一次可以查询的时间
     * @param lastEvent 当前时间
     */
    public void create(long groupId, long lastEvent, long nextEvent) throws SQLException {
        QueryRunner runner = new QueryRunner(DsUtils.getDataSource());
        String sql = "INSERT INTO weekcool (groupId,lastEvent, nextEvent) VALUES (?,?,?)";
        runner.update(sql, groupId, lastEvent, nextEvent);

    }

    public void update(long groupId, long lastEvent, long nextEvent) throws SQLException {
        QueryRunner runner = new QueryRunner(DsUtils.getDataSource());
        String sql = "UPDATE weekcool SET lastEvent= ?, nextEvent=?  WHERE groupId = ?";
        runner.update(sql, lastEvent, nextEvent,groupId);
    }

    public static void main(String[] args) throws SQLException {
        new DailyCoolDao().update(0000000L, 1560263990000L, 1560264000000L);
        /*System.out.println(nextEvent);*/
    }
}
