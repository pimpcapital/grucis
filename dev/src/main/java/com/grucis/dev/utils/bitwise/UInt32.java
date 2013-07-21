package com.grucis.dev.utils.bitwise;

import java.io.Serializable;

public class UInt32 extends Number
  implements Serializable, Comparable, Bitwise<UInt32>
{
  public static final long MAX_VALUE = 4294967295L;
  public static final long MIN_VALUE = 0L;
  private int uint32;

  public UInt32(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    this.uint32 = ((paramArrayOfByte[0] & 0xFF) << 24 | (paramArrayOfByte[1] & 0xFF) << 16 | (paramArrayOfByte[2] & 0xFF) << 8 | paramArrayOfByte[3] & 0xFF);
  }

  public UInt32(short paramShort)
  {
    this.uint32 = paramShort;
  }

  public UInt32(int paramInt)
  {
    this.uint32 = paramInt;
  }

  public UInt32(long paramLong)
  {
    this.uint32 = ((int)paramLong);
  }

  public static UInt32 valueOfBigEndian(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    return new UInt32(paramArrayOfByte);
  }

  public static UInt32 valueOfLittleEndian(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = paramArrayOfByte[3];
    arrayOfByte[1] = paramArrayOfByte[2];
    arrayOfByte[2] = paramArrayOfByte[1];
    arrayOfByte[3] = paramArrayOfByte[0];
    return new UInt32(arrayOfByte);
  }

  public long uint32Value()
  {
    return this.uint32;
  }

  public int intValue()
  {
    return (int)uint32Value();
  }

  public long longValue()
  {
    return uint32Value();
  }

  public float floatValue()
  {
    return (float)uint32Value();
  }

  public double doubleValue()
  {
    return uint32Value();
  }

  public byte[] toBigEndian()
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = ((byte)(this.uint32 >> 24 & 0xFF));
    arrayOfByte[1] = ((byte)(this.uint32 >> 16 & 0xFF));
    arrayOfByte[2] = ((byte)(this.uint32 >> 8 & 0xFF));
    arrayOfByte[3] = ((byte)(this.uint32 & 0xFF));
    return arrayOfByte;
  }

  public byte[] toLittleEndian()
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = ((byte)(this.uint32 & 0xFF));
    arrayOfByte[1] = ((byte)(this.uint32 >> 8 & 0xFF));
    arrayOfByte[2] = ((byte)(this.uint32 >> 16 & 0xFF));
    arrayOfByte[3] = ((byte)(this.uint32 >> 24 & 0xFF));
    return arrayOfByte;
  }

  public int compareTo(Object paramObject)
  {
    if (equals(paramObject))
      return 0;
    return (int)(uint32Value() - ((UInt32)paramObject).uint32Value());
  }

  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof UInt32)) && (((UInt32)paramObject).uint32Value() == uint32Value());
  }

  public int hashCode()
  {
    return intValue();
  }

  public String toString()
  {
    return Long.toString(uint32Value());
  }

  private static void verify(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    if (paramArrayOfByte == null)
      throw new IllegalArgumentException("doubleWord cannot be null.");
    if (paramArrayOfByte.length != 4)
      throw new IllegalArgumentException("Invalid length for byte array.  doubleWord must be four bytes long.");
  }

  public UInt32 and(UInt32 paramUInt32)
  {
    return new UInt32(uint32Value() & paramUInt32.uint32Value());
  }

  public UInt32 not()
  {
    return new UInt32(~uint32Value());
  }

  public UInt32 or(UInt32 paramUInt32)
  {
    return new UInt32(uint32Value() | paramUInt32.uint32Value());
  }

  public UInt32 xor(UInt32 paramUInt32)
  {
    return new UInt32(uint32Value() ^ paramUInt32.uint32Value());
  }

  public UInt32 addBitmask(UInt32 paramUInt32)
  {
    return or(paramUInt32);
  }

  public boolean hasBitmask(UInt32 paramUInt32)
  {
    return and(paramUInt32).equals(paramUInt32);
  }

  public UInt32 removeBitmask(UInt32 paramUInt32)
  {
    return and(paramUInt32.not());
  }
}
