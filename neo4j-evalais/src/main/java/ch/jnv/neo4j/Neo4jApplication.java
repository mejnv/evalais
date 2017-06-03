package ch.jnv.neo4j;

import java.time.LocalDate;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import ch.jnv.neo4j.model.Member;
import ch.jnv.neo4j.model.Session;
import ch.jnv.neo4j.repo.MemberRepository;
import ch.jnv.neo4j.repo.SessionRepository;

@SpringBootApplication
@EnableNeo4jRepositories
public class Neo4jApplication {

  private final static Logger LOG = LoggerFactory.getLogger(Neo4jApplication.class);

  public static void main(String[] args) throws Exception {

    SpringApplication.run(Neo4jApplication.class, args);
  }

  @Bean
  CommandLineRunner demo(MemberRepository memberRepository, SessionRepository sessionRepository) {
    return args -> {
      memberRepository.deleteAll();
      sessionRepository.deleteAll();

      Member jnv = new Member("jnv");
      Member val = new Member("val");
      Member bax = new Member("bax");
      Member dan = new Member("danny");
      
      Session docker = new Session("Docker");
      Session nosql = new Session("NO SQL");
      
      memberRepository.save(Arrays.asList(jnv, val, bax, dan));
      
      jnv.anime(nosql).friendWith(val, LocalDate.of(2010, 8, 24)).friendWith(bax);
      dan.participeA(docker, nosql);
      bax.participeA(docker);
      val.participeA(nosql);
      
      memberRepository.save(Arrays.asList(jnv, dan));
      
    };
  }



}