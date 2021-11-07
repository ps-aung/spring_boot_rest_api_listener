package com.psa.spring_boot_rest_api_listener;

import com.psa.spring_boot_rest_api_listener.utils.Config;
import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by pyaesoneaung at 11/07/2021
 */
public class DbfileListener implements FileListener {

    Logger logger = LoggerFactory.getLogger(DbfileListener.class);

    @Override
    public void fileCreated(FileChangeEvent fileChangeEvent) throws Exception {

    }

    @Override
    public void fileDeleted(FileChangeEvent fileChangeEvent) throws Exception {

    }

    @Override
    public void fileChanged(FileChangeEvent fileChangeEvent){

        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(Config.dbPath)), StandardCharsets.UTF_8);
            JSONParser parser = new JSONParser();
            Config.jsonDb = (JSONObject) parser.parse(reader);
            logger.info("DB updated from {}", fileChangeEvent.getFile().getURL().toString());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
