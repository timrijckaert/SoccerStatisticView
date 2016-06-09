package tim.rijckaert.be.library;

/**
 * Created by trijckaert on 09/06/16.
 */
public class Stat {

    enum Unit {
        PERCENTAGE("%"),
        ABSOLUTE("");

        private final String extension;

        Unit(final String extension) {
            this.extension = extension;
        }

        public String getExtension() {
            return extension;
        }
    }

    private final int sum;
    private String label;
    private int homeTeamValue;
    private int awayTeamValue;
    private Unit unit;

    public Stat(final String label, final int homeTeamValue, final int awayTeamValue, final boolean isPercentage) {
        this.label = label;
        this.homeTeamValue = homeTeamValue;
        this.awayTeamValue = awayTeamValue;
        this.sum = homeTeamValue + awayTeamValue;
        this.unit = isPercentage ? Unit.PERCENTAGE : Unit.ABSOLUTE;
    }

    public String getLabel() {
        return label;
    }

    public int getHomeTeamValue() {
        return homeTeamValue;
    }

    public int getAwayTeamValue() {
        return awayTeamValue;
    }

    public Unit getUnit() {
        return unit;
    }

    public int getSum() {
        return sum;
    }
}
