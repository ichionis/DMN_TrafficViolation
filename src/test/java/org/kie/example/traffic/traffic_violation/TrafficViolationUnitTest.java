package org.kie.example.traffic.traffic_violation;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNResult;

import gr.eurobank.pam.dmn.test.DMNBaseTest;

public class TrafficViolationUnitTest extends DMNBaseTest {

    private static final String DMN_NAMESPACE = "https://github.com/kiegroup/drools/kie-dmn/_A4BCA8B8-CF08-433F-93B2-A2598F19ECFF";
    private static final String DMN_MODEL = "Traffic Violation";

    private static final String DECISION_UNDER_TEST = "Should the driver be suspended?";


    public TrafficViolationUnitTest() {
        super(DMN_NAMESPACE, DMN_MODEL);
    }

    @Test
    public void driverShouldBeSuspended() {
        DMNContext input = getDMNRuntime().newContext();

        input.set("Driver", initDriver(false));
        input.set("Violation", initViolation(false));

        DMNResult dmnResult = evaluateAll(input);

        assertDecisionResult(dmnResult, DECISION_UNDER_TEST, "Yes");
    }

    @Test
    public void driverShouldNOTBeSuspended() {
        DMNContext input = getDMNRuntime().newContext();

        input.set("Driver", initDriver(true));
        input.set("Violation", initViolation(true));

        DMNResult dmnResult = evaluateAll(input);

        assertDecisionResult(dmnResult, DECISION_UNDER_TEST, "No");
    }

    // Inputs
    private Map<String, Object> initDriver(boolean noScenario) {
        Map<String, Object> driver = new HashMap<String,Object>();

        driver.put("Points", noScenario ? 10 : 15);

        return driver;
    }

    private Map<String, Object> initViolation(boolean noScenario) {
        Map<String, Object> violation = new HashMap<String,Object>();

        violation.put("Actual Speed", noScenario ? 120 : 140);
        violation.put("Speed Limit", 100);
        violation.put("Type", "speed");

        return violation;        
    }
}
