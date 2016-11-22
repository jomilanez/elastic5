package com.jomilanez.repository;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix="spring.data.elasticsearch")
public class ElasticsearchProperties {

    private final String clusterName;

    private final String clusterNodes;
}
