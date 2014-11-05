package com.optigra.funnypictures.generator;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.annotation.Resource;

import org.apache.commons.io.input.AutoCloseInputStream;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.optigra.funnypictures.generator.api.AdviceMemeContext;
import com.optigra.funnypictures.generator.api.AdviceMemeGenerator;
import com.optigra.funnypictures.generator.api.GeneratorException;
import com.optigra.funnypictures.generator.api.ImageHandle;
import com.optigra.funnypictures.generator.util.ImageInformationExtractor;
import com.optigra.funnypictures.model.content.MimeType;

/**
 * Generator of advice comics.
 * @author odisseus
 *
 */
public class BaseAdviceMemeGenerator implements AdviceMemeGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(BaseAdviceMemeGenerator.class);
	
	private float captionHeightRatio = 0.2f;

	public static final MimeType DEFAULT_OUTPUT_FORMAT = MimeType.IMAGE_PNG_PNG;

	private final MimeType internalFormat = MimeType.IMAGE_XIMAGEMAGICK_MIFF;
	
	private static final String TMP_FILE_NAME_BASE = "tmp_advice";

	private MimeType outputFormat;

	private ConvertCmd convertCommand = new ConvertCmd();

	@Resource(name = "imageInformationExtractor")
	private ImageInformationExtractor imageInfoExtractor;

	public final ImageInformationExtractor getImageInfoExtractor() {
		return imageInfoExtractor;
	}

	public final void setImageInfoExtractor(
			final ImageInformationExtractor imageInfoExtractor) {
		this.imageInfoExtractor = imageInfoExtractor;
	}

	@Override
	public ImageHandle generate(final AdviceMemeContext context) {

		LOG.debug("Generating an advice meme from context: " + context.toString());

		InputStream templateInputStream = null;
		Path templateInput = null;
		Path workingCopy = null;
		Path result = null;
		try {			
			
			templateInputStream = context.getTemplateInputStream();
			templateInput = toTempFile(templateInputStream, context.getMimeType().getExtension());
			
			workingCopy = convert(templateInput, internalFormat);
			
			Dimension originalDimension = imageInfoExtractor.getImageDimension(workingCopy);
			int captionHeight =  (int) (captionHeightRatio * originalDimension.height);
			
			insertCaption(workingCopy, context.getTopCaption(), originalDimension.width, captionHeight, "North");
			insertCaption(workingCopy, context.getBottomCaption(), originalDimension.width, captionHeight, "South");
			
			result = convert(workingCopy, outputFormat);
			
			InputStream resultStream = new AutoCloseInputStream(new FileInputStream(result.toString()));
			LOG.debug("Advice meme generated");

			return new ImageHandle(resultStream, outputFormat);

		} catch (IOException | IM4JavaException | InterruptedException e) {
			LOG.error("Internal generation procedure failed", e);
			throw new GeneratorException(e.getMessage(), e);
		} finally {
			try {
				if (templateInputStream != null) {
					templateInputStream.close();
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
				LOG.error("Error file file delete", e);
			}
		}
	}

	/**
	 * Inserts a caption into an image.
 	 * @param targetImage path to the target image
	 * @param caption contents of the block
	 * @param width width of caption area
	 * @param height height of caption area
	 * @param gravity the direction the caption should stick to (usually "North" or "South")
	 * @throws IOException when an IO problem occurs
	 * @throws InterruptedException when thread is interrupted
	 * @throws IM4JavaException when an im4java problem occurs
 	 */
	private void insertCaption(final Path targetImage, final String caption, 
			final int width, final int height, final String gravity) 
		throws IOException, InterruptedException, IM4JavaException {
		IMOperation op = new IMOperation();
		
		op.addImage();
		op.size(width, height);
		op.fill("white");
		op.stroke("black");
		op.strokewidth(height / 40 + 1);
		op.background("none");
		op.gravity("Center");
		op.font("Impact-Regular");
		op.addImage("caption:" + caption);
		op.gravity(gravity);
		op.composite();
		op.addImage();

		Object[] args = new String[] {targetImage.toString(), targetImage.toString()};
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);

		convertCommand.run(op, args);
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
