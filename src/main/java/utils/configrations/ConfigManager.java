package utils.configrations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

 private static ConfigManager configManager = null;
 public static Properties prop = new Properties();

 //Private constractor to only get the single object of that class

 public ConfigManager() throws IOException {
     String filename = System.getProperty("user.dir") + File.separator +
             "src" + File.separator + "main" + File.separator + "resources" + File.separator + "config.properties";
     System.out.println("filename  :" + filename);
     prop.load(new FileInputStream(filename));
 }

 // Return the object of that call

 public static ConfigManager getConfigManagerInstance(){
     try{
         if(configManager == null) {
         synchronized (ConfigManager.class) {
             configManager = new ConfigManager();
         }
     }
     }catch (IOException e){
         System.out.println("There is a null pointer exception");
         e.printStackTrace();
     }
     return configManager;
 }

 // Whatever values we would have in properties file, we can return the value from here
 public String getKeyValue(String key)
 {
     System.out.println("Test Returing value from key [" + key+ "] :" + System.getProperty(key, prop.getProperty(key)));
     return System.getProperty(key, prop.getProperty(key));
 }

}
