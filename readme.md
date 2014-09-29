##Setup
1. Récupérer le repository du workshop :
 `git clone https://github.com/xebia-france/workshop-clean-tests.git`

2. Builder le projet : 
 `maven clean install`

##Lancer le photomaton
1. Lancer l'application :
` mvn package Deploy locally`
` mvn tomcat7:run`

2. se rendre à l'url : [http://localhost:8080](http://localhost:8080)

##But du hands-on : Développer 5 user stories pour ajouter des fonctionnalités au photomaton.

Une user story est la description d’un besoin fonctionnel. Sa granularité est souvent trop grosse pour développer par petite étape “baby step”.
Il est donc nécessaire de décrire les comportements attendus d’une manière plus fine.

###1ère étape : Ouvrir US1-buy-a-picture.feature 

**As a photomaton customer,  I want to buy a full color portrait, In order to offer it to my mother**

Cette user story est une besoin simple qui a une valeur pour le client, son découpage ne permet cependant pas au développeur d’avancer petit à petit. L’écriture des scénarios va permettre de créer cette granularité, de valider que la compréhension du besoin lors des ateliers avec les 3 amigos.

Un découpage est proposé dans *US1-buy-a-picture.feature*. Le premier scénario est déjà implémenté, Il est déja possible de prendre une photo.
 
**A faire:** Implémenter les 2 derniers scénarios pour terminer la user story.

###2ème étape : Ajouter de nouvelles fonctionnalités au photomaton 

Les user stories a développer sont disponibles dans les fichiers suivants:
  * US2-identity-pictures.feature	
  * US3-vintage-picture.feature
  * US4-mini-picture.feature
  * US5-limited-shooting-attempts.feature

**A faire:** A vous de faire le travail de rédaction des scénarios. Ils doivent définir les étapes que vous souhaitez représenter dans votre découpage. 

La liste complète des stories à implémenter et leurs spécificités est disponible ici : [liste des stories](https://github.com/xebia-france/workshop-clean-tests/blob/master/src/site/stories.md)

##Dépendances externes

###Picture processor

Le photomaton délègue le traitement des photos à un module propriétaire dénommé le "picture processor". Au cours de l'implémentation des stories vous devrez lui envoyer des ordres dans un certain protocole dont la spécification est disponbile ici : [picture processor protocol](https://github.com/xebia-france/workshop-clean-tests/blob/master/src/site/picture-processor-firmware.md)

###Validation des photos d'identités

Un service externe de validation des photos d'identités est mis à disposition du photomaton : IdentityValidator.
Attention, il est préfèrable de ne pas l'appeler directement lors de l'écriture de vos tests...
