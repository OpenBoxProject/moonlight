package org.moonlightcontroller.southbound.client;

import java.util.List;

import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

import com.twitter.finagle.http.Http;
import com.twitter.util.StorageUnit;
import com.twitter.finagle.Service;

public class SouthboundClient implements ISouthboundClient {

//	StorageUnit su = new StorageUnit(1024 * 1024 * 1024);
//	private Http http;
//	private Service<HttpRequest, HttpResponse> client;
//	private void init () {
//		http = Http();
//		http.maxRequestSize(su);
//		http.maxResponseSize(su);
//		http.decompressionEnabled(true);
//		client =
//				ClientBuilder()
//				.codec(http)
//				.hostConnectionLimit(1000)
//				.hosts(new InetSocketAddress(apiUri, 80))
//				.requestTimeout(Duration.fromTimeUnit(6 * 60, TimeUnit.SECONDS))
//				.timeout(Duration.fromTimeUnit(6 * 60, TimeUnit.SECONDS))
//				.tcpConnectTimeout(Duration.fromTimeUnit(6 * 60, TimeUnit.SECONDS))
//				.connectTimeout(Duration.fromTimeUnit(6 * 60, TimeUnit.SECONDS))
//				.retries(1)
//				.build();
//	}
	

	 

	@Override
	public void sendProcessingGraph(InstanceLocationSpecifier loc, List<IStatement> stmts) {
		// TODO Auto-generated method stub

	}
}