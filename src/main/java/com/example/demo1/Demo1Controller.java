package com.example.demo1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.google.cloud.datastore.*;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import java.util.ArrayList;
import java.util.List;


@Controller
public class Demo1Controller {

    private  Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    @PostMapping("/add")
    public User add(@RequestBody User user) {

        Key taskKey = datastore.newKeyFactory().setKind("Email").newKey(user.email);
        Entity task =
                Entity.newBuilder(taskKey)
                        .set("name", user.name)
                        .set("email", user.email)
                        .build();

        datastore.put(task);

        return user;

    }

    @GetMapping("/find")
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        Query<Entity> query =
                Query.newEntityQueryBuilder()
                        .setKind("Email")
                        //.setFilter(PropertyFilter.eq("email", "bot3@gmail.com"))
                        .build();

        QueryResults<Entity> tasks = datastore.run(query);
        while (tasks.hasNext()) {
            Entity element = tasks.next();
            User user = new User();
            user.name = element.getString("name");
            user.email = element.getString("email");

            list.add(user);
        }
        return list;
    }

    @GetMapping("/find_by_name/{name}")
    public List<User> findbyname(@PathVariable String name) {
        List<User> list = new ArrayList<>();
        Query<Entity> query =
                Query.newEntityQueryBuilder()
                        .setKind("Email")
                        .setFilter(PropertyFilter.eq("name", name))
                        .build();

        QueryResults<Entity> tasks = datastore.run(query);
        User user = new User();

        while (tasks.hasNext()) {
            Entity element = tasks.next();

            user.name = element.getString("name");
            user.email = element.getString("email");

            list.add(user);
        }
        return list;
    }
    @GetMapping("/find_by_email/{email}")
    public List<User> findbyemail(@PathVariable String email) {
        List<User> list = new ArrayList<>();
        Query<Entity> query =
                Query.newEntityQueryBuilder()
                        .setKind("Email")
                        .setFilter(PropertyFilter.eq("email", email))
                        .build();

        QueryResults<Entity> tasks = datastore.run(query);
        while (tasks.hasNext()) {
            Entity element = tasks.next();
            User user = new User();
            user.name = element.getString("name");
            user.email = element.getString("email");

            list.add(user);
        }
        return list;
    }
    @PostMapping("/loggin")
    public List<User> loggin(@RequestBody User user) {
        List<User> list = new ArrayList<>();
        Query<Entity> query =
                Query.newEntityQueryBuilder()
                        .setKind("Email")

                        .setFilter(
                                CompositeFilter.and(
                                        PropertyFilter.eq("email", user.email)
                                        ,PropertyFilter.eq("name", user.name)))
                        .build();

        QueryResults<Entity> tasks = datastore.run(query);
        while (tasks.hasNext()) {
            Entity element = tasks.next();
            User user1 = new User();
            user1.name = element.getString("name");
            user1.email = element.getString("email");

            list.add(user1);
        }
        return list;
    }

    @PostMapping("/update/{email}")
    public User update(@PathVariable String email,@RequestBody User user) {
        List<User> list = new ArrayList<>();
        Query<Entity> query =
                Query.newEntityQueryBuilder()
                        .setKind("Email")
                        .setFilter(PropertyFilter.eq("email", user.email))
                        .build();

        QueryResults<Entity> tasks = datastore.run(query);
        while (tasks.hasNext()) {
            return new User();
        }
        Key taskKey = datastore.newKeyFactory().setKind("Email").newKey(email);
        Entity task =
                Entity.newBuilder(taskKey)
                        .set("name", user.name)
                        .set("email", user.email)
                        .build();
        datastore.put(task);
        return user;
    }
    @GetMapping("/delete/{email}")
    public void delete(@PathVariable String email) {
        Key taskKey = datastore.newKeyFactory().setKind("Email").newKey(email);
        datastore.delete(taskKey);
    }
}
