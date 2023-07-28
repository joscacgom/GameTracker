# GameTracker
The purpose of this application is to facilitate the planning and organization, as well as progress tracking, of a video game enthusiast. It allows users to create status lists and record the hours spent playing for each video games. It also allows them to search and filter the games they want to add to their lists.

## Follow this steps
There are several important commands to execute in order to run a dockerize version of the app:

First of all, we must have a elasticsearch container, we can create an elasticsearch container using the image provided by Elastic, as usually:
```
docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" \ docker.elastic.co/elasticsearch elasticsearch:7.17.2
```

After that we have to dockerize the backend (root directory):
```
docker build -t gametracker-back .
docker run -p 8080:8080 gametracker-back
```

Now that we have our backend running properly, we must index all the games in elastic, by clicking in the next endpoint:
[Index elastic games](http://127.0.0.1:8080/elastic/game/listGames)

Finally, we are able to dockerize and run the frontend side. To make it happen, we must be located at the frontend directory (cd frontend):
```
docker build -t gametracker-front .
docker run -p 8081:8081 gametracker-front
```

Great! our app is running smoothly at [GameTracker App](http://localhost:8081)

If we want to execute the app locally, we need first to modify the application properties:
```
#Configuracion ElasticSearch para Docker
spring.data.elasticsearch.cluster-nodes=172.17.0.2:9200
```
Comment the line above, and uncomment this one:
```
# Configuracion ElasticSearch para desplegar en local
# spring.data.elasticsearch.cluster-nodes=localhost:9200
```

Now, we must execute the backend first (root directory):
```
mvn spring-boot:run
```

Then we go for the frontend side (cd frontend):
```
vue serve
```

Remember that we need a elastic container running with the games properly indexed as we saw before.

## Developers
[José Cáceres](https://github.com/joscacgom) & [Mayte (Teresa) Fernández](https://github.com/teresafcoro)
