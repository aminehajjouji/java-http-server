package com.hajjouji.httpserver.config;


import com.hajjouji.httpserver.util.Json;

import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager configurationManager;
    private static Configuration myCUrrentConfiguration;
    private ConfigurationManager() {
    }
    public static ConfigurationManager getInstance() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }
    /*
        * This method should load the configuration file from the given path(old way)
     */
    /* public void loadConfigurationFile(String path)  {
        FileReader fileReader= null;
        try {
            fileReader = new FileReader(path);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb  = new StringBuffer();
        int i;
        try {
        while((i = fileReader.read())!=-1){
            sb.append((char)i);
        }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the configuration FIle",e);
        }
        try {
            myCUrrentConfiguration = Json.fromJson(conf,Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the configuration file, internal ", e);
        }
    }*/
    public void loadConfigurationFile(String path)  {
        try {
            myCUrrentConfiguration =  Json.readFile(path,Configuration.class);
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }


    }

        /*
        * This method should return the current configuration
     */
    public Configuration gerCurrentConfiguration() {
        if(myCUrrentConfiguration == null){
            throw new HttpConfigurationException("No current Cnfiguration Set.");
        }
        return myCUrrentConfiguration;
    }
}
