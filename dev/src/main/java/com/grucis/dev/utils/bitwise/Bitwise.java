package com.grucis.dev.utils.bitwise;

public abstract interface Bitwise<T>
{
  public abstract T and(T paramT);

  public abstract T or(T paramT);

  public abstract T xor(T paramT);

  public abstract T not();

  public abstract boolean hasBitmask(T paramT);

  public abstract T addBitmask(T paramT);

  public abstract T removeBitmask(T paramT);
}