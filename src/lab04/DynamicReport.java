package lab04;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class DynamicReport implements Report {
    private Catalog catalog;
    private String reportPath;
    private JasperReportBuilder report = DynamicReports.report();
    private String reportType;

    DynamicReport(Catalog catalog, String reportType, String reportPath) {
        this.catalog = catalog;
        this.reportType = reportType;
        this.reportPath = reportPath;
    }

    public void create() {
        report
                .columns(
                        Columns.column("Title", "title", DataTypes.stringType()),
                        Columns.column("Path", "path", DataTypes.stringType()),
                        Columns.column("Year", "year", DataTypes.integerType()),
                        Columns.column("Authors", "authors", DataTypes.stringType()))
                .title(//title of the report
                        Components.text(catalog.getName() + " Report")
                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                .setDataSource(createDataSource());

        try {
            report.show();

            //export the report
            switch(this.reportType){
                case "pdf":
                    report.toPdf(new FileOutputStream(reportPath));
                    break;
                case "xls":
                    report.toXls(new FileOutputStream(reportPath));
                    break;
                case "csv":
                    report.toCsv(new FileOutputStream(reportPath));
                    break;
                case "docx":
                    report.toDocx(new FileOutputStream(reportPath));
                    break;
                default: // if it reaches this case, you re doing something wrong
                    break;
            }
        } catch (DRException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("title", "path", "year", "authors");
        List<Item> items = catalog.getItems();

        for(Item item: items){
            StringBuilder authors = new StringBuilder();
            for(String author: item.getAuthors()){
                authors.append(author).append(" ");
            }
            dataSource.add(item.getTitle(), item.getPath(), item.getYear(), authors.toString());
        }

        return dataSource;
    }
}