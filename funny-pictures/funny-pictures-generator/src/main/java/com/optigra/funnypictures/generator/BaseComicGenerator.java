package com.optigra.funnypictures.generator;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.apache.commons.io.input.AutoCloseInputStream;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.optigra.funnypictures.generator.api.ComicContext;
import com.optigra.funnypictures.generator.api.ComicGenerator;
import com.optigra.funnypictures.generator.api.GeneratorException;
import com.optigra.funnypictures.generator.api.ImageHandle;
import com.optigra.funnypictures.model.content.MimeType;

public class BaseComicGenerator implements ComicGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseThumbnailGenerator.class);

	private MimeType outputFormat;
	
	public static final MimeType DEFAULT_OUTPUT_FORMAT = MimeType.IMAGE_PNG_PNG;

	private final MimeType internalFormat = MimeType.IMAGE_XIMAGEMAGICK_MIFF;
	
	private static final String TMP_FILE_NAME_BASE = "tmp_comic";

	private ConvertCmd convertCommand = new ConvertCmd();
	
	private int blockRoundingRadius = 10;

	@Override
	public ImageHandle generate(final ComicContext context) {
		LOG.debug("Generating a comic from context: " + context.toString());

		InputStream originalInputStream = null;
		Path templateInput = null;
		Path workingCopy = null;
		Path result = null;
		
		try {						
			originalInputStream = context.getTemplateInputStream();
			templateInput = toTempFile(originalInputStream, context.getInputMimeType().getExtension());		
			
			workingCopy = convert(templateInput, internalFormat);
								
			for (Map.Entry<Rectangle, String> block : context.getTextBlocks().entrySet()) {
				insertTextBlock(workingCopy, block.getKey(), block.getValue());
			}
			
			result = convert(workingCopy, outputFormat);
			
			InputStream resultStream = new AutoCloseInputStream(new FileInputStream(result.toString()));
			LOG.debug("Comic generated");

			return new ImageHandle(resultStream, outputFormat);

		} catch (IOException | IM4JavaException | InterruptedException e) {
			LOG.error("Internal generation procedure failed", e);
			throw new GeneratorException(e.getMessage(), e);
		} finally {
			try {
				if (originalInputStream != null) {
					originalInputStream.close();
				}
				LOG.debug("Deleting temporary files");
				if (templateInput != null) {
					Files.deleteIfExists(templateInput);
				}
				if (workingCopy != null) {
					Files.deleteIfExists(workingCopy);
				}
				LOG.debug("Temporary files deleted");
			} catch (Exception e) {
				LOG.error("Could not delete temporary file", e);
			}
		}
	}
	
	private void insertTextBlock(final Path targetImage, final Rectangle position, final String caption) throws IOException, InterruptedException, IM4JavaException{
		IMOperation op = new IMOperation();
		
		op.addImage();
		op.fill("white");
		op.draw(String.format("roundrectangle %d,%d,%d,%d,%d,%d", 
				position.x, position.y,
				position.x + position.width, position.y + position.height,
				blockRoundingRadius, blockRoundingRadius
				));
		op.fill("black");
		op.background("transparent");
		op.gravity("center");
		op.size(position.width, position.height);
		op.caption(caption);
		op.geometry(position.width, position.height, position.x, position.y);
		op.gravity("none");
		op.composite();
		op.addImage();

		Object[] args = new String[] {targetImage.toString(), targetImage.toString()};
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);

		convertCommand.run(op, args);
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
	
	/**
	 * Converts a given image to an identical image of given format.
	 * @param sourcePath path to the source image
	 * @param targetFormat target image format
	 * @return path to the target image
	 * @throws IOException when an IO problem occurs
	 * @throws InterruptedException when thread is interrupted
	 * @throws IM4JavaException when an im4java problem occurs
	 */
	private Path convert(final Path sourcePath, final MimeType targetFormat) throws IOException, InterruptedException, IM4JavaException {
		Path result = Files.createTempFile(TMP_FILE_NAME_BASE, targetFormat.getExtension());

		IMOperation op = new IMOperation();
		op.addImage();
		op.addImage();

		Object[] args = new String[] {sourcePath.toString(), result.toString()};
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);
		convertCommand.run(op, args);

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
