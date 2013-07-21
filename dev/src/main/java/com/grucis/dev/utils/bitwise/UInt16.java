package com.grucis.dev.utils.bitwise;

import java.io.Serializable;

public class UInt16 extends Number
  implements Serializable, Comparable, Bitwise<UInt16>
{
  public static final int MAX_VALUE = 65535;
  public static final int MIN_VALUE = 0;
  private short uint16;

  public UInt16(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    this.uint16 = ((short)((paramArrayOfByte[0] & 0xFF) << 8 | paramArrayOfByte[1] & 0xFF));
  }

  public UInt16(short paramShort)
  {
    this.uint16 = paramShort;
  }

  public UInt16(int paramInt)
  {
    this.uint16 = ((short)paramInt);
  }

  public UInt16(long paramLong)
  {
    this.uint16 = ((short)(int)paramLong);
  }

  public static UInt16 valueOfBigEndian(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    return new UInt16(paramArrayOfByte);
  }

  public static UInt16 valueOfLittleEndian(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = paramArrayOfByte[1];
    arrayOfByte[1] = paramArrayOfByte[0];
    return new UInt16(arrayOfByte);
  }

  public int uint16Value()
  {
    return this.uint16 & 0xFFFF;
  }

  public int intValue()
  {
    return uint16Value();
  }

  public long longValue()
  {
    return uint16Value();
  }

  public float floatValue()
  {
    return uint16Value();
  }

  public double doubleValue()
  {
    return uint16Value();
  }

  public byte[] toBigEndian()
  {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = ((byte)(this.uint16 >> 8 & 0xFF));
    arrayOfByte[1] = ((byte)(this.uint16 & 0xFF));
    return arrayOfByte;
  }

  public byte[] toLittleEndian()
  {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = ((byte)(this.uint16 & 0xFF));
    arrayOfByte[1] = ((byte)(this.uint16 >> 8 & 0xFF));
    return arrayOfByte;
  }

  public int compareTo(Object paramObject)
  {
    if (equals(paramObject))
      return 0;
    return uint16Value() - ((UInt16)paramObject).uint16Value();
  }

  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof UInt16)) && (((UInt16)paramObject).uint16Value() == uint16Value());
  }

  public int hashCode()
  {
    return intValue();
  }

  public String toString()
  {
    return Integer.toString(uint16Value());
  }

  private static void verify(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    if (paramArrayOfByte == null)
      throw new IllegalArgumentException("word cannot be null.");
    if (paramArrayOfByte.length != 2)
      throw new IllegalArgumentException("Invalid length for byte array.  word must be two bytes long.");
  }

  public UInt16 and(UInt16 paramUInt16)
  {
    return new UInt16(uint16Value() & paramUInt16.uint16Value());
  }

  public UInt16 not()
  {
    return new UInt16(~uint16Value());
  }

  public UInt16 or(UInt16 paramUInt16)
  {
    return new UInt16(uint16Value() | paramUInt16.uint16Value());
  }

  public UInt16 xor(UInt16 paramUInt16)
  {
    return new UInt16(uint16Value() ^ paramUInt16.uint16Value());
  }

  public UInt16 addBitmask(UInt16 paramUInt16)
  {
    return or(paramUInt16);
  }

  public boolean hasBitmask(UInt16 paramUInt16)
  {
    return and(paramUInt16).equals(paramUInt16);
  }

  public UInt16 removeBitmask(UInt16 paramUInt16)
  {
    return and(paramUInt16.not());
  }
}
