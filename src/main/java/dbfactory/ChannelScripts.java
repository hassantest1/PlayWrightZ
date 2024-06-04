package dbfactory;
import utils.common.CommonFun;
import utils.database.DBQueryExecutor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChannelScripts {
    public static int verifyNewlyCreatedChannelInDB(String channelName) throws SQLException {
        DBQueryExecutor dbQueryExecutor = new DBQueryExecutor();
        Connection conn = dbQueryExecutor.connectBD();
        Statement stmt = conn.createStatement();
        String sql = "SELECT CHANNEL_NAME,IS_ACTIVE FROM LKP_CHANNEL WHERE CHANNEL_NAME = "+"'"+channelName+"'"+" AND IS_ACTIVE = 'N'";
        ResultSet results_sets = stmt.executeQuery(sql);
        System.out.println(results_sets.toString());
        if (!results_sets.next()){
            return  0;
        }
        dbQueryExecutor.closeConnection();
        return 1;
    }
}
