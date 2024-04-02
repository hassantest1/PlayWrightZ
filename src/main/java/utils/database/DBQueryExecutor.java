package utils.database;

import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import constants.ZboxUrls;
import org.json.simple.JSONArray;

public class DBQueryExecutor {

    private final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private Connection conn = null;
    private Statement stmt = null;
    private JSONArray jsonArray;
    private JsonObject database_objects;

//    public DBQueryExecutor() {
//        try {
//            //ConfigManager get_keys = new ConfigManager();
//            Class.forName(JDBC_DRIVER);
//            conn = DriverManager.getConnection(ZboxUrls.ZBOX_DB_URL_QA,
//                    ZboxUrls.DB_USER_NAME_PASS,ZboxUrls.DB_USER_NAME_PASS);
//        } catch (Exception e) {
//            // TODO: handle exception
//            System.out.println(e.toString());
//        }
//    }

    public Connection connectBD(){
        try {
            //ConfigManager get_keys = new ConfigManager();
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(ZboxUrls.ZBOX_DB_URL_QA,
                    ZboxUrls.DB_USER_NAME_PASS,ZboxUrls.DB_USER_NAME_PASS);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }
        return conn;
    }

    public void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }


    public JSONArray dataBase() {
        jsonArray = new JSONArray();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM LKP_ACCOUNT_STATUS";
            ResultSet results_sets = stmt.executeQuery(sql);
            while (results_sets.next()) {
                database_objects = new JsonObject();
                database_objects.addProperty("ACCOUNT_STATUS_ID", results_sets.getString("ACCOUNT_STATUS_ID"));
                database_objects.addProperty("ACCOUNT_STATUS_CODE", results_sets.getString("ACCOUNT_STATUS_CODE"));
                database_objects.addProperty("ACCOUNT_STATUS_NAME", results_sets.getString("ACCOUNT_STATUS_NAME"));
                database_objects.addProperty("IS_ACTIVE", results_sets.getString("IS_ACTIVE"));
                database_objects.addProperty("CREATEUSER", results_sets.getString("CREATEUSER"));
                database_objects.addProperty("LASTUPDATEUSER", results_sets.getString("LASTUPDATEUSER"));
                database_objects.addProperty("LASTUPDATEDATE", results_sets.getString("LASTUPDATEDATE"));
                database_objects.addProperty("UPDATEINDEX", results_sets.getString("UPDATEINDEX"));
                jsonArray.add(database_objects);
            }

            System.out.println(jsonArray.toString());
            results_sets.close();
            stmt.close();
            conn.close();
            return jsonArray;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
            return null;
        }
    }

    public JSONArray getDBTables(String table_name) {
        jsonArray = new JSONArray();
        String sql = "SELECT * FROM "+table_name;
        try {
            stmt = conn.createStatement();
            ResultSet results_sets = stmt.executeQuery(sql);
            java.sql.ResultSetMetaData meta_data = results_sets.getMetaData();
            int column_count = meta_data.getColumnCount();
            while (results_sets.next()) {
                database_objects = new JsonObject();
                for (int i = 1; i<=column_count; i++) {
                    String column_name = meta_data.getColumnName(i).toString().toLowerCase();
                    String column_value = results_sets.getString(i);
                    database_objects.addProperty(column_name, column_value);
                }
                jsonArray.add(database_objects);
            }
            results_sets.close();
            stmt.close();
            conn.close();
            System.out.println(jsonArray.toString());
            return jsonArray;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.toString());
            return null;
        }
    }

    public JSONArray getDataByQuery(String query) {
        jsonArray = new JSONArray();
        try {
            stmt = conn.createStatement();
            ResultSet results_sets = stmt.executeQuery(query);
            java.sql.ResultSetMetaData meta_data = results_sets.getMetaData();
            int column_count = meta_data.getColumnCount();
            while (results_sets.next()) {
                database_objects = new JsonObject();
                for (int i = 1; i<=column_count; i++) {
                    String column_name = meta_data.getColumnName(i).toString().toLowerCase();
                    String column_value = results_sets.getString(i);
                    database_objects.addProperty(column_name, column_value);
                }
                jsonArray.add(database_objects);
            }
            System.out.println(jsonArray.toString());
            results_sets.close();
            stmt.close();
            conn.close();
            return jsonArray;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.toString());
            return null;
        }
    }
}
