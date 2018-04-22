package com.example.ndiaz.parquesbsas.model;

public class ParqueLikeBody {

    private int id_parque;
    private boolean increaseLike;
    private boolean decreaseLike;
    private boolean increaseHate;
    private boolean decreaseHate;

    public ParqueLikeBody(int id_parque, boolean increaseLike, boolean decreaseLike, boolean increaseHate, boolean decreaseHate) {
        this.id_parque = id_parque;
        this.increaseLike = increaseLike;
        this.decreaseLike = decreaseLike;
        this.increaseHate = increaseHate;
        this.decreaseHate = decreaseHate;
    }

    public int getId_parque() {
        return id_parque;
    }

    public boolean isIncreaseLike() {
        return increaseLike;
    }

    public boolean isDecreaseLike() {
        return decreaseLike;
    }

    public boolean isIncreaseHate() {
        return increaseHate;
    }

    public boolean isDecreaseHate() {
        return decreaseHate;
    }
}
