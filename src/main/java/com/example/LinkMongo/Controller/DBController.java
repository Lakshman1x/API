package com.example.LinkMongo.Controller;

import com.example.LinkMongo.Model.Person;
import com.example.LinkMongo.Repo.MyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/")
public class DBController {

    @Autowired
    MyRepo repo;

    @GetMapping("list")
    public  ResponseEntity<List<Person>> getList(){
        return  new ResponseEntity<List<Person>>(repo.findAll(),HttpStatus.OK);
    }

    @GetMapping("get/{name}")
    public ResponseEntity<Optional<Person>>getPerson(@PathVariable("name") String name) {
        return new ResponseEntity<Optional<Person>>(repo.findById(name),HttpStatus.OK);
    }

    @PostMapping("add")
    public  Person addPerson(@RequestBody Person userInput){
        Optional<Person> res =  repo.findById(userInput.getFirstName());
        if(res.isPresent()){return  null;}
        return repo.insert(userInput);
    }


    @PutMapping("update")
    public Person updatePerson(@RequestBody Person userInput){
        Optional<Person> res=repo.findById(userInput.getFirstName());
        if(res.isPresent()){
            return  repo.save((userInput));
        }
        return  null;
    }

    @PostMapping("delete")
    public Boolean deletePerson(@RequestBody Person userInput){
        Optional<Person> res= repo.findById(userInput.getFirstName());

        if(res.isPresent() && (res.get()).equals(userInput)){
            repo.delete(userInput);
            return  true;
        }
        return  false;
    }

    @DeleteMapping("delete/{name}")
    public Boolean deletePersonById(@PathVariable ("name") String name){
        if(repo.findById(name).isPresent()){
            repo.delete(new Person(name,""));
            return  true;
        }
        return  false;
    }

}
