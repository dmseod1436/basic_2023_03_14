package com.ll.basic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

@Controller
public class HomeController2 {
    private int count;
    private List<Person> people;
    public HomeController2() {
        count = -1;
        people = new ArrayList<>();
    }

    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(String name, int age){
        Person p = new Person(name, age);

        System.out.println(p);

        people.add(p);

        return "%d번 사람이 추가되었습니다.".formatted(p.getId());
        }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showPeople() {
        return people;
    }
}
@AllArgsConstructor
@Getter
@ToString
class Person {
    private static int lastId;
    private final int id;
    private final String name;
    private final int age;

    static {
        lastId = 0;
    }

    public Person(String name, int age) {
        this(++lastId, name, age);
    }
}