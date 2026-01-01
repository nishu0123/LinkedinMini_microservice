package com.Nishant.LinkedIn_Mini.ConnectionService.Repository;

import com.Nishant.LinkedIn_Mini.ConnectionService.Entity.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<PersonEntity , Long> {

    Optional<PersonEntity> findByUserId(Long userId);
}
