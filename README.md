# Hospital

- Clone project from GitHub.
- Change #Spring datasource configuration (/src/main/resources/application.properties).
- Change FlyWay configuration (flyway.properties).
- In terminal :  mvn clean flyway:migrate.
- Check migration status:  mvn flyway:info

Project was deployed in heroku.
link: https://hospital-pl.herokuapp.com

login :  password
serg : serg (administrator)
jb : jb (patient)
am : am (doctor)
basia : basia (nurse)