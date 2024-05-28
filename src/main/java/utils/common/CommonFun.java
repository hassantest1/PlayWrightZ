package utils.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFun {

    public static String getCurrentDate(String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getRandomName(String name){
        Random rand = new Random();
        Date date = new Date();
        return name+rand.nextInt(99999999)+date.getTime();
    }

    public static String getCheckerId(String paragraph){
        // Regex patterns to extract the Transaction ID and name
        String transactionIdPattern = "Transaction ID (\\d+)";
        String namePattern = "submitted to (\\w+) for approval";

        // Compile the patterns
        Pattern idPattern = Pattern.compile(transactionIdPattern);
        Pattern namePatternCompiled = Pattern.compile(namePattern);

        // Match the patterns
        Matcher idMatcher = idPattern.matcher(paragraph);
        Matcher nameMatcher = namePatternCompiled.matcher(paragraph);

        // Extract the Transaction ID and name
        if (idMatcher.find() && nameMatcher.find()) {
            String transactionId = idMatcher.group(1);
            String name = nameMatcher.group(1);
            System.out.println("Transaction ID: " + transactionId + name);
            return transactionId+","+name;
        }

        // Extract the Name
        if (nameMatcher.find()) {
            String name = nameMatcher.group(1);
            System.out.println("Name: " + name);
        }

        return "Noting found";
    }

}
