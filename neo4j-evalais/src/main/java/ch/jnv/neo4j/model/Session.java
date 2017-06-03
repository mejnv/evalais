package ch.jnv.neo4j.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Session {
  @GraphId private Long id;
  
  private String theme;

  private Session() {
    // Empty constructor required as of Neo4j API 2.0.5
  };

  public Session(String theme) {
    this.theme = theme;
  }
  
  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }
}
