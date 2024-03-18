# Recruitment task

## How to run:

Just run the EndToEndTest test. All the integration testing dependencies will be
resolved with TestContainers.

## Prequisities:

- Running Docker environment
- Internet access (to pull docker images)
- Gradle
- Enough RAM to handle kafka and Redis docker

### Notice:

Unfortunately the task isn't completed 100% percent.
I didn't have a free time during my weekend to complete that. The remaining steps:
- Debug Java records serialization with Jackson
- Load tests
- REST API module
- REST API client and integration test

Copyright Adam Puchała 2024, all rights reserved