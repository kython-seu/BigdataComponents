package com.kason.film.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoadPropertiesTest {
    @Test
    public void getString() throws Exception {
        String string = LoadProperties.getString("download.url");
        System.out.println(string);
    }

}