package Persistence;

import Entities.Invoice;

/**
 * Created by krirs on 19.03.2017.
 */
public interface DAOInvoice {

    /*creates an invoice according to arguments passed
    returns true in case of success, false otherwise
     */
    public boolean create (int id, double sum, String date, String customer, String address);

    /*updates the invoice if input and its key match
     returns true in case of success, false otherwise
      */
    public boolean update(int id, double sum, String date, String customer, String address);

   /* deletes the invoice if input and its key match
    returns true in case of success, false otherwise
    */
    public boolean delete(int id);

    /*finds the invoice if input and id match
    returns the invoice if found, null otherwise
     */
    public Invoice find(int id);


}
