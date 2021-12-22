package com.mycompany.exception;

import java.util.List;

public class MongoException {

	private MongoException() {
		
	}
	
	/***
	 * urls, ports 사이즈 일치하지 않을 시 IllegalArgumentException 발생
	 * @param urls
	 * @param ports
	 */
	public static void chkSizeException(List<String> urls, List<Integer> ports) {
		if(urls.size() != ports.size()) {
			throw new IllegalArgumentException("List size exception.  URL_LIST: " + urls.size() + " PORT_LIST: " + ports.size());
		}
	}
}
