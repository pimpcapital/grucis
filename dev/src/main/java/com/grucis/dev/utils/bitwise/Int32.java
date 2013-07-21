package com.grucis.dev.utils.bitwise;

import java.io.Serializable;

public class Int32 extends Number
  implements Serializable, Comparable, Bitwise<Int32>
{
  public static final int MAX_VALUE = 2147483647;
  public static final int MIN_VALUE = -2147483648;
  private int int32;

  public Int32(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    this.int32 = ((paramArrayOfByte[0] & 0xFF) << 24 | (paramArrayOfByte[1] & 0xFF) << 16 | (paramArrayOfByte[2] & 0xFF) << 8 | paramArrayOfByte[3] & 0xFF);
  }

  public Int32(short paramShort)
  {
    this.int32 = paramShort;
  }

  public Int32(int paramInt)
  {
    this.int32 = paramInt;
  }

  public Int32(long paramLong)
  {
    this.int32 = ((int)paramLong);
  }

  public static Int32 valueOfBigEndian(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    return new Int32(paramArrayOfByte);
  }

  public static Int32 valueOfLittleEndian(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = paramArrayOfByte[3];
    arrayOfByte[1] = paramArrayOfByte[2];
    arrayOfByte[2] = paramArrayOfByte[1];
    arrayOfByte[3] = paramArrayOfByte[0];
    return new Int32(arrayOfByte);
  }

  public int int32Value()
  {
    return intValue();
  }

  public int intValue()
  {
    return this.int32;
  }

  public long longValue()
  {
    return this.int32;
  }

  public float floatValue()
  {
    return this.int32;
  }

  public double doubleValue()
  {
    return this.int32;
  }

  public int compareTo(Object paramObject)
  {
    if (equals(paramObject))
      return 0;
    return intValue() - ((Int32)paramObject).intValue();
  }

  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof Int32)) && (((Int32)paramObject).intValue() == intValue());
  }

  public int hashCode()
  {
    return intValue();
  }

  public byte[] toBigEndian()
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = ((byte)(this.int32 >> 24 & 0xFF));
    arrayOfByte[1] = ((byte)(this.int32 >> 16 & 0xFF));
    arrayOfByte[2] = ((byte)(this.int32 >> 8 & 0xFF));
    arrayOfByte[3] = ((byte)(this.int32 & 0xFF));
    return arrayOfByte;
  }

  public byte[] toLittleEndian()
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = ((byte)(this.int32 & 0xFF));
    arrayOfByte[1] = ((byte)(this.int32 >> 8 & 0xFF));
    arrayOfByte[2] = ((byte)(this.int32 >> 16 & 0xFF));
    arrayOfByte[3] = ((byte)(this.int32 >> 24 & 0xFF));
    return arrayOfByte;
  }

  public String toString()
  {
    return Integer.toString(this.int32);
  }

  private static void verify(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    if (paramArrayOfByte == null)
      throw new IllegalArgumentException("doubleWord cannot be null.");
    if (paramArrayOfByte.length != 4)
      throw new IllegalArgumentException("Invalid length for byte array.  doubleWord must be four bytes long.");
  }

  public Int32 and(Int32 paramInt32)
  {
    return new Int32(int32Value() & paramInt32.int32Value());
  }

  public Int32 not()
  {
    return new Int32(~int32Value());
  }

  public Int32 or(Int32 paramInt32)
  {
    return new Int32(int32Value() | paramInt32.int32Value());
  }

  public Int32 xor(Int32 paramInt32)
  {
    return new Int32(int32Value() ^ paramInt32.int32Value());
  }

  public Int32 addBitmask(Int32 paramInt32)
  {
    return or(paramInt32);
  }

  public boolean hasBitmask(Int32 paramInt32)
  {
    return and(paramInt32).equals(paramInt32);
  }

  public Int32 removeBitmask(Int32 paramInt32)
  {
    return and(paramInt32.not());
  }
}
