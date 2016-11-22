package com.jomilanez.repository;

import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ElasticSearchEventsRepository implements EventsRepository {

    private final TransportClient client;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ElasticSearchEventsRepository(@NonNull TransportClient client) {
        this.client = client;
    }

    @Override
    public void save(List<Event> events) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        events.forEach(event -> bulkRequest.add(prepareIndex(event)));
        BulkResponse bulkResponse = bulkRequest.get();

        if (bulkResponse.hasFailures()) {
            LOGGER.error("Failed to save");
        }
    }

    private IndexRequestBuilder prepareIndex(Event event) {
        try {
            return client.prepareIndex("events", "event").setSource(objectMapper.writeValueAsBytes(event));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error when parsing event {}", event.toString(), e);
            return null;
        }
    }


}
