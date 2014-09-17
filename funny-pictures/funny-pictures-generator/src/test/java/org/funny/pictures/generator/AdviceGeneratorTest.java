package org.funny.pictures.generator;

import static org.junit.Assert.*;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.Test;


public class AdviceGeneratorTest {
	
	//FIXME create true automatic tests

	@Test
	public void testGenerate() throws Exception {
		AdviceContext context = new AdviceContext(
				FileSystems.getDefault().getPath("src", "test", "resources", "advice-dog.jpg"),
				"]})>Top caption'\"([<{", "''Bottom caption");
		
		Path output = (new AdviceGenerator()).generate(context);
		System.out.println(output.toAbsolutePath());
		
	}
	
}
