package ee.risthein.erko.dokumendid.entities;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.List;

/**
 * @author Erko Risthein
 */
@Entity
@Table(name = "person")
public class Person {

    private Integer id;
    private String firstName;
    private String lastName;
    private List<Employee> employees;

    @Id
    @SequenceGenerator(name = "person_seq", sequenceName = "person_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @Column(name = "person")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "person")
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, firstName, lastName, employees);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        return Objects.equal(this.id, other.id) && Objects.equal(this.firstName, other.firstName) && Objects.equal(this.lastName, other.lastName) && Objects.equal(this.employees, other.employees);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .toString();
    }
}
