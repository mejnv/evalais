package ch.jnv.neo4j.repo;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import ch.jnv.neo4j.model.Member;
import ch.jnv.neo4j.model.Session;

@Repository
public interface SessionRepository extends GraphRepository<Session> {
}