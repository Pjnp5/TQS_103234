/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(2);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size(), "add: elements count not as expected.");

        assertThrows(IllegalArgumentException.class, () -> setA.add(99), "add: adding element should have throw an IllegalArgumentException.");
        assertEquals(1, setA.size(), "add: elements count not as expected.");

        assertThrows(IllegalArgumentException.class, () -> setA.add(-5), "add: adding element should have throw an IllegalArgumentException.");
        assertEquals(1, setA.size(), "add: elements count not as expected.");

        setA.add(1);

        assertThrows(IllegalArgumentException.class, () -> setA.add(-5), "add: adding element should have throw an IllegalArgumentException.");
        assertEquals(2, setA.size(),"add: elements count not as expected.");

        assertThrows(IllegalArgumentException.class, () -> setB.add(11), "add: adding element should have throw an IllegalArgumentException.");
        assertFalse(setB.contains(11), "add: this element should not have been found on the set");
        assertEquals(6, setB.size(), "add: elements count not as expected.");



    }

    @Test
    void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};
        int[] elems2 = new int[]{10, 20, 20};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));

        assertThrows(IllegalArgumentException.class, () -> setA.add(elems2));
    }

    @Test
    void testSetIsEmpty() {
        assertTrue(setA.isEmpty());
        assertFalse(setB.isEmpty());
    }


    @Test
    void testIntersection() {
        assertTrue(setB.intersects(BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30})));
        assertFalse(setB.intersects(BoundedSetOfNaturals.fromArray(new int[]{5, 15, 25})));
        assertTrue(setB.intersects(setC));
        assertFalse(setC.intersects(setB));
    }

}
