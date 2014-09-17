package org.funny.pictures.generator.api;

import java.io.IOException;

import org.im4java.core.IM4JavaException;

public interface AdviceMemeGenerator {
	
	ImageHandle generate(AdviceMemeContext context) throws IOException, InterruptedException, IM4JavaException;
	
}
