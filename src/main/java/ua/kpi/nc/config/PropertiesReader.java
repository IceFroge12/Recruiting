package ua.kpi.nc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by dima on 26.04.16.
 */
public class PropertiesReader {

    private static Logger log = LoggerFactory.getLogger(PropertiesReader.class.getName());

    private static class SingletonHolder {
        static final PropertiesReader HOLDER_INSTANCE = new PropertiesReader();
    }

    public static PropertiesReader getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    private static String prop;

    public String propertiesReader(String property) {

        Properties p = new Properties();

        try(FileInputStream fileInputStream = new FileInputStream("C:\\Users\\IO\\Recruiting\\src\\main\\resources\\app.properties")) {
            p.load(fileInputStream);
            prop = p.getProperty(property);
        } catch (IOException e) {
            log.error("File not found", e);
        }
        return prop;
    }

}
