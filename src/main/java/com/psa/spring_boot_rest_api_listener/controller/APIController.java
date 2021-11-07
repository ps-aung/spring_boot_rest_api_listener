package com.psa.spring_boot_rest_api_listener.controller;

import com.psa.spring_boot_rest_api_listener.utils.Config;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pyaesoneaung at 11/07/2021
 */
@RestController
public class APIController {

    private static final String template = "\nPath\t\t: %s\nRequest Body\t: %s";

    Logger logger = LoggerFactory.getLogger(APIController.class);

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{path}", produces = "application/json; charset=utf-8")
    public ResponseEntity<String> apiListener(@RequestBody @Nullable String request,
                                    @PathVariable(name = "path") String path) {
        logger.info(String.format(template,path,request));
        String content = null;
        Object jsonObject = (JSONObject) Config.jsonDb.get(path);

        if (jsonObject != null) {
            if (jsonObject instanceof JSONArray) {
                content = ((JSONObject)((JSONArray) jsonObject).get(0)).toJSONString();
            } else if (jsonObject instanceof JSONObject) {
                content = ((JSONObject) jsonObject).toJSONString();
            } else {
                content = jsonObject.toString();
            }
        }
        if (content != null && !content.isEmpty()) {
            return new ResponseEntity<>(content, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
