package org.funny.pictures.generator.api;

import java.io.IOException;

public interface AdviceMemeGenerator {
	
	ImageHandle generate(AdviceMemeContext context) throws IOException, InterruptedException, GeneratorException;
	
}
