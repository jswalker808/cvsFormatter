package result;

public class FormatRequest {

    private String csvString;

    public FormatRequest(String csvString) {
        this.csvString = csvString;
    }

    public String getCsvString() {
        return csvString;
    }

    public void setCsvString(String csvString) {
        this.csvString = csvString;
    }
}
