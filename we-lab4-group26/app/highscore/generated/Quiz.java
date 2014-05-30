
package highscore.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for quiz complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="quiz">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="overview" type="{}overview" minOccurs="0"/>
 *         &lt;element name="categories" type="{}categories" minOccurs="0"/>
 *         &lt;element name="questions" type="{}questions" minOccurs="0"/>
 *         &lt;element name="users" type="{}users" minOccurs="0"/>
 *         &lt;element name="games" type="{}games" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "quiz", propOrder = {
    "overview",
    "categories",
    "questions",
    "users",
    "games"
})
public class Quiz {

    protected Overview overview;
    protected Categories categories;
    protected Questions questions;
    protected Users users;
    protected Games games;

    /**
     * Gets the value of the overview property.
     * 
     * @return
     *     possible object is
     *     {@link Overview }
     *     
     */
    public Overview getOverview() {
        return overview;
    }

    /**
     * Sets the value of the overview property.
     * 
     * @param value
     *     allowed object is
     *     {@link Overview }
     *     
     */
    public void setOverview(Overview value) {
        this.overview = value;
    }

    /**
     * Gets the value of the categories property.
     * 
     * @return
     *     possible object is
     *     {@link Categories }
     *     
     */
    public Categories getCategories() {
        return categories;
    }

    /**
     * Sets the value of the categories property.
     * 
     * @param value
     *     allowed object is
     *     {@link Categories }
     *     
     */
    public void setCategories(Categories value) {
        this.categories = value;
    }

    /**
     * Gets the value of the questions property.
     * 
     * @return
     *     possible object is
     *     {@link Questions }
     *     
     */
    public Questions getQuestions() {
        return questions;
    }

    /**
     * Sets the value of the questions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Questions }
     *     
     */
    public void setQuestions(Questions value) {
        this.questions = value;
    }

    /**
     * Gets the value of the users property.
     * 
     * @return
     *     possible object is
     *     {@link Users }
     *     
     */
    public Users getUsers() {
        return users;
    }

    /**
     * Sets the value of the users property.
     * 
     * @param value
     *     allowed object is
     *     {@link Users }
     *     
     */
    public void setUsers(Users value) {
        this.users = value;
    }

    /**
     * Gets the value of the games property.
     * 
     * @return
     *     possible object is
     *     {@link Games }
     *     
     */
    public Games getGames() {
        return games;
    }

    /**
     * Sets the value of the games property.
     * 
     * @param value
     *     allowed object is
     *     {@link Games }
     *     
     */
    public void setGames(Games value) {
        this.games = value;
    }

}
