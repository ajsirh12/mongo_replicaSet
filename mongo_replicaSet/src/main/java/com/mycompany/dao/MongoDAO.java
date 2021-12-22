package com.mycompany.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
		chkSizeException(urls, ports);
		
		URL_LIST = urls;
		PORT_LIST = ports;
		DB = database;
		
		REPL_SET = true;
	}
	
	// urls, ports 사이즈 일치하지 않을 시 IllegalArgumentException 발생
	private void chkSizeException(List<String> urls, List<Integer> ports) {
		if(urls.size() != ports.size()) {
			throw new IllegalArgumentException("List size exception.  urlSize: " + urls.size() + " portsSize: " + ports.size());
		}
	}
	
	// MongoClient 연결
	private MongoClient connetClient() {
		MongoClient client = null;
		
		MongoClientOptions options = MongoClientOptions.builder().connectTimeout(TIMEOUT).socketTimeout(TIMEOUT)
				.maxConnectionLifeTime(TIMEOUT).maxConnectionIdleTime(TIMEOUT * 20).build();
		
		if(REPL_SET) {
			client = new MongoClient(makeServerAddressList(URL_LIST, PORT_LIST), options);
		}
		else {
			client = new MongoClient(new ServerAddress(URL, PORT), options);
		}
		
		return client;
	}
	
	private List<ServerAddress> makeServerAddressList(List<String> urls, List<Integer> ports){
		List<ServerAddress> result = new ArrayList<ServerAddress>();
		
		for(int i=0; i<urls.size(); i++) {
			result.add(new ServerAddress(urls.get(i), ports.get(i)));
		}
		
		return result;
	}
}
