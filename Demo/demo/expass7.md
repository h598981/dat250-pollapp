# Assignment 7 
The goal of this assignement was to containarize my application

## Files created
Dockerfile
Docker-compose.yml
edited build.gradle


## Results and problems
The application was successfully built into docker image and connected to Redis.
However the DemoApplication didnt start inside DOcker - instead a small Redis demo class.
This caused the container to stop after printing "Redis demo disabled".

Not sure what the problem is, but i could be the Gradle build file pointing to the demo class as the start class instead of the main application. 

Also made initially a wrong path in the Dockerfile, but this was an easy fix when i noticed. 