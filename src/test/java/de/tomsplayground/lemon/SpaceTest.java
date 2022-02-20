package de.tomsplayground.lemon;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class SpaceTest {

	private JsonMapper mapper = new JsonMapper();

	@Test
	public void parseSpacesList() throws IOException {
		InputStream stream = SpaceTest.class.getResourceAsStream("/spaceListAnswer.json");
		String spacesJson = IOUtils.toString(stream, StandardCharsets.UTF_8);

		SpaceList spaceList = mapper.getMapper().readValue(spacesJson, SpaceList.class);
		
		assertEquals(2, spaceList.results.size());
		assertEquals(2, (int)spaceList.total);
		Space space = spaceList.results.get(0);
		assertEquals("sp_qyDZLFF77V0NR5WwXHTP3yDm2hpJfbC8VF", space.id);
		assertEquals(new BigDecimal("10000.0000"), space.getRiskLimitDecimal());
	}

}
