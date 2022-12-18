package org.bp.gate;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class BakeryIdentifierService {
    HashMap<String, TransactionIds> transactionIds = new HashMap<>();


    public static class TransactionIds {
        private String cakeId;
        private String breadId;

        public String getCakeId() {
            return cakeId;
        }

        public void setCakeId(String cakeId) {
            this.cakeId = cakeId;
        }

        public String getBreadId() {
            return breadId;
        }

        public void setBreadId(String breadId) {
            this.breadId = breadId;
        }


    }

    public String generateOrderId() {
        String orderId = UUID.randomUUID().toString();
        transactionIds.put(orderId, new TransactionIds());
        return orderId;
    }

    public void assignCakeOrderId(String id, String cakeId) {
        transactionIds.get(id).setCakeId(cakeId);
    }

    public void assignBreadId(String id, String breadId) {
        transactionIds.get(id).setBreadId(breadId);
    }

    public String getCakeOrderId(String id) {
        return transactionIds.get(id).getCakeId();
    }

    public String getBreadOrderId(String id) {
        return transactionIds.get(id).getBreadId();
    }


}
