package com.skypyb.dao;

import com.skypyb.core.ann.Select;

public interface OneTestDao {

    @Select("SELECT name FROM user WHERE id = 1")
    String query();
}
