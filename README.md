# Administration module
We configure here :

- Access rights
- Profiles
- User accounts

We also manage user authentication and authorization.

# Generate jOOQ classes
After adding new files to liquibase folder, we have to run the maven profile `jooqGen` like this:
```shell
mvn clean compile -PjooqGen
```

