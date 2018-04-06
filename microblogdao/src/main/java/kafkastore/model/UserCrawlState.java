/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package kafkastore.model;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.EncodingUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import javax.annotation.Generated;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2015-12-31")
public class UserCrawlState implements org.apache.thrift.TBase<UserCrawlState, UserCrawlState._Fields>, java.io.Serializable, Cloneable, Comparable<UserCrawlState> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserCrawlState");

  private static final org.apache.thrift.protocol.TField UID_FIELD_DESC = new org.apache.thrift.protocol.TField("uid", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SINCE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sinceID", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField LAST_CRAWL_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("lastCrawlTime", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField LAST_CRAWL_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("lastCrawlNum", org.apache.thrift.protocol.TType.I16, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UserCrawlStateStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UserCrawlStateTupleSchemeFactory());
  }

  public String uid; // required
  public long sinceID; // required
  public long lastCrawlTime; // required
  public short lastCrawlNum; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    UID((short)1, "uid"),
    SINCE_ID((short)2, "sinceID"),
    LAST_CRAWL_TIME((short)3, "lastCrawlTime"),
    LAST_CRAWL_NUM((short)4, "lastCrawlNum");

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
        case 1: // UID
          return UID;
        case 2: // SINCE_ID
          return SINCE_ID;
        case 3: // LAST_CRAWL_TIME
          return LAST_CRAWL_TIME;
        case 4: // LAST_CRAWL_NUM
          return LAST_CRAWL_NUM;
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
  private static final int __SINCEID_ISSET_ID = 0;
  private static final int __LASTCRAWLTIME_ISSET_ID = 1;
  private static final int __LASTCRAWLNUM_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.UID, new org.apache.thrift.meta_data.FieldMetaData("uid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SINCE_ID, new org.apache.thrift.meta_data.FieldMetaData("sinceID", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LAST_CRAWL_TIME, new org.apache.thrift.meta_data.FieldMetaData("lastCrawlTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LAST_CRAWL_NUM, new org.apache.thrift.meta_data.FieldMetaData("lastCrawlNum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserCrawlState.class, metaDataMap);
  }

  public UserCrawlState() {
  }

  public UserCrawlState(
    String uid,
    long sinceID,
    long lastCrawlTime,
    short lastCrawlNum)
  {
    this();
    this.uid = uid;
    this.sinceID = sinceID;
    setSinceIDIsSet(true);
    this.lastCrawlTime = lastCrawlTime;
    setLastCrawlTimeIsSet(true);
    this.lastCrawlNum = lastCrawlNum;
    setLastCrawlNumIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserCrawlState(UserCrawlState other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetUid()) {
      this.uid = other.uid;
    }
    this.sinceID = other.sinceID;
    this.lastCrawlTime = other.lastCrawlTime;
    this.lastCrawlNum = other.lastCrawlNum;
  }

  public UserCrawlState deepCopy() {
    return new UserCrawlState(this);
  }

  @Override
  public void clear() {
    this.uid = null;
    setSinceIDIsSet(false);
    this.sinceID = 0;
    setLastCrawlTimeIsSet(false);
    this.lastCrawlTime = 0;
    setLastCrawlNumIsSet(false);
    this.lastCrawlNum = 0;
  }

  public String getUid() {
    return this.uid;
  }

  public UserCrawlState setUid(String uid) {
    this.uid = uid;
    return this;
  }

  public void unsetUid() {
    this.uid = null;
  }

  /** Returns true if field uid is set (has been assigned a value) and false otherwise */
  public boolean isSetUid() {
    return this.uid != null;
  }

  public void setUidIsSet(boolean value) {
    if (!value) {
      this.uid = null;
    }
  }

  public long getSinceID() {
    return this.sinceID;
  }

  public UserCrawlState setSinceID(long sinceID) {
    this.sinceID = sinceID;
    setSinceIDIsSet(true);
    return this;
  }

  public void unsetSinceID() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SINCEID_ISSET_ID);
  }

  /** Returns true if field sinceID is set (has been assigned a value) and false otherwise */
  public boolean isSetSinceID() {
    return EncodingUtils.testBit(__isset_bitfield, __SINCEID_ISSET_ID);
  }

  public void setSinceIDIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SINCEID_ISSET_ID, value);
  }

  public long getLastCrawlTime() {
    return this.lastCrawlTime;
  }

  public UserCrawlState setLastCrawlTime(long lastCrawlTime) {
    this.lastCrawlTime = lastCrawlTime;
    setLastCrawlTimeIsSet(true);
    return this;
  }

  public void unsetLastCrawlTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LASTCRAWLTIME_ISSET_ID);
  }

  /** Returns true if field lastCrawlTime is set (has been assigned a value) and false otherwise */
  public boolean isSetLastCrawlTime() {
    return EncodingUtils.testBit(__isset_bitfield, __LASTCRAWLTIME_ISSET_ID);
  }

  public void setLastCrawlTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LASTCRAWLTIME_ISSET_ID, value);
  }

  public short getLastCrawlNum() {
    return this.lastCrawlNum;
  }

  public UserCrawlState setLastCrawlNum(short lastCrawlNum) {
    this.lastCrawlNum = lastCrawlNum;
    setLastCrawlNumIsSet(true);
    return this;
  }

  public void unsetLastCrawlNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LASTCRAWLNUM_ISSET_ID);
  }

  /** Returns true if field lastCrawlNum is set (has been assigned a value) and false otherwise */
  public boolean isSetLastCrawlNum() {
    return EncodingUtils.testBit(__isset_bitfield, __LASTCRAWLNUM_ISSET_ID);
  }

  public void setLastCrawlNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LASTCRAWLNUM_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case UID:
      if (value == null) {
        unsetUid();
      } else {
        setUid((String)value);
      }
      break;

    case SINCE_ID:
      if (value == null) {
        unsetSinceID();
      } else {
        setSinceID((Long)value);
      }
      break;

    case LAST_CRAWL_TIME:
      if (value == null) {
        unsetLastCrawlTime();
      } else {
        setLastCrawlTime((Long)value);
      }
      break;

    case LAST_CRAWL_NUM:
      if (value == null) {
        unsetLastCrawlNum();
      } else {
        setLastCrawlNum((Short)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case UID:
      return getUid();

    case SINCE_ID:
      return getSinceID();

    case LAST_CRAWL_TIME:
      return getLastCrawlTime();

    case LAST_CRAWL_NUM:
      return getLastCrawlNum();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case UID:
      return isSetUid();
    case SINCE_ID:
      return isSetSinceID();
    case LAST_CRAWL_TIME:
      return isSetLastCrawlTime();
    case LAST_CRAWL_NUM:
      return isSetLastCrawlNum();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UserCrawlState)
      return this.equals((UserCrawlState)that);
    return false;
  }

  public boolean equals(UserCrawlState that) {
    if (that == null)
      return false;

    boolean this_present_uid = true && this.isSetUid();
    boolean that_present_uid = true && that.isSetUid();
    if (this_present_uid || that_present_uid) {
      if (!(this_present_uid && that_present_uid))
        return false;
      if (!this.uid.equals(that.uid))
        return false;
    }

    boolean this_present_sinceID = true;
    boolean that_present_sinceID = true;
    if (this_present_sinceID || that_present_sinceID) {
      if (!(this_present_sinceID && that_present_sinceID))
        return false;
      if (this.sinceID != that.sinceID)
        return false;
    }

    boolean this_present_lastCrawlTime = true;
    boolean that_present_lastCrawlTime = true;
    if (this_present_lastCrawlTime || that_present_lastCrawlTime) {
      if (!(this_present_lastCrawlTime && that_present_lastCrawlTime))
        return false;
      if (this.lastCrawlTime != that.lastCrawlTime)
        return false;
    }

    boolean this_present_lastCrawlNum = true;
    boolean that_present_lastCrawlNum = true;
    if (this_present_lastCrawlNum || that_present_lastCrawlNum) {
      if (!(this_present_lastCrawlNum && that_present_lastCrawlNum))
        return false;
      if (this.lastCrawlNum != that.lastCrawlNum)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_uid = true && (isSetUid());
    list.add(present_uid);
    if (present_uid)
      list.add(uid);

    boolean present_sinceID = true;
    list.add(present_sinceID);
    if (present_sinceID)
      list.add(sinceID);

    boolean present_lastCrawlTime = true;
    list.add(present_lastCrawlTime);
    if (present_lastCrawlTime)
      list.add(lastCrawlTime);

    boolean present_lastCrawlNum = true;
    list.add(present_lastCrawlNum);
    if (present_lastCrawlNum)
      list.add(lastCrawlNum);

    return list.hashCode();
  }

  @Override
  public int compareTo(UserCrawlState other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetUid()).compareTo(other.isSetUid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uid, other.uid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSinceID()).compareTo(other.isSetSinceID());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSinceID()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sinceID, other.sinceID);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLastCrawlTime()).compareTo(other.isSetLastCrawlTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastCrawlTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastCrawlTime, other.lastCrawlTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLastCrawlNum()).compareTo(other.isSetLastCrawlNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastCrawlNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastCrawlNum, other.lastCrawlNum);
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
    StringBuilder sb = new StringBuilder("UserCrawlState(");
    boolean first = true;

    sb.append("uid:");
    if (this.uid == null) {
      sb.append("null");
    } else {
      sb.append(this.uid);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("sinceID:");
    sb.append(this.sinceID);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lastCrawlTime:");
    sb.append(this.lastCrawlTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lastCrawlNum:");
    sb.append(this.lastCrawlNum);
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

  private static class UserCrawlStateStandardSchemeFactory implements SchemeFactory {
    public UserCrawlStateStandardScheme getScheme() {
      return new UserCrawlStateStandardScheme();
    }
  }

  private static class UserCrawlStateStandardScheme extends StandardScheme<UserCrawlState> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserCrawlState struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // UID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.uid = iprot.readString();
              struct.setUidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SINCE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.sinceID = iprot.readI64();
              struct.setSinceIDIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LAST_CRAWL_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.lastCrawlTime = iprot.readI64();
              struct.setLastCrawlTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // LAST_CRAWL_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.lastCrawlNum = iprot.readI16();
              struct.setLastCrawlNumIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserCrawlState struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.uid != null) {
        oprot.writeFieldBegin(UID_FIELD_DESC);
        oprot.writeString(struct.uid);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SINCE_ID_FIELD_DESC);
      oprot.writeI64(struct.sinceID);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LAST_CRAWL_TIME_FIELD_DESC);
      oprot.writeI64(struct.lastCrawlTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LAST_CRAWL_NUM_FIELD_DESC);
      oprot.writeI16(struct.lastCrawlNum);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserCrawlStateTupleSchemeFactory implements SchemeFactory {
    public UserCrawlStateTupleScheme getScheme() {
      return new UserCrawlStateTupleScheme();
    }
  }

  private static class UserCrawlStateTupleScheme extends TupleScheme<UserCrawlState> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserCrawlState struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUid()) {
        optionals.set(0);
      }
      if (struct.isSetSinceID()) {
        optionals.set(1);
      }
      if (struct.isSetLastCrawlTime()) {
        optionals.set(2);
      }
      if (struct.isSetLastCrawlNum()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetUid()) {
        oprot.writeString(struct.uid);
      }
      if (struct.isSetSinceID()) {
        oprot.writeI64(struct.sinceID);
      }
      if (struct.isSetLastCrawlTime()) {
        oprot.writeI64(struct.lastCrawlTime);
      }
      if (struct.isSetLastCrawlNum()) {
        oprot.writeI16(struct.lastCrawlNum);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserCrawlState struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.uid = iprot.readString();
        struct.setUidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.sinceID = iprot.readI64();
        struct.setSinceIDIsSet(true);
      }
      if (incoming.get(2)) {
        struct.lastCrawlTime = iprot.readI64();
        struct.setLastCrawlTimeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.lastCrawlNum = iprot.readI16();
        struct.setLastCrawlNumIsSet(true);
      }
    }
  }

}
