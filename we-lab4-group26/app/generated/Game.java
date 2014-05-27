
package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for game complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="game">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gamer" type="{}gamer" maxOccurs="2"/>
 *         &lt;element name="round" type="{}round" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "game", propOrder = {
    "gamer",
    "round"
})
public class Game {

    @XmlElement(required = true)
    protected List<Gamer> gamer;
    @XmlElement(required = true)
    protected List<Round> round;

    /**
     * Gets the value of the gamer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gamer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGamer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Gamer }
     * 
     * 
     */
    public List<Gamer> getGamer() {
        if (gamer == null) {
            gamer = new ArrayList<Gamer>();
        }
        return this.gamer;
    }

    /**
     * Gets the value of the round property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the round property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRound().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Round }
     * 
     * 
     */
    public List<Round> getRound() {
        if (round == null) {
            round = new ArrayList<Round>();
        }
        return this.round;
    }

}
