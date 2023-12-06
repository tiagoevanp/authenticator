package br.com.confincp.computations;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import br.com.confincp.computations.helpers.DealEntity;
import br.com.confincp.computations.helpers.DealJson;
import br.com.confincp.computations.helpers.NumberValue;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/deal")
public class Deal {
    @POST
    public DealEntity calculate(DealJson deal) {
        DealEntity dealEntity = new DealEntity();

        dealEntity.id = deal.id;
        dealEntity.name = deal.name;
        dealEntity.supplier_id = deal.supplier_id;
        dealEntity.profit_percentage = deal.profit_percentage;
        dealEntity.quantity = deal.purchase.quantity;

        DecimalFormat df = new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.US));

        String cost = df.format(
                Double.parseDouble(deal.purchase.value.integer + "." +
                        deal.purchase.value.decimal)
                        / deal.purchase.quantity);

        String[] costArr = cost.split("\\.");

        int costInteger = Integer.parseInt(costArr[0]);
        int costDecimal = Integer.parseInt(costArr[1]);

        dealEntity.cost = new NumberValue();
        dealEntity.cost.integer = costInteger;
        dealEntity.cost.decimal = costDecimal;
        dealEntity.cost.type = deal.purchase.value.type;

        double profitPercentage = Double.parseDouble(
                deal.profit_percentage.integer + "." + deal.profit_percentage.decimal)
                / 100;

        double salePrice = Double.parseDouble(cost) * profitPercentage;

        double creditCardFee = 0;

        if (deal.credit_card_fee.value.decimal != 0) {
            creditCardFee = salePrice * Double.parseDouble(
                    deal.credit_card_fee.value.integer + "." + deal.credit_card_fee.value.decimal) /
                    100;
        } else {
            creditCardFee = salePrice *
                    Double.parseDouble(deal.credit_card_fee.value.integer + "") / 100;
        }

        double price = salePrice + creditCardFee;

        String[] priceArr = df.format(price).split("\\.");

        int priceInteger = Integer.parseInt(priceArr[0]);
        int priceDecimal = Integer.parseInt(priceArr[1]);

        dealEntity.price = new NumberValue();
        dealEntity.price.integer = priceInteger;
        dealEntity.price.decimal = priceDecimal;
        dealEntity.price.type = deal.purchase.value.type;

        return dealEntity;
    }
}
