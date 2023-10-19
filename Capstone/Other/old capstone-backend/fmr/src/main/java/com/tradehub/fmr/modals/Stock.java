package com.tradehub.fmr.modals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Stock {
    BigDecimal askPrice;
    BigDecimal bidPrice;
    BigDecimal priceTimestamp;
    Instrument instrument;
}
