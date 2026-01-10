package com.Nishant.LinkedIn_Mini.ConnectionService.Repository;

import com.Nishant.LinkedIn_Mini.ConnectionService.Entity.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PersonRepository extends Neo4jRepository<PersonEntity , Long> {

    Optional<PersonEntity> findByUserId(Long userId);

    @Query("""
MATCH (p:Person {userId: $userId})-[:CONNECTED_TO]->(c:Person)
RETURN c.userId AS userId, c.userName AS userName
""")
    List<PersonEntity> findFirstDegreeConnections(Long userId);



    @Query("""
MATCH (p:Person {userId: $userId})-[:CONNECTED_TO]->(f1:Person)
      -[:CONNECTED_TO]->(f2:Person)
WHERE f2.userId <> $userId
  AND NOT (p)-[:CONNECTED_TO]->(f2)
RETURN DISTINCT
       f2.userId   AS userId,
       f2.userName AS userName
""")
    List<PersonEntity> findSecondDegreeConnections(Long userId);




    @Query("""
MATCH (p:Person {userId: $userId})-[:CONNECTED_TO]->(f1:Person)
      -[:CONNECTED_TO]->(f2:Person)
      -[:CONNECTED_TO]->(f3:Person)
WHERE f3.userId <> $userId
  AND NOT (p)-[:CONNECTED_TO]->(f3)
  AND NOT (p)-[:CONNECTED_TO]->(f2)
RETURN DISTINCT
       f3.userId   AS userId,
       f3.userName AS userName
""")
    List<PersonEntity> findThirdDegreeConnections(Long userId);
}
