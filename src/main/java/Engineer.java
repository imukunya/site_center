import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Objects;

public class Engineer implements DatabaseManagement {

    int eng_id;
    String eng_names;

    public Engineer(String eng_names) {
        this.eng_names = eng_names;
    }

    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO engineers(eng_names) VALUES(:eng_names)";
            this.eng_id = (int) con.createQuery(sql, true)
                    .addParameter("eng_names", this.eng_names)
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM engineers WHERE eng_id = :eng_id;";
            con.createQuery(sql)
                    .addParameter("id", this.eng_id)
                    .executeUpdate();
        }
    }

    @Override
    public void update() {

        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE engineers SET eng_names = eng_names WHERE eng_id = :id";
            con.createQuery(sql)
                    .addParameter("eng_names", this.eng_names)
                    .addParameter("eng_id", this.eng_id)
                    .executeUpdate();
        }

    }

    @Override
    public void deleteById() {
        String sql = "DELETE from engineers WHERE eng_id=:eng_id"; //raw sql
        try(Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("eng_id", this.eng_id)
                    .executeUpdate();
        }

    }

    public static Engineer findById(int id) {
            try(Connection con = DB.sql2o.open()) {
                String sql = "SELECT * FROM engineers where eng_id=:id";
                Engineer engineer = con.createQuery(sql)
                        .addParameter("id", id)
                        .throwOnMappingFailure(false)
                        .executeAndFetchFirst(Engineer.class);
                return engineer;
            }
    }


    public static List<Engineer> all(){
        String sql = "SELECT * FROM engineers";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Engineer.class);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engineer engineer = (Engineer) o;
        return eng_id == engineer.eng_id &&
                Objects.equals(eng_names, engineer.eng_names);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eng_id, eng_names);
    }


}
