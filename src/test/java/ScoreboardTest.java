import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scoreboard.Scoreboard;

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
}
