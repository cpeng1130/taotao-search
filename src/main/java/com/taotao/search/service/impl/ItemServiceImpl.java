package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private SolrServer solrServer;


	@Autowired
	private ItemMapper itemMapper;
	
	@Override
	public TaotaoResult importItems() throws Exception {
		
		List<SearchItem> list = itemMapper.getItemList();
		
		for(SearchItem item :list){
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id",item.getId() );
			document.addField("item_title",item.getTitle());
			document.addField("item_sell_point",item.getTitle());
			document.addField("item_price",item.getPrice());
			document.addField("item_image",item.getImage());
			document.addField("item_category_name",item.getCategory_name());
			document.addField("item_desc",item.getItem_desc());	
			solrServer.add(document);
			
		}
		solrServer.commit();	
		return TaotaoResult.ok();
	}

}
