package com.zkdlu.elasticsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;

    @PostMapping("/index/{index}")
    public CreateIndexResponse createIndex(@PathVariable String index) throws IOException {
        return indexService.createIndex(index);
    }

    @DeleteMapping("/index/{index}")
    public AcknowledgedResponse deleteIndex(@PathVariable String index) throws IOException {
        return indexService.deleteIndex(index);
    }

    @GetMapping("/{index}/select/{position}")
    public Map getUser(@PathVariable String index, @PathVariable int position) throws IOException {
        return indexService.selectDocument(index).getHits()
                .getAt(position).getSourceAsMap();
    }

    @GetMapping("/{index}/select")
    public SearchResponse getAll(@PathVariable String index) throws IOException {
        return indexService.selectDocument(index);
    }

    @PostMapping("/{index}")
    public IndexResponse createUser(@PathVariable String index) throws IOException {
        UserDocument user = new UserDocument();
        user.setId("10");
        user.setName("asdf");
        user.setAge(10);

        return indexService.insertDocument(index, user);
    }
}
