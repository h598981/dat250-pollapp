# Dat250 Experiment Assignment 4 

## Technical problems
The Jakarta persistence annotations could not be resolved. 
This was caused by missing dependencies and IDE caching issues
Fixed by adding correct dependencies to build.gradle. Used alot of time on this so had to get a bit of AI help (Copilot, ChatGpt).

Another issue was that the IDE still didnt recognize the annotations even though the build succeded. 
This was solved by cleaning the Gradle cache and refreshing the project manually. 

Also had to make sure field names was aligned, "caption", "createdBy", "options", "email". 

## Database
Used the in memory h" database. 
Hibernate automatically generated the tables based on the JPA annotations

- users
- polls
- vote_options
- votes

# pending issues
no major issues remain, but probably have to circle back to this, and try it less pain free. 