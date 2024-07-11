package com.vicksanchez.challenge_literalura.services;

public interface IConvertData {
    <T> T getData(String json, Class<T> clase);
}
