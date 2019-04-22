import java.util.Date;
import java.util.List;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Patient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Hl7ToFhirTest {

    private Hl7ToFhir hl7ToFhir;

    @Before
    public void setUp() {
        hl7ToFhir = new Hl7ToFhir();
    }

    @Test
    public void mapHl7toFhir() {
        final Patient patient = hl7ToFhir.mapHl7toFhir();
        Assert.assertNotNull(patient);
        Assert.assertNotNull(patient.getIdentifierFirstRep());
        Assert.assertNotNull(patient.getName());
        Assert.assertNotNull(patient.getTelecomFirstRep());
        Assert.assertNotNull(patient.getGender());
        Assert.assertNotNull(patient.getBirthDate());
        Assert.assertNotNull(patient.getAddressFirstRep());
        Assert.assertNotNull(patient.getMaritalStatus());
    }

    @Test
    public void getIdentifier() {
        final Identifier identifier = hl7ToFhir.getIdentifier();
        Assert.assertEquals("22042019", identifier.getValue());
        //Not ignoring case on purpose.
        Assert.assertEquals("hl7v2ToFhir", identifier.getSystem());
    }

    @Test
    public void getName() {
        final HumanName name = hl7ToFhir.getName();
        Assert.assertNotNull(name);
        Assert.assertEquals("dr", name.getPrefixAsSingleString().toLowerCase());
        Assert.assertEquals("jane", name.getGivenAsSingleString().toLowerCase());
        Assert.assertEquals("doe", name.getFamily().toLowerCase());
    }

    @Test
    public void getTelecom() {
        final List<ContactPoint> contactPointList = hl7ToFhir.getTelecom();
        Assert.assertEquals(3, contactPointList.size());

        final ContactPoint contactPointHome = getContactPoint(contactPointList,
                                                              ContactPointUse.HOME,
                                                              ContactPointSystem.PHONE);
        Assert.assertNotNull(contactPointHome);
        Assert.assertEquals("+38631111000", contactPointHome.getValue());

        final ContactPoint contactPointWork = getContactPoint(contactPointList,
                                                              ContactPointUse.WORK,
                                                              ContactPointSystem.PHONE);

        Assert.assertNotNull(contactPointWork);
        Assert.assertEquals("+38631000111", contactPointWork.getValue());

        final ContactPoint contactPointEmail = getContactPoint(contactPointList,
                                                               ContactPointUse.MOBILE,
                                                               ContactPointSystem.EMAIL);
        Assert.assertNotNull(contactPointEmail);
        Assert.assertEquals("hl7v2tofhir@mail.com", contactPointEmail.getValue().toLowerCase());
    }

    @Test
    public void getGender() {
        final AdministrativeGender gender = hl7ToFhir.getGender();
        Assert.assertNotNull(gender);
        Assert.assertEquals(AdministrativeGender.FEMALE, gender);
    }

    @Test
    public void getBirthDate() {
        final Date birthDate = hl7ToFhir.getBirthDate();
        Assert.assertNotNull(birthDate);
        Assert.assertEquals("Thu Jan 01 06:31:40 CET 1970", birthDate.toString());
    }

    @Test
    public void getAddress() {
        final List<Address> addressList = hl7ToFhir.getAddress();
        Assert.assertNotNull(addressList);
        Assert.assertEquals(1, addressList.size());
        Assert.assertEquals("London", addressList.get(0).getCity());
        Assert.assertEquals("1", addressList.get(0).getPostalCode());
        Assert.assertEquals("The answer to life The Universe and everything 42",
                            addressList.get(0).getLine().get(0).getValueNotNull());
        Assert.assertEquals("England", addressList.get(0).getCountry());
    }

    private ContactPoint getContactPoint(final List<ContactPoint> contactPointList,
                                         final ContactPointUse contactPointUse,
                                         final ContactPointSystem contactPointSystem) {
        return contactPointList.stream()
                .filter(contactPoint -> contactPointUse.equals(contactPoint.getUse()) &&
                        contactPointSystem.equals(contactPoint.getSystem()))
                .findFirst()
                .orElse(null);
    }
}
