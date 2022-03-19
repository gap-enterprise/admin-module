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

# Generate jOOQ classes
After adding new files to liquibase folder, we have to run the maven profile `jooqGen` like this:
```shell
mvn clean compile -PjooqGen
```

