package com.example.jobs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.hibernate.annotations.GenericGenerator;

import java.util.Iterator;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Employer implements User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    String id;
    String companyName;
    String uniqueCode;
    String street;
    String country;
    String city;
    String username;
    String email;
    String password;

    @Override
    public String getFullName() {
        return companyName;
    }

    @Override
    public void setFullName(String s) {

    }

    @Override
    public Iterator<Group> getGroups() {
        return null;
    }

    @Override
    public Iterator<Role> getRoles() {
        return null;
    }

    @Override
    public UserDatabase getUserDatabase() {
        return null;
    }

    @Override
    public void addGroup(Group group) {

    }

    @Override
    public void addRole(Role role) {

    }

    @Override
    public boolean isInGroup(Group group) {
        return false;
    }

    @Override
    public boolean isInRole(Role role) {
        return false;
    }

    @Override
    public void removeGroup(Group group) {

    }

    @Override
    public void removeGroups() {

    }

    @Override
    public void removeRole(Role role) {

    }

    @Override
    public void removeRoles() {

    }

    @Override
    public String getName() {
        return null;
    }
}
