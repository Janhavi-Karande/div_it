package com.example.expensesharing.models;

import jakarta.persistence.*;
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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> members;

    @ManyToOne
    private User admin;
}
