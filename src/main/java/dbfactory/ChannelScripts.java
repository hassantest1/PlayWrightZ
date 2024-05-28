package dbfactory;
import utils.common.CommonFun;
import utils.database.DBQueryExecutor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChannelScripts {
    public static int CheckNewlyCreatedChannel(String channelName) throws SQLException {
        DBQueryExecutor dbQueryExecutor = new DBQueryExecutor();
        Connection conn = dbQueryExecutor.connectBD();
        Statement stmt = conn.createStatement();
        String sql = "select user_id, login_date from tbl_user_login_history where user_id = "+""+" and login_date >= "+"'"+ CommonFun.getCurrentDate("dd-MMM-yy")+"'";
        ResultSet results_sets = stmt.executeQuery(sql);
        System.out.println(results_sets.toString());
        if (!results_sets.next()){
            return  0;
        }
        dbQueryExecutor.closeConnection();
        return 1;
    }
}
