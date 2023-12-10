package com.melelee.annotation;


import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resources;

@Transactional(readOnly = true)
@Resources({})
public abstract class Demo {

    @Transactional
    @SuppressWarnings({})
    public void getInfo() {

    }

    public abstract void setInfo();
}
