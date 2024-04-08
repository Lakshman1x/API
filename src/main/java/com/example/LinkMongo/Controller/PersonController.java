package com.example.LinkMongo.Controller;

import com.example.LinkMongo.Model.Dto.UserInput;
import com.example.LinkMongo.Model.Pojo.Person;
import com.example.LinkMongo.Model.Dto.Response;
import com.example.LinkMongo.Repo.PersonRepo;
import com.example.LinkMongo.Services.PersonServices;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.LinkMongo.Services.Validator;


@RestController
@RequestMapping("/")
public class PersonController implements  Controller{
    private static final Logger logger = LogManager.getLogger(PersonController.class);
    PersonRepo repo;
    PersonServices services;

    @Autowired
    PersonController(PersonRepo repo, PersonServices services){
        this.repo=repo;
        this.services=services;
    }


    @GetMapping("list")
    public  ResponseEntity<List<Person>> getList(){
        logger.info("List of persons retrieved");
        return  new ResponseEntity<>(repo.findAll(),HttpStatus.OK);
    }


    @GetMapping("get/{name}")
    public ResponseEntity<?> getPerson(@PathVariable("name") String name) {
        Person person = services.getPerson(name);
        if (person != null) {
            logger.info("{} info retrieved", name);
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        logger.info("Person {} not found in database", name);
        return new ResponseEntity<>(new Response(false,"Person not found in database"),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("add")
    public ResponseEntity<Response> addPerson(@RequestBody @Valid UserInput userInput, BindingResult result) {
        Response res = Validator.getResponse(result);
        if(res.getStatus()){
            Person person = services.addPerson(userInput);
            if(person==null){
                res.setStatus(false);
                logger.info("Person already exists in the database");
                res.setMessage("Person already exists in the database");
                new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
            }else{
                logger.info("Person added to the database");
                res.setMessage("Person added to the database");
            }
        }
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
    }



    @PutMapping("update")
    public ResponseEntity<Response> updatePerson(@RequestBody @Valid UserInput userInput, BindingResult result ) {
        Response res = Validator.getResponse(result);
        if(res.getStatus()){
            Person person = services.updatePerson(userInput);
            if (person != null) {
                logger.info("{} information updated", person.getFirstName());
                res.setMessage("Details updated");
                return new ResponseEntity<>(res, HttpStatus.OK);
            }else{
                logger.info("Person {} not found in database", userInput.getFirstName());
                res.setMessage("Person "+ userInput.getFirstName() +" not found in database");
                res.setStatus(false);
                return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }



    @DeleteMapping("delete")
    public ResponseEntity<Response> deletePerson(@RequestBody @Valid UserInput userInput, BindingResult result) {
        Response res= Validator.getResponse(result);
        if(res.getStatus()){
            boolean delete = services.deletePerson(userInput);
            if(delete){
                logger.info("{} information deleted", userInput.getFirstName());
                res.setMessage("Details deleted");
                return new ResponseEntity<>(res, HttpStatus.OK);
            }else{
                logger.info("Person {} does not exist", userInput.getFirstName());
                res.setMessage("Person does not exist");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        }

        return  new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
    }


}
