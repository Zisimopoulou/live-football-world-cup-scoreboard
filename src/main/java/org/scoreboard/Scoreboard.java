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

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) throws MatchNotFoundException {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores must be positive integers");
        }

        Match match = findMatch(homeTeam, awayTeam);
        match.updateScore(homeScore, awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) throws MatchNotFoundException {
        Match match = findMatch(homeTeam, awayTeam);
        matches.remove(match);
    }

    public List<Match> getSortedMatchesInProgress() {
        List<Match> sortedMatches = new ArrayList<>(matches);
        sortedMatches.sort((m1, m2) -> {
            int scoreComparison = Integer.compare(
                    m2.getHomeScore() + m2.getAwayScore(),
                    m1.getHomeScore() + m1.getAwayScore()
            );

            if (scoreComparison != 0) {
                return scoreComparison;
            }

            int timeComparison = m2.getStartTime().compareTo(m1.getStartTime());

            if (timeComparison == 0) {
                return Integer.compare(m2.getSequenceNumber(), m1.getSequenceNumber());
            }

            return timeComparison;
        });
        return sortedMatches;
    }


    private Match findMatch(String homeTeam, String awayTeam) throws MatchNotFoundException {
        Match match = matches.stream().filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam)).findFirst().orElse(null);
        if (Objects.isNull(match)) {
            throw new MatchNotFoundException("Match not found between " + homeTeam + " and " + awayTeam);
        }
        return match;
    }

    private boolean isTeamNotInAnyMatch(String team) {
        return matches.stream().noneMatch(match -> match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team));
    }
}
