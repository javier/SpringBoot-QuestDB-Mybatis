# SpringBoot QuestDB  Mybatis

Demo project to use QuestDB with SpringBoot and Mybatis.

_Project forked from SpringBoot整合PostgreSQL+Mybatis demo, by banmajio (thanks!)_

# Getting started

If your QuestDB runs at localhost, using the default port `8812`, database `qdb`, user `admin`, and password `quest` you
are all set. Otherwise, please do change the file `./src/main/resources/application.yml accordingly`.

# Building

You need Java and Maven. Build with
```shell
mvn install
````


# Running

Start the server with
```shell
java -jar target/SpringBoot-QuestDB-0.0.1-SNAPSHOT.jar
```

After a few seconds you should see a log line saying:

```shell
INFO 37738 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path '/PostgreSQL'
```

# Trying out the application

_Please note  there is no error control. If you try to select from a nonexisting table, or similar statements, QuestDB will throw an error and
the application will just show an ugly generic message. Check your logs for extra information. Also, no attempts at security, performance, or even 
correctness have been done for this example. This is just a template to help configure mybatis and QuestDB._

Before we start inserting or querying data we need a table. You can create the table `user_test` by calling `http://localhost:8080/QuestDB/create`

Behind the scenes, the following SQL will be issued
```sql
CREATE TABLE IF NOT EXISTS user_test(ts timestamp, name symbol, age int)
		timestamp(ts)
		PARTITION BY DAY WAL
		DEDUP UPSERT KEYS(ts, name);
```

To insert some data, just replace parameters on this URL `http://localhost:8080/QuestDB/insert?name=javier&age=25`

And this is the SQL statement that will be executed:
```sql
insert into
user_test(ts, name,age) values(now(), #{name},#{age})
```

Now you can query your user list via `http://localhost:8080/QuestDB/select`

For this SQL
```sql
select * from user_test
```

You can update any existing user, by providing the name and new age, as in `http://localhost:8080/QuestDB/update?name=javier&age=45`

It will trigger this statement:
```sql
update user_test set
age=#{age} where name = #{name}
```

You can query your data again to see the updated record with `http://localhost:8080/QuestDB/select`

If you want to empty the table contents, but keep the table around, you can use `http://localhost:8080/QuestDB/truncate`

A select statement now should show and empty result set, via `http://localhost:8080/QuestDB/select`

And whenever you want to drop the table, just call `http://localhost:8080/QuestDB/drop`

Which will trigger 
```sql
drop table user_test
```