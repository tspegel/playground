import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Patient;

public class Hl7ToFhir {

    /**
     * Transform PID part of hl7v2 message below to FHIR Patient (R4) resource.
     */

    private Patient mapHl7toFhir() {
        return null;
    }

    public Identifier getIdentifier() {
        return new Identifier().setValue("22042019").setSystem("hl7v2ToFhir");
    }

}
