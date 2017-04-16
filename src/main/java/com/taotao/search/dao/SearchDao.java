package com.taotao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Repository;

import com.taotao.search.pojo.SearchResult;
@Repository
public interface SearchDao {

	SearchResult search(SolrQuery query) throws Exception;
}
