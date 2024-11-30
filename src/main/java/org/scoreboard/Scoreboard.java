package org.scoreboard;

import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Scoreboard {

    private final List<Match> matches;

    public Scoreboard() {
        this.matches = new ArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        if (StringUtils.isNotBlank(homeTeam) && StringUtils.isNotBlank(awayTeam) && !Objects.equals(homeTeam, awayTeam) && isTeamNotInAnyMatch(homeTeam) && isTeamNotInAnyMatch(awayTeam)) {
            Match newMatch = new Match(homeTeam, awayTeam);
            matches.add(newMatch);
        }
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = matches.stream().filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam)).findFirst().orElse(null);
        if (Objects.nonNull(match)) {
            match.updateScore(homeScore, awayScore);
        }
    }

    public List<Match> getSortedMatchesInProgress() {
        return new ArrayList<>(matches);
    }

    private boolean isTeamNotInAnyMatch(String team) {
        return matches.stream().noneMatch(match -> match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team));
    }
}
