package br.com.confincp.computations.helpers;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Purchase {
    public Date date;
    public NumberValue value;
    public NumberValue discount_value;
    public NumberValue discount_percentage;
    public int quantity;
}
