package br.com.confincp.computations.helpers;

import java.util.List;

public class ProductEntity {
    public String id;
    public String name;
    public Purchase purchase;
    public NumberValue profit_percentage;
    public OptionType supplier_id;
    public List<OptionType> supply_id;
    public OptionType discount_id;
}
