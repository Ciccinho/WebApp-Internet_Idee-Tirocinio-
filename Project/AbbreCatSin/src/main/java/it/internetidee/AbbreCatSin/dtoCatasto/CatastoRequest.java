package it.internetidee.AbbreCatSin.dtoCatasto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatastoRequest {

    private String tipoSogg;
    private String codFisc;
}
