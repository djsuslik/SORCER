package edu.pjatk.inn.coffeemaker;

import sorcer.service.Context;
import sorcer.service.ContextException;

import java.rmi.RemoteException;

/**
 * Created by Maksym Karashchuk on 2/2/19.
 */
public interface Payment {

    public Context pay(Context context) throws RemoteException, ContextException;

}