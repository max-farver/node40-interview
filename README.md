# Maxwell Farver - Interview

## Run commands
- ```java -jar example/hello-world-0.0.1-SNAPSHOT.jar server hello-world.yml```

## Notes
- Gradle used
- Second resource method added
- Unit tests written using Spock

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