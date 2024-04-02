package utils.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonFun {

    public static String getCurrentDate(String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

}
