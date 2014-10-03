package org.funny.pictures.generator;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.funny.pictures.generator.api.GeneratorException;
import org.funny.pictures.generator.api.ImageHandle;
import org.funny.pictures.generator.api.ThumbnailContext;
import org.funny.pictures.generator.api.ThumbnailGenerator;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.optigra.funnypictures.model.content.MimeType;

/**
 * Generator of thumbnails.
 * @author odisseus
 *
 */
public class BaseThumbnailGenerator implements ThumbnailGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseThumbnailGenerator.class);

	private MimeType outputFormat;

	private ConvertCmd convertCommand = new ConvertCmd();

	@Override
	public ImageHandle generate(final ThumbnailContext context) {
		LOG.debug("Generating a thumbnail from context: " + context.toString());

		Path templateInput = null;
		Path result = null;
		try {			
			
			templateInput = toTempFile(context.getTemplateInputStream(), context.getInputMimeType().getExtension());			
			result = Files.createTempFile("caption", outputFormat.getExtension());
			
			Dimension targetDimension = context.getThumbnailDimension();
			IMOperation op = new IMOperation();
			op.addImage();
			op.thumbnail(targetDimension.width, targetDimension.height, '^');
			op.gravity("center");
			op.extent(targetDimension.width, targetDimension.height);
			op.unsharp(0.0, 10.0);
			op.addImage();

			Object[] args = new String[] {templateInput.toString(), result.toString()};
			LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);

			convertCommand.run(op, args);
			
			InputStream resultStream = new FileInputStream(result.toString());
			LOG.debug("Thumbnail generated");

			return new ImageHandle(resultStream, outputFormat);

		} catch (IOException | IM4JavaException | InterruptedException e) {
			LOG.error("Internal generation procedure failed", e);
			throw new GeneratorException(e.getMessage(), e);
		} finally {
			try {
				LOG.debug("Deleting temporary files");
				if (templateInput != null) {
					Files.deleteIfExists(templateInput);
				}
				LOG.debug("Temporary files deleted");
			} catch (Exception e) {
				LOG.error("Error file file delete", e);
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
		Path result = Files.createTempFile("template", extensionSuffix);
		Files.copy(istream, result, StandardCopyOption.REPLACE_EXISTING);
		LOG.debug("Created a temporary file: " + result.toAbsolutePath().toString());
		return result;
	}
	
	/**
	 * Returns MIME type of output images.
	 * @return MIME type of output images
	 */
	public MimeType getOutputFormat() {
		return outputFormat;
	}

	/**
	 * Sets MIME type of output images.
	 * @param outputFormat MIME type of output images
	 */
	public void setOutputFormat(final MimeType outputFormat) {
		this.outputFormat = outputFormat;
		LOG.trace("Output format set to " + outputFormat.getType());
	}

}
