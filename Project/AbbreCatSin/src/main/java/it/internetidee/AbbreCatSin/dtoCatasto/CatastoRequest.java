package it.internetidee.AbbreCatSin.dtoCatasto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CatastoRequest {

    private String ist;
    private String usr;
    private String pwd;
    private String prd;
    private String tipoSogg;
    private String codFisc;
}
