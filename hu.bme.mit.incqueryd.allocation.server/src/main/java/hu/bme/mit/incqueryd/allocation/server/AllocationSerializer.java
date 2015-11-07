package hu.bme.mit.incqueryd.allocation.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.bme.mit.incqueryd.allocation.data.Allocation;

public class AllocationSerializer {

	public static String allocationToJson(Allocation allocation) {
		ObjectMapper mapper = new ObjectMapper();

		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(allocation);
		} catch (JsonProcessingException e) {
			return "{}";
		}

		return jsonInString;
	}

}
