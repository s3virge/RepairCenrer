package app.dao.handbooks.repair;

import org.junit.*;

import static org.junit.Assert.*;

public class RepairDaoTest {

    @BeforeClass
    public static void initSomeThing() {
        System.out.println("initSomeThing() was called");
    }

    @AfterClass
    public static void purgeSomeThing() {
        System.out.println("purgeSomeThing() was called");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("setup() was called");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("tearDown was called");
    }

    @Test
    public void insert() {
        System.out.println("insert() was called");
    }

    @Test
    public void updateMasterComments() {
        Assert.fail();
    }

    @Test
    public void updateDiagnosticResult() {
    }

    @Test
    public void updateRepairResult() {
    }

    @Test
    public void updateDeviceStatus() {
    }
}