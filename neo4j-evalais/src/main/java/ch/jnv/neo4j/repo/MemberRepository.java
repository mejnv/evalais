package ch.jnv.neo4j.repo;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import ch.jnv.neo4j.model.Member;

@Repository
public interface MemberRepository extends GraphRepository<Member> {

  Member findByName(String name);
  
//  @Query("MATCH (m:Movie)<-[rating:RATED]-(user)
//      WHERE id(m) = {movieId} RETURN rating")
//Iterable<Rating> getRatings(@Param("movieID") Long movieId);
}