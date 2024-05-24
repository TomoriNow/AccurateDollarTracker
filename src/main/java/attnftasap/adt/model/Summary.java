package attnftasap.adt.model;

public class Summary {
    private final SpendingReport spendingReport;

    public Summary(SpendingReport spendingReport) {
        this.spendingReport = spendingReport;
    }

    public int calculateRemainingBalance() {
        int totalExpectedSpending = spendingReport.getTotalExpectedSpending();
        int totalSpending = spendingReport.getTotalSpending();

        return totalExpectedSpending - totalSpending;
    }

}
