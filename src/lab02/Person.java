/*
    Author: Mutu Gheorghita
*/

package lab02;

public abstract class Person {
    private String name;
    private String email;

    Person(String name, String email) {
        setName(name);
        setEmail(email);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object person) {
        if (person == null) {
            return false;
        }
        if (!Person.class.isAssignableFrom(person.getClass())) {
            return false;
        }
        final Person other = (Person) person;
        return ((this.name == null) ? (other.getName() == null) : this.name.equals(other.getName())) && this.email.equals(other.getEmail());
    }

    public abstract boolean isFree();
}
