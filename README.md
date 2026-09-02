# Workshop 9.2 - Continuous Integration

This repository implements the Spring Boot CI workshop in `CIWorkshop v4.0.pdf`.

## Implemented API endpoints

| Method | Endpoint | Response |
|---|---|---|
| GET | `/` | `HEALTH CHECK OK!` |
| GET | `/version` | `The actual version is 1.0.0` |
| GET | `/nations` | JSON array containing 10 random nations |
| GET | `/currencies` | JSON array containing 20 random currencies |

## Run locally

Java 17 or newer is required.

```bash
./mvnw spring-boot:run
```

Then test the endpoints with a browser, Postman, or curl:

```bash
curl http://localhost:8080/
curl http://localhost:8080/version
curl http://localhost:8080/nations
curl http://localhost:8080/currencies
```

## Test and coverage

```bash
./mvnw clean verify
```

The tests exercise all four workshop endpoints. JaCoCo creates the HTML report at
`target/site/jacoco/index.html`. The Maven build fails if line coverage of
`DataController` is below 90%.

## GitHub Actions CI

`.github/workflows/maven.yml` runs on pushes and pull requests to `main` and contains:

1. `test` - builds with Java 17, runs all tests, enforces coverage, and uploads the JaCoCo report.
2. `sonar` - runs SonarCloud analysis after tests pass when `SONAR_TOKEN` is configured.
3. `snyk` - scans Maven dependencies after tests pass when `SNYK_TOKEN` is configured.

To enable the account-dependent integrations:

1. Create/import this repository in SonarCloud and disable Automatic Analysis.
2. In GitHub, open **Settings > Secrets and variables > Actions**.
3. Add repository secret `SONAR_TOKEN` from SonarCloud.
4. Import the repository into Snyk and add repository secret `SNYK_TOKEN`.
5. If the SonarCloud organization or project key differs, update the three `sonar.*`
   properties in `pom.xml`.

The built-in `GITHUB_TOKEN` is supplied automatically by GitHub Actions. Secrets are never
stored in this repository. When a third-party token is absent, its job reports that setup is
pending without breaking the test pipeline.

## Docker

The multi-stage Docker build compiles and tests the application, then produces a Java 17
runtime image. The container listens on port 5000.

```bash
docker build -t workshop9.2 .
docker run --rm -p 8090:5000 workshop9.2
```

Test it at `http://localhost:8090/`.

To publish to Docker Hub:

```bash
docker tag workshop9.2 YOUR_DOCKERHUB_USERNAME/workshop9.2:latest
docker push YOUR_DOCKERHUB_USERNAME/workshop9.2:latest
```

## Optional Railway deployment

Create a Railway project from this GitHub repository. Railway can build the Dockerfile and
redeploy automatically after pushes to the selected branch. Third-party account authorization
must be completed by the repository owner in Railway.
