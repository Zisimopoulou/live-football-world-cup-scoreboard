package org.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private final List<Match> matches;

    public Scoreboard() {
        this.matches = new ArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        if (isTeamNotInAnyMatch(homeTeam) && isTeamNotInAnyMatch(awayTeam))) {
            Match newMatch = new Match(homeTeam, awayTeam);
            matches.add(newMatch);
        }
    }

    public List<Match> getSortedMatchesInProgress() {
        return new ArrayList<>(matches);
    }

    private boolean isTeamNotInAnyMatch(String team) {
        return matches.stream().noneMatch(match -> match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team));
    }
}
