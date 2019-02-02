package edu.pjatk.inn.coffeemaker.impl;


import java.rmi.RemoteException;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * Created by Maksym Karashchuk on 2/2/19.
 */
public class Payment implements edu.pjatk.inn.coffeemaker.Payment {
    private int paymentCount = 0;

    @Override
    public Context pay(Context context) throws RemoteException, ContextException {
        paymentCount++;

        String method = (String)context.getValue("pay/method");
        Integer amountToPay = (Integer) context.getValue("pay/amountToPay");

        if(method.equals("card")){
            context.putValue("pay/paid", true);
            context.putValue("pay/time_stamp", new Date().getDate());
        }else if (method.equals("cash")){
            Integer amountAvail = (Integer) context.getValue("pay/amountAvail");
            if(amountAvail.intValue()<amountToPay.intValue()){
                context.putValue("pay/rest", amountAvail.intValue());
                context.putValue("pay/paid", false);
                context.putValue("pay/time_stamp", new Date().getDate());
            }else{
                int rest = amountAvail.intValue()-amountToPay.intValue();
                context.putValue("pay/rest", rest);
                context.putValue("pay/paid", true);
                context.putValue("pay/time_stamp", new Date().getDate());
            }
        }else{
            return null;
        }

        return context;
    }
}