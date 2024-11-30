import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scoreboard.Match;
import org.scoreboard.Scoreboard;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreboardTest {

    private Scoreboard scoreboard;

    @BeforeEach
    public void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void testScoreboardInitialization() {
        assertTrue(scoreboard.getSortedMatchesInProgress().isEmpty(), "Scoreboard should start empty");
    }

    @Test
    void testStartMatch() {
        scoreboard.startMatch("Mexico", "Canada");

        Match match = scoreboard.getSortedMatchesInProgress().get(0);
        assertEquals("Mexico", match.getHomeTeam(), "Home team should be Mexico");
        assertEquals("Canada", match.getAwayTeam(), "Home team should be Canada");
        assertEquals(0, match.getHomeScore(), "Initial home score should be 0");
        assertEquals(0, match.getAwayScore(), "Initial away score should be 0");
    }

    @Test
    void testSameTeamInMultipleMatches() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.startMatch("Greece", "Mexico");

        List<Match> matches = scoreboard.getSortedMatchesInProgress();
        assertEquals(1, matches.size());
        Match match = matches.get(0);
        assertEquals("Mexico", match.getHomeTeam(), "A team should not be in more than one match");
    }

    @Test
    void testEmptyTeamNames() {
        scoreboard.startMatch("", "Canada");
        assertEquals(0, scoreboard.getSortedMatchesInProgress().size(), "Team name cannot be empty");

        scoreboard.startMatch("Mexico", null);
        assertEquals(0, scoreboard.getSortedMatchesInProgress().size(), "Team name cannot be null");
    }

    @Test
    void testSameTeamForHomeAndAway() {
        scoreboard.startMatch("Mexico", "Mexico");

        assertEquals(0, scoreboard.getSortedMatchesInProgress().size(), "Match should not start with the same team for home and away");
    }

    @Test
    public void testUpdateScore() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 2, 3);

        Match match = scoreboard.getSortedMatchesInProgress().get(0);
        assertEquals(2, match.getHomeScore(), "Home score should be 2");
        assertEquals(3, match.getAwayScore(), "Away score should be 3");
    }

}
