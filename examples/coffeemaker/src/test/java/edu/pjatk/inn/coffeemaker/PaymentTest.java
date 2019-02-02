package edu.pjatk.inn.coffeemaker;

import edu.pjatk.inn.coffeemaker.impl.CoffeeMaker;
import edu.pjatk.inn.coffeemaker.impl.DeliveryImpl;
import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.core.provider.rendezvous.ServiceJobber;
import sorcer.po.operator;
import sorcer.service.*;
import sorcer.service.Domain;

import static edu.pjatk.inn.coffeemaker.impl.Recipe.getRecipe;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static sorcer.co.operator.*;
import static sorcer.co.operator.inVal;
import static sorcer.eo.operator.*;
import static sorcer.eo.operator.result;
import static sorcer.mo.operator.*;
import static sorcer.mo.operator.result;
import static sorcer.po.operator.ent;
import static sorcer.po.operator.invoker;
import static sorcer.po.operator.srv;

@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/coffeemaker")
public class PaymentTest {
    private final static Logger logger = LoggerFactory.getLogger(CoffeeServiceTest.class);


    @Test
    public void testPayment() throws Exception {
        Exertion paymentCash = task("payment", sig("pay",Payment.class), context(
                inVal("pay/method", "cash"),
                inVal("pay/amountToPay",10),
                inVal("pay/amountAvail",20)
        ));
        paymentCash = exert(paymentCash);
        logger.info("getRecipes: " + context(paymentCash));
        assertEquals(value(context(paymentCash), "pay/rest"), 10);

    }
}
