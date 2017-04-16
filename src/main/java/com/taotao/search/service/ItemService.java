package com.taotao.search.service;

import org.springframework.stereotype.Service;

import com.taotao.common.utils.TaotaoResult;
@Service
public interface ItemService  {

	TaotaoResult importItems() throws Exception;
}
