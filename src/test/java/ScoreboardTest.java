import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scoreboard.Match;
import org.scoreboard.MatchNotFoundException;
import org.scoreboard.Scoreboard;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {

    private Scoreboard scoreboard;

    @BeforeEach
    public void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void getMatches_GivenNoMatchesInProgress_ShouldBeEmpty() {
        assertTrue(scoreboard.getSortedMatchesInProgress().isEmpty(), "Scoreboard should start empty");
    }

    @Test
    void startMatch_GivenTeams_ShouldInitializeScoresToZero() {
        scoreboard.startMatch("Mexico", "Canada");

        Match match = scoreboard.getSortedMatchesInProgress().get(0);
        assertEquals("Mexico", match.getHomeTeam(), "Home team should be Mexico");
        assertEquals("Canada", match.getAwayTeam(), "Home team should be Canada");
        assertEquals(0, match.getHomeScore(), "Initial home score should be 0");
        assertEquals(0, match.getAwayScore(), "Initial away score should be 0");
    }

    @Test
    void startMatch_GivenTeamAlreadyInProgress_ShouldNotAllowSameTeamInMultipleMatches() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.startMatch("Greece", "Mexico");

        List<Match> matches = scoreboard.getSortedMatchesInProgress();
        assertEquals(1, matches.size());
        Match match = matches.get(0);
        assertEquals("Mexico", match.getHomeTeam(), "A team should not be in more than one match");
    }

    @Test
    void startMatch_GivenEmptyOrNullTeamNames_MatchShouldNotStart() {
        scoreboard.startMatch("", "Canada");
        assertEquals(0, scoreboard.getSortedMatchesInProgress().size(), "Team name cannot be empty");

        scoreboard.startMatch("Mexico", null);
        assertEquals(0, scoreboard.getSortedMatchesInProgress().size(), "Team name cannot be null");
    }

    @Test
    void startMatch_GivenSameTeamForHomeAndAway_MatchShouldNotStart() {
        scoreboard.startMatch("Mexico", "Mexico");

        assertEquals(0, scoreboard.getSortedMatchesInProgress().size(), "Match should not start with the same team for home and away");
    }

    @Test
    public void updateScore_GivenScores_ShouldUpdateCorrectly() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 2, 3);

        Match match = scoreboard.getSortedMatchesInProgress().get(0);
        assertEquals(2, match.getHomeScore(), "Home score should be 2");
        assertEquals(3, match.getAwayScore(), "Away score should be 3");
    }

    @Test
    void updateScore_MatchDoesNotExist_throwsException() {
        assertThrows(MatchNotFoundException.class, () -> {
            scoreboard.updateScore("Mexico", "Canada", 2, 3);
        });
    }

    @Test
    void updateScore_GivenNegativeScores_throwsException() {
        scoreboard.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.updateScore("Mexico", "Canada", -1, 3);
        });
    }

    @Test
    public void finishMatch_GivenMatches_ShouldRemoveMatch() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.finishMatch("Mexico", "Canada");

        assertTrue(scoreboard.getSortedMatchesInProgress().isEmpty(), "Match should be removed");
    }

    @Test
    public void finishMatch_MatchDoesNotExist_throwsException() {
        assertThrows(MatchNotFoundException.class, () -> {
            scoreboard.finishMatch("Mexico", "Canada");
        });
    }

    @Test
    void getMatchesInProgress_GivenDifferentScores_ShouldSortByTotalScore() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> matches = scoreboard.getSortedMatchesInProgress();

        assertEquals("Spain 10 - 2 Brazil", matches.get(0).toString(), "Spain 10 - 2 Brazil should be listed first");
        assertEquals("Mexico 0 - 5 Canada", matches.get(1).toString(), "Mexico 0 - 5 Canada should be listed second");
        assertEquals("Argentina 3 - 1 Australia", matches.get(2).toString(), "Argentina 3 - 1 Australia should be listed last");
    }

    @Test
    void getMatchesInProgress_GivenIdenticalScores_ShouldSortByTotalScoreThenByStartTime() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);

        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);

        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> matches = scoreboard.getSortedMatchesInProgress();
        assertEquals("Uruguay 6 - 6 Italy", matches.get(0).toString(), "Uruguay 6 - 6 Italy should be listed first");
        assertEquals("Spain 10 - 2 Brazil", matches.get(1).toString(), "Spain 10 - 2 Brazil should be listed second");
        assertEquals("Mexico 0 - 5 Canada", matches.get(2).toString(), "Mexico 0 - 5 Canada should be listed third");
        assertEquals("Argentina 3 - 1 Australia", matches.get(3).toString(), "Argentina 3 - 1 Australia should be listed fourth");
        assertEquals("Germany 2 - 2 France", matches.get(4).toString(), "Germany 2 - 2 France should be listed last");
    }

}
