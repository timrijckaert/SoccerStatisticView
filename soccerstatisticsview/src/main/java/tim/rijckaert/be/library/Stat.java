package tim.rijckaert.be.library;

/**
 * Created by trijckaert on 09/06/16.
 */
public class Stat {

    private final int sum;
    private String label;
    private final String homeTeamName;
    private int homeTeamValue;
    private final String awayTeamName;
    private int awayTeamValue;
    private Unit unit;

    public Stat(final String label,
                final String homeTeamName,
                final int homeTeamValue,
                final String awayTeamName,
                final int awayTeamValue,
                final boolean isPercentage) {
        this.label = label;
        this.homeTeamName = homeTeamName;
        this.homeTeamValue = homeTeamValue;
        this.awayTeamName = awayTeamName;
        this.awayTeamValue = awayTeamValue;
        this.sum = homeTeamValue + awayTeamValue;
        this.unit = isPercentage ? Unit.PERCENTAGE : Unit.ABSOLUTE;
        if (isPercentage && sum != 100 && sum != 0) {
            this.homeTeamValue = this.homeTeamValue * 100 / sum;
            this.awayTeamValue = 100 - this.homeTeamValue;
        }
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
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

    enum Unit {
        PERCENTAGE("%", "percent"),
        ABSOLUTE("", "");

        private final String extension;
        private final String accessibilityText;

        Unit(final String extension, final String accessibilityText) {
            this.extension = extension;
            this.accessibilityText = accessibilityText;
        }

        public String getExtension() {
            return extension;
        }

        public String getAccessibilityText() {
            return accessibilityText;
        }
    }
}
