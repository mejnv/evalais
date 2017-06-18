package ch.jnv.neo4j.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Member {

  @GraphId
  private Long id;

  private String pseudo;
  
  private String nom;
  
  private String prenom;

  private Member() {
    // Empty constructor required as of Neo4j API 2.0.5
  };


  public Member(String pseudo, String nom, String prenom) {
    this.pseudo = pseudo;
    this.nom = nom;
    this.prenom = prenom;
  };

  public Member(String pseudo) {
    this(pseudo, "", "");
  }

  /**
   * Neo4j doesn't REALLY have bi-directional relationships. It just means when
   * querying to ignore the direction of the relationship.
   * https://dzone.com/articles/modelling-data-neo4j
   */
  @Relationship(type = "FRIEND", direction = Relationship.UNDIRECTED)
  public Set<Friend> friends;

  public Member friendWith(Member friend, LocalDate since) {
    if (friends == null) {
      friends = new HashSet<>();
    }

    friends.add(new Friend(this, friend).since(since));
    

    return this;
  }

  public Member friendWith(Member friend) {
    return friendWith(friend, LocalDate.now());
  }

  @Relationship(type = "PARTICIPE", direction = Relationship.UNDIRECTED)
  public Set<Session> sessions;

  public Member participeA(Session... session) {
    if (sessions == null) {
      sessions = new HashSet<>();
    }

    sessions.addAll(Arrays.asList(session));
    return this;
  }

  @Relationship(type = "ANIME", direction = Relationship.UNDIRECTED)
  public Set<Session> anime;

  public Member anime(Session session) {
    if (anime == null) {
      anime = new HashSet<>();
    }

    anime.add(session);
    return this;
  }

  public String toString() {
    return this.pseudo;
  }

  public String getPseudo() {
    return pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }
}