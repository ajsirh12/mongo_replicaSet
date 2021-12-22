package com.mycompany.dao;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mycompany.exception.MongoException;
import com.mycompany.utils.MongoUtils;

public class MongoDAO {

	private MongoClient MONGO_CLIENT;
	private MongoDatabase MONGO_DATABASE;
	private MongoCollection<Document> MONGO_COLLECTION;
	
	private boolean REPL_SET = false;
	
	private String URL;
	private int PORT;
	private String DB;
	
	private List<String> URL_LIST;
	private List<Integer> PORT_LIST;
	
	private static final int TIMEOUT = 3000;
	
	public MongoDAO(String url, int port, String database) {
		URL = url;
		PORT = port;
		DB = database;
		
		REPL_SET = false;
	}
	
	public MongoDAO(List<String> urls, List<Integer> ports, String database) {
		// urls, ports 사이즈 확인
		MongoException.chkSizeException(urls, ports);
		
		URL_LIST = urls;
		PORT_LIST = ports;
		DB = database;
		
		REPL_SET = true;
	}
	
	// MongoClient 연결
	private MongoClient connetClient() {
		MongoClient client = null;
		
		MongoClientOptions options = MongoClientOptions.builder().connectTimeout(TIMEOUT).socketTimeout(TIMEOUT)
				.maxConnectionLifeTime(TIMEOUT).maxConnectionIdleTime(TIMEOUT * 20).build();
		
		if(REPL_SET) {
			client = new MongoClient(MongoUtils.makeServerAddressList(URL_LIST, PORT_LIST), options);
		}
		else {
			client = new MongoClient(new ServerAddress(URL, PORT), options);
		}
		
		return client;
	}
}
