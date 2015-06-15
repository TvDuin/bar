package datastoragelayer;

import junit.framework.TestCase;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thomas on 15-6-2015.
 */
public class DatabaseConnectionTest extends TestCase {

    private DatabaseConnection connection;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        connection = new DatabaseConnection();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testOpenConnection() throws Exception {

        try {
            boolean expected = true;
            boolean actual = connection.openConnection();

            assertEquals(expected, actual);
        }

        catch(Exception e) {
            throw e;
        }
    }
}