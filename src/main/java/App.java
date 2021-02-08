import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        get("/engineer/edit/:eng_id",(req,res) -> {
            Map<String,Object> model  = new HashMap<>();
            List<Engineer> engineers = Engineer.all();
            model.put("engineers",engineers);
            String eng_id = req.params("eng_id");
            Engineer engineer = Engineer.findById(Integer.parseInt(eng_id));
            model.put("editEngineer",engineer);


            model.put("createdEngineerFormView",false);
            model.put("createdEngineerFeedback",false);

            model.put("editEngineerFormView",true);
            model.put("editEngineerFeedback",false);




            return new ModelAndView(model,"engineer.hbs");
        }, new HandlebarsTemplateEngine());

        post("/engineer/update",(req,res) -> {
            Map<String,Object> model  = new HashMap<>();
            List<Engineer> engineers = Engineer.all();
            model.put("engineers",engineers);
            String names = req.queryParams("engineerNames");
            int eng_id = Integer.parseInt(req.queryParams("eng_id"));
            System.out.print(eng_id);
            Engineer engineer  =Engineer.findById(eng_id);
            engineer.setEng_names(names);
            engineer.setEng_id(eng_id);
            engineer.update();


            model.put("createdEngineerFormView",false);
            model.put("createdEngineerFeedback",false);

            model.put("editEngineerFormView",true);
            model.put("editEngineerFeedback",true);

            return new ModelAndView(model,"engineer.hbs");
        }, new HandlebarsTemplateEngine());

        get("/engineer/delete/:eng_id",(req,res) -> {
            Map<String,Object> model  = new HashMap<>();
            List<Engineer> engineers = Engineer.all();
            model.put("engineers",engineers);
            int eng_id = Integer.parseInt(req.params("eng_id"));
            System.out.print(eng_id);
            Engineer deleteEngineer  =Engineer.findById(eng_id);
            model.put("deleteEngineer",deleteEngineer);



            model.put("createdEngineerFormView",false);
            model.put("createdEngineerFeedback",false);

            model.put("editEngineerFormView",false);
            model.put("editEngineerFeedback",false);
            model.put("deleteEngineerConfirm",true);

            return new ModelAndView(model,"engineer.hbs");
        }, new HandlebarsTemplateEngine());

        post("/engineer/delete",(req,res) -> {
            Map<String,Object> model  = new HashMap<>();
            List<Engineer> engineers = Engineer.all();
            model.put("engineers",engineers);
            int eng_id = Integer.parseInt(req.queryParams("eng_id"));
            System.out.print(eng_id);
            Engineer deleteEngineer  =Engineer.findById(eng_id);
            deleteEngineer.delete();



            model.put("createdEngineerFormView",false);
            model.put("createdEngineerFeedback",false);

            model.put("editEngineerFormView",false);
            model.put("editEngineerFeedback",false);
            model.put("deleteEngineerConfirm",false);

            model.put("deleteEngineerFeedback",true);
            model.put("deleteEngineerFeedbackMSG","Engineer deleted");

            return new ModelAndView(model,"engineer.hbs");
        }, new HandlebarsTemplateEngine());


        /**
         * sites
         */
        get("/sites",(req,res) -> {
            Map<String,Object> model  = new HashMap<>();
            List<Site> sites = Site.all();
            model.put("sites",sites);
            model.put("createSiteFormView",true);
            model.put("createdSiteFeedback",false);
            return new ModelAndView(model,"site.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sites/add",(req,res) -> {
            Map<String,Object> model  = new HashMap<>();
            List<Site> sites = Site.all();
            model.put("sites",sites);

            String siteNames = req.queryParams("siteNames");
            String siteLocation = req.queryParams("siteLocation");
            String siteNotes = req.queryParams("siteNotes");
            String siteEngineer = req.queryParams("siteEngineer");

            Site site  =new Site(siteNames);
            site.save();

            //save the engineer site references
            site.saveSiteEngineer(site.site_id, Integer.parseInt(siteEngineer));

            //save site notes



            model.put("createSiteFormView",true);
            model.put("createdSiteFeedback",true);
            model.put("engNames",siteNames);

            return new ModelAndView(model,"engineer.hbs");
        }, new HandlebarsTemplateEngine());


    }
}
