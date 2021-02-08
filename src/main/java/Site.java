import org.sql2o.Connection;

import java.util.List;

public abstract class Site implements DatabaseManagement{
    int site_id;
    String  site_name;

    public Site(String site_name) {
        this.site_name = site_name;
    }

    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sites(site_name) VALUES(:site_name)";
            this.site_id = (int) con.createQuery(sql, true)
                    .addParameter("site_name", this.site_name)
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


}
