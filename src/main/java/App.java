import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.json.JsonParser;
import org.sql2o.*;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public boolean createEngineerFormView = false;
    public boolean createdEngineerFeedback = false;
    public static void main(String[] args) {

        staticFileLocation("/public");

        get("/",(req,res) -> {
            Map<String,Object> model  = new HashMap<>();
            List<Engineer> engineers = Engineer.all();
            model.put("engineers",engineers);
            model.put("engineers",engineers);
            model.put("createEngineerFormView",false);
            model.put("createdEngineerFeedback",false);
            return new ModelAndView(model,"engineer.hbs");
        }, new HandlebarsTemplateEngine());

        get("/engineers",(req,res) -> {
            Map<String,Object> model  = new HashMap<>();
            List<Engineer> engineers = Engineer.all();
            model.put("engineers",engineers);
            model.put("createEngineerFormView",true);
            model.put("createdEngineerFeedback",false);
            return new ModelAndView(model,"engineer.hbs");
        }, new HandlebarsTemplateEngine());

        post("/engineer/add",(req,res) -> {
            Map<String,Object> model  = new HashMap<>();
            List<Engineer> engineers = Engineer.all();
            model.put("engineers",engineers);
            String names = req.queryParams("engineerNames");
            Engineer engineer  =new Engineer(names);
            engineer.save();


            model.put("createdEngineerFormView",true);
            model.put("createdEngineerFeedback",true);
            model.put("engNames",names);

            return new ModelAndView(model,"engineer.hbs");
        }, new HandlebarsTemplateEngine());



    }
}
