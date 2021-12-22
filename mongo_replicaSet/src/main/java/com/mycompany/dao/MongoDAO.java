package com.mycompany.dao;

import java.util.List;
import java.util.Map;

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
	private MongoClient connectClient() {
		MongoClient client = null;
		
		MongoClientOptions options = MongoUtils.setMongoOptions(TIMEOUT);
		
		if(REPL_SET) {
			client = new MongoClient(MongoUtils.makeServerAddressList(URL_LIST, PORT_LIST), options);
		}
		else {
			client = new MongoClient(new ServerAddress(URL, PORT), options);
		}
		
		return client;
	}
	
	// MongoDatabase 연결
	private MongoDatabase connectDB(MongoClient client) {
		return client.getDatabase(DB);
	}
	
	// mongoDB 연결
	public void connectMongoDB() {
		MONGO_CLIENT = connectClient();
		MONGO_DATABASE = connectDB(MONGO_CLIENT);
	}
	
	// mongoDB 연결 해제
	public void disconnectMongoDB() {
		MONGO_CLIENT.close();
	}
	
	/***
	 * insertOne <br>
	 * @param collectionName
	 * @param param
	 */
	public void insertOne(String collectionName, Map<String, Object> param) {
		
		MongoCollection<Document> collection = MONGO_DATABASE.getCollection(collectionName);
		collection.insertOne(new Document(param));
	}
}
