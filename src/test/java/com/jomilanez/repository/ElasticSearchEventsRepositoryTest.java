package com.jomilanez.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "integration")
public class ElasticSearchEventsRepositoryTest {

    private ElasticSearchEventsRepository repository = new ElasticSearchEventsRepository(client);

    private static Client client;

    @Test
    public void shouldSaveEvents() throws Exception {
        repository.save(Arrays.asList(Event.builder().id("id").eventType("open").build()));

        GetResponse fields = client.prepareGet("events", "event", "id").execute().actionGet();
        assertThat(fields.getSource().get("eventType")).isEqualTo("open");
    }

    @BeforeClass
    public static void startEmbeddedElasticsearchServer() throws NodeValidationException, IOException {

        FileUtils.deleteDirectory(new File("target/elasticsearch"));

        Settings settings = Settings.builder()
                .put("path.home", "target/elasticsearch")
                .put("transport.type", "local")
                .put("http.enabled", false)
                .build();

        client = new Node(settings).start().client();

        client.admin().cluster().prepareHealth()
                .setWaitForGreenStatus()
                .get();

    }

    @AfterClass
    public static void shutdownEmbeddedElasticsearchServer() throws IOException {
        client.close();
    }

}
