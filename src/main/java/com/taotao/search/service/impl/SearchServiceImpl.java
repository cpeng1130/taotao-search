package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	public SearchResult search(String queryString, int page, int rows) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(queryString);
		solrQuery.setStart((page-1)*rows);
		solrQuery.setRows(rows);
		
		
		solrQuery.set("df","item_title");
		
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
		solrQuery.setHighlightSimplePost("</font>");
		
		SearchResult search = searchDao.search(solrQuery);
		Long recordCount = search.getRecordCount();
		int pageCount=(int) (recordCount/rows);
		if(recordCount%rows>0){
			pageCount++;
		}
		search.setPageCount(pageCount);
		search.setCurPage(page);
		return search;
	}

}
