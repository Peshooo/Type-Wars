# Type Wars
To run the project locally, pull the Redis and MariaDB Server images first, as the project depends on them.

```docker pull redis```

```docker pull mariadb/server```

Once you have the images locally, you can run them as follows:

```docker run -p 6379:6379 redis```

```docker run -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=typewars_records -p 3306:3306 mariadb/server```

Then run the record store with dev profile:

```cd RecordStore```

```mvn spring-boot:run -Dspring-boot.run.profiles=dev```

Then run the game server with dev profile:

```cd GameServer```

```mvn spring-boot:run -Dspring-boot.run.profiles=dev```

Then run the web mvc with dev profile:

```cd Web```

```mvn spring-boot:run -Dspring-boot.run.profiles=dev``` (note that running from the root folder with ```-pl Web``` will not work as the web-app folder is not visible from that context).

Then hit localhost:8080 and enjoy.