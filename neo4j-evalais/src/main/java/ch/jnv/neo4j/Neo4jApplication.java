package ch.jnv.neo4j;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import ch.jnv.neo4j.model.Member;
import ch.jnv.neo4j.repo.MemberRepository;

@SpringBootApplication
@EnableNeo4jRepositories
public class Neo4jApplication {

  private final static Logger log = LoggerFactory.getLogger(Neo4jApplication.class);

  public static void main(String[] args) throws Exception {

    SpringApplication.run(Neo4jApplication.class, args);
  }

  @Bean
  CommandLineRunner demo(MemberRepository repository) {
    return args -> {
      repository.deleteAll();

      Member jnv = new Member("jnv");
      Member val = new Member("val");

      List<Member> members = Arrays.asList(jnv);

      log.info("Before linking up with Neo4j...");

      members.stream().forEach(person -> log.info("\t" + person.toString()));

      repository.save(jnv);
      repository.save(val);

      jnv = repository.findByName(jnv.getName());
      jnv.friendWith(val);
      
      repository.save(jnv);

      log.info("Lookup each person by name...");
      members.stream().forEach(person -> log.info("\t" + repository.findByName(person.getName()).toString()));
    };
  }

}