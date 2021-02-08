import org.sql2o.Connection;

import java.util.Objects;

public class Note implements DatabaseManagement{

    String note;
    int note_id;
    int site_id;

    public Note(String note,int site_id) {
        this.note = note;
        this.site_id = site_id;
    }


    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO site_notes(note,site_id) VALUES(:site_note,:site_id)";
            this.note_id = (int) con.createQuery(sql, true)
                    .addParameter("site_note", this.note)
                    .addParameter("site_id", this.site_id)
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }

    @Override
    public void deleteById() {

    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return note_id == note1.note_id &&
                site_id == note1.site_id &&
                Objects.equals(note, note1.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(note, note_id, site_id);
    }
}
