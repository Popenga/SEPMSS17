package TestDAO;

import Entities.Box;
import Persistence.DAOBox;
import Persistence.JdbcBox;
import Service.SimpleService;
import Service.WendySimpleService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krirs on 24.03.2017.
 */
public class simpleTest {
    public static void main(String[] args) {
        DAOBox box = new JdbcBox();
        SimpleService simpleService = new WendySimpleService();

        ArrayList<Box> boxes = box.findAllBoxes();
        for(Box box1: boxes){
            System.out.println(box1);
        }
    }
}
