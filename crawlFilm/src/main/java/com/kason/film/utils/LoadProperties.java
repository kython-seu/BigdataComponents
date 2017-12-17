package com.kason.film.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.exit;
import static java.lang.System.in;

public class LoadProperties {

    private static final Logger logger = LoggerFactory.getLogger(LoadProperties.class);

    private static InputStream inputStream;
    private static final String downloadFileName = "download.properties";

    private static Properties prop = new Properties();
    static {
        try {
            File file = new File(downloadFileName);
            String s = file.getCanonicalPath() + "-" + file.getPath();
            logger.info("s : " + s);
            String path = file.getParentFile().getAbsolutePath();
            logger.info("path: " + path);
            File fileDir = new File(path);
            if(fileDir.exists()) {
                File[] files = fileDir.listFiles();
                for(File f : files){
                    inputStream = LoadProperties.class.getClassLoader().getResourceAsStream(f.getName());
                    if(inputStream == null){
                        inputStream = new FileInputStream(f.getName());
                    }

                    prop.load(inputStream);

                }
            }


        }catch (Exception e){
            logger.error("parse download properties failed");
            exit(0);
        }

    }

    public static String getString(String name){
        return prop.getProperty(name, null);
    }

}
