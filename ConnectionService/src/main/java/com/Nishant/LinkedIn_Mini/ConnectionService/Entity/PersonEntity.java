package com.Nishant.LinkedIn_Mini.ConnectionService.Entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.mapping.Property;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
@Node("Person")
public class PersonEntity {

//    @Id
//    @Property("id")
//    private Long id;

    @Id
    private Long userId;
    private String userName;

    //temporary commenting it out
//    @Relationship(type = "CONNECTED_TO", direction = Relationship.Direction.OUTGOING)
//    private List<PersonEntity> connections;
}
