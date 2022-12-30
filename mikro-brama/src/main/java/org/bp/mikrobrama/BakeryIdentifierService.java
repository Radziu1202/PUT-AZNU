package org.bp.mikrobrama;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class BakeryIdentifierService {

    public String getBakingIdentifier() {
		return UUID.randomUUID().toString();
	}


}
