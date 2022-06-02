package com.home.user.dtogetter;

public interface DtoGetter<T, I> {
    T getDtoFromExternalResource(I arg);
}
