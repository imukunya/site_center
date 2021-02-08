import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Objects;
public  class Site implements DatabaseManagement{
    int site_id;
    String  site_name;
    String site_location;


    public Site(String site_name,String site_location) {

        this.site_name = site_name;
        this.site_location = site_location;
    }

    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sites(site_name,site_location) VALUES(:site_name,:site_location)";
            this.site_id = (int) con.createQuery(sql, true)
                    .addParameter("site_name", this.site_name)
                    .addParameter("site_location", this.site_location)
                    .executeUpdate()
                    .getKey();
        }


    }

    @Override
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM sites WHERE site_id = :site_id;";
            con.createQuery(sql)
                    .addParameter("id", this.site_id)
                    .executeUpdate();
        }
    }

    @Override
    public void update() {

        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE sites SET site_name = site_name WHERE site_id = :id";
            con.createQuery(sql)
                    .addParameter("site_name", this.site_name)
                    .addParameter("site_id", this.site_id)
                    .executeUpdate();
        }

    }

    @Override
    public void deleteById() {
        String sql = "DELETE from sites WHERE site_id=:site_id"; //raw sql
        try(Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("site_id", this.site_id)
                    .executeUpdate();
        }

    }

    public static Site findById(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sites where site_id=:id";
            Site site = con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Site.class);
            return site;
        }
    }


    public static List<Site> all(){
        String sql = "SELECT * FROM sites";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Site.class);
        }
    }

    public void saveSiteEngineer(String site_id,String eng_id){
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO engineers_sites(site_id,eng_id) VALUES(:site_id,:eng_id)";
             con.createQuery(sql, true)
                    .addParameter("site_id", site_id)
                    .addParameter("eng_id", eng_id)
                    .executeUpdate()
                    .getKey();
        }
    }

    public void saveSiteNotes(String note, int site_id){
        Note n = new Note(note,site_id);
        n.save();
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getSite_location() {
        return site_location;
    }

    public void setSite_location(String site_location) {
        this.site_location = site_location;
    }
}
