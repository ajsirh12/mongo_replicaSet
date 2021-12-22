package com.mycompany.utils;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.ServerAddress;

public class MongoUtils {

	private MongoUtils() {
		
	}
	
	// ServerAddress List화
	public static List<ServerAddress> makeServerAddressList(List<String> urls, List<Integer> ports){
		List<ServerAddress> result = new ArrayList<ServerAddress>();
		
		for(int i=0; i<urls.size(); i++) {
			result.add(new ServerAddress(urls.get(i), ports.get(i)));
		}
		
		return result;
	}
}
