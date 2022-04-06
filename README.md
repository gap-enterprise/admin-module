<img src="https://gap.surati.io/img/logo.png" width="64px" height="64px"/>

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/gap-enterprise/admin-module)](http://www.rultor.com/p/gap-enterprise/admin-module)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Javadoc](http://www.javadoc.io/badge/io.surati.gap/admin-module.svg)](http://www.javadoc.io/doc/io.surati.gap/admin-module)
[![License](https://img.shields.io/badge/License-Surati-important.svg)](https://github.com/gap-enterprise/admin-module/blob/master/LICENSE.txt)
[![codecov](https://codecov.io/gh/gap-enterprise/admin-module/branch/master/graph/badge.svg)](https://codecov.io/gh/gap-enterprise/admin-module)
[![Hits-of-Code](https://hitsofcode.com/github/gap-enterprise/admin-module)](https://hitsofcode.com/view/github/gap-enterprise/admin-module)
[![Maven Central](https://img.shields.io/maven-central/v/io.surati.gap/admin-module.svg)](https://maven-badges.herokuapp.com/maven-central/io.surati.gap/admin-module)
[![PDD status](http://www.0pdd.com/svg?name=gap-enterprise/admin-module)](http://www.0pdd.com/p?name=gap-enterprise/admin-module)

Module `Admin` is used to configure :

- Access rights
- Profiles
- User accounts

We also manage user authentication and authorization.

# How to run the project
Firstly, you must create a PostgreSQL database (`db_gap` by example) and host it at a PostgreSQL instance (reachable locally by example at port `5070`).

Then, you can run the application at port `9090` with Maven like this:
```shell
mvn clean integration-test -Phit-refresh -Dport=9090 -Ddb.driver=org.postgresql.Driver -Ddb.url=jdbc:postgresql://127.0.0.1:5070/db_gap -Ddb.user=gap -Ddb.password=admin --threads=10
```

