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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-02-11")
public class TKeywordsQuery implements org.apache.thrift.TBase<TKeywordsQuery, TKeywordsQuery._Fields>, java.io.Serializable, Cloneable, Comparable<TKeywordsQuery> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TKeywordsQuery");

  private static final org.apache.thrift.protocol.TField QUERY_FIELD_DESC = new org.apache.thrift.protocol.TField("query", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField TOPK_FIELD_DESC = new org.apache.thrift.protocol.TField("topk", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField START_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("startTime", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField END_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("endTime", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TKeywordsQueryStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TKeywordsQueryTupleSchemeFactory());
  }

  public List<String> query; // required
  public int topk; // required
  public int startTime; // required
  public int endTime; // required
  /**
   * 
   * @see QueryType
   */
  public QueryType type; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    QUERY((short)1, "query"),
    TOPK((short)2, "topk"),
    START_TIME((short)3, "startTime"),
    END_TIME((short)4, "endTime"),
    /**
     * 
     * @see QueryType
     */
    TYPE((short)5, "type");

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
        case 1: // QUERY
          return QUERY;
        case 2: // TOPK
          return TOPK;
        case 3: // START_TIME
          return START_TIME;
        case 4: // END_TIME
          return END_TIME;
        case 5: // TYPE
          return TYPE;
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
  private static final int __TOPK_ISSET_ID = 0;
  private static final int __STARTTIME_ISSET_ID = 1;
  private static final int __ENDTIME_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.QUERY, new org.apache.thrift.meta_data.FieldMetaData("query", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.TOPK, new org.apache.thrift.meta_data.FieldMetaData("topk", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.START_TIME, new org.apache.thrift.meta_data.FieldMetaData("startTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.END_TIME, new org.apache.thrift.meta_data.FieldMetaData("endTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, QueryType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TKeywordsQuery.class, metaDataMap);
  }

  public TKeywordsQuery() {
  }

  public TKeywordsQuery(
    List<String> query,
    int topk,
    int startTime,
    int endTime,
    QueryType type)
  {
    this();
    this.query = query;
    this.topk = topk;
    setTopkIsSet(true);
    this.startTime = startTime;
    setStartTimeIsSet(true);
    this.endTime = endTime;
    setEndTimeIsSet(true);
    this.type = type;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TKeywordsQuery(TKeywordsQuery other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetQuery()) {
      List<String> __this__query = new ArrayList<String>(other.query);
      this.query = __this__query;
    }
    this.topk = other.topk;
    this.startTime = other.startTime;
    this.endTime = other.endTime;
    if (other.isSetType()) {
      this.type = other.type;
    }
  }

  public TKeywordsQuery deepCopy() {
    return new TKeywordsQuery(this);
  }

  @Override
  public void clear() {
    this.query = null;
    setTopkIsSet(false);
    this.topk = 0;
    setStartTimeIsSet(false);
    this.startTime = 0;
    setEndTimeIsSet(false);
    this.endTime = 0;
    this.type = null;
  }

  public int getQuerySize() {
    return (this.query == null) ? 0 : this.query.size();
  }

  public java.util.Iterator<String> getQueryIterator() {
    return (this.query == null) ? null : this.query.iterator();
  }

  public void addToQuery(String elem) {
    if (this.query == null) {
      this.query = new ArrayList<String>();
    }
    this.query.add(elem);
  }

  public List<String> getQuery() {
    return this.query;
  }

  public TKeywordsQuery setQuery(List<String> query) {
    this.query = query;
    return this;
  }

  public void unsetQuery() {
    this.query = null;
  }

  /** Returns true if field query is set (has been assigned a value) and false otherwise */
  public boolean isSetQuery() {
    return this.query != null;
  }

  public void setQueryIsSet(boolean value) {
    if (!value) {
      this.query = null;
    }
  }

  public int getTopk() {
    return this.topk;
  }

  public TKeywordsQuery setTopk(int topk) {
    this.topk = topk;
    setTopkIsSet(true);
    return this;
  }

  public void unsetTopk() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOPK_ISSET_ID);
  }

  /** Returns true if field topk is set (has been assigned a value) and false otherwise */
  public boolean isSetTopk() {
    return EncodingUtils.testBit(__isset_bitfield, __TOPK_ISSET_ID);
  }

  public void setTopkIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOPK_ISSET_ID, value);
  }

  public int getStartTime() {
    return this.startTime;
  }

  public TKeywordsQuery setStartTime(int startTime) {
    this.startTime = startTime;
    setStartTimeIsSet(true);
    return this;
  }

  public void unsetStartTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STARTTIME_ISSET_ID);
  }

  /** Returns true if field startTime is set (has been assigned a value) and false otherwise */
  public boolean isSetStartTime() {
    return EncodingUtils.testBit(__isset_bitfield, __STARTTIME_ISSET_ID);
  }

  public void setStartTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STARTTIME_ISSET_ID, value);
  }

  public int getEndTime() {
    return this.endTime;
  }

  public TKeywordsQuery setEndTime(int endTime) {
    this.endTime = endTime;
    setEndTimeIsSet(true);
    return this;
  }

  public void unsetEndTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ENDTIME_ISSET_ID);
  }

  /** Returns true if field endTime is set (has been assigned a value) and false otherwise */
  public boolean isSetEndTime() {
    return EncodingUtils.testBit(__isset_bitfield, __ENDTIME_ISSET_ID);
  }

  public void setEndTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ENDTIME_ISSET_ID, value);
  }

  /**
   * 
   * @see QueryType
   */
  public QueryType getType() {
    return this.type;
  }

  /**
   * 
   * @see QueryType
   */
  public TKeywordsQuery setType(QueryType type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case QUERY:
      if (value == null) {
        unsetQuery();
      } else {
        setQuery((List<String>)value);
      }
      break;

    case TOPK:
      if (value == null) {
        unsetTopk();
      } else {
        setTopk((Integer)value);
      }
      break;

    case START_TIME:
      if (value == null) {
        unsetStartTime();
      } else {
        setStartTime((Integer)value);
      }
      break;

    case END_TIME:
      if (value == null) {
        unsetEndTime();
      } else {
        setEndTime((Integer)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((QueryType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case QUERY:
      return getQuery();

    case TOPK:
      return getTopk();

    case START_TIME:
      return getStartTime();

    case END_TIME:
      return getEndTime();

    case TYPE:
      return getType();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case QUERY:
      return isSetQuery();
    case TOPK:
      return isSetTopk();
    case START_TIME:
      return isSetStartTime();
    case END_TIME:
      return isSetEndTime();
    case TYPE:
      return isSetType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TKeywordsQuery)
      return this.equals((TKeywordsQuery)that);
    return false;
  }

  public boolean equals(TKeywordsQuery that) {
    if (that == null)
      return false;

    boolean this_present_query = true && this.isSetQuery();
    boolean that_present_query = true && that.isSetQuery();
    if (this_present_query || that_present_query) {
      if (!(this_present_query && that_present_query))
        return false;
      if (!this.query.equals(that.query))
        return false;
    }

    boolean this_present_topk = true;
    boolean that_present_topk = true;
    if (this_present_topk || that_present_topk) {
      if (!(this_present_topk && that_present_topk))
        return false;
      if (this.topk != that.topk)
        return false;
    }

    boolean this_present_startTime = true;
    boolean that_present_startTime = true;
    if (this_present_startTime || that_present_startTime) {
      if (!(this_present_startTime && that_present_startTime))
        return false;
      if (this.startTime != that.startTime)
        return false;
    }

    boolean this_present_endTime = true;
    boolean that_present_endTime = true;
    if (this_present_endTime || that_present_endTime) {
      if (!(this_present_endTime && that_present_endTime))
        return false;
      if (this.endTime != that.endTime)
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_query = true && (isSetQuery());
    list.add(present_query);
    if (present_query)
      list.add(query);

    boolean present_topk = true;
    list.add(present_topk);
    if (present_topk)
      list.add(topk);

    boolean present_startTime = true;
    list.add(present_startTime);
    if (present_startTime)
      list.add(startTime);

    boolean present_endTime = true;
    list.add(present_endTime);
    if (present_endTime)
      list.add(endTime);

    boolean present_type = true && (isSetType());
    list.add(present_type);
    if (present_type)
      list.add(type.getValue());

    return list.hashCode();
  }

  @Override
  public int compareTo(TKeywordsQuery other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetQuery()).compareTo(other.isSetQuery());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQuery()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.query, other.query);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTopk()).compareTo(other.isSetTopk());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTopk()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.topk, other.topk);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStartTime()).compareTo(other.isSetStartTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startTime, other.startTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndTime()).compareTo(other.isSetEndTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endTime, other.endTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
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
    StringBuilder sb = new StringBuilder("TKeywordsQuery(");
    boolean first = true;

    sb.append("query:");
    if (this.query == null) {
      sb.append("null");
    } else {
      sb.append(this.query);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("topk:");
    sb.append(this.topk);
    first = false;
    if (!first) sb.append(", ");
    sb.append("startTime:");
    sb.append(this.startTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("endTime:");
    sb.append(this.endTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
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

  private static class TKeywordsQueryStandardSchemeFactory implements SchemeFactory {
    public TKeywordsQueryStandardScheme getScheme() {
      return new TKeywordsQueryStandardScheme();
    }
  }

  private static class TKeywordsQueryStandardScheme extends StandardScheme<TKeywordsQuery> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TKeywordsQuery struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // QUERY
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.query = new ArrayList<String>(_list8.size);
                String _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = iprot.readString();
                  struct.query.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setQueryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TOPK
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.topk = iprot.readI32();
              struct.setTopkIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // START_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.startTime = iprot.readI32();
              struct.setStartTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // END_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.endTime = iprot.readI32();
              struct.setEndTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = searchapi.QueryType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TKeywordsQuery struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.query != null) {
        oprot.writeFieldBegin(QUERY_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.query.size()));
          for (String _iter11 : struct.query)
          {
            oprot.writeString(_iter11);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TOPK_FIELD_DESC);
      oprot.writeI32(struct.topk);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(START_TIME_FIELD_DESC);
      oprot.writeI32(struct.startTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(END_TIME_FIELD_DESC);
      oprot.writeI32(struct.endTime);
      oprot.writeFieldEnd();
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeI32(struct.type.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TKeywordsQueryTupleSchemeFactory implements SchemeFactory {
    public TKeywordsQueryTupleScheme getScheme() {
      return new TKeywordsQueryTupleScheme();
    }
  }

  private static class TKeywordsQueryTupleScheme extends TupleScheme<TKeywordsQuery> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TKeywordsQuery struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetQuery()) {
        optionals.set(0);
      }
      if (struct.isSetTopk()) {
        optionals.set(1);
      }
      if (struct.isSetStartTime()) {
        optionals.set(2);
      }
      if (struct.isSetEndTime()) {
        optionals.set(3);
      }
      if (struct.isSetType()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetQuery()) {
        {
          oprot.writeI32(struct.query.size());
          for (String _iter12 : struct.query)
          {
            oprot.writeString(_iter12);
          }
        }
      }
      if (struct.isSetTopk()) {
        oprot.writeI32(struct.topk);
      }
      if (struct.isSetStartTime()) {
        oprot.writeI32(struct.startTime);
      }
      if (struct.isSetEndTime()) {
        oprot.writeI32(struct.endTime);
      }
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TKeywordsQuery struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.query = new ArrayList<String>(_list13.size);
          String _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = iprot.readString();
            struct.query.add(_elem14);
          }
        }
        struct.setQueryIsSet(true);
      }
      if (incoming.get(1)) {
        struct.topk = iprot.readI32();
        struct.setTopkIsSet(true);
      }
      if (incoming.get(2)) {
        struct.startTime = iprot.readI32();
        struct.setStartTimeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.endTime = iprot.readI32();
        struct.setEndTimeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.type = searchapi.QueryType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
    }
  }

}

