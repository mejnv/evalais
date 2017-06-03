package ch.jnv.neo4j.model;
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

	@GraphId private Long id;

	private String name;

	private Member() {
		// Empty constructor required as of Neo4j API 2.0.5
	};

	public Member(String name) {
		this.name = name;
	}

	/**
	 * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
	 * to ignore the direction of the relationship.
	 * https://dzone.com/articles/modelling-data-neo4j
	 */
	@Relationship(type = "FRIEND", direction = Relationship.UNDIRECTED)
	public Set<Member> friends;

	public void friendWith(Member friend) {
		if (friends == null) {
			friends = new HashSet<>();
		}
		friends.add(friend);
	}

	public String toString() {

		return this.name + "'s friends => "
				+ Optional.ofNullable(this.friends).orElse(
						Collections.emptySet()).stream().map(
								person -> person.getName()).collect(Collectors.toList());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}