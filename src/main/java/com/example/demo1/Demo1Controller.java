package com.example.demo1;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import com.google.appengine.api.datastore.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class Demo1Controller {

    private  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @PostMapping("/add")
    public User add(@RequestBody User user) {

        Entity employee = new Entity("Email");
        employee.setProperty("name", user.name);
        employee.setProperty("email", user.email);

        datastore.put(employee);

        return user;

    }

    @GetMapping("/find")
    public List<Entity> findAll() {
        Query q =
                new Query("Email");

        PreparedQuery pq = datastore.prepare(q);
        List<Entity> result = pq.asList(FetchOptions.Builder.withLimit(100));

        return result;
    }

    @GetMapping("/find_by_name/{name}")
    public Entity findbyname(@PathVariable String name) {
        Query.Filter propertyFilter =
                new Query.FilterPredicate("name", Query.FilterOperator.EQUAL, name);
        Query q = new Query("Email").setFilter(propertyFilter);
        PreparedQuery pq = datastore.prepare(q);
        Entity result = pq.asSingleEntity();

        return result;
    }
    @GetMapping("/find_by_email/{email}")
    public Entity findbyemail(@PathVariable String email) {
        Query.Filter propertyFilter =
                new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query q = new Query("Email").setFilter(propertyFilter);
        PreparedQuery pq = datastore.prepare(q);
        Entity result = pq.asSingleEntity();

        return result;
    }
    @PostMapping("/loggin")
    public Entity loggin(@RequestBody User user) {
        Query.Filter propertyFilter =
                new Query.CompositeFilter(Query.CompositeFilterOperator.AND, Arrays.asList(
                new Query.FilterPredicate("name", Query.FilterOperator.EQUAL, user.name),
                        new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, user.email)));
        Query q = new Query("Email").setFilter(propertyFilter);
        PreparedQuery pq = datastore.prepare(q);
        Entity result = pq.asSingleEntity();

        return result;
    }

    @PostMapping("/update/{id}")
    public User update(@PathVariable long id,@RequestBody User user) {
        Entity employee = new Entity("Employee", id);
        employee.setProperty("name", user.name);
        employee.setProperty("email", user.email);

        datastore.put(employee);
        return user;
    }
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        Key k1 = KeyFactory.createKey("Email", id);
        datastore.delete(k1);
    }
}
