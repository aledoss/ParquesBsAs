package com.example.ndiaz.parquesbsas.helpers;

import com.example.ndiaz.parquesbsas.model.Documento;

import java.util.List;

public class TipoDocumentoHelper {

    public TipoDocumentoHelper() {
    }

    public String[] getDocTypeArray(List<Documento> documentos) {
        String[] docTypes = new String[documentos.size()];
        for (int i = 0; i < documentos.size(); i++) {
            docTypes[i] = documentos.get(i).getTipoDocumento();
        }
        return docTypes;
    }


    public int getDocIdFromText(String docType, List<Documento> documentos) {
        int type = 0;

        for (Documento documento : documentos) {
            if (docType.equals(documento.getTipoDocumento())) {
                type = documento.getId();
            }
        }

        return type;
    }
}
