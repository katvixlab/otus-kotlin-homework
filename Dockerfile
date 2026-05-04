FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd -r -u 10001 -g root -d /app -s /sbin/nologin coachdesk \
  && chown -R 10001:0 /app

COPY ok-coachdesk/ok-coachdesk-app-ktor/build/libs/ok-coachdesk-app-ktor-all.jar /app/app.jar
COPY specs/specs-ts-v1.yml /app/specs/specs-ts-v1.yml
RUN chown 10001:0 /app/app.jar

USER 10001

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar"]