package com.example.expensesharing.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity(name = "groupss")
public class Group extends BaseModel {
    private String name;
    private String description;
    @OneToMany
    private List<Expense> expenses;

    @ManyToMany
    private List<User> members;

    @ManyToOne
    private User admin;
}
