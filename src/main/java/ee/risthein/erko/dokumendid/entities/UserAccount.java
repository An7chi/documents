package ee.risthein.erko.dokumendid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Erko Risthein
 */
@Entity
@Table(name = "user_account")
public class UserAccount {

    private Integer id;
    private String username;
    private String password;
    private Date validFrom;
    private Date validTo;
    private Employee employee;

    @Id
    @SequenceGenerator(name = "user_account_seq", sequenceName = "user_account_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_account_seq")
    @Column(name = "user_account")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "passw")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @ManyToOne
    @JoinColumn(name = "subject_fk", referencedColumnName = "employee")
    @JsonIgnore
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Transient
    public boolean isEnabled() {
        Date currentDate = new Date();
        return currentDate.after(validFrom) && currentDate.before(validTo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, username, password, employee);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UserAccount other = (UserAccount) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.username, other.username)
                && Objects.equal(this.password, other.password)
                && Objects.equal(this.employee, other.employee);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("password", password)
                .add("employee", employee)
                .toString();
    }
}
