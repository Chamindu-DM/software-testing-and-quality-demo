package quality_metrics;

/**
 * A simple utility class to calculate quality metrics like Defect Density.
 *
 * @author Gemini
 * @version 1.0
 */
public class DefectDensityCalculator {

    /**
     * Calculates the Defect Density of a software project.
     * Defect Density = (Total Defects / Project Size)
     * Project size is typically measured in KLOC (Kilo Lines of Code).
     *
     * @param totalDefects The total number of defects found.
     * @param linesOfCode The total lines of code in the project.
     * @return The defect density (defects per KLOC).
     */
    public double calculateDefectDensity(int totalDefects, double linesOfCode) {
        if (linesOfCode <= 0) {
            return 0.0;
        }
        double kloc = linesOfCode / 1000.0;
        return totalDefects / kloc;
    }

    /**
     * Calculates the Mean Time To Failure (MTTF).
     * MTTF = Total Uptime / Number of Failures
     * This is a simplified model based on test cycles.
     *
     * @param totalTestHours The total hours the application was tested.
     * @param numberOfFailures The number of failures (defects) found during testing.
     * @return The MTTF in hours.
     */
    public double calculateMTTF(double totalTestHours, int numberOfFailures) {
        if (numberOfFailures <= 0) {
            return totalTestHours; // Or a very high number to indicate high reliability
        }
        return totalTestHours / numberOfFailures;
    }
}
