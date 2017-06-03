package ch.jnv.neo4j.model;

import java.time.LocalDate;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "FRIEND")
public class Friend {
  @GraphId
  private Long relationshipId;

  @Property
  private String since;
  @StartNode
  private Member membre;
  @EndNode
  private Member ami;

  private Friend() {
    // Empty constructor required as of Neo4j API 2.0.5
  };

  public Friend(Member membre, Member ami) {
    this.membre = membre;
    this.ami = ami;
  }

  public Friend since(LocalDate since) {
    this.since = since.toString();
    return this;
  }
}
