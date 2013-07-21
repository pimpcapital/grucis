package com.grucis.dev.utils.bitwise;

import java.io.Serializable;

public class Int16 extends Number
  implements Serializable, Comparable, Bitwise<Int16>
{
  public static final short MAX_VALUE = 32767;
  public static final short MIN_VALUE = -32768;
  private short int16;

  public Int16(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    this.int16 = ((short)((paramArrayOfByte[0] & 0xFF) << 8 | paramArrayOfByte[1] & 0xFF));
  }

  public Int16(short paramShort)
  {
    this.int16 = paramShort;
  }

  public Int16(int paramInt)
  {
    this.int16 = ((short)paramInt);
  }

  public Int16(long paramLong)
  {
    this.int16 = ((short)(int)paramLong);
  }

  public static Int16 valueOfBigEndian(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    return new Int16(paramArrayOfByte);
  }

  public static Int16 valueOfLittleEndian(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    verify(paramArrayOfByte);
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = paramArrayOfByte[1];
    arrayOfByte[1] = paramArrayOfByte[0];
    return new Int16(arrayOfByte);
  }

  public short int16Value()
  {
    return shortValue();
  }

  public int intValue()
  {
    return this.int16;
  }

  public long longValue()
  {
    return this.int16;
  }

  public float floatValue()
  {
    return this.int16;
  }

  public double doubleValue()
  {
    return this.int16;
  }

  public byte[] toBigEndian()
  {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = ((byte)(this.int16 >> 8 & 0xFF));
    arrayOfByte[1] = ((byte)(this.int16 & 0xFF));
    return arrayOfByte;
  }

  public byte[] toLittleEndian()
  {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = ((byte)(this.int16 & 0xFF));
    arrayOfByte[1] = ((byte)(this.int16 >> 8 & 0xFF));
    return arrayOfByte;
  }

  public int compareTo(Object paramObject)
  {
    if (equals(paramObject))
      return 0;
    return shortValue() - ((Int16)paramObject).shortValue();
  }

  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof Int16)) && (((Int16)paramObject).shortValue() == shortValue());
  }

  public int hashCode()
  {
    return intValue();
  }

  public String toString()
  {
    return Short.toString(this.int16);
  }

  private static void verify(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    if (paramArrayOfByte == null)
      throw new IllegalArgumentException("word cannot be null.");
    if (paramArrayOfByte.length != 2)
      throw new IllegalArgumentException("Invalid length for byte array.  word must be two bytes long.");
  }

  public Int16 and(Int16 paramInt16)
  {
    return new Int16(int16Value() & paramInt16.int16Value());
  }

  public Int16 not()
  {
    return new Int16(~int16Value());
  }

  public Int16 or(Int16 paramInt16)
  {
    return new Int16(int16Value() | paramInt16.int16Value());
  }

  public Int16 xor(Int16 paramInt16)
  {
    return new Int16(int16Value() ^ paramInt16.int16Value());
  }

  public Int16 addBitmask(Int16 paramInt16)
  {
    return or(paramInt16);
  }

  public boolean hasBitmask(Int16 paramInt16)
  {
    return and(paramInt16).equals(paramInt16);
  }

  public Int16 removeBitmask(Int16 paramInt16)
  {
    return and(paramInt16.not());
  }
}
