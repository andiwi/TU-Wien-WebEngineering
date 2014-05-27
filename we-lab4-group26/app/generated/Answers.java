
package generated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for answers complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="answers">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="player" type="{}player" maxOccurs="2"/>
 *       &lt;/sequence>
 *       &lt;attribute name="question" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "answers", propOrder = {
    "player"
})
public class Answers {

    @XmlElement(required = true)
    protected List<Player> player;
    @XmlAttribute(name = "question", required = true)
    protected BigDecimal question;

    /**
     * Gets the value of the player property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the player property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlayer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Player }
     * 
     * 
     */
    public List<Player> getPlayer() {
        if (player == null) {
            player = new ArrayList<Player>();
        }
        return this.player;
    }

    /**
     * Gets the value of the question property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuestion() {
        return question;
    }

    /**
     * Sets the value of the question property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuestion(BigDecimal value) {
        this.question = value;
    }

}
