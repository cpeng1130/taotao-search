package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;
@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) throws Exception {
		
		QueryResponse response = solrServer.query(query);
		
		SolrDocumentList solrDocumentList = response.getResults();
		List<SearchItem> itemList = new ArrayList<>();
		for(SolrDocument solrDocument:solrDocumentList){
			SearchItem searchItem = new SearchItem();
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setItem_desc((String) solrDocument.get("item_desc"));
			searchItem.setPrice((Long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			
			List<String> list = highlighting.get(solrDocument.get("id")).get(solrDocument.get("item_title"));
			String itemTitle="";
			if(list!=null &&list.size()>0){
				itemTitle = list.get(0);
			}else{
				itemTitle=(String) solrDocument.get("item_title");
			}
			
			searchItem.setTitle(itemTitle);
			itemList.add(searchItem);
		}
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		result.setRecordCount(solrDocumentList.getNumFound());
		return result;
	}

}
