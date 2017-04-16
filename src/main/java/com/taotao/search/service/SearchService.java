package com.taotao.search.service;

import org.springframework.stereotype.Service;

import com.taotao.search.pojo.SearchResult;

@Service
public interface SearchService {
	SearchResult search(String queryString,int page,int row) throws Exception;
}
