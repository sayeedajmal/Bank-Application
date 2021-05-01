package com.Bank;

public class MiniTransaction {
    String Today;
    String Where;
    String Payment;
    String Ammount;
    /**
     * @param lDate
     * @param where
     * @param payment
     * @param ammount
     */
    /**
     * @param today
     * @param where
     * @param payment
     * @param string
     */
    public MiniTransaction(String today, String where, String payment, String string) {
        Today = today;
        Where = where;
        Payment = payment;
        Ammount = string;
    }
    /**
     * @return the today
     */
    public String getToday() {
        return Today;
    }
    /**
     * @param today the today to set
     */
    public void setToday(String today) {
        Today = today;
    }
    /**
     * @return the where
     */
    public String getWhere() {
        return Where;
    }
    /**
     * @param where the where to set
     */
    public void setWhere(String where) {
        Where = where;
    }
    /**
     * @return the payment
     */
    public String getPayment() {
        return Payment;
    }
    /**
     * @param payment the payment to set
     */
    public void setPayment(String payment) {
        Payment = payment;
    }
    /**
     * @return the ammount
     */
    public String getAmmount() {
        return Ammount;
    }
    /**
     * @param ammount the ammount to set
     */
    public void setAmmount(String ammount) {
        Ammount = ammount;
    }
   
}
    
