package org.funny.pictures.generator.api;

import java.io.OutputStream;

public interface AdviceMemeGenerator {
	
	OutputStream generate(AdviceMemeContext context);
	
}
