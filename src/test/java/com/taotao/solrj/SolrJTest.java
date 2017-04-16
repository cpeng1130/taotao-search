package com.taotao.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {
	@Test
	public void testSolrJ()throws Exception{
		
		SolrServer solrServer = new HttpSolrServer("http://192.168.6.188:8080/solr");
		
		SolrInputDocument document = new SolrInputDocument();
		
		document.addField("id", "solrtest01");
		document.addField("item_title", "test-title");
		document.addField("item_sell_point", "test-sell_point");
		
		solrServer.add(document);
		
		solrServer.commit();
		
	}
	@Test
	public void testQuery() throws Exception{
		
		SolrServer solrServer = new HttpSolrServer("http://192.168.6.188:8080/solr");
		
		SolrQuery query = new SolrQuery();
		
		query.setQuery("*:*");
		
		QueryResponse response = solrServer.query(query);
		
		SolrDocumentList list = response.getResults();
		for( SolrDocument solrDocument: list){
			System.out.println(solrDocument.get("id")); 
			System.out.println(solrDocument.get("item_title")); 
			System.out.println(solrDocument.get("item_sell_point")); 
		}
	}
	@Test
	public void testSolrCloud() throws Exception{
		
		String zkHost="192.168.6.188:2181,192.168.6.188:2182,192.168.6.188:2183";
		
		CloudSolrServer solrServer = new CloudSolrServer(zkHost);
		
		solrServer.setDefaultCollection("collection2");
		
		SolrInputDocument document= new SolrInputDocument();
		document.addField("id", "test01");
		document.addField("item_title", "testCloud");
		solrServer.add(document);
		solrServer.commit();
		
		
	}
}
