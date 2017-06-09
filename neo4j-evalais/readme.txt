https://neo4j.com/docs/cypher-refcard/current/

//delete all
MATCH (n)
DETACH DELETE n

//Mes amis qui ont déjà participés à une Session

//Create
CREATE (martin:Personne {nom: 'Garcia', prenom: 'Martin'}), 
(jnv:Personne {nom: 'Vuissoz', prenom: 'Jean-Nöel'}),
(steve:Personne {nom: 'Vaquin', prenom: 'Steve'}),
(jimmy:Personne {nom: 'Dubuis', prenom: 'Jimmy'}),
(archi:Departement {nom: 'Architecture'}),
(finance:Departement {nom: 'Finance'}), 
(martin)-[:TRAVAILLE]->(archi),
(jnv)-[:TRAVAILLE {since:2009}]->(archi),
(jnv)-[:TRAVAILLE {since:2004, until:2009}]->(finance),
(steve)-[:TRAVAILLE]->(finance),
(jimmy)-[:TRAVAILLE]->(finance)

MATCH (Personne)-[t:TRAVAILLE]->(Departement) RETURN Personne, t, Departement


//Query by Criteria
MATCH (p:Personne) 
WHERE p.nom = 'Jacob'
AND p.prenom = 'Olivier'
RETURN p

MATCH (p:Personne {nom: 'Jacob', prenom: 'Olivier'})
RETURN p

//UPDATE
MATCH (p:Personne {nom: 'Gabioud'})
//SET p.surnom=p.prenom,  p.prenom = 'Nicolas'
RETURN p

//Les personne participants au bbl nosql
MATCH (p:Personne)-[:PARTICIPE]->(bbl:Bbl {titre: 'SQL or NOSQL'})
RETURN p, bbl

//Les personne participants à la deuxième session du bbl nosql
MATCH (p:Personne)-[:PARTICIPE {noSession:2}]->(bbl:Bbl {titre: 'SQL or NOSQL'})<-[:ANIME]-(a:Personne)
RETURN p, bbl, a

//Les Bbls auxquels Nicolas à participé:
MATCH (p:Personne {nom: 'Gabioud'})-[:PARTICIPE]->(bbl) 
RETURN bbl, p

MATCH (b:Bbl)<-[:PARTICIPE]-(p:Personne {nom: 'Gabioud'})
RETURN b, p

//Les personnes qui ont participé au même Bbl que Nicolas
MATCH (nico:Personne {nom: 'Gabioud'})-[:PARTICIPE]->(bbl)<-[:PARTICIPE]-(p:Personne) 
RETURN p, bbl

//Les personnes qui ont participé au même Bbl et même session que Nicolas
MATCH (nico:Personne {nom: 'Gabioud'})-[nicop:PARTICIPE]->(bbl)<-[pp:PARTICIPE]-(p:Personne) 
WHERE  nicop.noSession = pp.noSession
RETURN p, bbl

//Ceux qui ont participé à plus d’une session
MATCH(p:Personne)-[pp:PARTICIPE]->() 
WITH count(pp) as nb, p as pers 
ORDER BY nb DESC
WHERE nb>1
RETURN pers, nb

//Le nombre de participants par bbl
MATCH(p:Personne)-[pp:PARTICIPE]->(bbl) 
WITH count(pp) as nb, bbl as bbl
ORDER BY nb DESC
RETURN nb, bbl

//Les personnes qui ont participé au bbl PKI mais pas au Bbl NoSql
MATCH (bbl:Bbl { titre: 'PKI et PKCS' })--(p:Personne),(bbl2:Bbl {titre: 'SQL or NOSQL'}) 
WHERE NOT (bbl)--(p)--(bbl2)
return bbl, p, bbl2

//Les bbls qui ont été animés par un participant des bbls animés par Christophe
MATCH (bbl:Bbl)<-[:ANIME]-(p:Personne)-[:PARTICIPE]->(bblChristophe:Bbl)<-[:ANIME]-(christophe:Personne {nom: 'Rodriguez'})
RETURN bbl, p, bblChristophe, christophe

//Les personnes intéressées à PKI ou Camel
MATCH (p:Personne)-[:PARTICIPE]->(bbl:Bbl)
WHERE bbl.titre= 'PKI et PKCS' 
OR bbl.titre = 'Camel'
RETURN p, bbl

//Les personnes intéressées à PKI et Camel
MATCH (bbl1:Bbl {titre: 'Camel'})<-[:PARTICIPE]-(p:Personne)-[:PARTICIPE]->(bbl2:Bbl {titre: 'PKI et PKCS'})
RETURN p, bbl1, bbl2



