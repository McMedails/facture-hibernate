package com.medails.service;

import java.util.Optional;

@FunctionalInterface
public interface Finder<T> 
{
    Optional<T> find(String name);
}