package hello.hellospring.service;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import javax.persistence.*;
import java.util.Set;


@Entity
class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    @OneToMany(mappedBy="person")
    private Set<Pet> pets;

    public String getFirstName() {
        return firstName;
    }

    public Set<Pet> getPets() {
        //FIXME
        return null;
    }

}

@Entity
class Pet {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public String getOwnerName() {
        //FIXME
        return person.getFirstName();
    }

    public String getName() {
        return name;
    }

    public Pet() {

    }
    public Pet(String name) {
        this.name = name;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}

class PersonNotFoundException extends RuntimeException {

}

@Service
class PersonService {

    private final EntityManager entityManager;

    public PersonService(EntityManager em) {
        this.entityManager = em;
    }
    public void addPet(Long personId, String petName) {

        Person person = entityManager.find(Person.class, personId);
        if (person == null) {
            throw new PersonNotFoundException();
        } else {
            Pet pet = new Pet(petName);
            pet.setPerson(person);
            entityManager.persist(pet);
        }
    }
}
