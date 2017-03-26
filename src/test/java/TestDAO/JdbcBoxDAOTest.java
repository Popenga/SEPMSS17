package TestDAO;

import Persistence.ConnectionFactory;
import Persistence.DAOBox;
import Persistence.JdbcBox;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by krirs on 22.03.2017.
 */
public class JdbcBoxDAOTest extends AbstractBoxDAOTest {
    @Before
    public void setUp() throws Exception {

        DAOBox boxDAO= new JdbcBox();
        setBoxDAO(boxDAO);
        ConnectionFactory.getConnection().setAutoCommit(false);
        createBoxTest();
        createBoxWithoutName();
        createBoxFind();
        updateWithWrongID();
        updateBoxTest();
        findBoxesTest();
        deleteBoxTest();
        deleteWithWrongID();



    }

    @After
    public void tearDown() throws Exception {
        ConnectionFactory.getConnection().rollback();
        ConnectionFactory.getConnection().setAutoCommit(true);
    }

}