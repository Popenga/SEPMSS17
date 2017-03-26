package TestDAO;

import Persistence.DAOBox;
import org.junit.Test;
import Entities.Box;

import static org.junit.Assert.*;

/**
 * Created by krirs on 22.03.2017.
 */
public abstract class AbstractBoxDAOTest {
    protected DAOBox daoBox;

    protected void setBoxDAO(DAOBox daoBox){
        this.daoBox=daoBox;
    }

    @Test
    public void createBoxTest(){

        if(daoBox.find(1000)!=null){
            assertFalse(daoBox.create(1000,"ff","crang",1000,true,false, ""));
        }
        else{
            assertTrue(daoBox.create(1000,"ff","crang",1000,true,false,""));
        }
    }

    @Test
    public void createBoxWithoutName(){
        assertFalse(daoBox.create(1001,"ff","",1000,true,false,""));
    }

    public void createBoxFind(){
        daoBox.create(1001,"ff","mähä",1000,true,false,"");
        assertTrue(daoBox.find(1001).getName().equals(("mähä")));
    }

    @Test
    public void updateWithWrongID(){
        assertFalse(daoBox.update(1999,"ff","",1000,true,false,""));

    }

    @Test
    public void updateBoxTest(){
        daoBox.update(1000,"ff","buhu",1000,false,false,"");
        assertTrue(daoBox.find(1000).getName().equals("buhu"));
    }

    @Test
    public void findBoxesTest(){
        assertFalse(daoBox.findAllBoxes().isEmpty());
    }


    @Test
    public void deleteBoxTest(){
        assertTrue(daoBox.delete(1001));

    }

    @Test
    public void deleteWithWrongID(){
        assertFalse(daoBox.delete(1999));

    }

}