package com.optigra.funnypictures.generator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.input.AutoCloseInputStream;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.optigra.funnypictures.generator.api.GeneratorException;
import com.optigra.funnypictures.generator.api.ImageHandle;
import com.optigra.funnypictures.generator.api.ImageLabellingContext;
import com.optigra.funnypictures.generator.api.LabelledImageGenerator;
import com.optigra.funnypictures.model.content.MimeType;

/**
 * Generator of labelled images.
 * @author odisseus
 *
 */
public class BaseLabelledImageGenerator implements LabelledImageGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseAdviceMemeGenerator.class);
	
	private static final String TMP_FILE_NAME_BASE = "tmp_labelling";

	private String labelBackgroundColor = "khaki";
	
	private ConvertCmd convertCommand = new ConvertCmd();

	@Override
	public ImageHandle generate(final ImageLabellingContext context) {
		LOG.debug("Generating a labelled image from context: " + context.toString());

		InputStream originalInputStream = null;
		Path source = null;
		Path result = null;
		try {			
			
			String imageFileExtension = context.getInputMimeType().getExtension();
			
			originalInputStream = context.getSourceInputStream();
			source = toTempFile(originalInputStream, imageFileExtension);			
			result = Files.createTempFile("labelled", imageFileExtension);
			
			IMOperation op = new IMOperation();
			op.addImage();
			op.background(labelBackgroundColor);
			op.addImage("label:" + context.getAnnotationText());
			op.gravity("east");
			op.append();
			op.addImage();

			Object[] args = new String[] {source.toString(), result.toString()};
			LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);

			convertCommand.run(op, args);
			
			InputStream resultStream = new AutoCloseInputStream(new FileInputStream(result.toString()));
			LOG.debug("Labelled image generated");

			return new ImageHandle(resultStream, MimeType.fromExtension(imageFileExtension));

		} catch (IOException | IM4JavaException | InterruptedException e) {
			LOG.error("Internal generation procedure failed", e);
			throw new GeneratorException(e.getMessage(), e);
		} finally {
			try {
				if (originalInputStream != null) {
					originalInputStream.close();
				}
				LOG.debug("Deleting temporary files");
				if (source != null) {
					Files.deleteIfExists(source);
				}
				LOG.debug("Temporary files deleted");
			} catch (Exception e) {
				LOG.error("Could not delete temporary file", e);
			}
		}
	}
	
	/**
	 * Writes data from an input stream to temporary file.
	 * @param istream input stream
	 * @param extensionSuffix extension of file to generate
	 * @return path to generated file
	 * @throws IOException when an IO problem occurs
	 */
	private Path toTempFile(final InputStream istream, final String extensionSuffix) throws IOException {
		Path result = Files.createTempFile(TMP_FILE_NAME_BASE, extensionSuffix);
		long bytesCopied = Files.copy(istream, result, StandardCopyOption.REPLACE_EXISTING);
		
		if (bytesCopied == 0) {
			LOG.warn("The input stream was empty");
		}
		LOG.debug("Created a temporary file: %1$s Size: %2$d bytes ", result.toAbsolutePath().toString(), bytesCopied);
		
		return result;
	}

}
