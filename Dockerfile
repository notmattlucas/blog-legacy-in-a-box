FROM springcommunity/spring-petclinic-rest

FROM openjdk:18-slim-buster
COPY --from=0 /app /app
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod 755 /wait-for-it.sh