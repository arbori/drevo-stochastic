package drevo.stochastic;

public enum ProblemType {
    MINIMIZE(1.0), MAXIMIZE(-1.0);

    private double value;

    ProblemType(double value) { this.value = value; }

    public double valueOf() { return value; }
}
