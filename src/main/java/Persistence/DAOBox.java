package Persistence;

import Entities.Box;

import java.util.ArrayList;

/**
 * Created by krirs on 20.03.2017.
 */
public interface DAOBox {

    /*creates an invoice according to arguments passed
  returns true in case of success, false otherwise
   */
    boolean create(int id, String pic, String name, double dailyRate, boolean window, boolean isDeleted, String comment);

    /*updates the invoice if input and its key match
     returns true in case of success, false otherwise
      */
    boolean update(int id, String pic, String name, double dailyRate, boolean window, boolean isDeleted, String comment);

    /* deletes the invoice if input and its key match
     returns true in case of success, false otherwise
     */
    boolean delete(int id);

    /*finds the Box if input and id match
    returns the Box if found, null otherwise
     */
    Box find(int id);

    ArrayList<Box> findAllBoxes();
}