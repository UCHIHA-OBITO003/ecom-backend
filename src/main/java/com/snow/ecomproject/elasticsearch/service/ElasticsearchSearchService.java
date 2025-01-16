package com.snow.ecomproject.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import com.snow.ecomproject.elasticsearch.model.ProductDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticsearchSearchService {

    private final ElasticsearchClient esClient;

    @Autowired
    public ElasticsearchSearchService(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }

    public List<ProductDocument> searchProducts(String searchTerm, Double maxMrp) {
        try {

            MatchQuery nameFuzzyQuery = MatchQuery.of(m -> m
                    .field("pName")
                    .query(searchTerm)
                    .fuzziness("AUTO")
            );


            final RangeQuery rangeQuery;
            if (maxMrp != null) {
                JsonData maxMrpJson = JsonData.of(maxMrp);
                rangeQuery = RangeQuery.of(r -> r
                        .field("mrp")
                        .lte(maxMrpJson)
                );
            } else {
                rangeQuery = null;
            }


            BoolQuery boolQuery = BoolQuery.of(b -> {
                if (rangeQuery != null) {
                    return b
                            .must(m -> m.match(nameFuzzyQuery))
                            .must(m -> m.range(rangeQuery));
                } else {
                    return b.must(m -> m.match(nameFuzzyQuery));
                }
            });


            SearchRequest request = SearchRequest.of(s -> s
                    .index("product_index") // Match your Elasticsearch index name
                    .query(q -> q.bool(boolQuery))
            );


            SearchResponse<ProductDocument> response = esClient.search(request, ProductDocument.class);


            List<ProductDocument> results = new ArrayList<>();
            response.hits().hits().forEach(hit -> {
                if (hit.source() != null) {
                    results.add(hit.source());
                }
            });

            return results;

        } catch (IOException e) {
            throw new RuntimeException("Failed to perform search query", e);
        }
    }
}
