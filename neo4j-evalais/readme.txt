//delete all
MATCH (n)
DETACH DELETE n

//First begin with neo4j console
//http://console.neo4j.org/

CREATE (danny:Personne {nom: 'Villemure', prenom: 'Danny'}), 
(jnv:Personne {nom: 'Vuissoz', prenom: 'Jean-Nöel'}),
(val:Personne {nom: 'Héritier', prenom: 'Valéry'}),
(bax:Personne {nom: 'Barmaz', prenom: 'Xavier'}),
(GM:Entreprise {nom: 'Groupe Mutuel'}),
(HEVS:Entreprise {nom: 'Haute Ecole Valaisanne'}),
(IO:Entreprise {nom: 'Iomedia'}), 
(danny)-[:TRAVAILLE]->(GM),
(jnv)-[:TRAVAILLE {since:2001}]->(IO),
(jnv)-[:TRAVAILLE {since:2004}]->(GM),
(bax)-[:TRAVAILLE]->(GM),
(bax)-[:TRAVAILLE {since:2011}]->(HEVS),
(val)-[:TRAVAILLE]->(HEVS)

MATCH (Personne)-[t:TRAVAILLE]->(Entreprise) RETURN Personne, t, Entreprise

//Continue with 

//Query by Criteria
MATCH (m:Member) 
WHERE m.nom = 'Vuissoz'
AND m.prenom = 'Jean-Noël'
RETURN m

//Same by JSON Style
MATCH (m:Member {nom: 'Vuissoz', prenom: 'Jean-Noël'})
RETURN m

//UPDATE
MATCH (m:Member {pseudo: 'dan'})
//SET m.pseudo=m.prenom,  m.nom = 'Villemure'
RETURN m

//Les membres participants à la session nosql
MATCH (m:Member)-[:PARTICIPE]->(session:Session {theme: 'NO SQL'})
RETURN m, session

//Les sessions auxquels Danny a participé:
MATCH (m:Member {nom: 'Villemure'})-[:PARTICIPE]->(session) 
RETURN session, m

MATCH (session:Session)<-[:PARTICIPE]-(m:Member {nom: 'Villemure'})
RETURN session, m

//Les membres qui ont participé au même session que Danny
MATCH (danny:Member {nom: 'Villemure'})-[:PARTICIPE]->(session)<-[:PARTICIPE]-(m:Member) 
RETURN m, session

//Ceux qui ont participé à plus d’une session
MATCH(m:Member)-[p:PARTICIPE]->() 
WITH count(p) as nb, m as pers 
ORDER BY nb DESC
WHERE nb>1
RETURN pers, nb

//Le nombre de participants par session
MATCH(m:Member)-[p:PARTICIPE]->(session) 
WITH count(p) as nb, session as session
ORDER BY nb DESC
RETURN nb, session

//Les membres qui ont participé à la session Docker mais pas à NoSql
MATCH (docker:Session { theme: 'Docker' })--(m:Member),(nosql:Session {theme: 'NO SQL'}) 
WHERE NOT (docker)--(m)--(nosql)
return docker, m, nosql

//Les sessions qui ont été animés par un participant des sessions animés par Jean-Noël
MATCH (session:Session)<-[:ANIME]-(m:Member)-[:PARTICIPE]->(sessionJnv:Session)<-[:ANIME]-(jnv:Member {pseudo: 'jnv'})
RETURN session, m, sessionJnv, jnv

//Les membres intéressées à Docker ou NoSQL
MATCH (m:Member)-[:PARTICIPE]->(session:Session)
WHERE session.theme= 'Docker' 
OR session.theme = 'NO SQL'
RETURN m, session

//Les membres intéressées à Docker et NoSQL
MATCH (sessionDocker:Session {theme: 'Docker'})<-[:PARTICIPE]-(m:Member)-[:PARTICIPE]->(sessionNosql:Session {theme: 'NO SQL'})
RETURN m, sessionDocker, sessionNosql


//Les amis depuis 2008
MATCH (m:Member {pseudo: 'jnv')-[:FRIEND { since: '2010-08-24' }]->(ami:Member)
RETURN m, ami

