**Live Football World Cup Scoreboard**

**Overview**
This project implements a Live Football World Cup Scoreboard, which allows you to manage and track ongoing football matches. The scoreboard supports operations to start new matches, update scores, finish matches, and retrieve a summary of all active matches ordered by their total score. Matches with identical scores are sorted by their start time.

**Features**
- Start a new match with an initial score of 0-0.
- Update the score of an ongoing match.
- Finish a match and remove it from the scoreboard.
- Retrieve a sorted list of matches based on their total score and start time.

**Classes**
- Scoreboard: Manages the list of matches and handles operations such as starting, updating, and finishing matches.
- Match: Represents an individual match, including home and away teams, scores, and the start time.
- MatchNotFoundException: Exception thrown when trying to update or finish a match that doesn't exist.

**Requirements**
- JUnit 5 for testing

**Design Decisions and Assumptions**
- The solution includes validation to prevent invalid team names, such as empty or null values, and ensures that a team cannot participate in more than one match simultaneously.
- The solution ensures that only positive scores are allowed, and throws an exception if an invalid score is provided.
- The solution ensures that matches cannot be updated or finished if they do not exist.
- Matches are sorted first by total score and then by start time if the scores are identical.
- The data are stored only during the runtime of the application. No persistent storage (e.g., database) is used.
- The teams are represented by their names as strings, and each match involves exactly two teams.
