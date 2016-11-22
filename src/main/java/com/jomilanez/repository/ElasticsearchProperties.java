package com.jomilanez.repository;


import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix="spring.data.elasticsearch")
public class ElasticsearchProperties {

    private String clusterName;

    private String clusterNodes;
}
