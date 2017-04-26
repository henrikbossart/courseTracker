package gruppe087.coursetracker;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by petercbu on 25.04.2017.
 */

public class PostitionTest extends TestCase {

    Position pos;
    Position pos2;
    Position pos3;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        pos = new Position(0);
        pos2 = new Position(0);
        pos3 = new Position(1);

    }

    @Test
    public void testHashCode(){
        int hashCode = pos.hashCode();
        assertEquals(pos.position, hashCode);
        assertEquals(0, hashCode);
    }

    @Test
    public void testEquals(){
        //Test two equal objects
        assertTrue(pos.equals(pos2));

        //Test two different objects
        assertFalse(pos.equals(pos3));
    }

    @Test
    public void testGetValue(){
        //Test if getValue returns the correct value
        assertEquals(0, pos.getValue());
    }

    @Test
    public void testCompareTo(){
        //Test if two equal objects get 0
        assertEquals(0, pos.compareTo(pos2));

        //Test if return is negative, when compared to a position with higher value
        assertTrue(pos.compareTo(pos3) < 0);
    }



    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
