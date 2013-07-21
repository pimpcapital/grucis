package com.grucis.dev.utils.bitwise;

import java.io.Serializable;

public class Int8 extends Number
  implements Serializable, Comparable, Bitwise<Int8>
{
  public static final byte MAX_VALUE = 127;
  public static final byte MIN_VALUE = -128;
  private byte int8;

  public Int8(byte paramByte)
  {
    this.int8 = paramByte;
  }

  public Int8(short paramShort)
  {
    this.int8 = ((byte)paramShort);
  }

  public Int8(int paramInt)
  {
    this.int8 = ((byte)paramInt);
  }

  public Int8(long paramLong)
  {
    this.int8 = ((byte)(int)paramLong);
  }

  public byte int8Value()
  {
    return byteValue();
  }

  public int intValue()
  {
    return this.int8;
  }

  public long longValue()
  {
    return this.int8;
  }

  public float floatValue()
  {
    return this.int8;
  }

  public double doubleValue()
  {
    return this.int8;
  }

  public int compareTo(Object paramObject)
  {
    if (equals(paramObject))
      return 0;
    return byteValue() - ((Int8)paramObject).byteValue();
  }

  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof Int8)) && (((Int8)paramObject).byteValue() == byteValue());
  }

  public int hashCode()
  {
    return intValue();
  }

  public String toString()
  {
    return Byte.toString(this.int8);
  }

  public Int8 and(Int8 paramInt8)
  {
    return new Int8(int8Value() & paramInt8.int8Value());
  }

  public Int8 not()
  {
    return new Int8(~int8Value());
  }

  public Int8 or(Int8 paramInt8)
  {
    return new Int8(int8Value() | paramInt8.int8Value());
  }

  public Int8 xor(Int8 paramInt8)
  {
    return new Int8(int8Value() ^ paramInt8.int8Value());
  }

  public Int8 addBitmask(Int8 paramInt8)
  {
    return or(paramInt8);
  }

  public boolean hasBitmask(Int8 paramInt8)
  {
    return and(paramInt8).equals(paramInt8);
  }

  public Int8 removeBitmask(Int8 paramInt8)
  {
    return and(paramInt8.not());
  }
}
