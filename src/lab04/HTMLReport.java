package lab04;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HTMLReport implements Report {
    private Catalog catalog;
    private String reportPath;

    HTMLReport(Catalog catalog, String reportPath){
        this.catalog = catalog;
        this.reportPath = reportPath;
    }

    public void create(){
        Configuration cfg = new Configuration();

        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.ROOT);

        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<>();

        input.put("title", "Items Catalog");
        input.put("catalog", this.catalog);

        input.put("items", catalog.getItems());

        try {
            FileTemplateLoader templateLoader = new FileTemplateLoader(new File("resources\\lab04\\catalogtemplates\\"));
            cfg.setTemplateLoader(templateLoader);
            Template template = cfg.getTemplate("cataloghtml.ftl");

            Writer fileWriter = new FileWriter(reportPath);
            template.process(input, fileWriter);
            fileWriter.close();
        }
        catch(TemplateException | IOException TIOE){
            System.out.println(TIOE.getMessage());
        }



    }
}
