# Maxwell Farver - Interview

**Hosted on DigitalOcean at** [https://node-40-interview-tr5x9.ondigitalocean.app/hello-world](https://node-40-interview-tr5x9.ondigitalocean.app/hello-world)

## Run commands
- ```java -jar example/hello-world-0.0.1-SNAPSHOT.jar server hello-world.yml```

## Notes
- Gradle used
- Second resource method added
- Unit tests written using Spock
- Dockerfile added for ease of hosting

## Endpoints

### hello-world
- no params

### hello-world/format
- "reverse": boolean
    - reverse the template string
- "scream": boolean
    - change the template string to uppercase
- "chopped": int
    - removed "n" characters from the original template