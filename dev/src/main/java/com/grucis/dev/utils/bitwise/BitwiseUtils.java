package com.grucis.dev.utils.bitwise;

public class BitwiseUtils {

  public static int int32(byte[] data) {
    return Int32.valueOfLittleEndian(data).intValue();
  }

  public static int uint32(byte[] data) {
    return UInt32.valueOfLittleEndian(data).intValue();
  }

  public static int uint16(byte[] data) {
    return UInt16.valueOfLittleEndian(data).intValue();
  }

  public static int uint8(byte data) {
    return new UInt8(data).intValue();
  }
}
