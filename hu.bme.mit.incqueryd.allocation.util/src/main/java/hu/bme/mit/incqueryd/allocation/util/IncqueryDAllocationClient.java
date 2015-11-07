package hu.bme.mit.incqueryd.allocation.util;

import hu.bme.mit.incqueryd.allocation.data.Allocation;
import hu.bme.mit.incqueryd.allocation.data.AllocationProcessInput;
import hu.bme.mit.incqueryd.csp.stats.StatsUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IncqueryDAllocationClient {
	
	public static Allocation allocate(String recipeFile, String statsFile, boolean optimizeForCommunication) throws IOException{
		Map<String, Long> stats = StatsUtil.loadStats(statsFile);
		ReteProcesses processes = ReteProcessUtil.createProcessesFromRete(recipeFile, stats);

		ObjectMapper mapper = new ObjectMapper();
		
		AllocationProcessInput input = new AllocationProcessInput(processes.getEdges(), processes.getNodes(), optimizeForCommunication);
		String inputAsJson = mapper.writeValueAsString(input);
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		Allocation allocation = null;
		
		try {
		    HttpPost request = new HttpPost("http://localhost:8090/allocation");
		    StringEntity payload = new StringEntity(inputAsJson);
		    request.addHeader("content-type", "application/json");
		    request.addHeader("Accept","application/json");
		    request.setEntity(payload);
		    HttpResponse response = httpClient.execute(request);
		    InputStream content = response.getEntity().getContent();
		    
		    String jsonContent = IOUtils.toString(content, "UTF-8");
		    allocation = mapper.readValue(jsonContent, Allocation.class);
			
		} catch (Exception ex) {
		    System.err.println(ex.getMessage());
		} finally {
		    httpClient.close();
		}
		
		return allocation;
		
	}

}
