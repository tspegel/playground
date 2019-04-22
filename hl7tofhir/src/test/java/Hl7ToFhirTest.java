import lombok.Getter;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Hl7ToFhirTest {

    private Hl7ToFhir hl7ToFhir;

    @Getter
    private Patient patient;

    private final Organization organization = new Organization();

    @Before
    public void setUp() {
        hl7ToFhir = new Hl7ToFhir();

    }

    @Test
    public void getIdentifier() {
        final Identifier identifier = hl7ToFhir.getIdentifier();
        Assert.assertEquals("22042019", identifier.getValue());
        //Not ignoring case on purpose.
        Assert.assertEquals("hl7v2ToFhir", identifier.getSystem());
    }

    @Test
    public void getName(){

    }

    @Test
    public void getTelecom(){

    }
}
