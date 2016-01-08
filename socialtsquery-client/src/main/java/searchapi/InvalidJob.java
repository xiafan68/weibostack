/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package searchapi;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
/**
 * Structs can also be exceptions, if they are nasty.
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2015-12-26")
public class InvalidJob extends TException implements org.apache.thrift.TBase<InvalidJob, InvalidJob._Fields>, java.io.Serializable, Cloneable, Comparable<InvalidJob> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("InvalidJob");

  private static final org.apache.thrift.protocol.TField WHAT_FIELD_DESC = new org.apache.thrift.protocol.TField("what", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField WHY_FIELD_DESC = new org.apache.thrift.protocol.TField("why", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new InvalidJobStandardSchemeFactory());
    schemes.put(TupleScheme.class, new InvalidJobTupleSchemeFactory());
  }

  public int what; // required
  public String why; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    WHAT((short)1, "what"),
    WHY((short)2, "why");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // WHAT
          return WHAT;
        case 2: // WHY
          return WHY;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __WHAT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.WHAT, new org.apache.thrift.meta_data.FieldMetaData("what", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.WHY, new org.apache.thrift.meta_data.FieldMetaData("why", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(InvalidJob.class, metaDataMap);
  }

  public InvalidJob() {
  }

  public InvalidJob(
    int what,
    String why)
  {
    this();
    this.what = what;
    setWhatIsSet(true);
    this.why = why;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public InvalidJob(InvalidJob other) {
    __isset_bitfield = other.__isset_bitfield;
    this.what = other.what;
    if (other.isSetWhy()) {
      this.why = other.why;
    }
  }

  public InvalidJob deepCopy() {
    return new InvalidJob(this);
  }

  @Override
  public void clear() {
    setWhatIsSet(false);
    this.what = 0;
    this.why = null;
  }

  public int getWhat() {
    return this.what;
  }

  public InvalidJob setWhat(int what) {
    this.what = what;
    setWhatIsSet(true);
    return this;
  }

  public void unsetWhat() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __WHAT_ISSET_ID);
  }

  /** Returns true if field what is set (has been assigned a value) and false otherwise */
  public boolean isSetWhat() {
    return EncodingUtils.testBit(__isset_bitfield, __WHAT_ISSET_ID);
  }

  public void setWhatIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __WHAT_ISSET_ID, value);
  }

  public String getWhy() {
    return this.why;
  }

  public InvalidJob setWhy(String why) {
    this.why = why;
    return this;
  }

  public void unsetWhy() {
    this.why = null;
  }

  /** Returns true if field why is set (has been assigned a value) and false otherwise */
  public boolean isSetWhy() {
    return this.why != null;
  }

  public void setWhyIsSet(boolean value) {
    if (!value) {
      this.why = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case WHAT:
      if (value == null) {
        unsetWhat();
      } else {
        setWhat((Integer)value);
      }
      break;

    case WHY:
      if (value == null) {
        unsetWhy();
      } else {
        setWhy((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case WHAT:
      return getWhat();

    case WHY:
      return getWhy();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case WHAT:
      return isSetWhat();
    case WHY:
      return isSetWhy();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof InvalidJob)
      return this.equals((InvalidJob)that);
    return false;
  }

  public boolean equals(InvalidJob that) {
    if (that == null)
      return false;

    boolean this_present_what = true;
    boolean that_present_what = true;
    if (this_present_what || that_present_what) {
      if (!(this_present_what && that_present_what))
        return false;
      if (this.what != that.what)
        return false;
    }

    boolean this_present_why = true && this.isSetWhy();
    boolean that_present_why = true && that.isSetWhy();
    if (this_present_why || that_present_why) {
      if (!(this_present_why && that_present_why))
        return false;
      if (!this.why.equals(that.why))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_what = true;
    list.add(present_what);
    if (present_what)
      list.add(what);

    boolean present_why = true && (isSetWhy());
    list.add(present_why);
    if (present_why)
      list.add(why);

    return list.hashCode();
  }

  @Override
  public int compareTo(InvalidJob other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetWhat()).compareTo(other.isSetWhat());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWhat()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.what, other.what);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetWhy()).compareTo(other.isSetWhy());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWhy()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.why, other.why);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("InvalidJob(");
    boolean first = true;

    sb.append("what:");
    sb.append(this.what);
    first = false;
    if (!first) sb.append(", ");
    sb.append("why:");
    if (this.why == null) {
      sb.append("null");
    } else {
      sb.append(this.why);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class InvalidJobStandardSchemeFactory implements SchemeFactory {
    public InvalidJobStandardScheme getScheme() {
      return new InvalidJobStandardScheme();
    }
  }

  private static class InvalidJobStandardScheme extends StandardScheme<InvalidJob> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, InvalidJob struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // WHAT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.what = iprot.readI32();
              struct.setWhatIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // WHY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.why = iprot.readString();
              struct.setWhyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, InvalidJob struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(WHAT_FIELD_DESC);
      oprot.writeI32(struct.what);
      oprot.writeFieldEnd();
      if (struct.why != null) {
        oprot.writeFieldBegin(WHY_FIELD_DESC);
        oprot.writeString(struct.why);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class InvalidJobTupleSchemeFactory implements SchemeFactory {
    public InvalidJobTupleScheme getScheme() {
      return new InvalidJobTupleScheme();
    }
  }

  private static class InvalidJobTupleScheme extends TupleScheme<InvalidJob> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, InvalidJob struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetWhat()) {
        optionals.set(0);
      }
      if (struct.isSetWhy()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetWhat()) {
        oprot.writeI32(struct.what);
      }
      if (struct.isSetWhy()) {
        oprot.writeString(struct.why);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, InvalidJob struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.what = iprot.readI32();
        struct.setWhatIsSet(true);
      }
      if (incoming.get(1)) {
        struct.why = iprot.readString();
        struct.setWhyIsSet(true);
      }
    }
  }

}

