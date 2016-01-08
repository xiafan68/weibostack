/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package cms;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-01-01")
public class TSearchServer implements org.apache.thrift.TBase<TSearchServer, TSearchServer._Fields>, java.io.Serializable, Cloneable, Comparable<TSearchServer> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TSearchServer");

  private static final org.apache.thrift.protocol.TField KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("key", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField IP_ADD_FIELD_DESC = new org.apache.thrift.protocol.TField("ipAdd", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PORT_FIELD_DESC = new org.apache.thrift.protocol.TField("port", org.apache.thrift.protocol.TType.I16, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TSearchServerStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TSearchServerTupleSchemeFactory());
  }

  public long key; // required
  public String ipAdd; // required
  public short port; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    KEY((short)1, "key"),
    IP_ADD((short)2, "ipAdd"),
    PORT((short)3, "port");

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
        case 1: // KEY
          return KEY;
        case 2: // IP_ADD
          return IP_ADD;
        case 3: // PORT
          return PORT;
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
  private static final int __KEY_ISSET_ID = 0;
  private static final int __PORT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.KEY, new org.apache.thrift.meta_data.FieldMetaData("key", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.IP_ADD, new org.apache.thrift.meta_data.FieldMetaData("ipAdd", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PORT, new org.apache.thrift.meta_data.FieldMetaData("port", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TSearchServer.class, metaDataMap);
  }

  public TSearchServer() {
  }

  public TSearchServer(
    long key,
    String ipAdd,
    short port)
  {
    this();
    this.key = key;
    setKeyIsSet(true);
    this.ipAdd = ipAdd;
    this.port = port;
    setPortIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TSearchServer(TSearchServer other) {
    __isset_bitfield = other.__isset_bitfield;
    this.key = other.key;
    if (other.isSetIpAdd()) {
      this.ipAdd = other.ipAdd;
    }
    this.port = other.port;
  }

  public TSearchServer deepCopy() {
    return new TSearchServer(this);
  }

  @Override
  public void clear() {
    setKeyIsSet(false);
    this.key = 0;
    this.ipAdd = null;
    setPortIsSet(false);
    this.port = 0;
  }

  public long getKey() {
    return this.key;
  }

  public TSearchServer setKey(long key) {
    this.key = key;
    setKeyIsSet(true);
    return this;
  }

  public void unsetKey() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __KEY_ISSET_ID);
  }

  /** Returns true if field key is set (has been assigned a value) and false otherwise */
  public boolean isSetKey() {
    return EncodingUtils.testBit(__isset_bitfield, __KEY_ISSET_ID);
  }

  public void setKeyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __KEY_ISSET_ID, value);
  }

  public String getIpAdd() {
    return this.ipAdd;
  }

  public TSearchServer setIpAdd(String ipAdd) {
    this.ipAdd = ipAdd;
    return this;
  }

  public void unsetIpAdd() {
    this.ipAdd = null;
  }

  /** Returns true if field ipAdd is set (has been assigned a value) and false otherwise */
  public boolean isSetIpAdd() {
    return this.ipAdd != null;
  }

  public void setIpAddIsSet(boolean value) {
    if (!value) {
      this.ipAdd = null;
    }
  }

  public short getPort() {
    return this.port;
  }

  public TSearchServer setPort(short port) {
    this.port = port;
    setPortIsSet(true);
    return this;
  }

  public void unsetPort() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PORT_ISSET_ID);
  }

  /** Returns true if field port is set (has been assigned a value) and false otherwise */
  public boolean isSetPort() {
    return EncodingUtils.testBit(__isset_bitfield, __PORT_ISSET_ID);
  }

  public void setPortIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PORT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case KEY:
      if (value == null) {
        unsetKey();
      } else {
        setKey((Long)value);
      }
      break;

    case IP_ADD:
      if (value == null) {
        unsetIpAdd();
      } else {
        setIpAdd((String)value);
      }
      break;

    case PORT:
      if (value == null) {
        unsetPort();
      } else {
        setPort((Short)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case KEY:
      return getKey();

    case IP_ADD:
      return getIpAdd();

    case PORT:
      return getPort();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case KEY:
      return isSetKey();
    case IP_ADD:
      return isSetIpAdd();
    case PORT:
      return isSetPort();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TSearchServer)
      return this.equals((TSearchServer)that);
    return false;
  }

  public boolean equals(TSearchServer that) {
    if (that == null)
      return false;

    boolean this_present_key = true;
    boolean that_present_key = true;
    if (this_present_key || that_present_key) {
      if (!(this_present_key && that_present_key))
        return false;
      if (this.key != that.key)
        return false;
    }

    boolean this_present_ipAdd = true && this.isSetIpAdd();
    boolean that_present_ipAdd = true && that.isSetIpAdd();
    if (this_present_ipAdd || that_present_ipAdd) {
      if (!(this_present_ipAdd && that_present_ipAdd))
        return false;
      if (!this.ipAdd.equals(that.ipAdd))
        return false;
    }

    boolean this_present_port = true;
    boolean that_present_port = true;
    if (this_present_port || that_present_port) {
      if (!(this_present_port && that_present_port))
        return false;
      if (this.port != that.port)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_key = true;
    list.add(present_key);
    if (present_key)
      list.add(key);

    boolean present_ipAdd = true && (isSetIpAdd());
    list.add(present_ipAdd);
    if (present_ipAdd)
      list.add(ipAdd);

    boolean present_port = true;
    list.add(present_port);
    if (present_port)
      list.add(port);

    return list.hashCode();
  }

  @Override
  public int compareTo(TSearchServer other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetKey()).compareTo(other.isSetKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.key, other.key);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIpAdd()).compareTo(other.isSetIpAdd());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIpAdd()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ipAdd, other.ipAdd);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPort()).compareTo(other.isSetPort());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPort()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.port, other.port);
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
    StringBuilder sb = new StringBuilder("TSearchServer(");
    boolean first = true;

    sb.append("key:");
    sb.append(this.key);
    first = false;
    if (!first) sb.append(", ");
    sb.append("ipAdd:");
    if (this.ipAdd == null) {
      sb.append("null");
    } else {
      sb.append(this.ipAdd);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("port:");
    sb.append(this.port);
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

  private static class TSearchServerStandardSchemeFactory implements SchemeFactory {
    public TSearchServerStandardScheme getScheme() {
      return new TSearchServerStandardScheme();
    }
  }

  private static class TSearchServerStandardScheme extends StandardScheme<TSearchServer> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TSearchServer struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.key = iprot.readI64();
              struct.setKeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // IP_ADD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ipAdd = iprot.readString();
              struct.setIpAddIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PORT
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.port = iprot.readI16();
              struct.setPortIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TSearchServer struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(KEY_FIELD_DESC);
      oprot.writeI64(struct.key);
      oprot.writeFieldEnd();
      if (struct.ipAdd != null) {
        oprot.writeFieldBegin(IP_ADD_FIELD_DESC);
        oprot.writeString(struct.ipAdd);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PORT_FIELD_DESC);
      oprot.writeI16(struct.port);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TSearchServerTupleSchemeFactory implements SchemeFactory {
    public TSearchServerTupleScheme getScheme() {
      return new TSearchServerTupleScheme();
    }
  }

  private static class TSearchServerTupleScheme extends TupleScheme<TSearchServer> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TSearchServer struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetKey()) {
        optionals.set(0);
      }
      if (struct.isSetIpAdd()) {
        optionals.set(1);
      }
      if (struct.isSetPort()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetKey()) {
        oprot.writeI64(struct.key);
      }
      if (struct.isSetIpAdd()) {
        oprot.writeString(struct.ipAdd);
      }
      if (struct.isSetPort()) {
        oprot.writeI16(struct.port);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TSearchServer struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.key = iprot.readI64();
        struct.setKeyIsSet(true);
      }
      if (incoming.get(1)) {
        struct.ipAdd = iprot.readString();
        struct.setIpAddIsSet(true);
      }
      if (incoming.get(2)) {
        struct.port = iprot.readI16();
        struct.setPortIsSet(true);
      }
    }
  }

}

