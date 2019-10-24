//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.22 at 11:07:53 AM CEST 
//


package tech.libeufin.messages.ebics.response;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


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
 *                   &lt;element name="static" type="{urn:org:ebics:H004}ResponseStaticHeaderType"/>
 *                   &lt;element name="mutable" type="{urn:org:ebics:H004}ResponseMutableHeaderType"/>
 *                 &lt;/sequence>
 *                 &lt;attGroup ref="{urn:org:ebics:H004}AuthenticationMarker"/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{urn:org:ebics:H004}AuthSignature"/>
 *         &lt;element name="body">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DataTransfer" type="{urn:org:ebics:H004}DataTransferResponseType" minOccurs="0"/>
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
 *       &lt;anyAttribute namespace='urn:org:ebics:H004'/>
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
    "authSignature",
    "body"
})
@XmlRootElement(name = "ebicsResponse")
public class EbicsResponse {

    @XmlElement(required = true)
    protected EbicsResponse.Header header;
    @XmlElement(name = "AuthSignature", required = true)
    protected SignatureType authSignature;
    @XmlElement(required = true)
    protected EbicsResponse.Body body;
    @XmlAttribute(name = "Version", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String version;
    @XmlAttribute(name = "Revision")
    protected Integer revision;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link EbicsResponse.Header }
     *     
     */
    public EbicsResponse.Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link EbicsResponse.Header }
     *     
     */
    public void setHeader(EbicsResponse.Header value) {
        this.header = value;
    }

    /**
     * Authentication signature.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getAuthSignature() {
        return authSignature;
    }

    /**
     * Sets the value of the authSignature property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setAuthSignature(SignatureType value) {
        this.authSignature = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link EbicsResponse.Body }
     *     
     */
    public EbicsResponse.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link EbicsResponse.Body }
     *     
     */
    public void setBody(EbicsResponse.Body value) {
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
     *         &lt;element name="DataTransfer" type="{urn:org:ebics:H004}DataTransferResponseType" minOccurs="0"/>
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
        protected DataTransferResponseType dataTransfer;
        @XmlElement(name = "ReturnCode", required = true)
        protected EbicsResponse.Body.ReturnCode returnCode;
        @XmlElement(name = "TimestampBankParameter")
        protected EbicsResponse.Body.TimestampBankParameter timestampBankParameter;

        /**
         * Gets the value of the dataTransfer property.
         * 
         * @return
         *     possible object is
         *     {@link DataTransferResponseType }
         *     
         */
        public DataTransferResponseType getDataTransfer() {
            return dataTransfer;
        }

        /**
         * Sets the value of the dataTransfer property.
         * 
         * @param value
         *     allowed object is
         *     {@link DataTransferResponseType }
         *     
         */
        public void setDataTransfer(DataTransferResponseType value) {
            this.dataTransfer = value;
        }

        /**
         * Gets the value of the returnCode property.
         * 
         * @return
         *     possible object is
         *     {@link EbicsResponse.Body.ReturnCode }
         *     
         */
        public EbicsResponse.Body.ReturnCode getReturnCode() {
            return returnCode;
        }

        /**
         * Sets the value of the returnCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link EbicsResponse.Body.ReturnCode }
         *     
         */
        public void setReturnCode(EbicsResponse.Body.ReturnCode value) {
            this.returnCode = value;
        }

        /**
         * Gets the value of the timestampBankParameter property.
         * 
         * @return
         *     possible object is
         *     {@link EbicsResponse.Body.TimestampBankParameter }
         *     
         */
        public EbicsResponse.Body.TimestampBankParameter getTimestampBankParameter() {
            return timestampBankParameter;
        }

        /**
         * Sets the value of the timestampBankParameter property.
         * 
         * @param value
         *     allowed object is
         *     {@link EbicsResponse.Body.TimestampBankParameter }
         *     
         */
        public void setTimestampBankParameter(EbicsResponse.Body.TimestampBankParameter value) {
            this.timestampBankParameter = value;
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
     *         &lt;element name="static" type="{urn:org:ebics:H004}ResponseStaticHeaderType"/>
     *         &lt;element name="mutable" type="{urn:org:ebics:H004}ResponseMutableHeaderType"/>
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
        protected ResponseStaticHeaderType _static;
        @XmlElement(required = true)
        protected ResponseMutableHeaderType mutable;
        @XmlAttribute(name = "authenticate", required = true)
        protected boolean authenticate;

        /**
         * Gets the value of the static property.
         * 
         * @return
         *     possible object is
         *     {@link ResponseStaticHeaderType }
         *     
         */
        public ResponseStaticHeaderType getStatic() {
            return _static;
        }

        /**
         * Sets the value of the static property.
         * 
         * @param value
         *     allowed object is
         *     {@link ResponseStaticHeaderType }
         *     
         */
        public void setStatic(ResponseStaticHeaderType value) {
            this._static = value;
        }

        /**
         * Gets the value of the mutable property.
         * 
         * @return
         *     possible object is
         *     {@link ResponseMutableHeaderType }
         *     
         */
        public ResponseMutableHeaderType getMutable() {
            return mutable;
        }

        /**
         * Sets the value of the mutable property.
         * 
         * @param value
         *     allowed object is
         *     {@link ResponseMutableHeaderType }
         *     
         */
        public void setMutable(ResponseMutableHeaderType value) {
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

    }

}