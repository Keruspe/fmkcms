package models.blog;

import javax.persistence.Entity;
import play.data.validation.Email;
import play.data.validation.MinSize;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 *
 * @author keruspe
 */
@Entity
public class User extends Model {

    @Required
    @Email
    public String email;

    // TODO: Is it encrypted ?
    @Required
    @Password
    @MinSize(6)
    public String password;

    @Required
    public boolean isAdmin = false;

    public String fullname;

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }

    @Override
    public String toString() {
        if (this.fullname == null || this.fullname.equals("")) {
            return this.email;
        }
        return this.fullname + " <" + this.email + ">";
    }

}
