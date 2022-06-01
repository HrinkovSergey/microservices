package com.home.company.dtogetter;

public interface DtoGetter<T, I> {
    T getDtoFromExternalResource(I arg);
}
