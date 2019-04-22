import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Patient;

public class Hl7ToFhir {

    /**
     * Map PID part of hl7v2 message below to FHIR Patient (R4) resource.
     */

    /**
     * Example of Hl7v2 message.
     * <p>
     * MSH|^~\&|SOME APP|GREAT ORMOND STREET HOSPITAL|SOME REC APP|UNIVERSITY COLLEGE
     * HOSPITAL|20190328144222||ADT^A04^ADT_A01|5641827|P|2.4|||AL|NE|EN|UTF-8|EN||
     * EVN|A04|20190328144213|||SOOSTW|20190328144213|
     * PID|1||22042019^^^CS^PI^^^~22042019^HL7V2TOFHIR~""^^^NLMINBIZA^PPN~""^^^NLRDW^DL~""^^^NLIND^CZ|025566180|Doe^Jane^^^DR^^L||19900301|F|NEW||The
     * answer to life The Universe and everything&42^""^London^^1^EN^M||+38631111000^PRN^PH~+38631000111^WPN^CP~^NET^Internet^hl7v2ToFhir@mail.com|||S|""||||||""|N|||||""|N|Y|NNNLD~NNWID
     * PD1|||
     * PV1||O|||||||||||||||||||||||||||||||||||||||||||
     * PV2|||""^""|""|||""||||||||||||||||||||||||||||||||||||||
     * IN1|1||""^^^LOCAL~""^^^UZOVI||""&""&""^^""^^""^NL^M|||||||""|||""|||||||||||||||||||||
     * IN2||||||||||||||||||||||||||||""|MMD
     */

    /**
     * Additional information:
     * When you can't read system property from message use "hl7v2ToFhir" as system.
     * When adding address, add whole street address into single Line (eg. Cesta na ključ 33, would go to one Line)
     * When adding Email Telecom set "Use" field to MOBILE (read FHIR documentation why)
     * When adding Marital Status set "code" as "Single"
     *
     */


    /**
     * Map PID part of hl7v2 message below to FHIR Patient (R4) resource.
     * @return FHIR R4 Patient
     */
    public Patient mapHl7toFhir() {
        final Patient patient = new Patient();
        patient.addIdentifier(getIdentifier());
        patient.addName(getName());
        patient.setTelecom(getTelecom());
        patient.setGender(getGender());
        patient.setBirthDate(getBirthDate());
        patient.setAddress(getAddress());
        patient.setMaritalStatus(getMaritalStatus());
        return patient;
    }

    public Identifier getIdentifier() {
        return new Identifier().setValue("22042019").setSystem("hl7v2ToFhir");
    }

    public HumanName getName() {
        return new HumanName()
                .addPrefix("Dr")
                .addGiven("Jane")
                .setFamily("Doe");
    }

    public List<ContactPoint> getTelecom() {
        final List<ContactPoint> telecom = new ArrayList<ContactPoint>();
        telecom.add(getContactPoint(ContactPointUse.HOME, ContactPointSystem.PHONE, "+38631111000"));
        telecom.add(getContactPoint(ContactPointUse.WORK, ContactPointSystem.PHONE, "+38631000111"));
        telecom.add(getContactPoint(ContactPointUse.MOBILE, ContactPointSystem.EMAIL, "hl7v2ToFhir@mail.com"));
        return telecom;
    }

    public ContactPoint getContactPoint(final ContactPointUse use, final ContactPointSystem system,
                                        final String value) {
        return new ContactPoint()
                .setUse(use)
                .setSystem(system)
                .setValue(value);
    }

    public AdministrativeGender getGender() {
        return AdministrativeGender.FEMALE;
    }

    public Date getBirthDate() {
        return new Date(19900301);
    }

    public List<Address> getAddress() {
        final List<Address> addressList = new ArrayList<Address>();
        final Address address = new Address()
                .setCity("London")
                .setCountry("England")
                .setPostalCode("1")
                .addLine("The answer to life The Universe and everything 42");
        addressList.add(address);
        return addressList;
    }

    public CodeableConcept getMaritalStatus() {
        return new CodeableConcept().addCoding(new Coding().setSystem("hl7v2ToFhir").setCode("Single"));
    }
}
