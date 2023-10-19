package com.tradehub.fmr.modals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Instrument {
    String instrumentId;
    String externalIdType;
    String externalId;
    String categoryId;
    String instrumentDescription;
    Long maxQuantity;
    Long minQuantity;
}
