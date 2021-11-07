package com.psa.spring_boot_rest_api_listener;

import com.psa.spring_boot_rest_api_listener.utils.Config;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.util.Arrays;

@SpringBootApplication
public class SpringBootRestApiListenerApplication implements ApplicationRunner {

	Logger logger = LoggerFactory.getLogger(SpringBootRestApiListenerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiListenerApplication.class, args);
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
		logger.info("NonOptionArgs: {}",args.getNonOptionArgs());
		logger.info("OptionNames: {}",args.getOptionNames());

		for(String name: args.getOptionNames()){
			logger.info("args-" + name + "=" + args.getOptionValues(name));
		}

		boolean containsOption = args.containsOption("db");
		if(!containsOption){
			CodeSource codeSource = SpringBootRestApiListenerApplication.class.getProtectionDomain().getCodeSource();

			try{
				File jarFile;
				if(codeSource==null){
					logger.info("Code source is null");
				}else{
					String classpath = codeSource.getLocation().toURI().getPath();
					logger.info("Jar file is running on location: {}",classpath);
					jarFile = new File(classpath);
					String jarLocation = jarFile.getParentFile().getPath();
					Config.dbPath = jarLocation + File.separator + "db.json";
					logger.info("Default DB using: {}", Config.dbPath);
				}
			}catch(URISyntaxException e){
				e.printStackTrace();
			}
		}else{
			Config.dbPath = args.getOptionValues("db").get(0);
		}

		try {
			File jsonFile = new File(Config.dbPath);
			FileSystemManager fsManager = VFS.getManager();
			FileObject fileObject = fsManager.resolveFile(jsonFile.toURI().getPath());
			DefaultFileMonitor monitor = new DefaultFileMonitor(new DbfileListener());
			monitor.setDelay(100);
			monitor.addFile(fileObject);
			monitor.start();
			InputStreamReader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
			JSONParser parser = new JSONParser();
			Config.jsonDb = (JSONObject) parser.parse(reader);
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}catch (ParseException e){
			e.printStackTrace();
		}

	}
}
