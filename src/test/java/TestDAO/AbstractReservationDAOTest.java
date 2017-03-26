package TestDAO;

import Persistence.DAOReservation;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by krirs on 25.03.2017.
 */
public abstract class AbstractReservationDAOTest {

    protected DAOReservation daoReservation;

    protected void setReservationDAO(DAOReservation daoReservation){
        this.daoReservation=daoReservation;
    }

    @Test
    public void createReservationTest(){

        if(daoReservation.find(1000)!=null){
            assertFalse(daoReservation.create(1000,1,1,"crang","2017-03-25","2017-01-27", 2,23,"Willi",false));
        }
        else{
            assertTrue(daoReservation.create(1000,1,1,"crang","2017-03-25","2017-01-27", 2,23,"Willi",false));
        }
    }


    @Test
    public void createReservationSuccess(){
        assertTrue(daoReservation.create(4000,1,1,"crang","2017-03-25","2017-01-27", 2,23,"Willi",false));
    }

    @Test
    public void updateWithWrongID(){
        assertFalse(daoReservation.update(1999,1,1,"crang","2017-03-25","2017-01-27", 2,23,"Willi",false));

    }

    @Test
    public void updateReservationTest(){
        daoReservation.update(1000,1,1,"baki","2017-03-25","2017-01-27", 2,23,"Willi",false);
        assertTrue(daoReservation.find(1000).getCustomer().equals("baki"));
    }

    @Test
    public void findReservationTest(){
        assertFalse(daoReservation.findAllReservations().isEmpty());
    }

    @Test
    public void deleteReservationTest(){
        assertTrue(daoReservation.delete(1000));

    }

    @Test
    public void deleteWithWrongID(){
        assertFalse(daoReservation.delete(4400));

    }

}
