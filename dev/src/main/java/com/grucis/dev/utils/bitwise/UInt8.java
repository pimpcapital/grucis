package com.grucis.dev.utils.bitwise;

import java.io.Serializable;

public class UInt8 extends Number
  implements Serializable, Comparable, Bitwise<UInt8>
{
  public static final short MAX_VALUE = 255;
  public static final short MIN_VALUE = 0;
  private byte uint8;

  public UInt8(byte paramByte)
  {
    this.uint8 = paramByte;
  }

  public UInt8(short paramShort)
  {
    this.uint8 = ((byte)paramShort);
  }

  public UInt8(int paramInt)
  {
    this.uint8 = ((byte)paramInt);
  }

  public UInt8(long paramLong)
  {
    this.uint8 = ((byte)(int)paramLong);
  }

  public short uint8Value()
  {
    return (short)(this.uint8 & 0xFF);
  }

  public short shortValue()
  {
    return uint8Value();
  }

  public int intValue()
  {
    return uint8Value();
  }

  public long longValue()
  {
    return uint8Value();
  }

  public float floatValue()
  {
    return uint8Value();
  }

  public double doubleValue()
  {
    return uint8Value();
  }

  public int compareTo(Object paramObject)
  {
    if (equals(paramObject))
      return 0;
    return uint8Value() - ((UInt8)paramObject).uint8Value();
  }

  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof UInt8)) && (((UInt8)paramObject).uint8Value() == uint8Value());
  }

  public int hashCode()
  {
    return intValue();
  }

  public String toString()
  {
    return Short.toString(uint8Value());
  }

  public UInt8 and(UInt8 paramUInt8)
  {
    return new UInt8(uint8Value() & paramUInt8.uint8Value());
  }

  public UInt8 not()
  {
    return new UInt8(~uint8Value());
  }

  public UInt8 or(UInt8 paramUInt8)
  {
    return new UInt8(uint8Value() | paramUInt8.uint8Value());
  }

  public UInt8 xor(UInt8 paramUInt8)
  {
    return new UInt8(uint8Value() ^ paramUInt8.uint8Value());
  }

  public UInt8 addBitmask(UInt8 paramUInt8)
  {
    return or(paramUInt8);
  }

  public boolean hasBitmask(UInt8 paramUInt8)
  {
    return and(paramUInt8).equals(paramUInt8);
  }

  public UInt8 removeBitmask(UInt8 paramUInt8)
  {
    return and(paramUInt8.not());
  }
}

