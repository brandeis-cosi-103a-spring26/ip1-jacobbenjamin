package edu.brandeis.cosi103a.ip2;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for AutomationCard class.
 */
public class AutomationCardTest {

    @Test
    public void testMethodCardProperties() {
        AutomationCard method = AutomationCard.createMethod();
        assertEquals("Method", method.getName());
        assertEquals(2, method.getCost());
        assertEquals(1, method.getAutomationPoints());
        assertEquals(0, method.getCoinValue());
    }

    @Test
    public void testModuleCardProperties() {
        AutomationCard module = AutomationCard.createModule();
        assertEquals("Module", module.getName());
        assertEquals(5, module.getCost());
        assertEquals(3, module.getAutomationPoints());
        assertEquals(0, module.getCoinValue());
    }

    @Test
    public void testFrameworkCardProperties() {
        AutomationCard framework = AutomationCard.createFramework();
        assertEquals("Framework", framework.getName());
        assertEquals(8, framework.getCost());
        assertEquals(6, framework.getAutomationPoints());
        assertEquals(0, framework.getCoinValue());
    }

    @Test
    public void testAutomationCardToString() {
        AutomationCard method = AutomationCard.createMethod();
        assertEquals("Method", method.toString());
    }

    @Test
    public void testAutomationCardInheritance() {
        AutomationCard card = AutomationCard.createModule();
        assertTrue(card instanceof Card);
    }

    @Test
    public void testMultipleInstancesAreIndependent() {
        AutomationCard card1 = AutomationCard.createMethod();
        AutomationCard card2 = AutomationCard.createMethod();
        
        // Both should have same properties
        assertEquals(card1.getName(), card2.getName());
        assertEquals(card1.getCost(), card2.getCost());
        assertEquals(card1.getAutomationPoints(), card2.getAutomationPoints());
        
        // But they are different objects
        assertNotSame(card1, card2);
    }
}
