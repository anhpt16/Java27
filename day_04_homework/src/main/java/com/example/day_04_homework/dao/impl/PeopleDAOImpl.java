package com.example.day_04_homework.dao.impl;

import com.example.day_04_homework.dao.PeopleDAO;
import com.example.day_04_homework.database.PeopleDB;
import com.example.day_04_homework.model.People;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PeopleDAOImpl implements PeopleDAO {
    @Override
    public List<People> getAllPeople() {
        return PeopleDB.peoples;
    }
}
