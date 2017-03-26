package TestDAO;

import Persistence.ConnectionFactory;
import Persistence.DAOReservation;
import Persistence.JdbcReservation;
import org.junit.After;
import org.junit.Before;

/**
 * Created by krirs on 25.03.2017.
 */
public class JdbcReservationDAOTest extends  AbstractReservationDAOTest {

        @Before
        public void setUp() throws Exception {

            DAOReservation reservationDAO= new JdbcReservation();
            setReservationDAO(reservationDAO);
            ConnectionFactory.getConnection().setAutoCommit(false);



        }

        @After
        public void tearDown() throws Exception {
            ConnectionFactory.getConnection().commit();
            ConnectionFactory.getConnection().rollback();
            ConnectionFactory.getConnection().setAutoCommit(true);
        }
}
