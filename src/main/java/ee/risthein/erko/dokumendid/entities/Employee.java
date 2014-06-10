package ee.risthein.erko.dokumendid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.List;

/**
 * @author Erko Risthein
 */
@Entity
@Table(name = "employee")
public class Employee {

    private Integer id;
    private Person person;
    private List<UserAccount> userAccounts;

    @Id
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @Column(name = "employee")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "person_fk", referencedColumnName = "person")
    @JsonIgnore
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @OneToMany(mappedBy = "employee")
    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(List<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, person, userAccounts);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        return Objects.equal(this.id, other.id) && Objects.equal(this.person, other.person) && Objects.equal(this.userAccounts, other.userAccounts);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("person", person)
                .toString();
    }
}
