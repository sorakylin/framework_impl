package com.skypyb.dao;

import com.skypyb.core.ann.Select;

public interface TwoTestDao {

    @Select("SELECT name FROM user WHERE id = 2")
    String query();
}
