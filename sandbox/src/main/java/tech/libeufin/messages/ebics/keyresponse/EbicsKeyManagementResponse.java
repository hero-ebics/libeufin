//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.18 at 06:44:54 PM CEST 
//


package tech.libeufin.messages.ebics.keyresponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="header">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="static">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="mutable" type="{urn:org:ebics:H004}KeyMgmntResponseMutableHeaderType"/>
 *                 &lt;/sequence>
 *                 &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="body">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DataTransfer" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="DataEncryptionInfo">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{urn:org:ebics:H004}DataEncryptionInfoType">
 *                                     &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="OrderData">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;urn:org:ebics:H004>OrderDataType">
 *                                     &lt;anyAttribute processContents='lax' namespace='urn:org:ebics:H004'/>
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ReturnCode">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;urn:org:ebics:H004>ReturnCodeType">
 *                           &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="TimestampBankParameter" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;urn:org:ebics:H004>TimestampType">
 *                           &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:org:ebics:H004}VersionAttrGroup"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "header",
    "body"
})
@XmlRootElement(name = "ebicsKeyManagementResponse")
public class EbicsKeyManagementResponse {

    @XmlElement(required = true)
    protected EbicsKeyManagementResponse.Header header;
    @XmlElement(required = true)
    protected EbicsKeyManagementResponse.Body body;
    @XmlAttribute(name = "Version", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String version;
    @XmlAttribute(name = "Revision")
    protected Integer revision;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link EbicsKeyManagementResponse.Header }
     *     
     */
    public EbicsKeyManagementResponse.Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link EbicsKeyManagementResponse.Header }
     *     
     */
    public void setHeader(EbicsKeyManagementResponse.Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link EbicsKeyManagementResponse.Body }
     *     
     */
    public EbicsKeyManagementResponse.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link EbicsKeyManagementResponse.Body }
     *     
     */
    public void setBody(EbicsKeyManagementResponse.Body value) {
        this.body = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the revision property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRevision() {
        return revision;
    }

    /**
     * Sets the value of the revision property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRevision(Integer value) {
        this.revision = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="DataTransfer" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="DataEncryptionInfo">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{urn:org:ebics:H004}DataEncryptionInfoType">
     *                           &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="OrderData">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;urn:org:ebics:H004>OrderDataType">
     *                           &lt;anyAttribute processContents='lax' namespace='urn:org:ebics:H004'/>
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ReturnCode">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;urn:org:ebics:H004>ReturnCodeType">
     *                 &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="TimestampBankParameter" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;urn:org:ebics:H004>TimestampType">
     *                 &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dataTransfer",
        "returnCode",
        "timestampBankParameter"
    })
    public static class Body {

        @XmlElement(name = "DataTransfer")
        protected EbicsKeyManagementResponse.Body.DataTransfer dataTransfer;
        @XmlElement(name = "ReturnCode", required = true)
        protected EbicsKeyManagementResponse.Body.ReturnCode returnCode;
        @XmlElement(name = "TimestampBankParameter")
        protected EbicsKeyManagementResponse.Body.TimestampBankParameter timestampBankParameter;

        /**
         * Gets the value of the dataTransfer property.
         * 
         * @return
         *     possible object is
         *     {@link EbicsKeyManagementResponse.Body.DataTransfer }
         *     
         */
        public EbicsKeyManagementResponse.Body.DataTransfer getDataTransfer() {
            return dataTransfer;
        }

        /**
         * Sets the value of the dataTransfer property.
         * 
         * @param value
         *     allowed object is
         *     {@link EbicsKeyManagementResponse.Body.DataTransfer }
         *     
         */
        public void setDataTransfer(EbicsKeyManagementResponse.Body.DataTransfer value) {
            this.dataTransfer = value;
        }

        /**
         * Gets the value of the returnCode property.
         * 
         * @return
         *     possible object is
         *     {@link EbicsKeyManagementResponse.Body.ReturnCode }
         *     
         */
        public EbicsKeyManagementResponse.Body.ReturnCode getReturnCode() {
            return returnCode;
        }

        /**
         * Sets the value of the returnCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link EbicsKeyManagementResponse.Body.ReturnCode }
         *     
         */
        public void setReturnCode(EbicsKeyManagementResponse.Body.ReturnCode value) {
            this.returnCode = value;
        }

        /**
         * Gets the value of the timestampBankParameter property.
         * 
         * @return
         *     possible object is
         *     {@link EbicsKeyManagementResponse.Body.TimestampBankParameter }
         *     
         */
        public EbicsKeyManagementResponse.Body.TimestampBankParameter getTimestampBankParameter() {
            return timestampBankParameter;
        }

        /**
         * Sets the value of the timestampBankParameter property.
         * 
         * @param value
         *     allowed object is
         *     {@link EbicsKeyManagementResponse.Body.TimestampBankParameter }
         *     
         */
        public void setTimestampBankParameter(EbicsKeyManagementResponse.Body.TimestampBankParameter value) {
            this.timestampBankParameter = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="DataEncryptionInfo">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{urn:org:ebics:H004}DataEncryptionInfoType">
         *                 &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="OrderData">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;urn:org:ebics:H004>OrderDataType">
         *                 &lt;anyAttribute processContents='lax' namespace='urn:org:ebics:H004'/>
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "dataEncryptionInfo",
            "orderData",
            "any"
        })
        public static class DataTransfer {

            @XmlElement(name = "DataEncryptionInfo", required = true)
            protected EbicsKeyManagementResponse.Body.DataTransfer.DataEncryptionInfo dataEncryptionInfo;
            @XmlElement(name = "OrderData", required = true)
            protected EbicsKeyManagementResponse.Body.DataTransfer.OrderData orderData;
            @XmlAnyElement(lax = true)
            protected List<Object> any;

            /**
             * Gets the value of the dataEncryptionInfo property.
             * 
             * @return
             *     possible object is
             *     {@link EbicsKeyManagementResponse.Body.DataTransfer.DataEncryptionInfo }
             *     
             */
            public EbicsKeyManagementResponse.Body.DataTransfer.DataEncryptionInfo getDataEncryptionInfo() {
                return dataEncryptionInfo;
            }

            /**
             * Sets the value of the dataEncryptionInfo property.
             * 
             * @param value
             *     allowed object is
             *     {@link EbicsKeyManagementResponse.Body.DataTransfer.DataEncryptionInfo }
             *     
             */
            public void setDataEncryptionInfo(EbicsKeyManagementResponse.Body.DataTransfer.DataEncryptionInfo value) {
                this.dataEncryptionInfo = value;
            }

            /**
             * Gets the value of the orderData property.
             * 
             * @return
             *     possible object is
             *     {@link EbicsKeyManagementResponse.Body.DataTransfer.OrderData }
             *     
             */
            public EbicsKeyManagementResponse.Body.DataTransfer.OrderData getOrderData() {
                return orderData;
            }

            /**
             * Sets the value of the orderData property.
             * 
             * @param value
             *     allowed object is
             *     {@link EbicsKeyManagementResponse.Body.DataTransfer.OrderData }
             *     
             */
            public void setOrderData(EbicsKeyManagementResponse.Body.DataTransfer.OrderData value) {
                this.orderData = value;
            }

            /**
             * Gets the value of the any property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the any property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAny().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Element }
             * {@link Object }
             * 
             * 
             */
            public List<Object> getAny() {
                if (any == null) {
                    any = new ArrayList<Object>();
                }
                return this.any;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{urn:org:ebics:H004}DataEncryptionInfoType">
             *       &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class DataEncryptionInfo
                extends DataEncryptionInfoType
            {

                @XmlAttribute(name = "authenticate", required = true)
                protected boolean authenticate;

                /**
                 * Gets the value of the authenticate property.
                 * 
                 */
                public boolean isAuthenticate() {
                    return authenticate;
                }

                /**
                 * Sets the value of the authenticate property.
                 * 
                 */
                public void setAuthenticate(boolean value) {
                    this.authenticate = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;urn:org:ebics:H004>OrderDataType">
             *       &lt;anyAttribute processContents='lax' namespace='urn:org:ebics:H004'/>
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class OrderData {

                @XmlValue
                protected byte[] value;
                @XmlAnyAttribute
                private Map<QName, String> otherAttributes = new HashMap<QName, String>();

                /**
                 * Datentyp für binäre Auftragsdaten (komprimiert, verschlüsselt und kodiert).
                 * 
                 * @return
                 *     possible object is
                 *     byte[]
                 */
                public byte[] getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     byte[]
                 */
                public void setValue(byte[] value) {
                    this.value = value;
                }

                /**
                 * Gets a map that contains attributes that aren't bound to any typed property on this class.
                 * 
                 * <p>
                 * the map is keyed by the name of the attribute and 
                 * the value is the string value of the attribute.
                 * 
                 * the map returned by this method is live, and you can add new attribute
                 * by updating the map directly. Because of this design, there's no setter.
                 * 
                 * 
                 * @return
                 *     always non-null
                 */
                public Map<QName, String> getOtherAttributes() {
                    return otherAttributes;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;urn:org:ebics:H004>ReturnCodeType">
         *       &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class ReturnCode {

            @XmlValue
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String value;
            @XmlAttribute(name = "authenticate", required = true)
            protected boolean authenticate;

            /**
             * Datentyp für Antwortcodes.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the authenticate property.
             * 
             */
            public boolean isAuthenticate() {
                return authenticate;
            }

            /**
             * Sets the value of the authenticate property.
             * 
             */
            public void setAuthenticate(boolean value) {
                this.authenticate = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;urn:org:ebics:H004>TimestampType">
         *       &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class TimestampBankParameter {

            @XmlValue
            protected XMLGregorianCalendar value;
            @XmlAttribute(name = "authenticate", required = true)
            protected boolean authenticate;

            /**
             * Datentyp für Zeitstempel.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setValue(XMLGregorianCalendar value) {
                this.value = value;
            }

            /**
             * Gets the value of the authenticate property.
             * 
             */
            public boolean isAuthenticate() {
                return authenticate;
            }

            /**
             * Sets the value of the authenticate property.
             * 
             */
            public void setAuthenticate(boolean value) {
                this.authenticate = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="static">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="mutable" type="{urn:org:ebics:H004}KeyMgmntResponseMutableHeaderType"/>
     *       &lt;/sequence>
     *       &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "_static",
        "mutable"
    })
    public static class Header {

        @XmlElement(name = "static", required = true)
        protected EbicsKeyManagementResponse.Header.Static _static;
        @XmlElement(required = true)
        protected KeyMgmntResponseMutableHeaderType mutable;
        @XmlAttribute(name = "authenticate", required = true)
        protected boolean authenticate;

        /**
         * Gets the value of the static property.
         * 
         * @return
         *     possible object is
         *     {@link EbicsKeyManagementResponse.Header.Static }
         *     
         */
        public EbicsKeyManagementResponse.Header.Static getStatic() {
            return _static;
        }

        /**
         * Sets the value of the static property.
         * 
         * @param value
         *     allowed object is
         *     {@link EbicsKeyManagementResponse.Header.Static }
         *     
         */
        public void setStatic(EbicsKeyManagementResponse.Header.Static value) {
            this._static = value;
        }

        /**
         * Gets the value of the mutable property.
         * 
         * @return
         *     possible object is
         *     {@link KeyMgmntResponseMutableHeaderType }
         *     
         */
        public KeyMgmntResponseMutableHeaderType getMutable() {
            return mutable;
        }

        /**
         * Sets the value of the mutable property.
         * 
         * @param value
         *     allowed object is
         *     {@link KeyMgmntResponseMutableHeaderType }
         *     
         */
        public void setMutable(KeyMgmntResponseMutableHeaderType value) {
            this.mutable = value;
        }

        /**
         * Gets the value of the authenticate property.
         * 
         */
        public boolean isAuthenticate() {
            return authenticate;
        }

        /**
         * Sets the value of the authenticate property.
         * 
         */
        public void setAuthenticate(boolean value) {
            this.authenticate = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Static {


        }

    }

}