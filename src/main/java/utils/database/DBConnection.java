package utils.database;

import com.google.gson.JsonObject;
import constants.ZboxUrls;
import org.json.simple.JSONArray;

import java.sql.Connection;
import java.sql.Statement;

public class DBConnection {
    private Connection conn = null;
    private Statement stmt = null;
    private JSONArray jsonArray;
    private JsonObject database_objects;
    public DBConnection() {
        try {
            //conn = DBConfig.getConnection(ZboxUrls.ZBOX_DB_URL_QA, ZboxUrls.DB_USER_NAME_PASS, ZboxUrls.DB_USER_NAME_PASS);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
