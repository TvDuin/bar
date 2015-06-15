package datastoragelayer;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import presentationlayer.JFrameBar;

import static org.junit.Assert.*;

/**
 * Created by Maarten on 13-6-2015.
 */

public class JFrameBarTest {

    private JFrameBar frame;

    public JFrameBarTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        JFrameBar frame = new JFrameBar();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testOrderPayed() throws Exception {

        try {
            String expected = "De bestelling van tafel 01 is betaald.";
            String actual = frame.orderPaid(01, 01);         // frame.orderPayed(int tableID, int EmployeeID);
            assertEquals(expected, actual);
        }
        catch (Exception e){
            throw e;
        }
    }
}