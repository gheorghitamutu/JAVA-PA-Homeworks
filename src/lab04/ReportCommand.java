package lab04;

import java.util.ArrayList;

public class ReportCommand implements Command {
    private Catalog catalog;
    ReportCommand(Catalog catalog){
        this.catalog = catalog;
    }

    public void execute(ArrayList<String> data) {
        String reportPath = data.get(1).replace("\"", "");
        String reportType = data.get(0).split(" ")[1].toLowerCase();

        reportPath = reportPath.endsWith("." + reportType) ? reportPath : reportPath + "." + reportType;

        switch(reportType){
            case "html":
                new HTMLReport(catalog, reportPath).create();
                break;
            case "pdf":
                new DynamicReport(catalog, reportType, reportPath).create();
                break;
            case "xls":
                new DynamicReport(catalog, reportType, reportPath).create();
                break;
            case "csv":
                new DynamicReport(catalog, reportType, reportPath).create();
                break;
            case "docx":
                new DynamicReport(catalog, reportType, reportPath).create();
                break;
            default:
                System.out.println("This format is not supported!");
        }
    }
}
