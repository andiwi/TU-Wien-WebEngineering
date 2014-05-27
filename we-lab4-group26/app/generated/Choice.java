
package generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="choice">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="nr" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="correct" type="{}correct" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "choice", propOrder = {
    "value"
})
public class Choice {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "nr", required = true)
    protected BigDecimal nr;
    @XmlAttribute(name = "correct")
    protected Correct correct;

    /**
     * Gets the value of the value property.
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
     * Gets the value of the nr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNr() {
        return nr;
    }

    /**
     * Sets the value of the nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNr(BigDecimal value) {
        this.nr = value;
    }

    /**
     * Gets the value of the correct property.
     * 
     * @return
     *     possible object is
     *     {@link Correct }
     *     
     */
    public Correct getCorrect() {
        return correct;
    }

    /**
     * Sets the value of the correct property.
     * 
     * @param value
     *     allowed object is
     *     {@link Correct }
     *     
     */
    public void setCorrect(Correct value) {
        this.correct = value;
    }

}
