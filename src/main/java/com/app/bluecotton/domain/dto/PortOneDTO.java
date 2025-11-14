package com.app.bluecotton.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PortOneDTO {
    private int code;
    private String message;
    private Response response;

    @Data
    public static class Response{

        @JsonProperty("imp_uid")
        private String impUid;

        @JsonProperty("merchant_uid")
        private String merchantUid;

        @JsonProperty("amount")
        private Long amount;

        @JsonProperty("status")
        private String status;

        @JsonProperty("pg_provider")
        private String pgProvider;

        @JsonProperty("paid_amount")
        private Long paidAmount;

        @JsonProperty("cancelled_amount")
        private Long cancelledAmount;

        @JsonProperty("pg_tid")
        private String pgTid;

        @JsonProperty("pg_id")
        private String pgId;

        @JsonProperty("card_name")
        private String cardName;

        @JsonProperty("card_quota")
        private Integer cardQuota;

        @JsonProperty("receipt_url")
        private String receiptUrl;

        @JsonProperty("pay_method")
        private String payMethod;

        @JsonProperty("currency")
        private String currency;

        private Card card;
    }

    @Data
    public static class Card{

        @JsonProperty("issuer_code")
        private String issuerCode;

        @JsonProperty("number")
        private String number;

        @JsonProperty("card_type")
        private String cardType;
    }
}
