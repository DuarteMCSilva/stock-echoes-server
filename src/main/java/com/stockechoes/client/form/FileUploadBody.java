package com.stockechoes.client.form;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;
import java.util.List;

public class FileUploadBody {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;

    @FormParam("columnNames")
    @PartType(MediaType.APPLICATION_JSON)
    public ColumnsMetadata columnMetaData;


    public List<String> getColumnNameList() {
        return List.of(
                columnMetaData.date, columnMetaData.quantity,
                columnMetaData.totalPrice, columnMetaData.fees,
                columnMetaData.productName
        );
    }


    public static class ColumnsMetadata {
        public String date;
        public String quantity;
        public String totalPrice;
        public String fees;
        public String productName;

    }
}
