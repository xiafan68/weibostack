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
public class TweetTuple implements org.apache.thrift.TBase<TweetTuple, TweetTuple._Fields>, java.io.Serializable, Cloneable, Comparable<TweetTuple> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TweetTuple");

  private static final org.apache.thrift.protocol.TField CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("content", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField POINTS_FIELD_DESC = new org.apache.thrift.protocol.TField("points", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TweetTupleStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TweetTupleTupleSchemeFactory());
  }

  public String content; // required
  public List<List<Integer>> points; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CONTENT((short)1, "content"),
    POINTS((short)2, "points");

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
        case 1: // CONTENT
          return CONTENT;
        case 2: // POINTS
          return POINTS;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CONTENT, new org.apache.thrift.meta_data.FieldMetaData("content", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.POINTS, new org.apache.thrift.meta_data.FieldMetaData("points", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TweetTuple.class, metaDataMap);
  }

  public TweetTuple() {
  }

  public TweetTuple(
    String content,
    List<List<Integer>> points)
  {
    this();
    this.content = content;
    this.points = points;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TweetTuple(TweetTuple other) {
    if (other.isSetContent()) {
      this.content = other.content;
    }
    if (other.isSetPoints()) {
      List<List<Integer>> __this__points = new ArrayList<List<Integer>>(other.points.size());
      for (List<Integer> other_element : other.points) {
        List<Integer> __this__points_copy = new ArrayList<Integer>(other_element);
        __this__points.add(__this__points_copy);
      }
      this.points = __this__points;
    }
  }

  public TweetTuple deepCopy() {
    return new TweetTuple(this);
  }

  @Override
  public void clear() {
    this.content = null;
    this.points = null;
  }

  public String getContent() {
    return this.content;
  }

  public TweetTuple setContent(String content) {
    this.content = content;
    return this;
  }

  public void unsetContent() {
    this.content = null;
  }

  /** Returns true if field content is set (has been assigned a value) and false otherwise */
  public boolean isSetContent() {
    return this.content != null;
  }

  public void setContentIsSet(boolean value) {
    if (!value) {
      this.content = null;
    }
  }

  public int getPointsSize() {
    return (this.points == null) ? 0 : this.points.size();
  }

  public java.util.Iterator<List<Integer>> getPointsIterator() {
    return (this.points == null) ? null : this.points.iterator();
  }

  public void addToPoints(List<Integer> elem) {
    if (this.points == null) {
      this.points = new ArrayList<List<Integer>>();
    }
    this.points.add(elem);
  }

  public List<List<Integer>> getPoints() {
    return this.points;
  }

  public TweetTuple setPoints(List<List<Integer>> points) {
    this.points = points;
    return this;
  }

  public void unsetPoints() {
    this.points = null;
  }

  /** Returns true if field points is set (has been assigned a value) and false otherwise */
  public boolean isSetPoints() {
    return this.points != null;
  }

  public void setPointsIsSet(boolean value) {
    if (!value) {
      this.points = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((String)value);
      }
      break;

    case POINTS:
      if (value == null) {
        unsetPoints();
      } else {
        setPoints((List<List<Integer>>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CONTENT:
      return getContent();

    case POINTS:
      return getPoints();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CONTENT:
      return isSetContent();
    case POINTS:
      return isSetPoints();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TweetTuple)
      return this.equals((TweetTuple)that);
    return false;
  }

  public boolean equals(TweetTuple that) {
    if (that == null)
      return false;

    boolean this_present_content = true && this.isSetContent();
    boolean that_present_content = true && that.isSetContent();
    if (this_present_content || that_present_content) {
      if (!(this_present_content && that_present_content))
        return false;
      if (!this.content.equals(that.content))
        return false;
    }

    boolean this_present_points = true && this.isSetPoints();
    boolean that_present_points = true && that.isSetPoints();
    if (this_present_points || that_present_points) {
      if (!(this_present_points && that_present_points))
        return false;
      if (!this.points.equals(that.points))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_content = true && (isSetContent());
    list.add(present_content);
    if (present_content)
      list.add(content);

    boolean present_points = true && (isSetPoints());
    list.add(present_points);
    if (present_points)
      list.add(points);

    return list.hashCode();
  }

  @Override
  public int compareTo(TweetTuple other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetContent()).compareTo(other.isSetContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.content, other.content);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPoints()).compareTo(other.isSetPoints());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPoints()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.points, other.points);
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
    StringBuilder sb = new StringBuilder("TweetTuple(");
    boolean first = true;

    sb.append("content:");
    if (this.content == null) {
      sb.append("null");
    } else {
      sb.append(this.content);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("points:");
    if (this.points == null) {
      sb.append("null");
    } else {
      sb.append(this.points);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TweetTupleStandardSchemeFactory implements SchemeFactory {
    public TweetTupleStandardScheme getScheme() {
      return new TweetTupleStandardScheme();
    }
  }

  private static class TweetTupleStandardScheme extends StandardScheme<TweetTuple> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TweetTuple struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content = iprot.readString();
              struct.setContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // POINTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.points = new ArrayList<List<Integer>>(_list16.size);
                List<Integer> _elem17;
                for (int _i18 = 0; _i18 < _list16.size; ++_i18)
                {
                  {
                    org.apache.thrift.protocol.TList _list19 = iprot.readListBegin();
                    _elem17 = new ArrayList<Integer>(_list19.size);
                    int _elem20;
                    for (int _i21 = 0; _i21 < _list19.size; ++_i21)
                    {
                      _elem20 = iprot.readI32();
                      _elem17.add(_elem20);
                    }
                    iprot.readListEnd();
                  }
                  struct.points.add(_elem17);
                }
                iprot.readListEnd();
              }
              struct.setPointsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TweetTuple struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.content != null) {
        oprot.writeFieldBegin(CONTENT_FIELD_DESC);
        oprot.writeString(struct.content);
        oprot.writeFieldEnd();
      }
      if (struct.points != null) {
        oprot.writeFieldBegin(POINTS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.LIST, struct.points.size()));
          for (List<Integer> _iter22 : struct.points)
          {
            {
              oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, _iter22.size()));
              for (int _iter23 : _iter22)
              {
                oprot.writeI32(_iter23);
              }
              oprot.writeListEnd();
            }
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TweetTupleTupleSchemeFactory implements SchemeFactory {
    public TweetTupleTupleScheme getScheme() {
      return new TweetTupleTupleScheme();
    }
  }

  private static class TweetTupleTupleScheme extends TupleScheme<TweetTuple> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TweetTuple struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetContent()) {
        optionals.set(0);
      }
      if (struct.isSetPoints()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetContent()) {
        oprot.writeString(struct.content);
      }
      if (struct.isSetPoints()) {
        {
          oprot.writeI32(struct.points.size());
          for (List<Integer> _iter24 : struct.points)
          {
            {
              oprot.writeI32(_iter24.size());
              for (int _iter25 : _iter24)
              {
                oprot.writeI32(_iter25);
              }
            }
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TweetTuple struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.content = iprot.readString();
        struct.setContentIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list26 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.LIST, iprot.readI32());
          struct.points = new ArrayList<List<Integer>>(_list26.size);
          List<Integer> _elem27;
          for (int _i28 = 0; _i28 < _list26.size; ++_i28)
          {
            {
              org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
              _elem27 = new ArrayList<Integer>(_list29.size);
              int _elem30;
              for (int _i31 = 0; _i31 < _list29.size; ++_i31)
              {
                _elem30 = iprot.readI32();
                _elem27.add(_elem30);
              }
            }
            struct.points.add(_elem27);
          }
        }
        struct.setPointsIsSet(true);
      }
    }
  }

}

